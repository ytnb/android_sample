package com.example.ytnb.twotouchmail

import android.content.Intent
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioButton
import kotlinx.android.synthetic.main.activity_pick_up.*

class PickUpActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pick_up)

        val btnSend: Button = findViewById(R.id.button_send)
        btnSend.setOnClickListener{
            //val rgPlace: RadioGroup = findViewById(R.id.rg_place)
            //val checkedId = rgPlace.getCheckedRadioButtonId()
            val checkedId = rg_place.checkedRadioButtonId
            val strPlace = findViewById<RadioButton>(checkedId).text.toString()
            val title = findViewById<EditText>(R.id.editText_subject).text.toString()
            val url = Uri.parse("mailto:${resources.getString(R.string.mail_to)}")
            val intent = Intent(Intent.ACTION_SENDTO, url)
            intent.putExtra(Intent.EXTRA_SUBJECT,title)
            intent.putExtra(Intent.EXTRA_TEXT, "${strPlace}に迎えに来て")
            startActivity(intent)
        }
    }
}
