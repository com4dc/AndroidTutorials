package jp.classmethod.komuro.soundgenerator;

import android.app.Activity;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity {
	
	private DigitalSoundGenerator audioSignal;
	private AudioTrack audioTrack;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		audioSignal = new DigitalSoundGenerator(44100, 44100);
		
		audioTrack = audioSignal.getAudioTrack();
		
		final byte[] c5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_C, 2);
		final byte[] d5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_D, 1);
		final byte[] e5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_E, 3);
		final byte[] f5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_F, 1);
		final byte[] g5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_G, 1);
		final byte[] a5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_A, 3);
		final byte[] b5 = audioSignal.get8BitSound(DigitalSoundGenerator.FREQ_B, 2);
		
		findViewById(R.id.startMelody).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// start sound
				
				// 再生中なら一旦止める
				if(audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
					audioTrack.stop();
					audioTrack.reloadStaticData();
				}
				
				audioTrack.play();
				
				audioTrack.write(e5, 0, e5.length);
				audioTrack.write(e5, 0, e5.length);

				audioTrack.write(g5, 0, g5.length);
				audioTrack.write(c5, 0, c5.length);
				audioTrack.write(d5, 0, d5.length);
				audioTrack.write(e5, 0, e5.length);
				
				
//				audioTrack.write(c5, 0, c5.length);
//				audioTrack.write(c5, 0, c5.length);
//				
//				audioTrack.write(e5, 0, e5.length);
//				audioTrack.write(g5, 0, g5.length);
//				audioTrack.write(a5, 0, a5.length);
//				audioTrack.write(b5, 0, b5.length);

//				audioTrack.play();
			}
		});
	}
	
	

	@Override
	protected void onPause() {
		super.onPause();
		
		if(audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
			audioTrack.stop();
			audioTrack.release();
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.activity_main, menu);
		return true;
	}

}
