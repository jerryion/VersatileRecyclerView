package com.crazypumpkin.versatilerecyclerview.library;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 滚轮控件的RecyclerView版本
 * Created by CrazyPumPkin on 2016/12/3.
 */

public class WheelRecyclerView extends RecyclerView {

    private final int DEFAULT_WIDTH = Util.dp2px(160);

    private final int DEFAULT_ITEM_HEIGHT = Util.dp2px(30);

    private final int DEFAULT_SELECT_TEXT_COLOR = Color.parseColor("#2196F3");

    private final int DEFAULT_UNSELECT_TEXT_COLOR = Color.parseColor("#8A000000");

    private final int DEFAULT_SELECT_TEXT_SIZE = Util.sp2px(16);

    private final int DEFAULT_UNSELECT_TEXT_SIZE = Util.sp2px(16);

    private final int DEFAULT_OFFSET = 1;

    private final int DEFAULT_DIVIDER_WIDTH = -1;

    private final int DEFAULT_DIVIDER_HEIGHT = Util.dp2px(1);

    private final int DEFAULT_DIVIVER_COLOR = Color.parseColor("#BBDEFB");

    private WheelAdapter mAdapter;

    private LinearLayoutManager mLayoutManager;

    private List<String> mDatas;

    private int mItemHeight;

    private int mOffset;

    private int mSelectTextColor;

    private int mUnselectTextColor;

    private float mSelectTextSize;

    private float mUnselectTextSize;

    private float mDividerWidth;

    private float mDividerHeight;

    private int mDividerColor;

    private Paint mPaint;

    public WheelRecyclerView(Context context) {
        super(context);
    }

    public WheelRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        TypedArray ta = context.obtainStyledAttributes(attrs, R.styleable.WheelRecyclerView);

        mSelectTextColor = ta.getColor(R.styleable.WheelRecyclerView_selectTextColor, DEFAULT_SELECT_TEXT_COLOR);
        mUnselectTextColor = ta.getColor(R.styleable.WheelRecyclerView_unselectTextColor, DEFAULT_UNSELECT_TEXT_COLOR);
        mSelectTextSize = ta.getDimension(R.styleable.WheelRecyclerView_selectTextSize, DEFAULT_SELECT_TEXT_SIZE);
        mUnselectTextSize = ta.getDimension(R.styleable.WheelRecyclerView_unselectTextSize, DEFAULT_UNSELECT_TEXT_SIZE);
        mOffset = ta.getInteger(R.styleable.WheelRecyclerView_wheelOffset, DEFAULT_OFFSET);
        mDividerWidth = ta.getDimension(R.styleable.WheelRecyclerView_dividerWidth, DEFAULT_DIVIDER_WIDTH);
        mDividerHeight = ta.getDimension(R.styleable.WheelRecyclerView_dividerHeight, DEFAULT_DIVIDER_HEIGHT);
        mDividerColor = ta.getColor(R.styleable.WheelRecyclerView_dividerColor, DEFAULT_DIVIVER_COLOR);

        ta.recycle();

        mDatas = new ArrayList<>();
        mPaint = new Paint();
        mPaint.setColor(mDividerColor);
        mPaint.setStrokeWidth(mDividerHeight);
        mItemHeight = DEFAULT_ITEM_HEIGHT;

