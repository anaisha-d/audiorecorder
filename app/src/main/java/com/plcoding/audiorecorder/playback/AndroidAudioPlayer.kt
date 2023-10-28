package com.plcoding.audiorecorder.playback

import android.content.Context
import android.media.MediaPlayer
import androidx.core.net.toUri
import java.io.File

class AndroidAudioPlayer(
    private val context: Context
): AudioPlayer {

    private var player: MediaPlayer? = null

    override fun playFile(file: File) {
        MediaPlayer.create(context, file.toUri()).apply {
            player = this
            println("player called")
            start()
        }
    }

    override fun stop() {
        println("Player stopped")
        player?.stop()
        player?.release()
        player = null
    }
}