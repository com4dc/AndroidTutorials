package jp.classmethod.android.sample.textureviewsample;

import java.io.IOException;

import android.app.Activity;
import android.graphics.SurfaceTexture;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.Menu;
import android.view.Surface;
import android.view.TextureView;
import android.view.TextureView.SurfaceTextureListener;

public class MainActivity extends Activity implements SurfaceTextureListener {
	
	
	private MediaPlayer player;
	private TextureView textureView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		textureView = new TextureView(this);
		textureView.setSurfaceTextureListener(this);
		setContentView(textureView);
		
//		setContentView(R.layout.activity_main);
//		findViewById(R.id.button1).setOnClickListener(new OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				Intent intent = new Intent();
//				intent.setClass(getApplicationContext(), TextureListActivity.class);
//				startActivity(intent);
//			}
//		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

	@Override
	public void onSurfaceTextureAvailable(SurfaceTexture surface, int width,
			int height) {
		Surface embedSurface = new Surface(surface);
		try {
			player = new MediaPlayer();
//			player.setDataSource("http://www.youtube.com/watch?v=UhF4F7VpinE");
			player.setDataSource("http://daily3gp.com/vids/747.3gp");
			player.setSurface(embedSurface);
			player.prepare();
			player.setAudioStreamType(AudioManager.STREAM_MUSIC);
			player.start();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalStateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public boolean onSurfaceTextureDestroyed(SurfaceTexture surface) {
		return false;
	}

	@Override
	public void onSurfaceTextureSizeChanged(SurfaceTexture surface, int width,
			int height) {
		
	}

	@Override
	public void onSurfaceTextureUpdated(SurfaceTexture surface) {
		
	}

}
