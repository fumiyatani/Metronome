package jp.androidbook.meteonorm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import kotlinx.android.synthetic.main.activity_first_contact.*

class FirstContactActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_first_contact)
    }

    fun onClick(view: View) {
        when(view) {
            metronomeButton -> createIntent(MetronomeActivity::class.java)
            intervalButton  -> createIntent(IntervalActivity::class.java)
        }
    }

    // <Any>でワイルドカードかと思ったらAny型だったから通常の<T>でやる
    private fun<T> createIntent(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}