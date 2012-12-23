package jp.classmethod.komuro.soundgenerator;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioTrack;

/**
 * ドレミファソラシドの音階を作成する
 * 
 * @author komuro
 * 
 */
public class DigitalSoundGenerator {

	// とりあえず１オクターブ分の音階を確保
	public static final double FREQ_C = 261.625565;
	public static final double FREQ_Cs = 277.182630;
	public static final double FREQ_D = 293.664767;
	public static final double FREQ_Ds = 311.126983;
	public static final double FREQ_E = 329.627556;
	public static final double FREQ_F = 349.228231;
	public static final double FREQ_Fs = 369.994227;
	public static final double FREQ_G = 391.994535;
	public static final double FREQ_Gs = 415.304697;
	public static final double FREQ_A = 440.0;
	public static final double FREQ_As = 466.163761;
	public static final double FREQ_B = 493.883301;

	private AudioTrack audioTrack;

	private int sampleRate;

	private int bufferSize;

	/**
	 * コンストラクタ
	 */
	public DigitalSoundGenerator(int sampleRate, int bufferSize) {
		this.sampleRate = sampleRate;
		this.bufferSize = bufferSize;

		// AudioTrackを作成
		this.audioTrack = new AudioTrack(AudioManager.STREAM_MUSIC, sampleRate,
				AudioFormat.CHANNEL_OUT_MONO,
				AudioFormat.ENCODING_DEFAULT, bufferSize,
				AudioTrack.MODE_STREAM);
	}
	
	/**
	 * 
	 * @param frequency 鳴らしたい音の周波数
	 * @return
	 */
	public byte[] get8BitSound(double frequency, int soundLengh) {
		// Bufferを作成
		byte[] buff = new byte[bufferSize * soundLengh];
		for(int i=0; i<buff.length; i++) {
			double wave = i / (this.sampleRate / frequency);
			buff[i] = (byte)(Math.round(wave) % 2 == 0 ? Byte.MAX_VALUE : Byte.MIN_VALUE );
		}
		
		return buff;
	}
	
	/**
	 * 
	 * @return
	 */
	public AudioTrack getAudioTrack() {
		return this.audioTrack;
	}

}
