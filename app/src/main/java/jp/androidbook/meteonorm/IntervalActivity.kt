package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import jp.androidbook.meteonorm.data.MusicalScale
import kotlinx.android.synthetic.main.activity_interval.*
import kotlin.math.sin

class IntervalActivity : BaseContentActivity() {

    /** ド4 */
    private var c4Track: AudioTrack? = null
    /** ド#4 */
    private var cs4Track: AudioTrack? = null
    /** レ4 */
    private var d4Track: AudioTrack? = null
    /** レ#4 */
    private var ds4Track: AudioTrack? = null
    /** ミ4 */
    private var e4Track: AudioTrack? = null
    /** ファ4 */
    private var f4Track: AudioTrack? = null
    /** ファ#4 */
    private var fs4Track: AudioTrack? = null
    /** ソ4 */
    private var g4Track: AudioTrack? = null
    /** ソ#4 */
    private var gs4Track: AudioTrack? = null
    /** ラ4 */
    private var a4Track: AudioTrack? = null
    /** ラ#4 */
    private var as4Track: AudioTrack? = null
    /** シ4 */
    private var b4Track: AudioTrack? = null
    /** ド4 */
    private var c5Track: AudioTrack? = null

    private var soundDataC: ByteArray? = null
    private var soundDataCS: ByteArray? = null
    private var soundDataD: ByteArray? = null
    private var soundDataDS: ByteArray? = null
    private var soundDataE: ByteArray? = null
    private var soundDataF: ByteArray? = null
    private var soundDataFS: ByteArray? = null
    private var soundDataG: ByteArray? = null
    private var soundDataGS: ByteArray? = null
    private var soundDataA: ByteArray? = null
    private var soundDataAS: ByteArray? = null
    private var soundDataB: ByteArray? = null
    private var soundDataCC: ByteArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval)
        initAudioTrack()
        backButton.setOnClickListener {
            onClickBackButton()
        }

        cButton.setOnClickListener {
            startSound(c4Track, soundDataC)
        }

        csButton.setOnClickListener {
            startSound(cs4Track, soundDataCS)
        }

        dButton.setOnClickListener {
            startSound(d4Track, soundDataD)
        }

        dsButton.setOnClickListener {
            startSound(ds4Track, soundDataDS)
        }

        eButton.setOnClickListener {
            startSound(e4Track, soundDataE)
        }

        fButton.setOnClickListener {
            startSound(f4Track, soundDataF)
        }

        fsButton.setOnClickListener {
            startSound(fs4Track, soundDataFS)
        }

        gButton.setOnClickListener {
            startSound(g4Track, soundDataG)
        }

        gsButton.setOnClickListener {
            startSound(gs4Track, soundDataGS)
        }

        aButton.setOnClickListener {
            startSound(a4Track, soundDataA)
        }

        asButton.setOnClickListener {
            startSound(as4Track, soundDataAS)
        }

        bButton.setOnClickListener {
            startSound(b4Track, soundDataB)
        }

        ccButton.setOnClickListener {
            startSound(c5Track, soundDataCC)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        c4Track?.release()
        cs4Track?.release()
        d4Track?.release()
        ds4Track?.release()
        e4Track?.release()
        f4Track?.release()
        fs4Track?.release()
        g4Track?.release()
        gs4Track?.release()
        a4Track?.release()
        as4Track?.release()
        b4Track?.release()
        c5Track?.release()
    }

    private fun initAudioTrack() {
        soundDataC = generate8BitSoundData(MusicalScale.C4.frequency)
        soundDataCS = generate8BitSoundData(MusicalScale.CS4.frequency)
        soundDataD = generate8BitSoundData(MusicalScale.D4.frequency)
        soundDataDS = generate8BitSoundData(MusicalScale.DS4.frequency)
        soundDataE = generate8BitSoundData(MusicalScale.E4.frequency)
        soundDataF = generate8BitSoundData(MusicalScale.F4.frequency)
        soundDataFS = generate8BitSoundData(MusicalScale.FS4.frequency)
        soundDataG = generate8BitSoundData(MusicalScale.G4.frequency)
        soundDataGS = generate8BitSoundData(MusicalScale.GS4.frequency)
        soundDataA = generate8BitSoundData(MusicalScale.A4.frequency)
        soundDataAS = generate8BitSoundData(MusicalScale.AS4.frequency)
        soundDataB = generate8BitSoundData(MusicalScale.B4.frequency)
        soundDataCC = generate8BitSoundData(MusicalScale.C5.frequency)

        // 新たに作成したインスタンスでplay()をすると初期化がうまくできないのか、
        // IllegalStateExceptionでクラッシュしていたので、フィールド変数をして最初に作成したインスタンスを保持し、
        // 再度play()を行う際はすでに初期化済のインスタンスを使用するようにしました。
        c4Track = createAudioTrack(soundDataC)
        cs4Track = createAudioTrack(soundDataCS)
        d4Track = createAudioTrack(soundDataD)
        ds4Track = createAudioTrack(soundDataDS)
        e4Track = createAudioTrack(soundDataE)
        f4Track = createAudioTrack(soundDataF)
        fs4Track = createAudioTrack(soundDataFS)
        g4Track = createAudioTrack(soundDataG)
        gs4Track = createAudioTrack(soundDataGS)
        a4Track = createAudioTrack(soundDataA)
        as4Track = createAudioTrack(soundDataAS)
        b4Track = createAudioTrack(soundDataB)
        c5Track = createAudioTrack(soundDataCC)
    }

    //各音源を生成する
    private fun generate8BitSoundData(frequency: Double): ByteArray {
        val soundData = ByteArray((SAMPLING_RATE * DURATION).toInt())

        for (i in soundData.indices) {
            val sample = (sin(2 * Math.PI * frequency * i / SAMPLING_RATE) * 255).toInt().toByte()
            soundData[i] = sample
        }
        return soundData
    }

    @Suppress("DEPRECATION")
    private fun createAudioTrack(soundData: ByteArray?): AudioTrack {
        return AudioTrack(
                AudioManager.STREAM_MUSIC,
                SAMPLING_RATE.toInt(),
                AudioFormat.CHANNEL_OUT_DEFAULT,
                AudioFormat.ENCODING_PCM_8BIT,
                soundData!!.size,
                AudioTrack.MODE_STREAM,
                AudioManager.AUDIO_SESSION_ID_GENERATE
        )
    }

    private fun startSound(track: AudioTrack?, soundData: ByteArray?) {
        soundData?.let {
            track?.write(it, 0, it.size)
            track?.play()
        }
    }

    companion object {
        // 1秒間にサンプリングする回数
        private const val SAMPLING_RATE = 44100.0
        // 音の長さ
        private const val DURATION = 0.125
    }
}