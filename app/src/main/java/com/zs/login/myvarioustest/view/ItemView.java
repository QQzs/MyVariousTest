package com.zs.login.myvarioustest.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.annotation.AttrRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.zs.login.myvarioustest.R;

/**
 * Created by zs
 * Date：2017年 05月 19日
 * Time：13:23
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class ItemView extends FrameLayout {

    private TextView mTvTitle;
    private int mTitleColor;
    private float mTitleSize;

    private TextView mTvContent;
    private int mContentColor;
    private int mHintContentColor;
    private float mContentSize;

    private ImageView mIvContent;

    private boolean mShowImage;
    private boolean mShowLine;

    private View line;

    public ItemView(@NonNull Context context) {
        this(context,null);
    }

    public ItemView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public ItemView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        LayoutInflater.from(context).inflate(R.layout.item_view_layout, this);
        mTvTitle = (TextView) findViewById(R.id.tv_item_title);
        mTvContent = (TextView) findViewById(R.id.tv_item_content);
        mIvContent = (ImageView) findViewById(R.id.iv_item_content);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ItemView);
        String title = array.getString(R.styleable.ItemView_title);
        mTitleColor = array.getColor(R.styleable.ItemView_titleColor,0);
        mTitleSize = array.getDimension(R.styleable.ItemView_titleSize,0);

        mShowImage = array.getBoolean(R.styleable.ItemView_showImage,false);
        String hint = array.getString(R.styleable.ItemView_hint);
        String content = array.getString(R.styleable.ItemView_content);
        mContentColor = array.getColor(R.styleable.ItemView_contentColor,0x333333);
        mContentSize = array.getDimension(R.styleable.ItemView_contentSize,0);
        mHintContentColor = array.getColor(R.styleable.ItemView_hintContentColor,0);

        mShowLine = array.getBoolean(R.styleable.ItemView_showLine,true);
        line = findViewById(R.id.line_item);

        if (title != null) {
            mTvTitle.setText(title);
        }
        if (mTitleColor != 0){
            mTvTitle.setTextColor(mTitleColor);
        }
        if (mTitleSize != 0){
            mTvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX,mTitleSize);
        }

        if(mShowImage){
            mTvContent.setVisibility(GONE);
            mIvContent.setVisibility(VISIBLE);
        }else{
            mIvContent.setVisibility(GONE);
            mTvContent.setVisibility(VISIBLE);

            if (TextUtils.isEmpty(content)){
                if (!TextUtils.isEmpty(hint)){
                    mTvContent.setText(hint);
                    if (mHintContentColor != 0){
                        mTvContent.setTextColor(mHintContentColor);
                    }
                }
            }else{
                mTvContent.setText(content);
                if (mContentColor != 0){
                    mTvContent.setTextColor(mContentColor);
                }
                if (mContentSize != 0){
                    mTvContent.setTextSize(TypedValue.COMPLEX_UNIT_PX,mContentSize);
                }
            }
        }
        if (mShowLine){
            line.setVisibility(VISIBLE);
        }else{
            line.setVisibility(GONE);
        }

    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent(String content){
        mTvContent.setText(content);
        mTvContent.setTextColor(mContentColor);
    }

    /**
     * 设置图片
     * @param image
     */
    public void setImage(String image){

    }
}
