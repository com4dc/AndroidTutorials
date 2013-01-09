package jp.classmethod.komuro.sample.sacspeaking.util;

import android.graphics.Bitmap;
import android.graphics.Matrix;

/**
 * ImageUtilityクラス.
 * @author komuro.hiraku
 *
 */
public class ImageUtil {

	/**
	 * 入力されたBitmapを指定されたサイズにリサイズしてコピー
	 * @param source 元画像のBitmapデータ
	 * @param resizeWidth リサイズ幅
	 * @param resizeHeight リサイズ高さ
	 * @return リサイズ後のBitmapデータ
	 */
	public static Bitmap resizeImage(Bitmap source, int resizeWidth, int resizeHeight) {
		int srcWidth = source.getWidth();
		int srcHeight = source.getHeight();
		
		float widthScale = (float)resizeWidth / (float)srcWidth;
		float heightScale = (float)resizeHeight / (float)srcHeight;
		
		Matrix matrix = new Matrix();
		if(widthScale > heightScale) {
			matrix.postScale(heightScale, widthScale);
		} else {
			matrix.postScale(widthScale, heightScale);
		}
		
		Bitmap dst = Bitmap.createBitmap(source, 0, 0, srcWidth, srcHeight, matrix, true);
		
		// 元ソースを掃除
		source.recycle();
		return dst;
	}

}
