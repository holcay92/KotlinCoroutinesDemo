package com.example.coroutinesdemo1

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import kotlinx.coroutines.*

class MainActivity : AppCompatActivity() {
    private var count = 0
    private lateinit var messageTextView: TextView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView = findViewById<TextView>(R.id.tvCount)
        val countButton = findViewById<Button>(R.id.btnCount)
        val downloadButton = findViewById<Button>(R.id.btnDownload)
        messageTextView = findViewById(R.id.tvMessage)

        countButton.setOnClickListener {
            textView.text = count++.toString()
        }
        downloadButton.setOnClickListener {
            // this is an interface that is used to run the code in the background
            CoroutineScope(Dispatchers.IO).launch {
                downloadUserData()
            }
        }
    }
    // we used suspend keyword to make this function delayable
// sometimes logcat will not be able to catch all the logs and gives EOF error
// to avoid this we use delay function
    private suspend fun downloadUserData() {
        for (i in 1..200000) {
            Log.i("MyTag", "Downloading user $i in ${Thread.currentThread().name}")
            withContext(Dispatchers.Main) {
                messageTextView.text = "Downloading user $i "
            }

            delay(100)
        }
    }
}
