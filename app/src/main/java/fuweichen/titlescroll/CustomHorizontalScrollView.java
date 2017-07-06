package fuweichen.titlescroll;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static android.os.Build.VERSION_CODES.M;

/**
 * Created by Administrator on 2017/7/5.
 */

public class CustomHorizontalScrollView extends HorizontalScrollView implements ViewPager.OnPageChangeListener{
    private static  final String TAG = "CustomScrollView";
    private int bgColor = Color.WHITE;
    private int itemPaddingLeft = 10;
    private int itemPaddingRight = 10;
    private int itemDefaultColor = Color.BLACK;
    private int itemSelectColor = Color.RED;
    private int itemIndicateColor = Color.RED;
    private int itemIndicateHeight = 2;

    private LinearLayout lyHorizontalView;
    private int nCurSelectPosition = 0;
    private int curItemIndicateLeft = 0;
    private int curItemIndicateWidth = 0;
    private int curItemScorllLeft = 0;

    private Paint mPaint;

    private OnSelectItemListener onSelectItemListener;

    public void setOnSelectItemListener(OnSelectItemListener onSelectItemListener)
    {
        this.onSelectItemListener = onSelectItemListener;
    }

    public void setBgColor(int bgColor)
    {
        this.bgColor = bgColor;
        setBackgroundColor(bgColor);
        invalidate();
    }

    public void setItemIndicateColor(int itemIndicateColor)
    {
        this.itemIndicateColor = itemIndicateColor;
    }

    public void setItemIndicateHeight(int itemIndicateHeight)
    {
        this.itemIndicateHeight = itemIndicateHeight;
        invalidate();
    }

    public void setItemPaddingLeft(int itemPaddingLeft)
    {
        this.itemPaddingLeft = itemPaddingLeft;
    }

    public void setItemPaddingRight(int itemPaddingRight)
    {
        this.itemPaddingRight = itemPaddingRight;
    }

    public void setItemDefaultColor(int itemDefaultColor)
    {
        this.itemDefaultColor = itemDefaultColor;
    }

    public void setItemSelectColor(int itemSelectColor)
    {
        this.itemSelectColor = itemSelectColor;
    }

    public CustomHorizontalScrollView(Context context) {
        this(context, null);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CustomHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context)
    {
        lyHorizontalView = new LinearLayout(context);
        lyHorizontalView.setOrientation(LinearLayout.HORIZONTAL);
        lyHorizontalView.setGravity(Gravity.CENTER_VERTICAL);
        lyHorizontalView.setBackgroundColor(Color.TRANSPARENT);
        addView(lyHorizontalView);
    }

