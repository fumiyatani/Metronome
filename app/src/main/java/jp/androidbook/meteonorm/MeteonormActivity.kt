package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_meteonorm.*





class MeteonormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_meteonorm)
        startButton.setOnClickListener {
            generated8BitSound(440, 0.125)
        }
        stopButton.setOnClickListener {
            // メトロノームストップ
        }
    }

    private fun generated8BitSound(frequency: Int, duration: Double) {
        val sampleRate = 44100.0 // Feel free to change this
        val soundData = ByteArray((sampleRate * duration).toInt())

        for (i in soundData.indices) {
            val sample = (Math.sin(2 * Math.PI * frequency * i / sampleRate) * 255).toByte()
            soundData[i] = sample
        }

        val track = createAudioTrack(sampleRate, soundData)

        track.write(soundData, 0, soundData.size)
        track.play()
    }

    private fun createAudioTrack(sampleRate: Double, soundData: ByteArray): AudioTrack {
        val track = AudioTrack(AudioManager.STREAM_MUSIC,
                sampleRate.toInt(),
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT, soundData.size,
                AudioTrack.MODE_STATIC
        )
        return track
    }
}
