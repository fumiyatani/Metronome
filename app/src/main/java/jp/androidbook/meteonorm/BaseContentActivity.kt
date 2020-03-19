package jp.androidbook.meteonorm

import androidx.appcompat.app.AppCompatActivity

open class BaseContentActivity : AppCompatActivity() {

    fun onClickBackButton() {
        finish()
    }
}
