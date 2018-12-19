package com.example.jb.test4.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Scroller;


import com.example.jb.test4.bean.homeBean.ChatViewData;
import com.example.jb.test4.util.LoadIconUtil;
import com.example.jb.test4.util.Util;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * Created by 666 on 2018/3/30.
 */

public class ChatView2 extends View {

    private int dispatchLastX;
    private int dispatchLastY;

    private String[] time     = new String[49];
    private String[] weather  = new String[49];
    private int[] temperature = new int[49];
    private int[] aqi         = new int[49];
    private String[] wind_speed  = new String[49];
    private int[] directIcon  = new int[49];
    private String[] aqiLevel = new String[49];
    private int[] aqiColor = new int[49];

    private float radius = Util.dpToPixelF(3);
    private float x = Util.dpToPixelF(50);//
    private float y = Util.dpToPixelF(100);//
    private float lineWidth = Util.dpToPixelF(0.5f);//时间线线宽
    private float lineWidth2 = Util.dpToPixelF(1);//折线线宽
    private float spacingX = Util.dpToPixelF(60);//两个点之间的间距
    private float distanceX = Util.dpToPixelF(30);//时间线的起点
    private float totalX = Util.dpToPixelF(2960);// 时间线总长
    private float distanceY = Util.dpToPixelF(225);//时间线的起点
    private float temp = Util.dpToPixelF(2);
    private float minX = Util.dpToPixelF(22);//图标移动的最小区间
    private float lastX = 0;//记录上一个触摸点；
    private float disX = 0;
    private ArrayList<Integer> flag;//记录虚线x坐标
    private int measureWidth;

    private int mMinFlingSpeed;
    private int mMaxFlingSpeed;
    private VelocityTracker mVelocityTracker ;
    private int pointId;

    private Scroller scroller;
    private Paint mpaint;
    private Paint dottedPaint;
    private Paint polyLinePaint;
    private Paint circlePaint;
    private Paint coverPaint;
    private Paint timePaint;
    private Paint apiPaint;
    private Paint apiTestPaint;
    private Paint windLevel;
    private Paint table;

    private Path dottedPath; // 虚线路径1
    private Path dottedPath2; // 虚线路径2


    private RectF rectF  ; // aqi的rect
    private RectF rectF2 ; // 风向的rect

    private RectF rectF3 ; // 天气图标的rect
    private Bitmap bitmap2 ;
    private Rect  rect2 ;
    private Bitmap[] bitmaps;
    private List<Bitmap> bitmaps2;


    private float[] polyLines = new float[196];
    private float[] circles   = new float[98];
    private float[] times     = new float[196];
    private float[] rectFs   = new float[196];
    private float[] rectF2s   = new float[196];
    private float[] temTxs    = new float[98];
    private float[] timeTxs   = new float[98];
    private float[] windLTxs  = new float[98];
    private float[] aqiTx     = new float[98];

    private boolean hadData = false;

    private Context context;

