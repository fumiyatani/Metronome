package jp.androidbook.meteonorm

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
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

    private fun<T> createIntent(clazz: Class<T>) {
        val intent = Intent(this, clazz)
        startActivity(intent)
    }
}