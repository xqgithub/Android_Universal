package com.universal.aifun.baselibrary.ui.activities

import android.os.Bundle
import com.tencent.tinker.loader.shareutil.ShareTinkerInternals
import com.universal.aifun.baselibrary.R
import com.universal.aifun.baselibrary.base.BaseActivity
import com.universal.aifun.baselibrary.common.ConfigConstants
import com.universal.aifun.baselibrary.common.clickWithTrigger
import com.universal.aifun.baselibrary.ui.presenters.TestTinkerPresenter
import com.universal.aifun.baselibrary.ui.views.TestTinkerView
import com.universal.aifun.baselibrary.utils.LogUtils
import com.universal.aifun.baselibrary.utils.ToastUtils
import kotlinx.android.synthetic.main.activity_test_tinker.*


class TestTinkerActivity : BaseActivity(), TestTinkerView {

    private val mPresenter: TestTinkerPresenter by lazy { TestTinkerPresenter(this, this) }

    override fun onBeforeSetContentLayout() {
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_test_tinker
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initUiView() {
    }

    override fun initData() {
    }

    override fun initListener() {
        tv_load.setOnClickListener {
            mPresenter.loadPath()
        }
        tv_clear.setOnClickListener {
            mPresenter.clearPath()
        }
        tv_toast.setOnClickListener {
//            LogUtils.i(ConfigConstants.TAG_ALL, "使用Tinker前的内容,我叫罗杰1")
            LogUtils.i(ConfigConstants.TAG_ALL, "使用Tinker后的内容,我叫路飞2")
//            LogUtils.i(ConfigConstants.TAG_ALL, "我叫索隆，是一个剑士3")
//            LogUtils.i(ConfigConstants.TAG_ALL, "我叫娜美，是一个航海士4")
        }
        tv_show.setOnClickListener {
            mPresenter.showInfo()
        }

        tv_killself.setOnClickListener {
            ShareTinkerInternals.killAllOtherProcess(this@TestTinkerActivity)
            android.os.Process.killProcess(android.os.Process.myPid())
        }


    }

    override fun showLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "开始加载")
    }

    override fun hideLoading() {
        LogUtils.i(ConfigConstants.TAG_ALL, "停止加载")
    }

}
