package com.zs.various.view;

import android.content.Context;
import android.os.Handler;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * @author zhangshuai
 * @Date 2022/9/2
 * @Description
 */
public class SearchEditText extends androidx.appcompat.widget.AppCompatEditText {

    /**
     * 默认等待输入时间
     */
    private static final int ACTION_TIME = 300;

    /**
     * 等待输入时间
     */
    private long waitTime = ACTION_TIME;

    private SearchEditListener searchEditListener;

    public SearchEditText(@NonNull Context context) {
        super(context);
        initView();
    }

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public SearchEditText(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (searchEditListener != null) {
                    searchEditListener.onTextChanged(s.toString().trim());
                }
                removeCallbacks(editRunning);
                postDelayed(editRunning, waitTime);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private final Runnable editRunning = new Runnable() {
        @Override
        public void run() {
            removeCallbacks(editRunning);
            if (searchEditListener != null && getText() != null) {
                String editString = TextUtils.isEmpty(getText()) ? "" : getText().toString().trim();
                searchEditListener.startSearchAction(editString);
            }
        }
    };

    /**
     * 动态设置等待输入时间
     * @param waitTime
     */
    public void setWaitTime(long waitTime) {
        this.waitTime = waitTime;
    }

    public void setSearchEditListener(SearchEditListener searchEditListener) {
        this.searchEditListener = searchEditListener;
    }

    public interface SearchEditListener {

        /**
         * 输入内容变化
         * @param text 搜索词
         */
        void onTextChanged(String text);

        /**
         * 开始搜索
         * @param text 搜索词
         */
        void startSearchAction(String text);
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        removeCallbacks(editRunning);
    }
}
