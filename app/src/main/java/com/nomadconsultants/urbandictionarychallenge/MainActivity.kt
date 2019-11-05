package com.nomadconsultants.urbandictionarychallenge

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar)
        toolbar.title = getString(R.string.toolbar_title)
        toolbar.setBackgroundColor(ContextCompat.getColor(this, R.color.toolbarBg))
    }
}
