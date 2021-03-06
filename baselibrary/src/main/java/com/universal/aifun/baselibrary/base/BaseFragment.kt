package com.universal.aifun.baselibrary.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentActivity
import com.trello.rxlifecycle3.components.support.RxFragment


/**
 * Date:2021/11/4
 * Time:9:11
 * author:dimple
 * Fragment的基类
 */
abstract class BaseFragment : RxFragment() {

    protected lateinit var mActivity: FragmentActivity

    //Fragment 是否加载数据
    var isLoadData: Boolean = true

    /**
     * Fragment和Activity建立关联的时候调用，被附加到Activity中去。
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    /**
     * 系统会在创建Fragment时调用此方法。可以初始化一段资源文件等等
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = requireActivity()
    }

    /**
     * 系统会在Fragment首次绘制其用户界面时调用此方法。 要想为Fragment绘制 UI，从该方法中返回的 View 必须是Fragment布局的根视图。
     * 如果Fragment未提供 UI，您可以返回 null。
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //1.初始化Component组件
//        setupComponent(MyApplication.myapplication.getMyAppComponet())
        //2.加载xml布局
        return inflater.inflate(getLayoutId(), container, false)
    }

    /**
     * 在Fragment被绘制后，调用此方法，可以初始化控件资源
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 启动 Fragment 时被回调，此时Fragment可见
     */
    override fun onStart() {
        super.onStart()
    }

    /**
     * 恢复 Fragment 时被回调，获取焦点时回调
     * 当FragmentStatePagerAdapter 中 behavior参数传入FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT 可以在该方法中实现懒加载
     */
    override fun onResume() {
        super.onResume()
        if (isLoadData) {
            //延迟加载的逻辑放到里面
            lazyLoad()
            isLoadData = false
        }
    }

    /**
     * 暂停 Fragment 时被回调，失去焦点时回调。
     */
    override fun onPause() {
        super.onPause()
    }

    /**
     * 停止 Fragment 时被回调,Fragment 不可见
     */
    override fun onStop() {
        super.onStop()
    }

    /**
     * 销毁与Fragment的视图，但是未与Activity解绑
     */
    override fun onDestroyView() {
        super.onDestroyView()
    }

    /**
     * 销毁 Fragment 时被回调
     */
    override fun onDestroy() {
        super.onDestroy()
    }

    /**
     * Fragment和Activity解除关联的时候调用。
     */
    override fun onDetach() {
        super.onDetach()
    }


    /**
     * 得到xml布局文件的id
     */
    protected abstract fun getLayoutId(): Int

    /**
     * 延迟加载 子类重写此方法
     */
    protected abstract fun lazyLoad()
}