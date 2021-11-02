package com.universal.aifun.baselibrary.ui.presenters

import android.Manifest
import android.app.Activity
import android.content.Context
import android.os.Message
import com.universal.aifun.baselibrary.R
import com.universal.aifun.baselibrary.TKExtManage
import com.universal.aifun.baselibrary.base.BasePresenter
import com.universal.aifun.baselibrary.common.ConfigConstants
import com.universal.aifun.baselibrary.help.MySPUtilsUser
import com.universal.aifun.baselibrary.ui.activities.PermissionsActivity
import com.universal.aifun.baselibrary.ui.dialog.ServiceAgreementDialog
import com.universal.aifun.baselibrary.ui.views.StartUpView
import com.universal.aifun.baselibrary.utils.HandlerUtils
import com.universal.aifun.baselibrary.utils.LogUtils
import com.universal.aifun.baselibrary.utils.PublicPracticalMethodFromJAVA
import com.universal.aifun.baselibrary.utils.ScreenTools
import java.util.*

/**
 * Date:2021/10/26
 * Time:16:21
 * author:joker
 * 启动页的Presenter
 */
class StartUpPresenter(private var mContext: Context, private var startUpView: StartUpView) : BasePresenter<StartUpView>(mContext, startUpView), HandlerUtils.OnReceiveMessageListener {


    private val WHATCODE_RUNNINGTIMER = 1

    // 时间类
    private lateinit var timer: Timer

    //计时时间
    private var timecount = 2


    //权限数据
    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION,
        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE
    )

    private var permissionsListener: PermissionsActivity.PermissionsListener? = null

    init {
        initHandler(this)
    }


    /**
     * 倒计时的内部类
     */
    private var timerTask: TimerTask = object : TimerTask() {
        override fun run() {
            PublicPracticalMethodFromJAVA.getInstance().runHandlerFun(mHandler, WHATCODE_RUNNINGTIMER, 100)
        }
    }


    /**
     * 启动计时器
     */
    fun startTimerTask() {
        timerTask?.let {
            timer = Timer()
            timer.schedule(it, 0, 1000)
        }
    }

    /**
     * 设置权限监听
     */
    fun setPermissionsListener(permissionsListener: PermissionsActivity.PermissionsListener) {
        this.permissionsListener = permissionsListener
    }

    /**
     * 显示协议弹框
     */
    fun showServiceAgreementDialog() {
        var dialogWidth = if (ScreenTools.getInstance().isPad(mContext)) mContext.resources.getDimensionPixelSize(R.dimen.dimen_390x) else mContext.resources.getDimensionPixelSize(R.dimen.dimen_340x)
        with(ServiceAgreementDialog(mContext)) {
            this.setDialogWidth(dialogWidth)
            this.setServiceAgreementDialogListener {//同意协议回调
                //更新 用户协议和隐私政策 标识
                MySPUtilsUser.getInstance().saveUserPrivacyAgreementStatus(true)
                isApplyPermission(permissions) { _result ->
                    if (_result) {
                        permissionsListener?.let {
                            PublicPracticalMethodFromJAVA.getInstance().startPermissionsActivity(mContext as Activity, permissions, it, ConfigConstants.PERMISSIONS_GRANTED_STARTUPACTIVITY)
                        }
                    } else {
                        LogUtils.i(ConfigConstants.TAG_ALL, "准备跳转到首页")
                    }
                }
            }
            if (!this.isShowing) this.show()
        }
    }


    /**
     * 设置启动页的图片
     */
    fun setLogo(result: (Int) -> Unit) {
        result(TKExtManage.getInstance().getStartLogo(mContext))
    }


    /**
     * handler回调
     */
    override fun handlerMessage(msg: Message) {
        when (msg.what) {
            WHATCODE_RUNNINGTIMER -> {
                timecount--
                if (timecount < 0) {
                    timer.cancel()
                    val isAgree = MySPUtilsUser.getInstance().userPrivacyAgreementStatus
                    if (isAgree) {
                        isApplyPermission(permissions) { _result ->
                            if (_result) {
                                permissionsListener?.let {
                                    PublicPracticalMethodFromJAVA.getInstance().startPermissionsActivity(mContext as Activity, permissions, it, ConfigConstants.PERMISSIONS_GRANTED_STARTUPACTIVITY)
                                }
                            } else {
                                LogUtils.i(ConfigConstants.TAG_ALL, "准备跳转到首页")
                            }
                        }
                    } else {
                        //弹出协议弹框
                        showServiceAgreementDialog()
                    }
                }
            }
        }
    }
}