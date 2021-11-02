package com.universal.aifun.baselibrary.weiget;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;

import com.universal.aifun.baselibrary.utils.StringUtils;

/**
 * Date:2021/5/27
 * Time:17:48
 * author:joker
 * 自定义EditText,后期可以扩展
 */
public class EditTextCustomize extends AppCompatEditText implements View.OnFocusChangeListener, TextWatcher {


    private Context mContext;
    private onEditTextListener onEditTextListener;

    public EditTextCustomize(@NonNull Context context) {
        super(context);
    }

    public EditTextCustomize(@NonNull Context context, @Nullable AttributeSet attrs) {
        // 这里构造方法也很重要，不加这个很多属性不能再XML里面定义
        this(context, attrs, android.R.attr.editTextStyle);
        this.mContext = context;

    }

    public EditTextCustomize(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * 设置监听
     */
    public void setOnEditTextListener(onEditTextListener onEditTextListener) {
        this.onEditTextListener = onEditTextListener;
    }


    /**
     * 初始化
     */
    private void init() {
        // 设置焦点改变的监听
        setOnFocusChangeListener(this);
        // 设置输入框里面内容发生改变的监听
        addTextChangedListener(this);
    }


    /**
     * 设置EditText框的背景
     *
     * @param view         要改变的view
     * @param CornerRadius 圆角度数
     * @param strokewidth  边的宽度
     * @param strokeColor  边的颜色
     * @param bgcolor      背景色
     */

    public void setEditTextBG(View view, int CornerRadius, int strokewidth, String strokeColor, String bgcolor) {
        setDynamicShapeRECTANGLE(mContext, view, CornerRadius, strokewidth, strokeColor, bgcolor);
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.beforeTextChanged(s, start, count, after);
        }
    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.onTextChanged(s, start, before, count);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.afterTextChanged(s);
        }
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!StringUtils.isBlank(onEditTextListener)) {
            onEditTextListener.onFocusChange(v, hasFocus);
        }
    }

//    @Override
//    protected void onSelectionChanged(int selStart, int selEnd) {
//        super.onSelectionChanged(selStart, selEnd);
//        //保证光标始终在最后面
//        if (selStart == selEnd) {//防止不能多选
//            setSelection(getText().length());
//        }
//    }

    /**
     * 动态设置Shape  RECTANGLE
     */
    private void setDynamicShapeRECTANGLE(Context mContext, View view, float CornerRadius, int strokewidth, String strokeColor, String bgcolor) {
        GradientDrawable drawable = new GradientDrawable();
        //设置shape的形状
        drawable.setShape(GradientDrawable.RECTANGLE);

        //设置shape的圆角度数
        if (!StringUtils.isBlank(CornerRadius) && CornerRadius != -1) {
            drawable.setCornerRadius(CornerRadius);
        }

        //设置shape的边的宽度和颜色
        if (!StringUtils.isBlank(strokewidth) && strokewidth != -1
                && !StringUtils.isBlank(strokeColor)) {
//            drawable.setStroke(strokewidth, ContextCompat.getColor(mContext, R.color.appblack));
            drawable.setStroke(strokewidth, Color.parseColor(strokeColor));
        }

        //设置shape的背景色
        if (!StringUtils.isBlank(bgcolor)) {
//            drawable.setColor(ContextCompat.getColor(mContext, bgcolor));
            drawable.setColor(Color.parseColor(bgcolor));
        }
        view.setBackground(drawable);
    }


    /**
     * 接口输出
     */
    public interface onEditTextListener {

        void beforeTextChanged(CharSequence s, int start, int count, int after);

        void onTextChanged(CharSequence s, int start, int before, int count);

        void afterTextChanged(Editable s);

        void onFocusChange(View v, boolean hasFocus);
    }
}
