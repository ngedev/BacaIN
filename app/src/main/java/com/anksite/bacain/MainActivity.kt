package com.anksite.bacain

import android.content.ClipboardManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.speech.tts.TextToSpeech
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {
    lateinit var mTTS : TextToSpeech

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fabSpeak.setOnClickListener {
            et_text.isEnabled = false
            et_text.isEnabled = true
            mTTS.speak(et_text.text, TextToSpeech.QUEUE_FLUSH, null, null)
        }
    }

    override fun onResume() {
        super.onResume()

        val clipBoardManager = applicationContext.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager
        val copiedString = clipBoardManager.primaryClip?.getItemAt(0)?.text?.toString()
        et_text.setText(copiedString)

        mTTS = TextToSpeech(this){
            if(it!= TextToSpeech.ERROR){
                mTTS.language = Locale("id","ID")
            }
        }
    }

    override fun onPause() {
        super.onPause()
        mTTS.stop()
        mTTS.shutdown()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.menu_clear) {
            mTTS.stop()
            et_text.setText("")
        }

        return super.onOptionsItemSelected(item)
    }
}
