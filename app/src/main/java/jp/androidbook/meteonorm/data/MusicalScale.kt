package jp.androidbook.meteonorm.data

/**
 * 音階
 *
 * @param frequency 音階の周波数
 */
@Suppress("MemberVisibilityCanBePrivate")
enum class MusicalScale(val frequency: Double) {
    /** ド4 */
    C4(261.626),
    /** ド#4 */
    CS4(277.183),
    /** レ4 */
    D4(293.665),
    /** レ#4 */
    DS4(311.127),
    /** ミ4 */
    E4(329.628),
    /** ファ4 */
    F4(349.228),
    /** ファ#4 */
    FS4(369.994),
    /** ソ4 */
    G4(391.995),
    /** ソ#4 */
    GS4(415.305),
    /** ラ4 */
    A4(440.0),
    /** ラ#4 */
    AS4(466.164),
    /** シ4 */
    B4(493.883),
    /** ド4 */
    C5(523.251),
}