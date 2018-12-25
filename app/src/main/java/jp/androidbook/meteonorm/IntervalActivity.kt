package jp.androidbook.meteonorm

import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_interval.*

/**
 * TODO: このままだとこのクラスがきもいから、SoundCreateクラスのManagerクラスを作る
 */

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

    var soundDataC: ByteArray? = null
    var soundDataCS: ByteArray? = null

    var soundDataD: ByteArray? = null
    var soundDataDS: ByteArray? = null

    var soundDataE: ByteArray? = null

    var soundDataF: ByteArray? = null
    var soundDataFS: ByteArray? = null

    var soundDataG: ByteArray? = null
    var soundDataGS: ByteArray? = null

    var soundDataA: ByteArray? = null
    var soundDataAS: ByteArray? = null

    var soundDataB: ByteArray? = null

    var soundDataCC: ByteArray? = null


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

    private fun setClickListenerToButton() {

    }

    private fun initAudioTrack() {
        // 1秒間にサンプリングする回数
        val sampleRate = 44100.0
        // 音の長さ
        val duration = 0.125

        soundDataC = generate8BitSoundData(frequencyC, duration, sampleRate)
        soundDataCS = generate8BitSoundData(frequencyCS, duration, sampleRate)

        soundDataD = generate8BitSoundData(frequencyD, duration, sampleRate)
        soundDataDS = generate8BitSoundData(frequencyDS, duration, sampleRate)

        soundDataE = generate8BitSoundData(frequencyE, duration, sampleRate)

        soundDataF = generate8BitSoundData(frequencyF, duration, sampleRate)
        soundDataFS = generate8BitSoundData(frequencyFS, duration, sampleRate)

        soundDataG = generate8BitSoundData(frequencyG, duration, sampleRate)
        soundDataGS = generate8BitSoundData(frequencyGS, duration, sampleRate)

        soundDataA = generate8BitSoundData(frequencyA, duration, sampleRate)
        soundDataAS = generate8BitSoundData(frequencyAS, duration, sampleRate)

        soundDataB = generate8BitSoundData(frequencyB, duration, sampleRate)

        soundDataCC = generate8BitSoundData(frequencyCC, duration, sampleRate)


        // 新たに作成したインスタンスでplay()をすると初期化がうまくできないのか、
        // IllegalStateExceptionでクラッシュしていたので、フィールド変数をして最初に作成したインスタンスを保持し、
        // 再度play()を行う際はすでに初期化済のインスタンスを使用するようにしました。
        cTrack = createAudioTrack(sampleRate, soundDataC)
        csTrack = createAudioTrack(sampleRate, soundDataCS)

        dTrack = createAudioTrack(sampleRate, soundDataD)
        dsTrack = createAudioTrack(sampleRate, soundDataDS)

        eTrack = createAudioTrack(sampleRate, soundDataE)

        fTrack = createAudioTrack(sampleRate, soundDataF)
        fsTrack = createAudioTrack(sampleRate, soundDataFS)

        gTrack = createAudioTrack(sampleRate, soundDataG)
        gsTrack = createAudioTrack(sampleRate, soundDataGS)

        aTrack = createAudioTrack(sampleRate, soundDataA)
        asTrack = createAudioTrack(sampleRate, soundDataAS)

        bTrack = createAudioTrack(sampleRate, soundDataB)

        ccTrack = createAudioTrack(sampleRate, soundDataCC)
    }

    //各音源を生成する
    private fun generate8BitSoundData(frequency: Double, duration: Double, sampleRate: Double): ByteArray {
        val soundData = ByteArray((sampleRate * duration).toInt())

        for (i in soundData.indices) {
            val sample = (Math.sin(2 * Math.PI * frequency * i / sampleRate) * 255).toByte()
            soundData[i] = sample
        }
        return soundData
    }

    private fun createAudioTrack(sampleRate: Double, soundData: ByteArray?): AudioTrack {
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
        track!!.write(soundData, 0, soundData!!.size)
        track.play()
    }
}