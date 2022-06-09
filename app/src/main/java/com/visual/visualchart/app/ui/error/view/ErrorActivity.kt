package com.visual.visualchart.app.ui.error.view

import android.content.ClipData
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import cat.ereza.customactivityoncrash.CustomActivityOnCrash
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ToastUtils
import com.visual.visualchart.app.base.BaseActivity
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
                val mClipData = ClipData.newPlainText("errorLog",it)
                // 将ClipData内容放到系统剪贴板里。
                clipboardManager?.setPrimaryClip(mClipData)
                ToastUtils.showShort("已复制错误日志")
            }

        }
    }
}