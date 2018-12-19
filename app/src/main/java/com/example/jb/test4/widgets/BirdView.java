package com.example.jb.test4.widgets;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.graphics.PointF;

import com.example.jb.test4.R;
import com.example.jb.test4.util.Util;


public class BirdView extends View {

    private Paint paint;
    private Path path;

    private int n = 0;
    private float t = 0;

    private PointF p0 ;
    private PointF p1 ;
    private PointF p2 ;

    private PointF p02 ;
    private PointF p12;
    private PointF p22;

    private Bitmap[] bitmaps;


    private Rect rect;
    private RectF rectF1;
    private RectF rectF2;
    private RectF rectF3;
    private RectF rectF4;
    private RectF rectF5;
    private RectF rectF6;

    public void setN(int n){
        this.n = n;
        invalidate();
    }
    public int getN(){
        return n;
    }

    public void setT(float t){
        this.t = t;
    }
    public float getT(){
        return t;
    }
    public BirdView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        int[] resources = {R.drawable.blue01,R.drawable.blue02,R.drawable.blue03,R.drawable.blue04,R.drawable.blue05,
                R.drawable.blue06,R.drawable.blue07,R.drawable.blue08,R.drawable.blue09,R.drawable.blue10,R.drawable.blue11,
                R.drawable.blue12,R.drawable.blue13,R.drawable.blue14,R.drawable.blue15,R.drawable.blue16};

        bitmaps = new Bitmap[resources.length];

        for (int i = 0; i < resources.length; i++){
            bitmaps[i] = BitmapFactory.decodeResource(getResources(),resources[i]);
        }

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.STROKE);
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(5);
        path = new Path();

        p0 = new PointF(Util.dpToPixelF(500),Util.dpToPixelF(-20));
        p1 = new PointF(Util.dpToPixelF(100),Util.dpToPixelF(100));
        p2 = new PointF(Util.dpToPixelF(-200),Util.dpToPixelF(-200));
        p02 = new PointF(Util.dpToPixelF(500),Util.dpToPixelF(0));
        p12 = new PointF(Util.dpToPixelF(100),Util.dpToPixelF(100));
        p22 = new PointF(Util.dpToPixelF(-250),Util.dpToPixelF(-200));

        rect = new Rect(0,0,0,0);
        rectF1 = new RectF(0,0,0,0);
        rectF2 = new RectF(0,0,0,0);
        rectF3 = new RectF(0,0,0,0);
        rectF4 = new RectF(0,0,0,0);
        rectF5 = new RectF(0,0,0,0);
        rectF6 = new RectF(0,0,0,0);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        PointF point = Util.getPointFromQuadBezier(t,p0,p1,p2);
        PointF point2 = Util.getPointFromQuadBezier(t,p02,p12,p22);


        float left1 = Util.dpToPixelF(110) + point.x;
        float left2 = Util.dpToPixelF(60)  + point.x;
        float left3 = Util.dpToPixelF(90)  + point.x;
        float left4 = Util.dpToPixelF(50)  + point.x;
        float left5 = Util.dpToPixelF(80)  + point.x;
        float left6 = Util.dpToPixelF(130)  + point2.x;
        float right1 = left1 + Util.dpToPixelF(15);
        float right2 = left2 + Util.dpToPixelF(15);
        float right3 = left3 + Util.dpToPixelF(15);
        float right4 = left4 + Util.dpToPixelF(15);
        float right5 = left5 + Util.dpToPixelF(15);
        float right6 = left6 + Util.dpToPixelF(15);

        float top1 = Util.dpToPixelF(50) + point.y;
        float top2 = Util.dpToPixelF(80) + point.y;
        float top3 = Util.dpToPixelF(70) + point.y;
        float top4 = Util.dpToPixelF(105)+ point.y;
        float top5 = Util.dpToPixelF(100)+ point.y;
        float top6 = Util.dpToPixelF(80)+ point2.y;
        float bottom1 = top1+ Util.dpToPixelF(15);
        float bottom2 = top2+ Util.dpToPixelF(15);
        float bottom3 = top3+ Util.dpToPixelF(15);
        float bottom4 = top4+ Util.dpToPixelF(15);
        float bottom5 = top5+ Util.dpToPixelF(15);
        float bottom6 = top6+ Util.dpToPixelF(15);

        rect.set(0,0,bitmaps[0].getWidth(),bitmaps[0].getHeight());
        rectF1.set(left1 , top1, right1, bottom1);
        rectF2.set(left2 , top2, right2, bottom2);
        rectF3.set(left3 , top3, right3, bottom3);
        rectF4.set(left4 , top4, right4, bottom4);
        rectF5.set(left5 , top5, right5, bottom5);
        rectF6.set(left6 , top6, right6, bottom6);

        path.moveTo(p0.x,p0.y);
        path.quadTo(p1.x,p1.y,p2.x,p2.y);

        canvas.drawBitmap(bitmaps[n],rect,rectF1,paint);
        canvas.drawBitmap(bitmaps[n],rect,rectF2,paint);
        canvas.drawBitmap(bitmaps[n],rect,rectF3,paint);
        canvas.drawBitmap(bitmaps[n],rect,rectF4,paint);
        canvas.drawBitmap(bitmaps[n],rect,rectF5,paint);
        canvas.drawBitmap(bitmaps[(n+9)%16],rect,rectF6,paint);
    }
    
}
