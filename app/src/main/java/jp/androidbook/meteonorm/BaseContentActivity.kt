package jp.androidbook.meteonorm

import android.support.v7.app.AppCompatActivity

open class BaseContentActivity : AppCompatActivity() {

    fun onClickBackButton() {
        finish()
    }
}
