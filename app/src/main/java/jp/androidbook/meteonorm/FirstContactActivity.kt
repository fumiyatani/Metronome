package jp.androidbook.meteonorm

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import androidx.databinding.DataBindingUtil
import jp.androidbook.meteonorm.databinding.ActivityFirstContactBinding
import kotlinx.android.synthetic.main.activity_first_contact.*

class FirstContactActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFirstContactBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_first_contact)
        setupViews()
    }

    private fun setupViews() {
        // キーボード画面に遷移
        binding.intervalButton.setOnClickListener {
            Intent(this, IntervalActivity::class.java).also {
                startActivity(it)
            }
        }
        // メトロノーム画面に遷移
        binding.metronomeButton.setOnClickListener {
            Intent(this, MetronomeActivity::class.java).also {
                startActivity(it)
            }
        }
    }
}