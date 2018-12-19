package com.example.jb.test4.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.jb.test4.bean.homeBean.DailyFroecastData;
import com.example.jb.test4.util.Util;

import java.util.ArrayList;
import java.util.List;

public class DailyView extends View {

    private Paint p1;//time(week),weather,windDirection;
    private Paint p2;//temperature
    private Paint p3;//windLevel;
    private Paint p4;//time(detail);
    private Paint p5;//aqi
    private Paint pChat;//chatLine;
    private Paint pC;//circle;
    private Paint pL;//line
    private List<Bitmap> bitmaps1;
    private List<Bitmap> bitmaps2;

    private Rect rect;
    private List<RectF> rectFs1;
    private List<RectF> rectFs2;

    private List<RectF> rectsA;

    private float[][] timeWL = new float[5][5];
    private float[][] timeDL = new float[5][5];
    private float[][] weatherL = new float[5][5];
    private float[][] weather20hL = new float[5][5];
    private float[][] windDL = new float[5][5];
    private float[][] windLL = new float[5][5];
    private float[][] aqiTL = new float[5][5];
    private float[][] tMaxL = new float[5][5];
    private float[][] tMinL = new float[5][5];
    private float[][] lMaxL = new float[5][5];
    private float[][] lMinL = new float[5][5];

    private float iconW = Util.dpToPixelF(25);
    private float iconH = Util.dpToPixelF(25);

    private float left = Util.dpToPixelF(45);//第一个时间（今天）距离左边界；
    private float top = Util.dpToPixelF(110);//第一个时间（今天）距离上边界；

    private DailyFroecastData dailyFroecastData;
    private boolean hasData ;


    public DailyView(Context context) {
        super(context);
    }

    public DailyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DailyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setData(DailyFroecastData dailyFroecastData){
        this.dailyFroecastData = dailyFroecastData;
        init();
        hasData = true;
    }

    private void init() {
        p1 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p3 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p4 = new Paint(Paint.ANTI_ALIAS_FLAG);
        p5 = new Paint(Paint.ANTI_ALIAS_FLAG);
        pChat = new Paint(Paint.ANTI_ALIAS_FLAG);
        pC = new Paint(Paint.ANTI_ALIAS_FLAG);
        pL = new Paint(Paint.ANTI_ALIAS_FLAG);

        p1.setColor(0xff000000);
        p1.setTextSize(Util.spToPx(12));
        p1.setTextAlign(Paint.Align.CENTER);
        p2.setColor(0xff4b4b4b);
        p2.setTextSize(Util.spToPx(14));
        p2.setTextAlign(Paint.Align.CENTER);
        p3.setColor(0xff646464);
        p3.setTextSize(Util.spToPx(12));
        p3.setTextAlign(Paint.Align.CENTER);
        p4.setColor(0xff7f7f7f);
        p4.setTextSize(Util.spToPx(10));
        p4.setTextAlign(Paint.Align.CENTER);
        p5.setTextSize(Util.spToPx(10));
        p5.setTextAlign(Paint.Align.CENTER);
        p5.setStrokeWidth(Util.dpToPixelF(1));
        pChat.setColor(0xffd3d3d3);
        pChat.setStrokeWidth(Util.dpToPixelF(1));
        pChat.setStyle(Paint.Style.STROKE);
        pC.setColor(0xffffffff);
        pC.setStyle(Paint.Style.FILL);
        pL.setColor(0xffd3d3d3);
        pL.setStrokeWidth(1);



        bitmaps1 = new ArrayList<>();
        bitmaps2 = new ArrayList<>();
        rectFs1 = new ArrayList<>();
        rectFs2 = new ArrayList<>();
        rectsA = new ArrayList<>();

        for (int i = 0; i < dailyFroecastData.getTimeW().length; i++) {
            float spacing = i * Util.dpToPixelF(90);
            Bitmap bitmap1 = BitmapFactory.decodeResource(getResources(), dailyFroecastData.getWeatherIcon()[i]);
            Bitmap bitmap2 = BitmapFactory.decodeResource(getResources(), dailyFroecastData.getWeather20hIcon()[i]);
            bitmaps1.add(bitmap1);
            bitmaps2.add(bitmap2);
            float left1 = left - iconW / 2 + spacing;
            float top1 = Util.dpToPixelF(170) - iconH / 2;
            float right1 = left1 + iconW;
            float bottom1 = top1 + iconH;
            float left2 = left - iconW / 2 + spacing;
            float top2 = Util.dpToPixelF(390) - iconH / 2;
            float right2 = left2 + iconW;
            float bottom2 = top2 + iconH;
            RectF rectF1 = new RectF(left1, top1, right1, bottom1);
            RectF rectF2 = new RectF(left2, top2, right2, bottom2);
            rectFs1.add(rectF1);
            rectFs2.add(rectF2);

            timeWL[i][0] = left + spacing;
            timeWL[i][1] = top;
            timeDL[i][0] = left + spacing;
            timeDL[i][1] = Util.dpToPixelF(125);
            weatherL[i][0] = left + spacing;
            weatherL[i][1] = Util.dpToPixelF(200);
            weather20hL[i][0] = left + spacing;
            weather20hL[i][1] = Util.dpToPixelF(420);
            windDL[i][0] = left + spacing;
            windDL[i][1] = Util.dpToPixelF(440);
            windLL[i][0] = left + spacing;
            windLL[i][1] = Util.dpToPixelF(460);
            aqiTL[i][0] = left + spacing;
            aqiTL[i][1] = Util.dpToPixelF(486);

            float leftA = Util.dpToPixelF(30) + spacing;
            float topA = Util.dpToPixelF(475);
            float rightA = leftA + Util.dpToPixelF(30);
            float bottomA = topA + Util.dpToPixelF(15);
            RectF aqiRectF = new RectF(leftA, topA, rightA, bottomA);
            rectsA.add(aqiRectF);

            float chatPosOffsetsMAX = 0;
            float chatPosOffsetsMIN = 0;
            if (i != 0) {
                chatPosOffsetsMAX = Util.dpToPixelF(3) * (dailyFroecastData.getMaxTem()[i] - dailyFroecastData.getMaxTem()[0]);
                chatPosOffsetsMIN = Util.dpToPixelF(3) * (dailyFroecastData.getMinTem()[i] - dailyFroecastData.getMinTem()[0]);
            }
            lMaxL[i][0] = left + spacing;
            lMaxL[i][1] = Util.dpToPixelF(270) - chatPosOffsetsMAX;
            tMaxL[i][0] = left + spacing;
            tMaxL[i][1] = Util.dpToPixelF(250) - chatPosOffsetsMAX;
            lMinL[i][0] = left + spacing;
            lMinL[i][1] = Util.dpToPixelF(320) - chatPosOffsetsMIN;
            tMinL[i][0] = left + spacing;
            tMinL[i][1] = Util.dpToPixelF(340) - chatPosOffsetsMIN;
        }
        rect = new Rect(0, 0, bitmaps1.get(0).getWidth(), bitmaps1.get(0).getHeight());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (hasData){
            canvas.drawLine(0,Util.dpToPixelF(140),Util.dpToPixelF(90) * 5,Util.dpToPixelF(140),pL);
            canvas.drawLine(0,Util.dpToPixelF(500),Util.dpToPixelF(90) * 5,Util.dpToPixelF(500),pL);
            drawTime(canvas);
            drawWeather(canvas);
            drawChat(canvas);
        }
    }

