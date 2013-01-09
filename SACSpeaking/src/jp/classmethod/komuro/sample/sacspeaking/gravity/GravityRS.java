package jp.classmethod.komuro.sample.sacspeaking.gravity;

import jp.classmethod.komuro.sample.sacspeaking.R;
import jp.classmethod.renderscript.sample.ScriptC_gravity;
import jp.classmethod.renderscript.sample.ScriptField_Point;
import android.content.res.Resources;
import android.renderscript.Mesh;
import android.renderscript.ProgramFragmentFixedFunction;
import android.renderscript.RenderScriptGL;

public class GravityRS {

	public static final int PART_COUNT = 50000; // 総パーティクル数

    public GravityRS() {
    }

    private Resources mRes;
    private RenderScriptGL mRS;      // レンダリングのコンテクスト
    private ScriptC_gravity mScript; // スクリプトファイルへの
                                     // エントリークラス

    // 初期化関数
    public void init(RenderScriptGL rs, Resources res,
                     int width, int height) {
        mRS = rs;
        mRes = res;

        // GLSLコードを用いないシンプルなフラグメントシェーダーを作成
        ProgramFragmentFixedFunction.Builder pfb
            = new ProgramFragmentFixedFunction.Builder(rs);

        // バーテックスプログラムから指定された色を使用する
        pfb.setVaryingColor(true);

        // フラグメントシェーダーをレンダリングコンテクストにバインド
        rs.bindProgramFragment(pfb.create());
        
        // スクリプト内のPoint構造体×パーティクル数の分だけ
        // メモリー領域を確保する
        ScriptField_Point points = new ScriptField_Point(mRS,
                                                         PART_COUNT);
        
        // RenderScriptで表示する幾何学データのコンテナを作成する
        // オブジェクト
        Mesh.AllocationBuilder smb = new Mesh.AllocationBuilder(mRS);

        // pointsを頂点データとしてオブジェクトに追加
        smb.addVertexAllocation(points.getAllocation());

        // データの種類を点座標に設定
        smb.addIndexSetType(Mesh.Primitive.POINT);

        // コンテナを作成
        Mesh sm = smb.create();

        // スクリプトファイルのエントリークラスを生成
        mScript = new ScriptC_gravity(mRS, mRes, R.raw.gravity);

        // スクリプト内のpartMeshにMeshをセット
        mScript.set_partMesh(sm);

        // pointsとスクリプト内のpointをバインド
        mScript.bind_point(points);

        // レンダリングコンテクストにスクリプトをバインド
        mRS.bindRootScript(mScript); 
    }

    // タッチ情報のアップデート
    public void newTouchPosition(float x, float y, float pressure,
                                 int id) {
        mScript.set_gTouchX(x); // スクリプト内のgTouchXにX座標をセット
        mScript.set_gTouchY(y); // スクリプト内のgTouchYにY座標をセット
    }
}