package com.zs.various.activity

import android.graphics.Bitmap
import android.media.MediaMetadataRetriever
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import cn.jzvd.JZVideoPlayerStandard
import com.zs.various.R
import kotlinx.android.synthetic.main.activity_video.*
import java.util.*

class VideoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)

        var url = "http://mvideo.spriteapp.cn/video/2017/1202/5a228eb680283_wpc.mp4"
        videoplayer?.setUp(url, JZVideoPlayerStandard.SCREEN_WINDOW_NORMAL, "搞笑视频")
        videoplayer?.thumbImageView?.setImageBitmap(getNetVideoBitmap(url))
//
//        videoplayer?.progressBar?.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
//            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, p2: Boolean) {
//                Log.d("My_Log","progress = " + progress)
//            }
//
//            override fun onStartTrackingTouch(seekBar: SeekBar?) {
//                Log.d("My_Log","start progress ==== " + seekBar?.progress)
//            }
//
//            override fun onStopTrackingTouch(seekBar: SeekBar?) {
//                Log.d("My_Log","pause progress ==== " + seekBar?.progress)
//            }
//
//        })

//        videoplayer?.fullscreenButton?.visibility = View.GONE
//        videoplayer?.progressBar?.progressDrawable = ContextCompat.getDrawable(this,R.drawable.seekbar_bg)
//        videoplayer?.bottomProgressBar?.progressDrawable = ContextCompat.getDrawable(this,R.drawable.seekbar_bg)

    }

    /**
     * 服务器返回url，通过url去获取视频的第一帧
     * Android 原生给我们提供了一个MediaMetadataRetriever类
     * 提供了获取url视频第一帧的方法,返回Bitmap对象
     *
     * @param videoUrl
     * @return
     */
    fun getNetVideoBitmap(videoUrl: String): Bitmap? {
        var startTime = System.currentTimeMillis()
        var bitmap: Bitmap? = null
        val retriever = MediaMetadataRetriever()
        try {
            //根据url获取缩略图
            retriever.setDataSource(videoUrl, HashMap())
            //获得第一帧图片
            bitmap = retriever.frameAtTime
        } catch (e: IllegalArgumentException) {
            e.printStackTrace()
        } finally {
            retriever.release()
        }
        var endTime = System.currentTimeMillis()
        return bitmap
    }

    override fun onBackPressed() {
//        if (JCVideoPlayer.backPress()) {
//            return
//        }
        super.onBackPressed()
    }

    override fun onPause() {
        super.onPause()
//        JCVideoPlayer.releaseAllVideos()
    }
}