        init();
    }

    private void init() {
        mLayoutManager = new LinearLayoutManager(getContext());
        setLayoutManager(mLayoutManager);
        addItemDecoration(new DividerItemDecoration());
        mAdapter = new WheelAdapter();
        setAdapter(mAdapter);
        addOnScrollListener(new OnWheelScrollListener());
    }

    @Override
    protected void onMeasure(int widthSpec, int heightSpec) {
        int height;
        int heightSpecSize = MeasureSpec.getSize(heightSpec);
        int heightSpecMode = MeasureSpec.getMode(heightSpec);
        switch (heightSpecMode) {
            case MeasureSpec.EXACTLY:
                height = heightSpecSize;
                mItemHeight = height / (mOffset * 2 + 1);
                break;
            default:
                height = (mOffset * 2 + 1) * mItemHeight;
                break;
        }
        setMeasuredDimension(getDefaultSize(DEFAULT_WIDTH, widthSpec), height);
    }

    public void setData(List<String> datas) {
        mDatas.clear();
        mDatas.addAll(datas);
        mAdapter.notifyDataSetChanged();
    }


    private class WheelAdapter extends Adapter<WheelAdapter.WheelHolder> {

        @Override
        public WheelHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            WheelHolder holder = new WheelHolder(LayoutInflater.from(getContext()).inflate(R.layout.item_wheel, parent, false));
            holder.name.getLayoutParams().height = mItemHeight;
            return holder;
        }

        @Override
        public int getItemCount() {
            return mDatas.size() == 0 ? 0 : mDatas.size() + mOffset * 2;
        }

        @Override
        public void onBindViewHolder(WheelHolder holder, int position) {
            if (position < mOffset || position > mDatas.size() + mOffset - 1) {
                holder.name.setText("");
            } else {
                holder.name.setText(mDatas.get(position - mOffset));
            }
        }

        class WheelHolder extends ViewHolder {

            TextView name;

            public WheelHolder(View itemView) {
                super(itemView);
                name = (TextView) itemView.findViewById(R.id.tv_name);
            }
        }
    }

    private class OnWheelScrollListener extends OnScrollListener {
        @Override
        public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            super.onScrollStateChanged(recyclerView, newState);
            if (newState == RecyclerView.SCROLL_STATE_IDLE) {
                Rect rect = new Rect();
                mLayoutManager.findViewByPosition(mLayoutManager.findFirstVisibleItemPosition()).getHitRect(rect);
                if (Math.abs(rect.top) > mItemHeight / 2) {
                    smoothScrollBy(0, rect.bottom);
                } else {
                    smoothScrollBy(0, rect.top);
                }
            }
        }

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstVisiblePos = mLayoutManager.findFirstVisibleItemPosition();
            Rect rect = new Rect();
            mLayoutManager.findViewByPosition(firstVisiblePos).getHitRect(rect);
            boolean overScroll = Math.abs(rect.top) < mItemHeight / 2 ? false : true;
            if (overScroll) {
                for (int i = 1; i <= 1 + mOffset * 2; i++) {
                    TextView item = (TextView) mLayoutManager.findViewByPosition(firstVisiblePos + i);
                    if (i == mOffset + 1) {
                        item.setTextColor(mSelectTextColor);
                        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSelectTextSize);
                    } else {
                        item.setTextColor(mUnselectTextColor);
                        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnselectTextSize);
                    }
                }
            } else {
                for (int i = 0; i < 1 + mOffset * 2; i++) {
                    TextView item = (TextView) mLayoutManager.findViewByPosition(firstVisiblePos + i);
                    if (i == mOffset) {
                        item.setTextColor(mSelectTextColor);
                        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mSelectTextSize);
                    } else {
                        item.setTextColor(mUnselectTextColor);
                        item.setTextSize(TypedValue.COMPLEX_UNIT_PX, mUnselectTextSize);
                    }
                }
            }
        }
    }

    private class DividerItemDecoration extends ItemDecoration{
        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, State state) {
            float startX = mDividerWidth == DEFAULT_DIVIDER_WIDTH ? getMeasuredWidth() / 8 : getMeasuredWidth() / 2 - mDividerWidth / 2;
            float topY = mItemHeight * mOffset;
            float endX = mDividerWidth == DEFAULT_DIVIDER_WIDTH ? getMeasuredWidth() / 8 * 7 : getMeasuredWidth() / 2 + mDividerWidth / 2;
            float bottomY = mItemHeight * (mOffset + 1);

            c.drawLine(startX, topY, endX, topY, mPaint);
            c.drawLine(startX, bottomY, endX, bottomY, mPaint);
        }
    }

}
