package jp.androidbook.meteonorm

import android.os.Bundle
import kotlinx.android.synthetic.main.activity_interval.*

class IntervalActivity : BaseContentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_interval)
        backButton.setOnClickListener{
            onClickBackButton()
        }
    }

}