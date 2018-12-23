package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_metronome.*

/**
 * メトロノームは完成したから別のページに移動して
 * ドレミファソラシドの音階を作ってあげるのはどうでしょうか？
 */

class MetronomeActivity : BaseContentActivity() {

    /** AudioTrackオブジェクト */
    private var track: AudioTrack? = null

    /** 1分を表す定数 */
    private val defaultTempo = 60000

    /** BPMを表す数: デフォルトは60bpm */
    private var bpmTempo = 60

    private var isRunning = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metronome)

        // 1秒間にサンプリングする回数
        val sampleRate = 44100.0

        // 音声データ
        val soundData = generated8BitSound(500, 0.125, sampleRate)

        // 新たに作成したインスタンスでplay()をすると初期化がうまくできないのか、
        // IllegalStateExceptionでクラッシュしていたので、フィールド変数をして最初に作成したインスタンスを保持し、
        // 再度play()を行う際はすでに初期化済のインスタンスを使用するようにしました。
        track = createAudioTrack(sampleRate, soundData)

        val handler = Handler()
        val runnable = createRunnable(handler, soundData)

        startButton.setOnClickListener {
            if (isRunning) {
                handler.removeCallbacks(runnable)
            }
            bpmTempo = Integer.parseInt(bpmEdit.text.toString())
            handler.post(runnable)
            isRunning = true
        }

        stopButton.setOnClickListener {
            handler.removeCallbacks(runnable)
            isRunning = false
        }

        backButton.setOnClickListener{
            onClickBackButton()
        }
    }

    /**
     * @param handler   ハンドラー
     * @param soundData 音声データ
     */
    private fun createRunnable(handler: Handler, soundData: ByteArray): Runnable {
        // object : <T> の書き方は匿名クラスをnewする書き方と同じ
        // Javaだと以下のようになる
        // return new Runnable() { @Override public void run(){} }
        return object :Runnable {
            override fun run() {
                handler.removeCallbacks(this)
                startSound(track, soundData)
                handler.postDelayed(this, defaultTempo.toLong() / bpmTempo)
            }
        }
    }

    /**
     * 8bitの音を作成する
     *
     * @param frequency  周波数
     * @param duration   音の長さ
     * @param sampleRate 1秒あたりのサンプル数
     *
     * @return 音声データ
     */
    private fun generated8BitSound(frequency: Int, duration: Double, sampleRate: Double): ByteArray {
        val soundData = ByteArray((sampleRate * duration).toInt())

        for (i in soundData.indices) {
            val sample = (Math.sin(2 * Math.PI * frequency * i / sampleRate) * 255).toByte()
            soundData[i] = sample
        }
        return soundData
    }

    /**
     * AudioTrackのインスタンツを生成する
     * @param sampleRate 1秒あたりのサンプル数
     * @param soundData  音声データ
     * @return AudioTrack
     */
    private fun createAudioTrack(sampleRate: Double, soundData: ByteArray): AudioTrack {
        return AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate.toInt(),
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT,
                soundData.size,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
        )
    }

    /**
     * 作成した音を再生する
     * @param track     AudioTrack?
     * @param soundData 音声データ
     */
    private fun startSound(track: AudioTrack?, soundData: ByteArray) {
        track!!.write(soundData, 0, soundData.size)
        track.play()
    }
}
