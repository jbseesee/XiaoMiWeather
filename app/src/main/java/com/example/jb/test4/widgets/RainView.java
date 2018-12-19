package com.example.jb.test4.widgets;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.example.jb.test4.util.Util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by 666 on 2018/4/15.
 */

public class RainView extends View {

    int r = 10;

    List<Rain> rains;
    public RainView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        rains = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            Rain rain = new Rain(new Random().nextInt(3) + 1);//new Random().nextInt(3) + 1
            rains.add(rain);
        }

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(final Canvas canvas) {
        super.onDraw(canvas);

        for (final Rain rain : rains){
            rain.draw(canvas);
        }
        postInvalidateDelayed(20);

    }
}

class Rain {
    private float x;

    private float y;
    private float dis;
    private float y0;
    private int n;
    Paint paint ;
    boolean a = false;
    Random random = new Random();
    public Rain(int n){
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        Shader shader = new LinearGradient( Util.dpToPixelF(380), Util.dpToPixelF(145), Util.dpToPixelF(380), Util.dpToPixelF(139), 0x00ffffff, 0xffffffff, Shader.TileMode.CLAMP);
        paint.setShader(shader);
        paint.setAlpha(60 * n);
        this.n = n;
        x = random.nextInt((int)Util.dpToPixelF(380));
        y = Util.dpToPixelF(random.nextFloat()*(-100));
        y0 = y + Util.dpToPixelF(4f) * n;
        dis = Util.dpToPixelF(2f) * n  ;
    }

    void draw(final Canvas canvas){
        y  += dis;
        y0 += dis;
        canvas.drawLine(x,y,x,y0,paint);
        if (y > Util.dpToPixelF(200)){
            reset();
        }
    }

    void reset(){
        x = random.nextInt((int)Util.dpToPixelF(380));
        y = Util.dpToPixelF(random.nextFloat()*(-10));
        y0 = y + Util.dpToPixelF(4f) * n;

    }

}

