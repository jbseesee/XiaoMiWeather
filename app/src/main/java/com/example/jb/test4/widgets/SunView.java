package com.example.jb.test4.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.jb.test4.bean.homeBean.SunViewData;
import com.example.jb.test4.util.Util;

public class SunView extends View {
    private Paint paintC;
    private Paint paintLP;
    private Paint paintLD;
    private Paint testPaint;
    private Paint sunPaint;
    private Paint coverPaint;
    private Paint coverLPaint;
    private Paint mPaint;

    private Path pathpre;
    private Path pathLD ;

    private RectF oval;

    private float percentV = 0;
    private String sunRise ="日出 05:27";
    private String sunSet ="日落 18:58";
    public SunView(Context context) {
        super(context);
        init();
    }

    public SunView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public SunView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public void setPercentV(float v){
        percentV = v;
        invalidate();
    }

    public float getPercentV(){
        return percentV;
    }

    public void setData(SunViewData sunViewData){
        this.sunRise = "日出 " + sunViewData.getSunRiseS();
        this.sunSet = "日落 " + sunViewData.getSunSetS();
        invalidate();
    }

    public void init(){
        Shader shader = new LinearGradient( Util.dpToPixelF(180), Util.dpToPixelF(95), Util.dpToPixelF(180), 0, 0x00fe8000, 0x64fe8000, Shader.TileMode.CLAMP);
        oval = new RectF(Util.dpToPixelF(-50),Util.dpToPixelF(45),Util.dpToPixelF(410),Util.dpToPixelF(505));
        paintC = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLP = new Paint(Paint.ANTI_ALIAS_FLAG);
        paintLD = new Paint(Paint.ANTI_ALIAS_FLAG);
        testPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        sunPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        coverPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        coverLPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        testPaint.setStyle(Paint.Style.FILL);
        testPaint.setColor(Color.BLUE);

        paintC.setShader(shader);
        paintC.setStyle(Paint.Style.FILL);

        paintLP.setColor(0xfffe8000);
        paintLP.setStyle(Paint.Style.STROKE);
        paintLP.setStrokeWidth(Util.dpToPixelF(1.3f));

        paintLD.setColor(0xfffe8000);
        paintLD.setStyle(Paint.Style.STROKE);
        paintLD.setStrokeWidth(Util.dpToPixelF(1.3f));
        PathEffect pathEffect = new DashPathEffect(new float[]{Util.dpToPixelF(10), Util.dpToPixelF(10)}, 0);
        paintLD.setPathEffect(pathEffect);

        sunPaint.setColor(0xffffd400);

        coverPaint.setColor(0xffffffff);

        coverLPaint.setStyle(Paint.Style.STROKE);
        coverLPaint.setColor(0xffffffff);
        coverLPaint.setStrokeWidth(Util.dpToPixelF(3));

        mPaint.setColor(0xfffe8000);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(Util.spToPx(10));

        pathpre = new Path();
        pathLD = new Path();

    }
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float angleP = (percentV * 80);
        float angle = 50 + (percentV * 80);

        pathLD.addArc(oval,-50,-80);

        pathpre.moveTo(getX(50),getY(50));
        pathpre.addArc(oval,-130,angleP);
        pathpre.moveTo(getX(angle),getY(angle));
        pathpre.lineTo(getX(angle),Util.dpToPixelF(100));
        pathpre.lineTo(getX(50),getY(50));

        canvas.drawPath(pathLD,paintLD);//虚线弧线
        canvas.drawArc(oval,-130,angleP,false,coverLPaint);//遮盖实线处的虚线
        canvas.drawArc(oval,-50,-80,false,paintC);//弧形背景色
        canvas.drawArc(oval,-130,angleP,false,paintLP);//实线弧线
        canvas.drawPath(pathpre,paintC);//已过去时间的弧形背景色
        canvas.drawCircle(getX(50),getY(50),Util.dpToPixelF(2),mPaint);
        canvas.drawCircle(getX(130),getY(130),Util.dpToPixelF(2),mPaint);
        canvas.drawCircle(getX(angle),getY(angle),Util.dpToPixelF(11),coverPaint);
        canvas.drawCircle(getX(angle),getY(angle),Util.dpToPixelF(9),sunPaint);
        canvas.drawText(sunRise,Util.dpToPixelF(75),Util.dpToPixelF(102),mPaint);
        canvas.drawText(sunSet,Util.dpToPixelF(285),Util.dpToPixelF(102),mPaint);

        pathpre.rewind();
        pathLD.rewind();

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int width = MeasureSpec.getSize(widthMeasureSpec);//宽度为父view可用宽度
        int height = (int)Util.dpToPixelF(120);

        height = resolveSize(height,heightMeasureSpec);

        setMeasuredDimension(width,height);
    }

    private float getX(double angle){
      float x = (- Util.dpToPixelF(230) * (float) Math.cos((Math.PI / 180) * angle)) + Util.dpToPixelF(180);
      return x;
    }

    private float getY(double angle){
        float y = (-Util.dpToPixelF(230) * (float) Math.sin((Math.PI / 180) * angle)) + Util.dpToPixelF(45) + Util.dpToPixelF(230);
        return y;
    }
}
