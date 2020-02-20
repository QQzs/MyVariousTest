package com.zs.various.activity;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.squareup.picasso.Picasso;
import com.zs.various.R;
import com.zs.various.util.DensityUtil;

/**
 * Created by zs
 * Date：2017年 08月 24日
 * Time：8:50
 * —————————————————————————————————————
 * About:
 * —————————————————————————————————————
 */

public class RelativeActivity extends Activity implements View.OnClickListener{


    private RelativeLayout rl_home_guid;
    private ImageView iv_page_guid;
    private ImageView iv_ad;

    private RelativeLayout.LayoutParams mParams;
    private int mPageGuid = 1;
    private String mImageUrl;
    private boolean mFlag;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        rl_home_guid = findViewById(R.id.rl_home_guid);
        iv_page_guid = findViewById(R.id.iv_page_guid);
        iv_ad = findViewById(R.id.iv_ad);

        mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl_home_guid.setOnClickListener(this);

        iv_ad.setOnClickListener(this);

        setImage();

        try {
            String id1 = "raw:/storage/emulated/0/Download/browser/问答社区接口文档1.0-19.doc";
            String id2 = "raw:/storage/emulated/0/Download/browser/Webinar 测试 修改 V4.pptx";
            long test1 = Long.valueOf(id1);
            Log.d("My_Log","id1 = " + test1);
            long test2 = Long.valueOf(id2);
            Log.d("My_Log","id2 = " + test2);
        } catch (NumberFormatException e) {
            Log.d("My_Log","error = " + e.toString());
        }


    }

    private void setImage(){

        if (mFlag){
            mImageUrl = "http://files.ibaodian.com/web/banner/20180108/222411515398321605.png";
        }else{
            mImageUrl = "http://files.ibaodian.com/web/banner/20171130/397031512028511487.jpg";
        }
        Picasso.with(this)
                .load(mImageUrl)
                .resize(DensityUtil.dip2px(this,300),DensityUtil.dip2px(this,400))
                .centerInside()
                .into(iv_ad);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.rl_home_guid:
                if (mPageGuid == 1){
                    mPageGuid ++;
                    mParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
                    iv_page_guid.setLayoutParams(mParams);
                    iv_page_guid.setImageResource(R.mipmap.home_guid_03);

                }else if (mPageGuid == 2){
                    mPageGuid ++;
                    int screenWidth = DensityUtil.getDisplayWidth(this);
                    int mBannerPagerHeight = (int) (screenWidth * 0.3495f);
                    mParams.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM,0);
                    mParams.topMargin = DensityUtil.dip2px(this,50) + mBannerPagerHeight;
                    iv_page_guid.setLayoutParams(mParams);
                    iv_page_guid.setImageResource(R.mipmap.home_guid_01);

                }else if (mPageGuid == 3){
                    mPageGuid ++;
                    rl_home_guid.setVisibility(View.GONE);
                }
                break;
            case R.id.iv_ad:
                mFlag = !mFlag;
                setImage();
                break;
        }




    }
}
