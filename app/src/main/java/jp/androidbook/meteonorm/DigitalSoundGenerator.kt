package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack

class DigitalSoundGenerator(sampleRate: Int, bufferSize: Int) {

    val sampleRate: Int
    get() = sampleRate
    val bufferSize: Int
        get() = bufferSize

    public fun createAudioTrack(): AudioTrack {

        // AudioTrackを作成
        val audioTrack = AudioTrack(
                AudioManager.STREAM_MUSIC, // 音楽ストリームを設定
                sampleRate, // サンプルレート
                AudioFormat.CHANNEL_OUT_MONO, // モノラル
                AudioFormat.ENCODING_DEFAULT, // オーディオデータフォーマットPCM16とかPCM8とか
                bufferSize, // バッファ・サイズ
                AudioTrack.MODE_STREAM) // Streamモード。データを書きながら再生する

        return audioTrack
    }

    /**
     * サウンド生成
     * @param frequency 鳴らしたい音の周波数
     * @param soundLengh 音の長さ
     * @return 音声データ
     */
    fun getSound(frequency: Double, soundLength: Double): ByteArray {
        // byteバッファを作成
        val buffer = ByteArray(Math.ceil(bufferSize * soundLength).toInt())
        for (i in buffer.indices) {
            var wave = i / (sampleRate / frequency) * (Math.PI * 2)
            wave = Math.sin(wave)
            buffer[i] = (if (wave > 0.0) java.lang.Byte.MAX_VALUE else java.lang.Byte.MIN_VALUE).toByte()
        }

        return buffer
    }
}