package com.lijingbo.customview_viewpager.activity;

import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.lijingbo.customview_viewpager.R;
import com.lijingbo.customview_viewpager.utils.DensityUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ViewPager vp;
    private LinearLayout ll_guide_group;
    private List< ImageView > imagesLists;
    private int[] guideImages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
        initData();
        initAdapter();
    }


    private void initView() {
        setContentView(R.layout.activity_main);
        vp = (ViewPager) findViewById(R.id.vp_guide);
        ll_guide_group = (LinearLayout) findViewById(R.id.ll_guide_group);
    }

    private void initData() {
        guideImages = new int[]{R.drawable.guide_1, R.drawable.guide_2, R.drawable.guide_3};
        imagesLists = new ArrayList<>();

        for ( int i = 0; i < guideImages.length; i++ ) {
            ImageView imageView = new ImageView(MainActivity.this);
            imageView.setBackgroundResource(guideImages[i]);
            imagesLists.add(imageView);
        }

        for ( int i = 0; i < guideImages.length; i++ ) {
            View point = new View(MainActivity.this);
            point.setBackgroundResource(R.drawable.shapr_point_gray);
            int pix = DensityUtil.dip2px(this, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(pix, pix);
            if ( i > 0 ) {
                params.leftMargin = pix;
            }
            point.setLayoutParams(params);
            ll_guide_group.addView(point);
        }


    }

    private void initAdapter() {
        vp.setAdapter(new GuideAdapter());
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

}
