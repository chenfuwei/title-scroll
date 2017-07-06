package fuweichen.titlescroll;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;



/**
 * ʵ���������ŵ�����Ч��
 * @author xll
 */
public class TestActivity extends FragmentActivity {
	
	private static final String TAG = "MainActivity.this";
	
	/** ����Tab*/
	private PagerSlidingTabStrip tab;
	/** �������������viewPager*/
	private ViewPager viewPager;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_test);
		InitTabInfo();
	}

	private void InitTabInfo() {
		tab = (PagerSlidingTabStrip) findViewById(R.id.tabs);
		//����tab����ɫ
//		tab.setBackgroundColor(Color.BLUE);
		//����tab��͸����
//		tab.setAlpha(0.7f);
		//AndroidϵͳĬ��֧���������壬�ֱ�Ϊ����sans��, ��serif��, ��monospace"������֮�⻹����ʹ�����������ļ���*.ttf��
//		Typeface typeface = Typeface.defaultFromStyle(R.style.textAppearance);
//		tab.setTypeface(typeface,R.style.textAppearance);
		//ѡ�е�tabλ�ù�������һλ
		tab.setScrollOffset(0);
		// true ��д  false Сд
		tab.setShouldExpand(true);
		tab.setIndicatorHeight(10);//����ָʾ���ĸ߶�
		//����ָʾ������ɫ
		tab.setIndicatorColor(getResources().getColor(R.color.red));
		//tab������ߵ���ɫ
		tab.setDividerColor(getResources().getColor(R.color.huise));
		//tabδѡ��ʱ��������ɫ
		tab.setTextColor(getResources().getColor(R.color.heise));
		//tab ѡ��ʱ������ɫ
		tab.setSelectedTextColor(getResources().getColor(R.color.red));
		viewPager = (ViewPager) findViewById(R.id.viewPager);
		viewPager.setAdapter(new TabAdapter(getSupportFragmentManager()));
		//��ViewPager���ö���
//		viewPager.setAnimation(animation);
		tab.setViewPager(viewPager);
//		viewPager.setOnPageChangeListener(new MyPageChangeListener());
		tab.setOnPageChangeListener(new MyPageChangeListener());
	}

	/**
	 * Tab ���ü���
	 */
	class MyPageChangeListener implements OnPageChangeListener{
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onPageScrollStateChanged");
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onPageScrolled");
		}

		@Override
		public void onPageSelected(int arg0) {
			// TODO Auto-generated method stub
			Log.i(TAG, "onPageSelected");
			int position = tab.getChildCount();
			Log.i(TAG, position+"====position");
		}
	}
	
	
	
	
	/**
	 * ��ʼ��Fragment
	 */
	class TabAdapter extends FragmentStatePagerAdapter{
		/** ������������*/
		private  String[] titles={"����","���","����","ͷ��","����","�ƾ�"};

		public TabAdapter(FragmentManager fm) {
			super(fm);
			// TODO Auto-generated constructor stub
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment fragment = new TabFragment(titles[arg0]);
			return fragment;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return titles.length==0?0:titles.length;
		}
		
		/**
		 * ����CharSequence �˷�����д
		 */
		@Override
		public CharSequence getPageTitle(int position) {
			// TODO Auto-generated method stub
			return (titles[position]==null||titles[position].equals(""))?null:titles[position];
		}
	}

}
