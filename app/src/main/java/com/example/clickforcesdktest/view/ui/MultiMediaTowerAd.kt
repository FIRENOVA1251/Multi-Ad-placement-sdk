package com.example.clickforcesdktest.view.ui

import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import android.widget.VideoView
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VolumeOff
import androidx.compose.material.icons.filled.VolumeUp
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import coil.compose.rememberAsyncImagePainter
import com.example.clickforcesdktest.data.model.AdResponse


@Composable
fun MultiMediaTowerAd(adResponse: AdResponse?) {

    val context = LocalContext.current

    // 確保 API 數據已經加載
    if (adResponse == null) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            CircularProgressIndicator() // 顯示載入動畫
        }
        return
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // 影片廣告
        adResponse.item?.bannerContent?.video?.let { videoUrl ->
            VideoAdPlayer(videoUrl)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // 橫幅圖片廣告

        Box(
            modifier = Modifier
                .width(300.dp)
                .height(80.dp)
                .clickable {
                    val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.bannerUrl))
                    context.startActivity(intent)

                }
        ) {
            Image(
                painter = rememberAsyncImagePainter(adResponse.item?.bannerContent?.image),
                contentDescription = "Banner Image",
                modifier = Modifier
                    .matchParentSize(),
                contentScale = ContentScale.Crop
            )

            Image(
                painter = rememberAsyncImagePainter("https://cdn.holmesmind.com/cf.png"),
                contentDescription = "CF Logo",
                modifier = Modifier
                    .width(23.dp)
                    .height(20.dp)
                    .align(Alignment.BottomEnd)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(adResponse.item?.iconUrl))
                        context.startActivity(intent)
                    }
            )
        }

    }
}

@Composable
fun VideoAdPlayer(videoUrl: String) {
    var isMuted by remember { mutableStateOf(false) } // 控制靜音狀態
    var mediaPlayer by remember { mutableStateOf<MediaPlayer?>(null) }

    Surface(
        modifier = Modifier
            .width(300.dp)
            .aspectRatio(16f / 9f)
            .background(Color.Gray),
        color = Color.Transparent
    ) {
        AndroidView(
            factory = { ctx ->
                VideoView(ctx).apply {

                    // Video source.
                    setVideoURI(Uri.parse(videoUrl))

                    // Video will start once it is ready.
                    setOnPreparedListener { mp ->
                        mp.isLooping = true // 影片循環播放
                        setVolume(mp, isMuted)
                        mp.start()
                        mediaPlayer = mp
                    }
                }
            },

            modifier = Modifier.fillMaxSize()
        )

        DisposableEffect(Unit) {
            onDispose {
                mediaPlayer?.let {
                    try {
                        if (it.isPlaying) {
                            it.stop()
                        }
                        it.release()
                    } catch (e: IllegalStateException) {
                        e.printStackTrace() // 避免崩潰
                    }
                }
                mediaPlayer = null
            }
        }

        // 靜音按鈕（左下角）
        Box(
            modifier = Modifier
                .background(Color.Transparent)
                .clickable {
                    isMuted = !isMuted
                    mediaPlayer?.let { setVolume(it, isMuted) }
                }, // 點擊切換靜音狀態
            contentAlignment = Alignment.BottomStart
        ) {
            Icon(
                imageVector = if (isMuted) Icons.Default.VolumeOff else Icons.Default.VolumeUp,
                contentDescription = if (isMuted) "Unmute" else "Mute",
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )
        }


    }
}


// 控制影片音量的方法
private fun setVolume(mediaPlayer: MediaPlayer, isMuted: Boolean) {
    if (isMuted) {
        mediaPlayer.setVolume(0f, 0f) // 靜音
    } else {
        mediaPlayer.setVolume(1f, 1f) // 解除靜音
    }
}


//22111  22001