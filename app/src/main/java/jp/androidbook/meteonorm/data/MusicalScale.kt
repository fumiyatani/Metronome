package jp.androidbook.meteonorm.data

/**
 * TODO 音階を鍵盤の位置から計算できるようになったので、Enumをなくしたい。
 * 音階
 *
 * @param frequency 音階の周波数
 */
@Suppress("MemberVisibilityCanBePrivate")
enum class MusicalScale(val frequency: Double) {
    /** ド4 */
    C4(StandardMusicalScaleGenerator().create(39)),
    /** ド#4 */
    CS4(StandardMusicalScaleGenerator().create(40)),
    /** レ4 */
    D4(StandardMusicalScaleGenerator().create(41)),
    /** レ#4 */
    DS4(StandardMusicalScaleGenerator().create(42)),
    /** ミ4 */
    E4(StandardMusicalScaleGenerator().create(43)),
    /** ファ4 */
    F4(StandardMusicalScaleGenerator().create(44)),
    /** ファ#4 */
    FS4(StandardMusicalScaleGenerator().create(45)),
    /** ソ4 */
    G4(StandardMusicalScaleGenerator().create(46)),
    /** ソ#4 */
    GS4(StandardMusicalScaleGenerator().create(47)),
    /** ラ4 */
    A4(StandardMusicalScaleGenerator().create(48)),
    /** ラ#4 */
    AS4(StandardMusicalScaleGenerator().create(49)),
    /** シ4 */
    B4(StandardMusicalScaleGenerator().create(50)),
    /** ド4 */
    C5(StandardMusicalScaleGenerator().create(51)),
}