    public ChatView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        scroller = new Scroller(context);
    }
    public void setData(ChatViewData chatViewData){
        init();
        time = chatViewData.getTime();
        weather = chatViewData.getWeather();
        temperature = chatViewData.getTemperature();
        aqi = chatViewData.getAqi();
        wind_speed = chatViewData.getWind_speed();
        directIcon = chatViewData.getDirectIcon();
        aqiLevel = chatViewData.getAqiLevel();
        aqiColor = chatViewData.getAqiColor();
        pathCalculation();
        hadData = true;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = (int) Util.dpToPixelF(270);

        height = resolveSize(height,heightMeasureSpec);

        setMeasuredDimension(width,height);

        measureWidth = width;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hadData) {
            canvas.drawText("逐小时预报",measureWidth / 2, Util.dpToPixelF(30),table);
            canvas.drawLine(distanceX, distanceY, totalX, distanceY, mpaint);//画时间线
            canvas.drawPath(dottedPath, dottedPaint);
            canvas.drawPath(dottedPath2, dottedPaint);
            canvas.drawLines(times, timePaint);
            drawPre(canvas);
            for (int i = 1; i < temperature.length; i++) {
                canvas.drawLine(polyLines[i * 4], polyLines[i * 4 + 1], polyLines[i * 4 + 2], polyLines[i * 4 + 3], polyLinePaint);
                canvas.drawCircle(circles[i * 2], circles[i * 2 + 1], Util.dpToPixelF(5f), coverPaint);
                canvas.drawCircle(circles[i * 2], circles[i * 2 + 1], radius, circlePaint);
                canvas.drawText(temperature[i] + "℃", temTxs[i * 2], temTxs[i * 2 + 1], timePaint);
                canvas.drawText(time[i], timeTxs[i * 2], timeTxs[i * 2 + 1], timePaint);
                canvas.drawText(wind_speed[i], windLTxs[i * 2], windLTxs[i * 2 + 1], windLevel);
                rectF.set(rectFs[i * 4], rectFs[i * 4 + 1], rectFs[i * 4 + 2], rectFs[i * 4 + 3]);
                rectF2.set(rectF2s[i * 4], rectF2s[i * 4 + 1], rectF2s[i * 4 + 2], rectF2s[i * 4 + 3]);
                apiPaint.setColor(aqiColor[i]);
                canvas.drawRoundRect(rectF, Util.dpToPixelF(3), Util.dpToPixelF(3), apiPaint);
                canvas.drawText(aqi[i] + " " + aqiLevel[i], aqiTx[i * 2], aqiTx[i * 2 + 1], apiTestPaint);
                canvas.drawBitmap(bitmaps[i], rect2, rectF2, null);
            }
            for (int i = 0; i < flag.size() - 1; i++) {
                rectF3.set(WeatherIconPositionX(i) - Util.dpToPixelF(10), Util.dpToPixelF(140),
                        WeatherIconPositionX(i) + Util.dpToPixelF(10), Util.dpToPixelF(160));
                canvas.drawBitmap(bitmaps2.get(i), rect2, rectF3, null);
            }
        }

    }
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float startX = event.getX();
        int index = -1;


        if (mVelocityTracker == null){
            mVelocityTracker = VelocityTracker.obtain();
        }
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                index = event.getActionIndex();
                pointId = event.getPointerId(index);
                if (!scroller.isFinished()){
                    scroller.abortAnimation();
                }
                break;
            case MotionEvent.ACTION_MOVE:
                event.findPointerIndex(pointId);
                int dis = (int)(startX - lastX) ;
                if (getScrollX()<=0 && dis >= 0){
                    break;
                }else if (getScrollX() >= Util.dpToPixelF(2630) && dis <= 0 ){
                    break;
                }else {
                    scrollBy(-dis,0);
                }


                break;
            case MotionEvent.ACTION_UP:

                mVelocityTracker.computeCurrentVelocity(1000,mMaxFlingSpeed);
                int initialVelocity = (int)mVelocityTracker.getXVelocity(pointId);
                if (Math.abs(initialVelocity) > mMinFlingSpeed){
                    scroller.fling(getScrollX(),0,-initialVelocity,0,0,(int)Util.dpToPixelF(2630),0,0);
                    invalidate();
                }
                if (mVelocityTracker != null) {
                    mVelocityTracker.clear();
                }
                break;
        }
        lastX = startX;
        if (mVelocityTracker != null) {
            mVelocityTracker.addMovement(event);
        }
        return true;
    }

    private float WeatherIconPositionX(int i){
        disX = getScrollX();
        float leftLocationXQ  = x +      flag.get(i)     * spacingX;
        float rightLocationXQ = x +      flag.get(i + 1) * spacingX;
        float leftLocationXM  = x+disX + flag.get(i)     * spacingX;
        float rightLocationXM = x+disX + flag.get(i + 1) * spacingX;
        float leftLocationX   = x-disX + flag.get(i)     * spacingX;
        float rightLocationX  = x-disX + flag.get(i + 1) * spacingX;
        int flag2 = -1;
        if ((leftLocationX <= 0 && rightLocationX <= 0) || (leftLocationX >= measureWidth &&rightLocationX >= measureWidth))//在屏幕外
            flag2 = 0;
        else if (leftLocationX >= 0 && rightLocationX >= measureWidth)//进入屏幕
            flag2 = 1;
        else if (leftLocationX >= 0 && rightLocationX <= measureWidth)//完全在屏幕
            flag2 = 2;
        else if (leftLocationX <= 0 && rightLocationX >= measureWidth)//屏幕在边界线内
            flag2 = 3;
        else if (leftLocationX <= 0 && rightLocationX <= measureWidth)//退出屏幕
            flag2 = 4;
        switch (flag2){
            case 0: return (leftLocationXQ + rightLocationXQ) / 2;

            case 1: if (measureWidth - leftLocationX <= minX)
                return (leftLocationXQ +(leftLocationXQ + minX)) / 2;
            else
                return (leftLocationXM + measureWidth) / 2;

            case 2: return (leftLocationXQ + rightLocationXQ) / 2;

            case 3: return (measureWidth / 2 + disX);

            case 4: if (rightLocationX > minX)
                return rightLocationXM / 2;
            else
                return (rightLocationXQ + rightLocationXQ - minX) / 2;




        }

        return 0;

    }
    private void init(){
        ViewConfiguration configuration = ViewConfiguration.get(context);
        mMinFlingSpeed = configuration.getScaledMinimumFlingVelocity();
        mMaxFlingSpeed = configuration.getScaledMaximumFlingVelocity();
        mpaint = new Paint(Paint.ANTI_ALIAS_FLAG);//普通画笔（时间线和时间点）
        dottedPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//虚线画笔
        polyLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);//折线画笔
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        coverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);//遮盖画笔
        timePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        apiPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        apiTestPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        windLevel = new Paint(Paint.ANTI_ALIAS_FLAG);
        table = new Paint(Paint.ANTI_ALIAS_FLAG);//标题
        dottedPath = new Path();//虚线路径
        dottedPath2 = new Path();//单个Path太大会无法绘制，所以要分成两段
        mpaint.setStyle(Paint.Style.STROKE);
        mpaint.setStrokeWidth(lineWidth);
        //mpaint.setStrokeCap(Paint.Cap.ROUND);
        mpaint.setColor(0xffb9b9b9);
        polyLinePaint.setStyle(Paint.Style.STROKE);
        polyLinePaint.setColor(0xff34c7f2);
        polyLinePaint.setStrokeWidth(lineWidth2);
        dottedPaint.setStyle(Paint.Style.STROKE);
        dottedPaint.setStrokeWidth(lineWidth);
        dottedPaint.setColor(0xffb9b9b9);
        PathEffect effect = new DashPathEffect(new float[]{Util.dpToPixelF(5f), Util.dpToPixelF(5f)}, 0);//虚线effect
        dottedPaint.setPathEffect(effect);

        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setColor(0xff34c7f2);
        circlePaint.setStrokeWidth(Util.dpToPixelF(1.5f));

        coverPaint.setStyle(Paint.Style.FILL);
        coverPaint.setColor(Color.WHITE);

        timePaint.setStyle(Paint.Style.FILL);
        timePaint.setStrokeWidth(lineWidth);

        timePaint.setTextAlign(Paint.Align.CENTER);
        timePaint.setTextSize(Util.spToPx(13));
        timePaint.setColor(0xff696969);
        apiTestPaint.setTextSize(Util.spToPx(10));
        apiTestPaint.setColor(Color.WHITE);
        apiTestPaint.setTextAlign(Paint.Align.CENTER);
        windLevel.setTextSize(Util.spToPx(13));
        windLevel.setColor(0xffc3c3c3);
        windLevel.setTextAlign(Paint.Align.CENTER);
        table.setTextSize(Util.spToPx(13));
        table.setColor(Color.BLACK);
        table.setTextAlign(Paint.Align.CENTER);
        rectF = new RectF(0,0,0,0);
        rectF2 = new RectF(0,0,0,0);
        rectF3 = new RectF(0,0,0,0);
    }

    private void pathCalculation(){
        bitmaps = getBitmaps(directIcon);
        bitmaps2 = new ArrayList<>();
        flag = new ArrayList<>();//记录虚线x坐标
        bitmap2 = BitmapFactory.decodeResource(getResources(), LoadIconUtil.skycon(weather[0]));
        rect2 = new Rect(0,0,bitmap2.getWidth(),bitmap2.getHeight());
        flag.add(0);
        bitmaps2.add(bitmap2);
        float temperatureDifferenc = 0;//温差
        float tempY = y;// 用于记录上一个时间温度点坐标的Y点，以便于作为path开始点的Y
        dottedPath.moveTo(x, y + Util.dpToPixelF(5f));
        dottedPath.lineTo(x, Util.dpToPixelF(165));
        for (int i =0; i<weather.length; i++) {
            if (i < weather.length - 1) {
                temperatureDifferenc = temperature[i + 1] - temperature[i];
            }
            float startX = x + i * spacingX;
            float startY = tempY;
            float stopX = x + (i + 1) * spacingX;
            float stopY = startY - (temperatureDifferenc * temp);
            tempY = stopY;
            if (i < weather.length - 1  ) {
                polyLines[i*4]     = startX;
                polyLines[i*4 + 1] = startY;
                polyLines[i*4 + 2] = stopX;
                polyLines[i*4 + 3] = stopY;
            }
            circles[i*2]     = startX;
            circles[i*2 + 1] = startY;
            times[i*4]     = startX;
            times[i*4 + 1] = distanceY;
            times[i*4 + 2] = startX;
            times[i*4 + 3] = Util.dpToPixelF(228);
            temTxs[i*2]     = startX;
            temTxs[i*2 + 1] = startY - Util.dpToPixelF(10);
            timeTxs[i*2]     = startX;
            timeTxs[i*2 + 1] = Util.dpToPixelF(248);
            windLTxs[i*2] = startX + Util.dpToPixelF(6);
            windLTxs[i*2 + 1] = Util.dpToPixelF(185);
            aqiTx[i*2] = startX;
            aqiTx[i*2 + 1] = Util.dpToPixelF(207f);
            rectFs[i*4] = Util.dpToPixelF(25) + i * spacingX;
            rectFs[i*4+1] = Util.dpToPixelF(195);
            rectFs[i*4+2] = Util.dpToPixelF(75) + i * spacingX;
            rectFs[i*4+3] =Util.dpToPixelF(212);
            rectF2s[i*4] = Util.dpToPixelF(35) + i * spacingX;
            rectF2s[i*4+1] = Util.dpToPixelF(175);
            rectF2s[i*4+2] = Util.dpToPixelF(45) + i * spacingX;
            rectF2s[i*4+3] =Util.dpToPixelF(185);
            if (i>0) {
                if (!weather[i].equals(weather[i - 1]) || i == weather.length-1) {
                    if (i < 24) {
                        dottedPath.moveTo(startX, startY + Util.dpToPixelF(5f));
                        dottedPath.lineTo(startX, Util.dpToPixelF(165));
                    } else {
                        dottedPath2.moveTo(startX, startY + Util.dpToPixelF(5f));
                        dottedPath2.lineTo(startX, Util.dpToPixelF(165));
                    }
                    flag.add(i);
                }
                if (!weather[i].equals(weather[i - 1])){
                    bitmaps2.add(BitmapFactory.decodeResource(getResources(),LoadIconUtil.skycon(weather[i])));
                }
            }

        }
    }

    @Override
    public void computeScroll() {
        if (scroller.computeScrollOffset()){
            scrollTo(scroller.getCurrX(),scroller.getCurrY());
            postInvalidate();
        }
    }

    private void drawPre(Canvas canvas){
        polyLinePaint.setColor(0xffc3c3c3);
        circlePaint.setColor(0xffc3c3c3);
        coverPaint.setColor(Color.WHITE);
        canvas.drawLine(polyLines[0],polyLines[1],polyLines[2],polyLines[3],polyLinePaint);
        canvas.drawCircle(circles[0],circles[1],Util.dpToPixelF(5),coverPaint);
        canvas.drawCircle(circles[0],circles[1],radius,circlePaint);
        timePaint.setColor(0xffb9b9b9);
        canvas.drawText(temperature[0] + "℃",temTxs[0],temTxs[1],timePaint);
        canvas.drawText(time[0],timeTxs[0],timeTxs[1],timePaint);
        apiPaint.setColor(0xffdbdbdb);
        windLevel.setColor(0xffb2b2b2);
        rectF.set(rectFs[0],rectFs[1],rectFs[2],rectFs[3]);
        canvas.drawRoundRect(rectF,Util.dpToPixelF(3),Util.dpToPixelF(3),apiPaint);
        canvas.drawText(aqi[0] + " " + aqiLevel[0],aqiTx[0],aqiTx[1],apiTestPaint);
        canvas.drawText(wind_speed[0],windLTxs[0],windLTxs[1],windLevel);
        rectF2.set(rectF2s[0], rectF2s[1],rectF2s[2],rectF2s[3]);
        canvas.drawBitmap(bitmaps[0],rect2,rectF2,null);
        polyLinePaint.setColor(0xff34c7f2);
        circlePaint.setColor(0xff34c7f2);
        timePaint.setColor(0xff696969);
        windLevel.setColor(0xffc3c3c3);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        int x = (int)ev.getX();
        int y = (int) ev.getY();
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:

                break;
            case MotionEvent.ACTION_MOVE:

                int detalX = x - dispatchLastX;
                int detalY = y - dispatchLastY;

                //滑动的水平距离大于竖直距离，请求父View不要拦截移动事件；
                if (Math.abs(detalY) < Math.abs(detalX)) {
                    getParent().requestDisallowInterceptTouchEvent(true);
                } else
                    break;
            case MotionEvent.ACTION_UP:
                break;
        }

        dispatchLastX = x;
        dispatchLastY = y;
        return super.dispatchTouchEvent(ev);
    }

    private Bitmap[] getBitmaps(int[] iconId){

        Map<Integer,Bitmap> bitmapMap = new HashMap<>() ;
        bitmapMap.put(iconId[0],BitmapFactory.decodeResource(getResources(),iconId[0]));
        for (int i = 1; i < iconId.length; i++){
            if (!bitmapMap.containsKey(iconId[i])){
                bitmapMap.put(iconId[i],BitmapFactory.decodeResource(getResources(),iconId[i]));
            }
        }

        Bitmap[] bitmaps = new Bitmap[iconId.length];

        for (int i = 0; i < iconId.length; i++){
            bitmaps[i] = bitmapMap.get(iconId[i]);
        }

        return bitmaps;
    }
}

