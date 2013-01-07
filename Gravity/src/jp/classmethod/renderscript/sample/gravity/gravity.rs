#pragma version(1)
#pragma rs java_package_name(jp.classmethod.renderscript.sample)
#pragma stateFragment(parent)

#include "rs_graphics.rsh"

// static変数はエントリーポイントが生成されない（？）
static int initialized = 0;

rs_mesh partMesh; // 描画するデータ

float gTouchX = 0.f; // X座標
float gTouchY = 0.f; // Y座標

// パーティクルの構造体
typedef struct __attribute__((packed, aligned(4))) Point {
    float2 delta;
    float2 position;
    uchar4 color;
} Point_t;
Point_t *point;

// パーティクルの初期化
void initParticles()
{
    // データサイズを取得
    int size = rsAllocationGetDimX(rsGetAllocation(point));

    float width = rsgGetWidth();   // サーフェイスの幅
    float height = rsgGetHeight(); // サーフェイスの高さ

    uchar4 c = rsPackColorTo8888(0.9f, 0.0f, 0.9f); // 色

    Point_t *p = point;
    for (int i = 0; i < size; i++) {
        p->position.x = rsRand(width);  // 乱数でX座標をセット
        p->position.y = rsRand(height); // 乱数でY座標をセット
        p->delta.x = 0;
        p->delta.y = 0;
        p->color = c;
        p++;
    }
    initialized = 1; // 初期化完了
}

/*
 * root()は描画する毎に呼ばれる
 */
int root() {
    // 初回実行時に初期化
    if (!initialized) {
        initParticles();
    }

    float width = rsgGetWidth(); 
    float height = rsgGetHeight();

    // 指定色でレンダリングサーフェイスをクリア
    rsgClearColor(0.f, 0.f, 0.f, 1.f);
    
    int size = rsAllocationGetDimX(rsGetAllocation(point));

    Point_t *p = point;
    for (int i = 0; i < size; i++) {
        float diff_x = gTouchX - p->position.x;
        float diff_y = gTouchY - p->position.y;
        float acc = 50.f/(diff_x * diff_x + diff_y * diff_y);
        float acc_x = acc * diff_x;
        float acc_y = acc * diff_y;
        p->delta.x += acc_x;
        p->delta.y += acc_y;
        p->position.x += p->delta.x;
        p->position.y += p->delta.y;
        p->delta.x *= 0.96;
        p->delta.y *= 0.96;
        if (p->position.x > width) {
            p->position.x = 0;
        } else if (p->position.x < 0) {
            p->position.x = width;
        }
        if (p->position.y > height) {
            p->position.y = 0;
        } else if (p->position.y < 0) {
            p->position.y = height;
        }
        p++;
    }    
 
    rsgDrawMesh(partMesh); // 座標データ列の描画
    
    return 1; // おおよそ1ms後に再描画
}
