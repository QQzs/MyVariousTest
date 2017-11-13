package com.zs.various.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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

    private RelativeLayout.LayoutParams mParams;
    int mPageGuid = 1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.relative_layout);
        rl_home_guid = (RelativeLayout) findViewById(R.id.rl_home_guid);
        iv_page_guid = (ImageView) findViewById(R.id.iv_page_guid);
        mParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        rl_home_guid.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {

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


    }
}
