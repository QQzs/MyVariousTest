package com.zs.various.activity

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.tbruyelle.rxpermissions2.RxPermissions
import com.zs.various.R
import com.zs.various.util.DownloadUtil
import kotlinx.android.synthetic.main.activity_update_layout.*
import org.jetbrains.anko.toast

class UpdateActivity : AppCompatActivity() {

//    var mDownloadUtil: DownloadUtil? = null

    private val mDownloadUtil: DownloadUtil by lazy {
        DownloadUtil.getInstance(this)

    }

    @SuppressLint("CheckResult")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_update_layout)
//        mDownloadUtil = DownloadUtil.getInstance(this)
        var rxPermission = RxPermissions(this)
        rxPermission.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe { granted ->
                    if (!granted){
                        toast("请开启读写权限")
                    }
                }

    }

    fun showDialog(view: View){
        mDownloadUtil?.showNoticeDialog()
    }

    fun updateVersion(view: View){

        var url = "http://ec-test01-data.oss-cn-shanghai.aliyuncs.com/file-space/hospital1/20180630/YFZX_062905.apk"

        mDownloadUtil?.download(url,mDownloadUtil?.apkDir,object : DownloadUtil.OnDownloadListener{
            override fun onDownloadSuccess() {
                toast("下载成功")
                tv_download?.text = "download"
            }

            override fun onDownloading(progress: Int) {
                tv_download?.text = "$progress%"
            }

            override fun onDownloadFailed() {
                toast("下载失败")
                tv_download?.text = "download"
            }

        })

    }

}