package jp.classmethod.komuro.soundgenerator;

import java.util.ArrayList;
import java.util.List;

import jp.classmethod.komuro.soundgenerator.sound.SoundDto;

import android.app.Activity;
import android.media.AudioTrack;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;

public class MainActivity extends Activity implements Runnable{
	public static final double EIGHTH_NOTE = 0.125;
	public static final double FORTH_NOTE = 0.25;
	public static final double HALF_NOTE = 0.5;
	public static final double WHOLE_NOTE = 1.0;
	
	// Sound生成クラス
	private DigitalSoundGenerator soundGenerator;
	// Sound再生クラス
	private AudioTrack audioTrack;
	// 譜面データ
	private List<SoundDto> soundList = new ArrayList<SoundDto>();
	
	/**
	 * 譜面データを作成
	 */
	private void initScoreData() {
		// 譜面データ作成
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, EIGHTH_NOTE), EIGHTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, EIGHTH_NOTE), EIGHTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_G, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_C, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_D, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, FORTH_NOTE), FORTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, FORTH_NOTE), FORTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_D, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_D, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_D, WHOLE_NOTE), WHOLE_NOTE));
	
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_G, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, HALF_NOTE), HALF_NOTE));
		
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, EIGHTH_NOTE), EIGHTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, EIGHTH_NOTE), EIGHTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_G, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_C, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_D, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, 3), 3));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, FORTH_NOTE), FORTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, WHOLE_NOTE), WHOLE_NOTE));
		soundList.add(new SoundDto(generateEmptySound(soundGenerator, EIGHTH_NOTE), EIGHTH_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, EIGHTH_NOTE), EIGHTH_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, EIGHTH_NOTE), EIGHTH_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_E, WHOLE_NOTE), WHOLE_NOTE));
		
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_G, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_G, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_F, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_D, HALF_NOTE), HALF_NOTE));
		soundList.add(new SoundDto(generateSound(soundGenerator, DigitalSoundGenerator.FREQ_C, WHOLE_NOTE), WHOLE_NOTE));
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// SoundGeneratorクラスをサンプルレート44100で作成
		soundGenerator = new DigitalSoundGenerator(44100, 44100);
		
		// 再生用AudioTrackは、同じサンプルレートで初期化したものを利用する
		audioTrack = soundGenerator.getAudioTrack();
		findViewById(R.id.startMelody).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// start sound
				Thread th = new Thread(MainActivity.this);
				th.start();
			}
		});
		
		// スコアデータを作成
		initScoreData();		
	}

	@Override
	protected void onPause() {
		super.onPause();
	}
	
	

	@Override
	protected void onDestroy() {
		super.onDestroy();
		
		// 再生中だったら停止してリリース
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
	
	/**
	 * ８ビットのピコピコ音を生成する.
	 * @param gen Generator
	 * @param freq 周波数(音階)
	 * @param length 音の長さ
	 * @return 音データ
	 */
	public byte[] generateSound(DigitalSoundGenerator gen, double freq, double length) {
		return gen.get8BitSound(freq, length);
	}
	
	/**
	 * 無音データを作成する
	 * @param gen Generator
	 * @param length 無音データの長さ
	 * @return 無音データ
	 */
	public byte[] generateEmptySound(DigitalSoundGenerator gen, double length) {
		return gen.getEmptySound(length);
	}

	@Override
	public void run() {
		
		// 再生中なら一旦止める
		if(audioTrack.getPlayState() == AudioTrack.PLAYSTATE_PLAYING) {
			audioTrack.stop();
			audioTrack.reloadStaticData();
		}
		
		audioTrack.play();
	
		for(SoundDto dto : soundList) {
			audioTrack.write(dto.getSound(), 0, dto.getSound().length);
		}
	}

}
