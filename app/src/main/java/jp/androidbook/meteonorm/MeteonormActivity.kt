package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import kotlinx.android.synthetic.main.activity_meteonorm.*

class MeteonormActivity : AppCompatActivity() {

    val sampleRate = 44100.0 // Feel free to change this

    var track: AudioTrack? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteonorm)

        // ここでAudioTrackの変数をメンバとして持っておかないとplay()でクラッシュしてしまう
        // なぜか新たに作成したインスタンスを入れるとuninitializeって出てくるから連打に対応できない。。。
        val soundData = generated8BitSound(440, 0.125)
        track = createAudioTrack(sampleRate, soundData)
        startButton.setOnClickListener {
            startSound(track, soundData)
        }
        stopButton.setOnClickListener {
            // メトロノームストップ
        }
    }

    private fun generated8BitSound(frequency: Int, duration: Double): ByteArray {
        val sampleRate = 44100.0 // Feel free to change this
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
                AudioFormat.ENCODING_PCM_8BIT, soundData.size,
                AudioTrack.MODE_STREAM
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
