package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_meteonorm.*

/**
 * 本当はメトロノームを作る予定だったんだよ
 * 314BPMのメトロノームがないから。
 */

class MeteonormActivity : AppCompatActivity() {

    var track: AudioTrack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteonorm)

        val sampleRate = 44100.0 // Feel free to change this

        // 音階データの作成(ドレミ、今回はラ)
        val soundData = generated8BitSound(990, 0.125, sampleRate)

        // 新たに作成したインスタンスでplay()をすると初期化がうまくできないのか、
        // IllegalStateExceptionでクラッシュしていたので、フィールド変数をして最初に作成したインスタンスを保持し、
        // 再度play()を行う際はすでに初期化済のインスタンスを使用するようにしました。
        track = createAudioTrack(sampleRate, soundData)
        startButton.setOnClickListener {
            startSound(track, soundData)
        }
        stopButton.setOnClickListener {
        }
    }

    private fun generated8BitSound(frequency: Int, duration: Double, sampleRate: Double): ByteArray {
        val soundData = ByteArray((sampleRate * duration).toInt())

        for (i in soundData.indices) {
            val sample = (Math.sin(2 * Math.PI * frequency * i / sampleRate) * 255).toByte()
            soundData[i] = sample
        }
        return soundData
    }

    private fun createAudioTrack(sampleRate: Double, soundData: ByteArray): AudioTrack {
        val track = AudioTrack(
                AudioManager.STREAM_MUSIC,
                sampleRate.toInt(),
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT,
                soundData.size,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
        )
        return track
    }

    private fun startSound(track: AudioTrack?, soundData: ByteArray) {
        try {
            track!!.write(soundData, 0, soundData.size)
            track.play()
        } catch (e: IllegalStateException) {
            Log.d(this.localClassName, "tanilog : startSound IllegalStateException")
        }

    }
}
