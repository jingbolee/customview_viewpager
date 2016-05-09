package com.lijingbo.customview_viewpager.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lijingbo.customview_viewpager.R;
import com.lijingbo.customview_viewpager.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {
    private static final String TAG = "GuideActivity";

    private ViewPager vp;
    private LinearLayout ll_guide_group;
    private List< ImageView > imagesLists;
    private ImageView view_red_point;
    private Button btn_startapp;
    private int[] guideImages;
    private int pointDis; //两个点之间的距离

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initListener();
    }


    private void initView() {
        setContentView(R.layout.activity_guide);
        vp = (ViewPager) findViewById(R.id.vp_guide);
        ll_guide_group = (LinearLayout) findViewById(R.id.ll_guide_group);
        view_red_point = (ImageView) findViewById(R.id.view_red_point);
        btn_startapp = (Button) findViewById(R.id.btn_startapp);
    }

    private void initData() {
        guideImages = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        imagesLists = new ArrayList<>();

        for ( int i = 0; i < guideImages.length; i++ ) {
            ImageView imageView = new ImageView(GuideActivity.this);
            imageView.setBackgroundResource(guideImages[i]);
            imagesLists.add(imageView);
        }

        for ( int i = 0; i < guideImages.length; i++ ) {
            View point = new View(GuideActivity.this);
            point.setBackgroundResource(R.drawable.shapr_point_gray);
            int pix = DensityUtil.dip2px(this, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pix, pix);
            if ( i > 0 ) {
                params.leftMargin = pix;
            }
            point.setLayoutParams(params);
            ll_guide_group.addView(point);
        }


//        final ViewTreeObserver observer = ll_guide_group.getViewTreeObserver();
//        observer.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
//            @TargetApi( Build.VERSION_CODES.JELLY_BEAN )
//            @Override
//            public void onGlobalLayout() {
//                observer.removeOnGlobalLayoutListener(this);
////                observer.removeGlobalOnLayoutListener(this);
//                pointDis = ll_guide_group.getChildAt(1).getLeft() - ll_guide_group.getChildAt(0).getLeft();
//                Log.e(TAG, "连个point的距离为：" + pointDis);
//            }
//        });

        ll_guide_group.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_guide_group.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                pointDis = ll_guide_group.getChildAt(1).getLeft() - ll_guide_group.getChildAt(0).getLeft();
                Log.e(TAG, "连个point的距离为：" + pointDis);
            }
        });

    }

    private void initListener() {
        vp.setAdapter(new GuideAdapter());
        vp.addOnPageChangeListener(new GuidePageChangeListener());
    }


    class GuideAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imagesLists.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            container.addView(imagesLists.get(position));
            return imagesLists.get(position);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

//            System.out.println("viewpager改变监听：" + position + ", " + positionOffset + ", " + positionOffsetPixels);
            int offset = (int) (pointDis * positionOffset + position * pointDis);
////            ViewGroup.LayoutParams params = view_red_point.getLayoutParams();
//            int l = view_red_point.getLeft();
//            int t = view_red_point.getTop();
//            int r = view_red_point.getRight();
//            int b = view_red_point.getBottom();
//            System.out.println("view_red_point的左上下右：" + l + "," + t + "," + r + "," + b);
////            view_red_point.layout(l + offset, t, r + offset, b);
//            view_red_point.scrollTo(l + offset, t);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams) view_red_point.getLayoutParams();
            params.leftMargin = offset;
            view_red_point.setLayoutParams(params);


        }

        @Override
        public void onPageSelected(int position) {
            if ( position == imagesLists.size() - 1 ) {
                btn_startapp.setVisibility(View.VISIBLE);
                AlphaAnimation alpha = new AlphaAnimation(0.1f, 1.0f);
                alpha.setDuration(1000);
                btn_startapp.startAnimation(alpha);
            } else {
                btn_startapp.setVisibility(View.INVISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }

    //点击开始体验按钮进入到MainActivity页面
    public void goToActivity(View v) {
        getSharedPreferences("viewpager", MODE_PRIVATE).edit().putBoolean("first", false).commit();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }

}
