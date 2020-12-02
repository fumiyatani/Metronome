package jp.androidbook.meteonorm.data

import java.lang.IllegalArgumentException
import kotlin.math.pow

/**
 * TODO 今の作りだと鍵盤数が88のものしか対応していない
 * TODO 今の作りだと基準周波数も同じものしか選べない
 * TODO 拡張性がないからFactoryなどのデザインパターンを導入する？
 */
class StandardMusicalScaleGenerator {

    /**
     * 鍵盤の位置から周波数を計算する
     *
     * @param positionNumber 0~87の範囲を指定する
     */
    fun create(positionNumber: Int): Double {
        if (positionNumber < 0 || positionNumber > 87) {
            throw IllegalArgumentException("out of range position is $positionNumber")
        }

        // concertPitchが鳴る鍵盤位置を基準としてどのくらい離れているかを計算する。
        // 49番目よりも高い音の場合は正の数となり、
        // 49番目よりも低い音の場合は負の数となる。
        val relativePosition = positionNumber.toDouble() - CONCERT_PITCH_POSITION

        // 基準となる周波数 * 2^(d/12)
        return CONCERT_PITCH * 2.0.pow((relativePosition / 12.0))
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "MusicalScaleGenerator"

        private const val CONCERT_PITCH: Double = 440.0
        private const val CONCERT_PITCH_POSITION: Double = 49.0
    }
}