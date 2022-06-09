package com.visual.visualchart.app.ui.error.view

import android.content.ClipData
import android.content.Intent
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.visual.visualchart.app.base.BaseActivity
import com.visual.visualchart.app.ext.showMessage
import com.visual.visualchart.app.utils.SettingUtil
import com.visual.visualchart.databinding.ActivityErrorBinding

import me.hgj.jetpackmvvm.base.viewmodel.BaseViewModel
import me.hgj.jetpackmvvm.ext.util.clipboardManager
import me.hgj.jetpackmvvm.ext.view.clickNoRepeat


/**
 * 作者　: hegaojian
 * 时间　: 2020/3/12
 * 描述　:
 */
class ErrorActivity : BaseActivity<BaseViewModel, ActivityErrorBinding>() {

    override fun initView(savedInstanceState: Bundle?)  {
        mViewBind.includedTitle.toolbar.title = "发生错误"
        supportActionBar?.setBackgroundDrawable(ColorDrawable(SettingUtil.getColor(this)))

        BarUtils.setStatusBarColor(this, SettingUtil.getColor(this))
        val config = CustomActivityOnCrash.getConfigFromIntent(intent)
        mViewBind.errorRestart.clickNoRepeat{
            config?.run {
                CustomActivityOnCrash.restartApplication(this@ErrorActivity, this)
            }
        }
        mViewBind.errorSendError.clickNoRepeat {
            CustomActivityOnCrash.getStackTraceFromIntent(intent)?.let {
                showMessage(it,"发现有Bug不去打作者脸？","必须打",{
                    val mClipData = ClipData.newPlainText("errorLog",it)
                    // 将ClipData内容放到系统剪贴板里。
                    clipboardManager?.setPrimaryClip(mClipData)
                    ToastUtils.showShort("已复制错误日志")
                    try {
                        val url = "mqqwpa://im/chat?chat_type=wpa&uin=824868922"
                        startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
                    } catch (e: Exception) {
                        ToastUtils.showShort("请先安装QQ")
                    }
                },"我不敢")
            }


        }
    }
}