package fuweichen.titlescroll;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CustomHorizontalScrollView.OnSelectItemListener{
    private static final String TAG = "testtablayout";
    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    private List<Fragment> fragmentList;
    private List<String>  titleList;

    private int nCurPosition = 0;
    private int nCurScrollX = 0;
    private int nTabWidth = 144;

    private CustomHorizontalScrollView mCusScrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fragmentList = new ArrayList<Fragment>();
        titleList = new ArrayList<String>();
        mViewPager = (ViewPager)findViewById(R.id.viewpager);

        mCusScrollView = (CustomHorizontalScrollView)findViewById(R.id.customHorizontalScrollView);
        mCusScrollView.setOnSelectItemListener(this);

        mCusScrollView.setItemIndicateColor(getResources().getColor(R.color.tab_indicator));
        mCusScrollView.setItemIndicateHeight(getResources().getDimensionPixelSize(R.dimen.item_indicate_height));
        mCusScrollView.setBgColor(getResources().getColor(R.color.title_bg));
        mCusScrollView.setItemDefaultColor(getResources().getColor(R.color.tab_default_color));
        mCusScrollView.setItemSelectColor(getResources().getColor(R.color.tab_select_color));
        mCusScrollView.setItemPaddingLeft(getResources().getDimensionPixelSize(R.dimen.item_padding_left));
        mCusScrollView.setItemPaddingRight(getResources().getDimensionPixelSize(R.dimen.item_padding_right));


        for(int i = 0; i < 20; i++)
        {
            String tab1 = "tab" + i;
            TestFragment testFragment = TestFragment.newInstance(tab1);
            fragmentList.add(testFragment);
            titleList.add(tab1);
        }

        mCusScrollView.setItems(titleList);
        mViewPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
        mViewPager.addOnPageChangeListener(mCusScrollView);
    }

    private Bitmap getTestBitmap()
    {
        Bitmap mBlendDesBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.a33).copy(Bitmap.Config.ARGB_8888, true);
        Canvas mCanvas = new Canvas(mBlendDesBitmap);

        Bitmap mBlendSrcBitmap = BitmapFactory.decodeResource(getResources(), R.mipmap.b33);
        BitmapDrawable d = new BitmapDrawable(mBlendSrcBitmap);
        d.setTargetDensity(mBlendSrcBitmap.getDensity());
        d.setBounds(0,0,d.getIntrinsicWidth(),d.getIntrinsicHeight());

//        Matrix matrix = new Matrix();
//        //折中的算法，以分辨率720 * 1280， Density为320为基准进行换算
//        float n1 = 1f / 5;
//        int nDes = 720 * mBlendSrcBitmap.getDensity() / 320;
//        if(nWidth < nDes)
//        {
//            //针对发布出去的水印设置，发布出去会裁剪
//            nDes = nWidth;
//        }
//        float sx = nDes * n1  / (float)mBlendSrcBitmap.getWidth();
//
//        final float sy = sx;
//        matrix.postScale(sx,sy);
//        matrix.postTranslate(nWidth - n1 * nDes - nVideoLogoMarginRight, nVideoLogoMarignTop);
//
//        mCanvas.concat(matrix);
        d.draw(mCanvas);

        return mBlendDesBitmap;
    }

    private void initTabLayout()
    {
        mTabLayout = (TabLayout)findViewById(R.id.designTabLayout);
        mTabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);



//        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                if(position < nCurPosition)
//                {
//                    //页面向左滑动
//                    mTabLayout.scrollTo(nCurScrollX - (int)positionOffset*nTabWidth,  0);
//                }else
//                {
//                    //像右滑动
//                    mTabLayout.scrollTo(nCurScrollX + (int)positionOffset*nTabWidth, 0);
//                }
//                Log.i(TAG, "onPageScrolled position = " + position + " positionOffset = " + positionOffset
//                        + " positionOffsetPixels = " + positionOffsetPixels
//                        + " nCurScrollX = " + nCurScrollX);
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                nCurPosition = position;
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//                if(state == ViewPager.SCROLL_STATE_SETTLING || state == ViewPager.SCROLL_STATE_IDLE)
//                {
//                    nCurScrollX = mTabLayout.getScrollX();
//                }
//                Log.i(TAG, "onPageScrollStateChanged state = " + state + " nCurScrollX = " + nCurScrollX);
//            }
//        });
        for(int i = 0; i < 20; i++)
        {
            String tab1 = "tab" + i;
            mTabLayout.addTab(mTabLayout.newTab().setText(tab1));
            TestFragment testFragment = TestFragment.newInstance(tab1);
            fragmentList.add(testFragment);
            titleList.add(tab1);
        }
        mViewPager.setAdapter(new TestFragmentAdapter(getSupportFragmentManager()));
        mTabLayout.setupWithViewPager(mViewPager);
        nCurScrollX = mTabLayout.getScrollX();


        //mViewPager.addOnPageChangeListener无效，因为tablayout内部会自动添加addOnPageChangeListener，造成多个实现，会引起时间冲突
//        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(mTabLayout)
//        {
//            @Override
//            public void onPageScrollStateChanged(int state) {
////                super.onPageScrollStateChanged(state);
//                if(state == ViewPager.SCROLL_STATE_SETTLING || state == ViewPager.SCROLL_STATE_IDLE)
//                {
//                    nCurScrollX = mTabLayout.getScrollX();
//                }
//                Log.i(TAG, "onPageScrollStateChanged state = " + state + " nCurScrollX = " + nCurScrollX);
//            }
//
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
////                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
//                int xScroll = (int)(positionOffset*nTabWidth);
//                if(position < nCurPosition)
//                {
//                    //页面向左滑动
//                    mTabLayout.scrollTo(nCurScrollX - xScroll,  0);
//                }else
//                {
//                    //像右滑动
//                    mTabLayout.scrollTo(nCurScrollX + xScroll, 0);
//                }
//                Log.i(TAG, "onPageScrolled position = " + position + " positionOffset = " + positionOffset
//                        + " positionOffsetPixels = " + positionOffsetPixels
//                        + " nCurScrollX = " + (nCurScrollX + xScroll));
//            }
//
//            @Override
//            public void onPageSelected(int position) {
////                super.onPageSelected(position);
//                nCurPosition = position;
//
//            }
//        });
    }

    @Override
    public void onSelectItem(int position) {
        mViewPager.setCurrentItem(position);
    }

    private class TestFragmentAdapter extends FragmentPagerAdapter
    {
        public TestFragmentAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return titleList.get(position);
        }
    }
}
