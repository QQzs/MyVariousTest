package com.zs.various.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.zs.various.application.MyApp;
import com.zs.various.bean.ImgInfo;
import com.zs.various.util.DensityUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * -------------------------------------
 * 作者：Vitta
 * -------------------------------------
 * 时间：2018/12/5 下午2:50
 * -------------------------------------
 * 描述：九宫格图片展示控件
 * -------------------------------------
 * 备注：
 * 1、宽度写match 或 固定值，不建议写wrap（取屏幕宽度）
 * 2、边上没有间距
 * 3、不支持padding 支持margin
 * 4、数据多于9条会截取
 * -------------------------------------
 */
public class NineGridLayout extends ViewGroup {

    /**
     * 封装思路 ：
     * 1、list setListData 去new 图片，add到父布局里
     * 2、requestLayout 去测量 onMeasure onLayout布局（参考展示规则,根据行列计算宽高）
     * 3、new ItemView 增加缓存逻辑
     * <p>
     * 展示规则 ：
     * 1、1张图 ：7/10 2/3 两个关键节点计算尺寸
     * 2、4张图 ：四宫格展示 7/10
     * 3、其他小于9张图 ：9宫格展示
     */

    //九宫格默认宽度
    private double GRID_DEFAULT_TOTAL_WIDTH = MyApp.getAppContext().getResources().getDisplayMetrics().widthPixels - 2 * DensityUtil.dip2px(12);
    //单张图最大宽度是 <最大宽度 * 0.7>
    public double mSignalImageLength = 0.7 * GRID_DEFAULT_TOTAL_WIDTH;
    //单张图临界比例
    public double mSignalCriticalRatio = 3f / 2f;
    //图片间隙
    private int mGap = DensityUtil.dip2px(6);
    //图片view缓存list（多次setData复用之前new的view，不重新去add）
    private List<View> mViewList = new ArrayList<>();
    //存储计算的图片宽高
    private double mImageWidth;
    private double mImageHeight;
    //图片数据
    private List<ImgInfo> mList;

    public NineGridLayout(Context context) {
        super(context);
    }