    public void setItems(final List<String> items)
    {
        if(null == items && items.size() <= 0)
        {
            Log.d(TAG, "setItems: items is null");
            return;
        }
        for(final String item : items) {
            TextView tv = new TextView(getContext());
            tv.setText(item);
            tv.setTextColor(itemDefaultColor);
            tv.setGravity(Gravity.CENTER);
            tv.setPadding(itemPaddingLeft, 0, itemPaddingRight, 0);
            lyHorizontalView.addView(tv, new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.MATCH_PARENT));

            tv.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    nCurSelectPosition = items.indexOf(item);
                    if(null != onSelectItemListener)
                    {
                        onSelectItemListener.onSelectItem(nCurSelectPosition);
                    }
                    updateItemTab();
                }
            });
        }
        nCurSelectPosition = 0;
        nSaveSelectPosition = 0;
        updateItemTab();
        postDelayed(new Runnable() {
            @Override
            public void run() {
                setIndicateStatus();
                invalidate();
            }
        }, 500);
    }

    private void setIndicateStatus()
    {
        //首次初始化indicate标志位
        TextView tv = (TextView)lyHorizontalView.getChildAt(nCurSelectPosition);
        curItemIndicateLeft = tv.getLeft();
        curItemIndicateWidth = tv.getWidth();
    }

    private void updateItemTab()
    {
        int nChildCount = lyHorizontalView.getChildCount();
        for(int i = 0; i < nChildCount; i++)
        {
            TextView tv = (TextView)lyHorizontalView.getChildAt(i);
            tv.setTextColor(i == nCurSelectPosition ? itemSelectColor : itemDefaultColor);
        }
    }

    private SCROLLSTATE scrollState = SCROLLSTATE.NONE;
    private boolean bScrolling = false;
    private int nSaveSelectPosition = 0;
    private int nScrollPosition = 0;

    enum SCROLLSTATE
    {
        NONE,
        PRE,
        NEXT
    }

    private void twoWayPrePage(float positionOffset)
    {
        TextView tvPositionView = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition);
        int preWidth = (int)(tvPositionView.getWidth() * (1-positionOffset) / 2);
        curItemScorllLeft = (int) (curItemIndicateLeft - preWidth);
        scrollTo(nScrollPosition - preWidth, 0);
    }

    private void twoWayNextPage(float positionOffset)
    {
        TextView tvPositionView = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition);
        int nextWidth = (int)(tvPositionView.getWidth() * positionOffset / 2);
        scrollTo(nScrollPosition + nextWidth, 0);
        curItemScorllLeft = (int) (curItemIndicateLeft + nextWidth + nextWidth);
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
        Log.i(TAG, "onPageScrolled() called with: position = [" + position + "], positionOffset = [" + positionOffset + "], positionOffsetPixels = [" + positionOffsetPixels + "], nCurSelectPosition = [" + nCurSelectPosition + "]");
        if(bScrolling && positionOffsetPixels > 0) {
            //在确定滑向下一页时，position < nCurSelectPosition，因此需要加scrollState != SCROLLSTATE.NEXT这个条件
            if (scrollState == SCROLLSTATE.PRE || (scrollState != SCROLLSTATE.NEXT && position < nCurSelectPosition)) {
                //向前一页滑动
                TextView tvPositionView = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition);
                TextView tvPrePositionView = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition - 1);


                int nextWidth = (int)(tvPrePositionView.getWidth() * (1 - positionOffset) + 0.5);
                int morWidth = (int)((tvPrePositionView.getWidth() - tvPositionView.getWidth()) * (1 - positionOffset) + 0.5);
                curItemIndicateWidth = (int)(tvPositionView.getWidth() + morWidth);

                TextView tvFirstPositionView = (TextView)lyHorizontalView.getChildAt(0);
                if (curItemIndicateLeft - nextWidth <= tvFirstPositionView.getWidth() / 2) {
                    curItemScorllLeft = curItemIndicateLeft - nextWidth;
                }else
                {
                    nScrollPosition = curItemIndicateLeft - nextWidth - tvFirstPositionView.getWidth() / 2;
                    scrollTo(nScrollPosition, 0);
                    Log.d(TAG, " nScrollPosition = " + nScrollPosition + " curItemIndicateLeft = " + curItemIndicateLeft + " nextWidth = " + nextWidth + " positionOffset = " + positionOffset);
                    curItemScorllLeft = curItemIndicateLeft - nextWidth;
                }
                invalidate();
            } else if (scrollState == SCROLLSTATE.NEXT || position == nCurSelectPosition) {
                //向下一页滑动
                TextView tvPositionView = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition);
                TextView tvNextPositionView = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition + 1);
                int nextWidth = (int)(tvPositionView.getWidth() * positionOffset + 0.5);
                int morWidth = (int)((tvNextPositionView.getWidth() - tvPositionView.getWidth()) * positionOffset + 0.5);
                curItemIndicateWidth = (int)(tvPositionView.getWidth() + morWidth);

                TextView tvFirstPositionView = (TextView)lyHorizontalView.getChildAt(0);
                if (curItemIndicateLeft + nextWidth <= tvFirstPositionView.getWidth() / 2) {
                    curItemScorllLeft = curItemIndicateLeft + nextWidth;
                    invalidate();
                }else
                {
                    nScrollPosition = curItemIndicateLeft + nextWidth - tvFirstPositionView.getWidth() / 2;
                    scrollTo(nScrollPosition, 0);
                    curItemScorllLeft = curItemIndicateLeft + nextWidth;
                    invalidate();
                }
            }
        }
    }

    @Override
    public void onPageSelected(int position) {
        Log.d(TAG, "onPageSelected() called with: position = [" + position + "]");

        //以下判断是在手指滑动释放后，来判断是滑向下一页，还是滑向前一页
        if(position > nCurSelectPosition)
        {
            scrollState = SCROLLSTATE.NEXT;
        }else if(position < nCurSelectPosition)
        {
            scrollState = SCROLLSTATE.PRE;
        }else
        {
            scrollState = SCROLLSTATE.NONE;
        }
        nCurSelectPosition = position;
        updateItemTab();
    }

    @Override
    public void onPageScrollStateChanged(int state) {
        Log.d(TAG, "onPageScrollStateChanged() called with: state = [" + state + "]");
        if(state == ViewPager.SCROLL_STATE_IDLE)
        {
            scrollState = SCROLLSTATE.NONE;
            nSaveSelectPosition = nCurSelectPosition;
            nScrollPosition = getScrollX();
            curItemIndicateLeft = curItemScorllLeft;
//            setIndicateStatus();
            invalidate();
        }
        bScrolling = state != ViewPager.SCROLL_STATE_IDLE;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(lyHorizontalView.getChildCount() > 0)
        {
            TextView tv = (TextView)lyHorizontalView.getChildAt(nSaveSelectPosition);

            int left = curItemScorllLeft;
            int right = left + curItemIndicateWidth;
            int bottom = tv.getBottom();
            int top = getHeight() - itemIndicateHeight;
            canvas.save();
            canvas.clipRect(left, top, right, bottom);
            canvas.drawColor(itemIndicateColor);
            canvas.restore();
//            if(null == mPaint)
//            {
//                mPaint = new Paint();
//            }
//            mPaint.setColor(itemIndicateColor);
//            mPaint.setStyle(Paint.Style.FILL);
//            canvas.drawRect(left, top, right, bottom, mPaint);
        }
    }

    public interface OnSelectItemListener
    {
        void onSelectItem(int position);
    }
}
