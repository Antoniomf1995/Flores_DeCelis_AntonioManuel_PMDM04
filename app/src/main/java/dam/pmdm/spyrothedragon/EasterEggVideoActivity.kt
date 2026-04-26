package dam.pmdm.spyrothedragon

import android.net.Uri
import android.os.Bundle
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity

class EasterEggVideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_easter_egg_video)

        val videoView = findViewById<VideoView>(R.id.videoViewEasterEgg)

        val uri = Uri.parse("android.resource://$packageName/${R.raw.easter_egg_video}")
        videoView.setVideoURI(uri)

        videoView.setOnPreparedListener { mediaPlayer ->
            mediaPlayer.isLooping = false
            videoView.start()
        }

        videoView.setOnCompletionListener {
            finish()
        }
    }
}