package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_interval.*
import kotlin.math.sin

class IntervalActivity : BaseContentActivity() {

    /** ド 低音 */
    private var cTrack: AudioTrack? = null
    /** ド シャープ */
    private var csTrack: AudioTrack? = null
    /** レ */
    private var dTrack: AudioTrack? = null
    /** レ シャープ */
    private var dsTrack: AudioTrack? = null
    /** ミ */
    private var eTrack: AudioTrack? = null
    /** ファ */
    private var fTrack: AudioTrack? = null
    /** ファ シャープ */
    private var fsTrack: AudioTrack? = null
    /** ソ */
    private var gTrack: AudioTrack? = null
    /** ソ シャープ */
    private var gsTrack: AudioTrack? = null
    /** ラ */
    private var aTrack: AudioTrack? = null
    /** ラ シャープ */
    private var asTrack: AudioTrack? = null
    /** シ */
    private var bTrack: AudioTrack? = null
    /** ド 高音 */
    private var ccTrack: AudioTrack? = null

    // 1秒間にサンプリングする回数
    private val sampleRate = 44100.0
    // 音の長さ
    private val duration = 0.125

    /**  */
    private val frequencyC = 261.63
    /**  */
    private val frequencyCS = 277.18
    /**  */
    private val frequencyD = 293.66
    /**  */
    private val frequencyDS = 311.13
    /**  */
    private val frequencyE = 329.63
    /**  */
    private val frequencyF = 349.23
    /**  */
    private val frequencyFS = 369.99
    /**  */
    private val frequencyG = 392.00
    /**  */
    private val frequencyGS = 415.30
    /**  */
    private val frequencyA = 440.00
    /**  */
    private val frequencyAS = 466.16
    /**  */
    private val frequencyB = 493.88
    /**  */
    private val frequencyCC = 523.25

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
            startSound(cTrack, soundDataC)
        }

        csButton.setOnClickListener {
            startSound(csTrack, soundDataCS)
        }

        dButton.setOnClickListener {
            startSound(dTrack, soundDataD)
        }

        dsButton.setOnClickListener {
            startSound(dsTrack, soundDataDS)
        }

        eButton.setOnClickListener {
            startSound(eTrack, soundDataE)
        }

        fButton.setOnClickListener {
            startSound(fTrack, soundDataF)
        }

        fsButton.setOnClickListener {
            startSound(fsTrack, soundDataFS)
        }

        gButton.setOnClickListener {
            startSound(gTrack, soundDataG)
        }

        gsButton.setOnClickListener {
            startSound(gsTrack, soundDataGS)
        }

        aButton.setOnClickListener {
            startSound(aTrack, soundDataA)
        }

        asButton.setOnClickListener {
            startSound(asTrack, soundDataAS)
        }

        bButton.setOnClickListener {
            startSound(bTrack, soundDataB)
        }

        ccButton.setOnClickListener {
            startSound(ccTrack, soundDataCC)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        cTrack?.release()
        csTrack?.release()
        dTrack?.release()
        dsTrack?.release()
        eTrack?.release()
        fTrack?.release()
        fsTrack?.release()
        gTrack?.release()
        gsTrack?.release()
        aTrack?.release()
        asTrack?.release()
        bTrack?.release()
        ccTrack?.release()
    }

    private fun initAudioTrack() {
        soundDataC = generate8BitSoundData(frequencyC)
        soundDataCS = generate8BitSoundData(frequencyCS)

        soundDataD = generate8BitSoundData(frequencyD)
        soundDataDS = generate8BitSoundData(frequencyDS)

        soundDataE = generate8BitSoundData(frequencyE)

        soundDataF = generate8BitSoundData(frequencyF)
        soundDataFS = generate8BitSoundData(frequencyFS)

        soundDataG = generate8BitSoundData(frequencyG)
        soundDataGS = generate8BitSoundData(frequencyGS)

        soundDataA = generate8BitSoundData(frequencyA)
        soundDataAS = generate8BitSoundData(frequencyAS)

        soundDataB = generate8BitSoundData(frequencyB)

        soundDataCC = generate8BitSoundData(frequencyCC)


        // 新たに作成したインスタンスでplay()をすると初期化がうまくできないのか、
        // IllegalStateExceptionでクラッシュしていたので、フィールド変数をして最初に作成したインスタンスを保持し、
        // 再度play()を行う際はすでに初期化済のインスタンスを使用するようにしました。
        cTrack = createAudioTrack(soundDataC)
        csTrack = createAudioTrack(soundDataCS)

        dTrack = createAudioTrack(soundDataD)
        dsTrack = createAudioTrack(soundDataDS)

        eTrack = createAudioTrack(soundDataE)

        fTrack = createAudioTrack(soundDataF)
        fsTrack = createAudioTrack(soundDataFS)

        gTrack = createAudioTrack(soundDataG)
        gsTrack = createAudioTrack(soundDataGS)

        aTrack = createAudioTrack(soundDataA)
        asTrack = createAudioTrack(soundDataAS)

        bTrack = createAudioTrack(soundDataB)

        ccTrack = createAudioTrack(soundDataCC)
    }

    //各音源を生成する
    private fun generate8BitSoundData(frequency: Double): ByteArray {
        val soundData = ByteArray((sampleRate * duration).toInt())

        for (i in soundData.indices) {
            val sample = (sin(2 * Math.PI * frequency * i / sampleRate) * 255).toByte()
            soundData[i] = sample
        }
        return soundData
    }

    @Suppress("DEPRECATION")
    private fun createAudioTrack(soundData: ByteArray?): AudioTrack {
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

    private fun startSound(track: AudioTrack?, soundData: ByteArray?) {
        soundData?.let {
            track?.write(it, 0, it.size)
            track?.play()
        }
    }
}