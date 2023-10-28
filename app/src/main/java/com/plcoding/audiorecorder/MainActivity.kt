package com.plcoding.audiorecorder

import android.Manifest
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import android.view.KeyEvent
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.mutableStateOf
import androidx.core.app.ActivityCompat
import com.plcoding.audiorecorder.playback.AndroidAudioPlayer
import com.plcoding.audiorecorder.record.AndroidAudioRecorder
import com.plcoding.audiorecorder.ui.theme.AudioRecorderTheme
import java.io.File

class MainActivity : ComponentActivity() {

    private val recorder by lazy {
        AndroidAudioRecorder(applicationContext)
    }

    private val player by lazy {
        AndroidAudioPlayer(applicationContext)
    }

    private var audioFile: File? = null
    override fun onKeyUp(keyCode: Int, event: KeyEvent): Boolean {
        return when (keyCode) {
            KeyEvent.KEYCODE_R -> {
                File(cacheDir, "audio.mp3").also {
                    recorder.start(it)
                    audioFile = it
                }
                true
            }
            KeyEvent.KEYCODE_S -> {
                recorder.stop()
                true
            }
            KeyEvent.KEYCODE_P -> {
                player.playFile(audioFile!!)
                true
            }
            KeyEvent.KEYCODE_X -> {
                player.stop()
                true
            }
            else -> super.onKeyUp(keyCode, event)
        }
    }


    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCompat.requestPermissions(
            this,
            arrayOf(Manifest.permission.RECORD_AUDIO),
            0
        )
        setContent {
            val convertedText = remember { mutableStateOf("") }
            AudioRecorderTheme {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = {
                        File(cacheDir, "audio.mp3").also {
                            recorder.start(it)
                            audioFile = it
                        }
                    }) {
                        Text(text = "Start recording")
                    }
                    Button(onClick = {
                        recorder.stop()
                    }) {
                        Text(text = "Stop recording")
                    }
                    Button(onClick = {
                        println("Converted Text:")
                    }) {
                        Text(text = "Save File")
                    }
//
//                    Button(onClick = {
//                        player.stop()s
//                    }) {
//                        Text(text = "Stop playing")
//                    }
//                    Button(onClick = { /*TODO*/
//                        val converter = SpeechToTextConverter("ddd")
////                        val text = converter.convertToText(applicationContext)
////                        convertedText.value = text // Update the convertedText value
//
//                    }) {
//                        Text(text = "Convert to text")

//                    }
//                    Text(text = convertedText.value)
                }
            }
        }
    }
}