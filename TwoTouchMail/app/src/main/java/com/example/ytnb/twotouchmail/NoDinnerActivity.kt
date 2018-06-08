package com.example.ytnb.twotouchmail

import android.content.Intent
import android.net.Uri
import kotlinx.android.synthetic.main.activity_no_dinner.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class NoDinnerActivity : AppCompatActivity(), View.OnClickListener, View.OnLongClickListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_no_dinner)

        button_send.setOnClickListener(this)
        button_send.setOnLongClickListener(this)
    }

    override fun onClick(view: View?) {
        val uri = Uri.parse("mailto:${resources.getString(R.string.mail_to)}")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, editText_subject.text)
        intent.putExtra(Intent.EXTRA_TEXT, "遅くなるので飯いらない")
        startActivity(intent)
    }

    override fun onLongClick(v: View?): Boolean {
        val uri = Uri.parse("mailto:${resources.getString(R.string.mail_to)}")
        val intent = Intent(Intent.ACTION_SENDTO, uri)
        intent.putExtra(Intent.EXTRA_SUBJECT, editText_subject.text)
        intent.putExtra(Intent.EXTRA_TEXT, "遅くなるので食事いりません¥n連絡遅くなってごめんなさい")
        startActivity(intent)
        return true
    }
}