    public NineGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public NineGridLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        if (!isEmpty(mList)) {
            int size = mList.size();
            switch (size) {
                case 1:
                    measureOneImageWidth();
                    break;
                case 4:
                    //2行2列展示
                    measureGridImage(widthMeasureSpec, 2);
                    break;
                default:
                    //3行3列展示
                    measureGridImage(widthMeasureSpec, 3);
                    break;
            }
        } else {
            setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                    getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
        }
    }

    /**
     * 测量一张图宽高
     */
    private void measureOneImageWidth() {
        ImgInfo imgInfo = mList.get(0);
        //宽高比
        double imageWidth = imgInfo.getWidth();
        double imageHeight = imgInfo.getHeight();
        //图片宽高异常处理
        if (imageWidth <= 0 || imageHeight <= 0) {
            imageWidth = imageHeight = mSignalImageLength;
        }
        boolean isVertical = imageWidth < imageHeight;
        if (isVertical) {
            double temp = imageWidth;
            imageWidth = imageHeight;
            imageHeight = temp;
        }
        double aspectRatio = imageWidth / imageHeight;//肯定是>1的
        double width = mSignalImageLength;
        double height = mSignalImageLength / Math.min(mSignalCriticalRatio, aspectRatio);
        if (isVertical) {
            //宽 < 高
            mImageWidth = height;
            mImageHeight = width;
        } else {
            //宽 >= 高
            mImageWidth = width;
            mImageHeight = height;
        }
        //measure child
        View childAt = getChildAt(0);
        if (childAt != null) {
            childAt.measure(MeasureSpec.makeMeasureSpec((int) mImageWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec((int) mImageHeight, MeasureSpec.EXACTLY));
        }
        //measure self
        setMeasuredDimension((int) mImageWidth, (int) mImageHeight);
    }

    /**
     * 测量九宫格宽高
     */
    private void measureGridImage(int widthMeasureSpec, int rowColumnCount) {
        double measuredWidth;
        if (MeasureSpec.getMode(widthMeasureSpec) == MeasureSpec.EXACTLY) {
            measuredWidth = MeasureSpec.getSize(widthMeasureSpec);
        } else {
            measuredWidth = GRID_DEFAULT_TOTAL_WIDTH;
        }
        double imageLength = (measuredWidth - mGap * 2) / 3f;
        mImageWidth = mImageHeight = imageLength;
        //一共几行
        int size = mList.size();
        int row = (size - 1) / rowColumnCount + 1;
        double showHeight = imageLength * row + mGap * (row - 1);
        //一共几列
        int column = size < rowColumnCount ? size : rowColumnCount;
        double showWidth = imageLength * column + mGap * (column - 1);
        //measure child
        for (int i = 0; i < size; i++) {
            View childAt = getChildAt(i);
            if (childAt != null) {
                childAt.measure(MeasureSpec.makeMeasureSpec((int) mImageWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec((int) mImageHeight, MeasureSpec.EXACTLY));
            }
        }
        //measure self
        setMeasuredDimension((int) showWidth, (int) showHeight);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        if (count == 1) {
            View childAt = getChildAt(0);
            childAt.layout(0, 0, (int) mImageWidth, (int) mImageHeight);
        } else if (count == 4) {
            for (int i = 0; i < count; i++) {
                View childAt = getChildAt(i);
                //在第几行
                int row = i / 2;
                //在第几列
                int column = i % 2;
                double left = (mImageWidth + mGap) * column;
                double top = (mImageHeight + mGap) * row;
                childAt.layout((int) left, (int) top, (int) (left + mImageWidth), (int) (top + mImageHeight));
            }
        } else if (count > 1) {
            for (int i = 0; i < count; i++) {
                View childAt = getChildAt(i);
                //在第几行
                int row = i / 3;
                //在第几列
                int column = i % 3;
                double left = (mImageWidth + mGap) * column;
                double top = (mImageHeight + mGap) * row;
                childAt.layout((int) left, (int) top, (int) (left + mImageWidth), (int) (top + mImageHeight));
            }
        }
    }

    public void setImageList(List<ImgInfo> list) {

        if (isEmpty(list)) {
            setVisibility(GONE);
            return;
        }
        setVisibility(VISIBLE);
        if (list.size() > 9) {
            //超出9条数据截取一下
            list = list.subList(0, 9);//这个截取是左闭右开[inclusive,exclusive)
        }
        this.mList = list;
        int size = mList.size();
        int childCount = getChildCount();
        if (size >= childCount) {
            //需要addView
            for (int i = 0; i < size; i++) {
                if (i >= childCount) {
                    addItemView(i);
                }
                setImageData(getChildAt(i), list.get(i), i);
            }
        } else {
            //需要移除view (size < childCount)
            for (int i = 0; i < childCount; i++) {
                if (i < size) {
                    setImageData(getChildAt(i), list.get(i), i);
                } else {
                    View itemView = mViewList.get(i);
                    ViewGroup parent = (ViewGroup) itemView.getParent();
                    if (parent != null) {
                        parent.removeView(itemView);
                    }
                }
            }
        }
        //申请重绘
        requestLayout();
    }

    /**
     * 添加图片到父布局，增加缓存的逻辑
     *
     * @param position 需要addView的位置
     */
    private void addItemView(final int position) {
        int cacheSize = mViewList.size();
        View itemView;
        if (position < cacheSize) {
            //先从viewList里面取
            itemView = mViewList.get(position);
        } else {
            //没有直接new出来
            itemView = getNewItemView();
            mViewList.add(itemView);
        }
        addView(itemView);
    }

    /**
     * 图片数据是否为空
     */
    private boolean isEmpty(List<ImgInfo> list) {
        return list == null || list.isEmpty();
    }

    /**
     * 创建ItemView
     */
    protected View getNewItemView() {
        FrameLayout rcFrameLayout = new FrameLayout(getContext());
//        rcFrameLayout.setRadius(DensityUtil.dip2px(9));
        //img
        ImageView imageView = new ImageView(getContext());
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        rcFrameLayout.addView(imageView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
        //gif icon
        ImageView gifIcon = new ImageView(getContext());
//        gifIcon.setImageResource(R.drawable.icon_gif);
        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(DensityUtil.dip2px(30), DensityUtil.dip2px(15));
        params.gravity = Gravity.BOTTOM | Gravity.RIGHT;
        rcFrameLayout.addView(gifIcon, params);
        gifIcon.setVisibility(GONE);
        return rcFrameLayout;
    }

    /**
     * 给imageView设置图片资源
     *
     * @param itemView view
     * @param imgInfo  data
     */
    protected void setImageData(View itemView, ImgInfo imgInfo, int pos) {
        if (itemView instanceof FrameLayout) {
            View imageView = ((FrameLayout) itemView).getChildAt(0);
            View gifIcon = ((FrameLayout) itemView).getChildAt(1);
            if (imageView instanceof ImageView && gifIcon instanceof ImageView) {
//                if (TextUtils.isEmpty(imgInfo.getUrl())) {
//                    imageView.setId(0);
//                } else {
                imageView.setId(pos);//加pos的原因是防止即合理多个图片地址一样
//                }
                String url = imgInfo.getUrl();
//                gifIcon.setVisibility(CommonUtils.isGifUrl(url) ? VISIBLE : GONE);
//                if (mImageWidth > 0 && mImageHeight > 0) {
//                    UxinImageLoader.loadCoverBitmap(url, (ImageView) imageView, R.color.color_f4f4f4, (int) mImageWidth, (int) mImageHeight);
//                } else {
//                    UxinImageLoader.loadCoverBitmap(url, (ImageView) imageView, R.color.color_f4f4f4);
//                }
            }
        }

    }

    /**
     * 设置图片间隙
     *
     * @param gap 单位px
     */
    public void setGap(int gap) {
        this.mGap = gap;
    }

    /**
     * 设置单张图宽高比临界值（只需要设置宽>高时，宽<高时会根据此比例自动计算）
     * 比如 ：宽大于高时，宽高比最大是多少，默认是3/2
     *
     * @param signalCriticalRatio 宽高比
     */
    public void setSignalCriticalRatio(double signalCriticalRatio) {
        this.mSignalCriticalRatio = signalCriticalRatio;
    }

    /**
     * 设置单张图最大尺寸
     * 默认是屏幕宽度的0.7倍
     */
    public void setSignalImageLength(double signalImageLength) {
        this.mSignalImageLength = signalImageLength;
    }

    /**
     * 九宫格控件点击事件
     */
    public interface ItemOnClickListener {
        void onImageItemClick(View view, int imagePosition, List<ImgInfo> list, String publishName);
    }

}
