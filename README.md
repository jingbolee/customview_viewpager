# customview_viewpager
##功能
1. 通过ViewPager实现app的引导页
2. 全屏显示，通过theme设置去掉actionbar和系统状态栏
3. 只有第一次进入会运行app的引导页，就是说当点击过开始体验按钮以后，第二次进入应用不会再出现
4. point会跟随viewpager滑动而动态滑动

##ViewPager引导页知识点

- ViewPager最多缓存3个页面。
- ViewPager设置**addOnPageChangeListener()**，其中onPageScrolled()方法可以获取到ViewPager处于哪一页，滑动偏移量等信息
- Button在最后一个页面显示，可以通过onPageSelected()方法中的位置来判断是否到了最后一页，来显示button
- ViewPager设置**setAdapter()**，可以把需要显示的引导页显示出来
- setAdapter()需要实现一个adapter**继承PagerAdapter**,实现4个主要的方法：
   - public int **getCount()**:返回引导页的数量
   - public boolean **isViewFromObject(View view, Object object)**：判断显示的是否是同一张图片，返回view == object
      - 返回值boolean：判断是否使用缓存，true表示使用缓存。false表示去创建。
      - 参数view:表示当前的view
      - 参数object:将要进入的新创建的view，由instantiateItem()方法创建。
   - public Object **instantiateItem(ViewGroup container, int position)**：显示的图片可以进行缓存的时候，会调用这个方法进行显示图片的初始化，将要显示的ImageView加入到ViewGroup中，然后作为返回值返回
   - public void **destroyItem(ViewGroup container, int position, Object object)**：PagerAdapter只缓存三张要显示的图片，如果滑动的图片超出了缓存的范围，就会调用这个方法，将图片销毁.如：container.removeView(view(object)) 
- 获取到两个point的距离，在onCreate()中的时候，界面还没有layout完成，可以通过getViewTreeObserver()来观察，当完成了onLayout以后，才能获取到距离

        ll_guide_group.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                ll_guide_group.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                pointDis = ll_guide_group.getChildAt(1).getLeft() - ll_guide_group.getChildAt(0).getLeft();
                Log.e(TAG, "连个point的距离为：" + pointDis);
            }
        });  

- 设置全屏，并且去掉系统属性栏
 
        <style name="NoTitle" parent="Theme.AppCompat.Light.NoActionBar">
            <item name="android:windowNoTitle">true</item>
            <item name="android:windowActionBar">false</item>
            <item name="android:windowFullscreen">true</item>
            <item name="android:windowContentOverlay">@null</item>
        </style>

- 小圆点可以通过shape属性来设置。
   
        <?xml version="1.0" encoding="utf-8"?>
        <shape xmlns:android="http://schemas.android.com/apk/res/android"
        	 android:shape="oval">
           <solid android:color="@android:color/darker_gray"/>
        </shape>

- 给Button设置按压样式，通过selector
- 给Button的文字颜色设置按压样式，通过selector，通过设置android:color的属性
- SharePreference的使用

##适配问题
- 在XML中设置大小用的单位是dp，但是在代码中设置大小用的是pix，假如在代码中设置大小的话，需要把dp转换为具体手机的pix，否则会出现适配问题。

		public class DensityUtil {  
  
    	/** 
    	 * 根据手机的分辨率从 dip 的单位 转成为 px(像素) 
    	 */  
   		public static int dip2px(Context context, float dpValue) {  
        	final float scale = context.getResources().getDisplayMetrics().density;  
        	return (int) (dpValue * scale + 0.5f);  
    	}  
  
   		 /** 
   		  * 根据手机的分辨率从 px(像素) 的单位 转成为 dp 
   		  */  
    	public static int px2dip(Context context, float pxValue) {  
       	 	final float scale = context.getResources().getDisplayMetrics().density;  
        	return (int) (pxValue / scale + 0.5f);  
   		 }  
		} 