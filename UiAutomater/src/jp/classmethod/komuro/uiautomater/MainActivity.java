package jp.classmethod.komuro.uiautomater;

import jp.classmethod.komuro.uiautomater.view.FaceDetectTextureView;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;

public class MainActivity extends Activity {
	
	/** カメラのプレビューを表示するTextureViewオブジェクト. */ 
	private FaceDetectTextureView textureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		// TextureViewをインスタンス化
		textureView = new FaceDetectTextureView(this);
		// TextureViewを配置
		setContentView(textureView);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
