package com.visual.visualchart.app.ui.main.view

import com.visual.visualchart.app.base.BaseActivity
import com.visual.visualchart.app.ui.main.viewmodel.HomeViewModel
import android.os.Bundle
import com.blankj.utilcode.util.GsonUtils
import com.visual.visualchart.app.data.WebToNativeDataPar
import com.visual.visualchart.databinding.ActivityMainBinding

class MainActivity : BaseActivity<HomeViewModel, ActivityMainBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mViewBind.webView.registerHandler("dataWebToNative") { data, function ->
            mViewBind.text2.text = GsonUtils.fromJson(data, WebToNativeDataPar::class.java).param
            function.onCallBack("submitFromWeb exe, response data from Java")
        }
        mViewBind.btnSend.setOnClickListener {
            mViewBind.webView.callHandler(
                "dataNativeToWeb",
                GsonUtils.toJson(WebToNativeDataPar("dataNativeToWeb"))
            ) { data -> mViewBind.text2.text = data }
        }
        mViewBind.webView.loadUrl("file:///android_asset/demo.html")
    }

    companion object {
        private const val TAG = "MainActivity"
    }
}