    private void drawTime(Canvas canvas){
        for (int i = 0; i < dailyFroecastData.getTimeW().length;i++){
            canvas.drawText(dailyFroecastData.getTimeW()[i],timeWL[i][0],timeWL[i][1],p1);
            canvas.drawText(dailyFroecastData.getTimeD()[i],timeDL[i][0],timeDL[i][1],p4);

            if (dailyFroecastData.getTimeW()[i].equals("今天")){
                float leftR =i * Util.dpToPixelF(45);
                float topR = Util.dpToPixelF(140);
                float rightR = leftR + Util.dpToPixelF(90);
                float bottomR = topR + Util.dpToPixelF(360);
                Paint todayB = new Paint(Paint.ANTI_ALIAS_FLAG);
                todayB.setColor(0xfffafafa);
                canvas.drawLine(leftR,topR, leftR ,bottomR,pL);
                canvas.drawLine(rightR,topR, rightR ,bottomR,pL);
                canvas.drawRect(leftR,topR,rightR,bottomR,todayB);
            }
        }
    }

    private void drawWeather(Canvas canvas){
        for (int i = 0; i < dailyFroecastData.getTimeW().length;i++){
            canvas.drawBitmap(bitmaps1.get(i),rect,rectFs1.get(i),new Paint());
            canvas.drawBitmap(bitmaps2.get(i),rect,rectFs2.get(i),new Paint());
            canvas.drawText(dailyFroecastData.getSkycon()[i],weatherL[i][0],weatherL[i][1],p1);
            canvas.drawText(dailyFroecastData.getSkycon20h()[i],weather20hL[i][0],weather20hL[i][1],p1);
            canvas.drawText(dailyFroecastData.getWindDirection()[i],windDL[i][0],windDL[i][1],p1);
            canvas.drawText(dailyFroecastData.getWindLevel()[i],windLL[i][0],windLL[i][1],p3);
            p5.setColor(dailyFroecastData.getAqiColor()[i]);
            p5.setStyle(Paint.Style.FILL);
            canvas.drawText(dailyFroecastData.getAqiQuality()[i],aqiTL[i][0],aqiTL[i][1],p5);
            p5.setStyle(Paint.Style.STROKE);
            canvas.drawRoundRect(rectsA.get(i),Util.dpToPixelF(3),Util.dpToPixelF(3),p5);
        }
    }

    private void drawChat(Canvas canvas){
        for (int i = 0; i < dailyFroecastData.getTimeW().length;i++){
            if (i < tMaxL.length-1) {
                canvas.drawLine(lMaxL[i][0], lMaxL[i][1], lMaxL[i + 1][0], lMaxL[i + 1][1], pChat);
                canvas.drawLine(lMinL[i][0], lMinL[i][1], lMinL[i + 1][0], lMinL[i + 1][1], pChat);
            }
            canvas.drawCircle(lMaxL[i][0], lMaxL[i][1],Util.dpToPixelF(3),pC);
            canvas.drawCircle(lMaxL[i][0], lMaxL[i][1],Util.dpToPixelF(3),pChat);
            canvas.drawText(dailyFroecastData.getMaxTem()[i] + "℃",tMaxL[i][0],tMaxL[i][1],p2);
            canvas.drawCircle(lMinL[i][0], lMinL[i][1],Util.dpToPixelF(3),pC);
            canvas.drawCircle(lMinL[i][0], lMinL[i][1],Util.dpToPixelF(3),pChat);
            canvas.drawText(dailyFroecastData.getMinTem()[i] + "℃",tMinL[i][0],tMinL[i][1],p2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int width = (int) Util.dpToPixelF(450);
        int height = (int) Util.dpToPixelF(500);

        width = resolveSize(width, widthMeasureSpec);
        height = resolveSize(height, heightMeasureSpec);

        setMeasuredDimension(width, height);
    }
}
