package com.zs.various.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.squareup.picasso.Picasso;
import com.zs.various.R;
import com.zs.various.util.DensityUtil;
import com.zs.various.util.RoundTransform;

/**
 * Created by zs
 * Date：2017年 05月 19日
 * Time：13:23
 * —————————————————————————————————————
 * About:自定义 item
 * —————————————————————————————————————
 */

public class ItemView extends FrameLayout {

    private ImageView mIvIcon;
    private TextView mTvTitle;
    private int mTitleColor;
    private float mTitleSize;

    private TextView mTvContent;
    private int mContentColor;
    private int mHintContentColor;
    private float mContentSize;
    private int mContentLines;

    private ImageView mIvContent;

    private boolean mShowIcon;
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
        mIvIcon = (ImageView) findViewById(R.id.iv_item_icon);
        mTvTitle = (TextView) findViewById(R.id.tv_item_title);
        mTvContent = (TextView) findViewById(R.id.tv_item_content);
        mIvContent = (ImageView) findViewById(R.id.iv_item_content);

        TypedArray array = context.obtainStyledAttributes(attrs,R.styleable.ItemView);

        mShowIcon = array.getBoolean(R.styleable.ItemView_showIcon,false);
        Drawable icon = array.getDrawable(R.styleable.ItemView_itemIcon);
        if (mShowIcon){
            mIvIcon.setVisibility(VISIBLE);
            mIvIcon.setImageDrawable(icon);
        }else{
            mIvIcon.setVisibility(GONE);
        }

        String title = array.getString(R.styleable.ItemView_itemTitle);
        mTitleColor = array.getColor(R.styleable.ItemView_titleColor,0);
        mTitleSize = array.getDimension(R.styleable.ItemView_titleSize,0);

        mShowImage = array.getBoolean(R.styleable.ItemView_showImage,false);
        String hint = array.getString(R.styleable.ItemView_hint);
        String content = array.getString(R.styleable.ItemView_contentString);
        mContentColor = array.getColor(R.styleable.ItemView_contentColor, getResources().getColor(R.color.font_black));
        mContentSize = array.getDimension(R.styleable.ItemView_contentSize, DensityUtil.dip2px(context,15));
        mHintContentColor = array.getColor(R.styleable.ItemView_hintContentColor,getResources().getColor(R.color.font_lightgray));
        mContentLines = array.getInteger(R.styleable.ItemView_contentLines,2);

        mShowLine = array.getBoolean(R.styleable.ItemView_showLine,true);
        line = findViewById(R.id.line_item);

        if (!TextUtils.isEmpty(title)) {
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
            mTvContent.setMaxLines(mContentLines);

            if (TextUtils.isEmpty(content)){
                if (!TextUtils.isEmpty(hint)){
                    mTvContent.setHint(hint);
                    if (mHintContentColor != 0){
                        mTvContent.setHintTextColor(mHintContentColor);
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
        array.recycle();

    }

    /**
     * 设置标题
     * @param title
     */
    public void setTitle(String title){
        mTvTitle.setText(title);
    }

    /**
     * 获取标题
     * @return
     */
    public String getTitle(){
        return mTvTitle.getText().toString().trim();
    }

    /**
     * 设置内容
     * @param content
     */
    public void setContent(String content){
        mTvContent.setText(content);
    }

    /**
     * 获取内容
     */
    public String getContent(){
        if (TextUtils.isEmpty(mTvContent.getText().toString().trim())){
            return "";
        }else{
            return mTvContent.getText().toString().trim();
        }
    }

    /**
     * 设置图片
     * @param image
     */
    public void setImage(String image){
        Picasso.with(getContext()).load(image).transform(new RoundTransform(DensityUtil.dip2px(getContext(),4))).resize(DensityUtil.dip2px(getContext(), 30), DensityUtil.dip2px(getContext(), 30)).into(mIvContent);
    }
}
