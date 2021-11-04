package com.universal.aifun.baselibrary.ui.fragments

import android.os.Bundle
import com.universal.aifun.baselibrary.R
import com.universal.aifun.baselibrary.base.BaseFragment
import com.universal.aifun.baselibrary.common.ConfigConstants
import com.universal.aifun.baselibrary.ui.presenters.OneFragmentPresenter
import com.universal.aifun.baselibrary.ui.views.OneFragmentView
import com.universal.aifun.baselibrary.utils.LogUtils

/**
 * Date:2021/11/4
 * Time:9:20
 * author:dimple
 *  测试的fragment
 */
class OneFragment : BaseFragment(), OneFragmentView {

    val mPresenter: OneFragmentPresenter by lazy { OneFragmentPresenter(mActivity, this) }
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LogUtils.i(ConfigConstants.TAG_ALL, "初始化了---OneFragment")
    }

    override fun getLayoutId(): Int {
        return R.layout.fragment_one
    }

    override fun onStart() {
        super.onStart()
    }

    override fun onPause() {
        super.onPause()
    }


    override fun onStop() {
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * Fragment 延迟加载数据
     */
    override fun lazyLoad() {
        LogUtils.i(ConfigConstants.TAG_ALL, "我要开始加载数据了---OneFragment")
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }
}