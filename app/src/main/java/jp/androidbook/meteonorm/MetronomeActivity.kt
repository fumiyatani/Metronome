package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.os.Handler
import kotlinx.android.synthetic.main.activity_metronome.*
import kotlin.math.sin


class MetronomeActivity : BaseContentActivity() {

    /** AudioTrackオブジェクト */
    private var track: AudioTrack? = null
    private var soundData: ByteArray? = null

    /** 1分を表す定数 */
    private val defaultTempo = 60000
    // 1秒間にサンプリングする回数
    private val sampleRate = 44100.0
    private val defaultFrequency = 500
    // 音の長さ
    private val defaultDuration = 0.125

    /** BPMを表す数: デフォルトは60bpm */
    private var bpmTempo = 60

    private var isRunning = false

    val handler = Handler()

    private var runnable: Runnable? = object :Runnable {
        override fun run() {
            handler.removeCallbacks(this)
            startSound(track, soundData)
            handler.postDelayed(this, defaultTempo.toLong() / bpmTempo)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_metronome)
        // 音声データ
        soundData = generated8BitSound()

        // 新たに作成したインスタンスでplay()をすると初期化がうまくできないのか、
        // IllegalStateExceptionでクラッシュしていたので、フィールド変数をして最初に作成したインスタンスを保持し、
        // 再度play()を行う際はすでに初期化済のインスタンスを使用するようにしました。
        track = createAudioTrack()

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



    override fun onDestroy() {
        super.onDestroy()
        handler.removeCallbacks(runnable)
        track?.release()
    }

    /**
     * 8bitの音を作成する
     * @return 音声データ
     */
    private fun generated8BitSound(): ByteArray {
        val soundData = ByteArray((sampleRate * defaultDuration).toInt())

        for (i in soundData.indices) {
            val sample = (sin(2 * Math.PI * defaultFrequency * i / sampleRate) * 255).toByte()
            soundData[i] = sample
        }
        return soundData
    }

    /**
     * AudioTrackのインスタンツを生成する
     * @return AudioTrack
     */
    @Suppress("DEPRECATION")
    private fun createAudioTrack(): AudioTrack {
        return AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate.toInt(),
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT,
                soundData!!.size,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
        )
    }

    /**
     * 作成した音を再生する
     * @param track     AudioTrack?
     * @param soundData 音声データ
     */
    private fun startSound(track: AudioTrack?, soundData: ByteArray?) {
        soundData?.let {
            track?.write(it, 0, it.size)
            track?.play()
        }

    }
}
