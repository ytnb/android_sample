package com.example.ytnb.twotouchmail

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_pick_up.*

class PickUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_up)

        button_send.setOnClickListener{
            val url = Uri.parse("mailto:${resources.getString(R.string.mail_to)}")
            val intent = Intent(Intent.ACTION_SENDTO, url)

            val title = editText_subject.text.toString()
            intent.putExtra(Intent.EXTRA_SUBJECT,title)

            val checkedId = rg_place.checkedRadioButtonId
            val strPlace = findViewById<RadioButton>(checkedId).text.toString()
            intent.putExtra(Intent.EXTRA_TEXT, "${strPlace}に迎えに来て")

            startActivity(intent)
        }
    }
}
