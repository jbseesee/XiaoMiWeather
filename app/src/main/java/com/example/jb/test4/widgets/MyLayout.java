package com.example.jb.test4.widgets;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import com.example.jb.test4.ui.addCityPage.MyLayoutAdapter;

public class MyLayout extends ViewGroup {

    private MyLayoutAdapter adapter;

    private MyLayoutAdapter.OnItemClickListener onItemClickListener;

    private int position;


    int[] childViewLeft;
    int[] childViewTop;
    int[] childViewRight;
    int[] childViewbottom;
    public MyLayout(Context context) {
        super(context);
    }

    public MyLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        //获得父view给本view设置的宽高和模式
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);

        int width = 0;
        int height= 0;
        int lineWidth = getPaddingLeft();//每一行的总宽
        int lineHeight = 0;//每一行的总高
        int baseLineTop = getPaddingTop();//总是等于上一行的底部；

        int childViewCount = getChildCount();
        childViewLeft = new int[childViewCount];
        childViewTop = new int[childViewCount];
        childViewRight = new int[childViewCount];
        childViewbottom = new int[childViewCount];

        for (int i = 0; i < childViewCount;i++){
            View childView = getChildAt(i);
            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
            MarginLayoutParams childMLP = (MarginLayoutParams)childView.getLayoutParams();

            //子view的总宽
            int childViewWight = childView.getMeasuredWidth() + childMLP.leftMargin + childMLP.rightMargin;
            int childViewHeight = childView.getMeasuredHeight() + childMLP.topMargin + childMLP.bottomMargin;


            if (childViewWight + lineWidth > widthSize){

                width = Math.max(width,lineWidth + getPaddingRight());
                height += lineHeight;
                baseLineTop = height;

                lineWidth = childViewWight + getPaddingLeft() + getPaddingRight();
                lineHeight = childViewHeight;

                childViewLeft[i] = getPaddingLeft();
                childViewTop[i] = baseLineTop;
                childViewRight[i] = childViewLeft[i] + childView.getMeasuredWidth();
                childViewbottom[i] = childViewTop[i] + childView.getMeasuredHeight();

            }else {
                childViewLeft[i] = lineWidth;
                childViewTop[i] = baseLineTop;
                childViewRight[i] = childViewLeft[i] + childView.getMeasuredWidth();
                childViewbottom[i] = childViewTop[i] + childView.getMeasuredHeight();
                lineWidth += childViewWight;
                lineHeight = Math.max(childViewHeight,lineHeight);

            }

            if (i == getChildCount() - 1){
                height += lineHeight + getPaddingTop() + getPaddingBottom();
                width = Math.max(width,lineWidth);
            }


        }
        setMeasuredDimension((widthMode == MeasureSpec.EXACTLY) ? widthSize
                : width, (heightMode == MeasureSpec.EXACTLY) ? heightSize
                : height);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        for (int i = 0; i < getChildCount(); i ++){
            View view = getChildAt(i);
            view.layout(childViewLeft[i],childViewTop[i],childViewRight[i],childViewbottom[i]);
        }
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs)
    {
        return new MarginLayoutParams(getContext(), attrs);
    }

    public void setAdapter(MyLayoutAdapter adapter){
        this.adapter = adapter;
        position = 0;
        for (int i = 0; i < adapter.getCount();i++){
            View view = adapter.getView(position,null,this);
            position++;
            addView(view);
        }
        requestLayout();
    }

    public void setOnItemClickListener(MyLayoutAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
        adapter.setOnItemClickListener(this.onItemClickListener);

    }
}
