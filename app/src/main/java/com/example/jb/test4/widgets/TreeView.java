package com.example.jb.test4.widgets;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.example.jb.test4.R;
import com.example.jb.test4.util.Util;

public class TreeView extends View {
    private Bitmap bitmapTrunk;
    private Bitmap bitmapBranch;
    private Bitmap bitmapBallL;
    private Bitmap bitmapBallM;
    private Bitmap bitmapBallR;

    private Bitmap bitmapLeaf;

    private Rect rectTrunk;
    private RectF rectBranch;
    private RectF rectBallL;
    private RectF rectBallM;
    private RectF rectBallR;
    private RectF rectFTrunk;
    private RectF rectFBranch;
    private RectF rectFBallL;
    private RectF rectFBallM;
    private RectF rectFBallR;


    //控制点
    private PointF p10;
    private PointF p11;
    private PointF p12;

    private PointF p20;
    private PointF p21;
    private PointF p22;

    private PointF p30;
    private PointF p31;
    private PointF p32;

    private PointF p40;
    private PointF p41;
    private PointF p42;



    private Paint paint;
    private Paint paintAlpha;

    private Matrix matrix;
    private Matrix matrix3;
    private Matrix matrix4;
    private Matrix matrix5;
    private int angle;

    private float t;

    private int alphap;

    private int treeAngle;


    PaintFlagsDrawFilter paintFlagsDrawFilter;


    public TreeView(Context context) {
        super(context);
        init();
    }

    public TreeView(Context context,  AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TreeView(Context context,  AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    /**
     * set和get方法为属性动画所调用
     */
    public void setAngle(int angle){
        this.angle = angle;
    }
    public int getAngle(){
        return this.angle;
    }

    public float getT() {
        return t;
    }

    public void setT(float t) {
        this.t = t;
    }

    public int getAlphap() {
        return alphap;
    }

    public void setAlphap(int alphap) {
        this.alphap = alphap;
    }

    public int getTreeAngle() {
        return treeAngle;
    }

    public void setTreeAngle(int treeAngle) {
        this.treeAngle = treeAngle;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.setDrawFilter(paintFlagsDrawFilter);
        drawLeaf(canvas);
        drawTree(canvas);
    }
    private void init(){

        paintFlagsDrawFilter = new PaintFlagsDrawFilter(0, Paint.ANTI_ALIAS_FLAG | Paint.FILTER_BITMAP_FLAG);

        paint = new Paint(Paint.ANTI_ALIAS_FLAG);

        paintAlpha = new Paint(Paint.ANTI_ALIAS_FLAG);



        bitmapTrunk = BitmapFactory.decodeResource(getResources(), R.drawable.bg_sunny_tree_trunk_night);
        bitmapBranch = BitmapFactory.decodeResource(getResources(),R.drawable.bg_sunny_tree_branch_night);
        bitmapBallL = BitmapFactory.decodeResource(getResources(),R.drawable.bg_sunny_tree_ball_left_night);
        bitmapBallM = BitmapFactory.decodeResource(getResources(),R.drawable.bg_sunny_tree_ball_middle_night);
        bitmapBallR = BitmapFactory.decodeResource(getResources(),R.drawable.bg_sunny_tree_ball_right_night);

        bitmapLeaf = BitmapFactory.decodeResource(getResources(),R.drawable.bg_sunny_tree_leaf_night);


        rectTrunk = new Rect(0,0,bitmapTrunk.getWidth(),bitmapTrunk.getHeight());
        rectBranch = new RectF(0,0,bitmapBranch.getWidth(),bitmapBranch.getHeight());
        rectBallL = new RectF(0,0,bitmapBallL.getWidth(),bitmapBallL.getHeight());
        rectBallM = new RectF(0,0,bitmapBallM.getWidth(),bitmapBallM.getHeight());
        rectBallR = new RectF(0,0,bitmapBallR.getWidth(),bitmapBallR.getHeight());

        rectFTrunk = new RectF(Util.dpToPixelF(280),Util.dpToPixelF(45),Util.dpToPixelF(340),Util.dpToPixelF(120));
        rectFBranch = new RectF(Util.dpToPixelF(271),Util.dpToPixelF(62),Util.dpToPixelF(291),Util.dpToPixelF(70));
        rectFBallL = new RectF(Util.dpToPixelF(252),Util.dpToPixelF(42),Util.dpToPixelF(277),Util.dpToPixelF(67));
        rectFBallM = new RectF(Util.dpToPixelF(257),Util.dpToPixelF(15),Util.dpToPixelF(302),Util.dpToPixelF(60));
        rectFBallR = new RectF(Util.dpToPixelF(285),Util.dpToPixelF(48),Util.dpToPixelF(305),Util.dpToPixelF(68));

        matrix = new Matrix();
        matrix3 = new Matrix();
        matrix4 = new Matrix();
        matrix5 = new Matrix();

        p10 = new PointF(Util.dpToPixelF(260),Util.dpToPixelF(30));
        p11 = new PointF(Util.dpToPixelF(200),Util.dpToPixelF(50));
        p12 = new PointF(Util.dpToPixelF(200),Util.dpToPixelF(85));
        p20 = new PointF(Util.dpToPixelF(265),Util.dpToPixelF(35));
        p21 = new PointF(Util.dpToPixelF(210),Util.dpToPixelF(55));
        p22 = new PointF(Util.dpToPixelF(210),Util.dpToPixelF(90));
        p30 = new PointF(Util.dpToPixelF(260),Util.dpToPixelF(40));
        p31 = new PointF(Util.dpToPixelF(215),Util.dpToPixelF(45));
        p32 = new PointF(Util.dpToPixelF(215),Util.dpToPixelF(80));
        p40 = new PointF(Util.dpToPixelF(265),Util.dpToPixelF(45));
        p41 = new PointF(Util.dpToPixelF(235),Util.dpToPixelF(50));
        p42 = new PointF(Util.dpToPixelF(235),Util.dpToPixelF(85));

    }

    private void drawLeaf(Canvas canvas){

        //曲线上的点
        PointF pB1 =  Util.getPointFromQuadBezier(t,p10,p11,p12);
        PointF pB2 =  Util.getPointFromQuadBezier(t,p20,p21,p22);
        PointF pB3 =  Util.getPointFromQuadBezier(t,p30,p31,p32);
        PointF pB4 =  Util.getPointFromQuadBezier(t,p40,p41,p42);

        float left1 = pB1.x;
        float left2 = pB2.x;
        float left3 = pB3.x;
        float left4 = pB4.x;

        float top1 = pB1.y;
        float top2 = pB2.y;
        float top3 = pB3.y;
        float top4 = pB4.y;

        float right1 = left1 + Util.dpToPixelF(6);
        float right2 = left2 + Util.dpToPixelF(4);
        float right3 = left3 + Util.dpToPixelF(6);
        float right4 = left4 + Util.dpToPixelF(4);

        float bottom1 = top1 + Util.dpToPixelF(6);
        float bottom2 = top2 + Util.dpToPixelF(4);
        float bottom3 = top3 + Util.dpToPixelF(6);
        float bottom4 = top4 + Util.dpToPixelF(4);

        RectF rectLeaf = new RectF(0,0,bitmapLeaf.getWidth(),bitmapLeaf.getHeight());
        RectF rectFLeaf1 = new RectF(left1,top1,right1,bottom1);
        RectF rectFLeaf2 = new RectF(left2,top2,right2,bottom2);
        RectF rectFLeaf3 = new RectF(left3,top3,right3,bottom3);
        RectF rectFLeaf4 = new RectF(left4,top4,right4,bottom4);

        Matrix matrix1 = new Matrix();
        matrix1.setRectToRect(rectLeaf,rectFLeaf1, Matrix.ScaleToFit.FILL);
        matrix1.postRotate(angle,(left1 + right1)/2,(top1 + bottom1)/2);
        paintAlpha.setAlpha(alphap);
        canvas.drawBitmap(bitmapLeaf,matrix1,paintAlpha);

        Matrix matrix2 = new Matrix();
        matrix2.setRectToRect(rectLeaf,rectFLeaf2, Matrix.ScaleToFit.FILL);
        matrix2.postRotate(angle,(left2 + right2)/2,(top2 + bottom2)/2);
        paintAlpha.setAlpha(alphap);
        canvas.drawBitmap(bitmapLeaf,matrix2,paintAlpha);

        Matrix matrix3 = new Matrix();
        matrix3.setRectToRect(rectLeaf,rectFLeaf3, Matrix.ScaleToFit.FILL);
        matrix3.postRotate(angle,(left3 + right3)/2,(top1 + bottom1)/2);
        paintAlpha.setAlpha(alphap);
        canvas.drawBitmap(bitmapLeaf,matrix3,paintAlpha);

        Matrix matrix4 = new Matrix();
        matrix4.setRectToRect(rectLeaf,rectFLeaf4, Matrix.ScaleToFit.FILL);
        matrix4.postRotate(angle,(left4 + right4)/2,(top4 + bottom4)/2);
        paintAlpha.setAlpha(alphap);
        canvas.drawBitmap(bitmapLeaf,matrix4,paintAlpha);

    }

    private void drawTree(Canvas canvas){
        matrix.setRectToRect(rectBranch,rectFBranch, Matrix.ScaleToFit.FILL);
        matrix3.setRectToRect(rectBallL,rectFBallL, Matrix.ScaleToFit.FILL);
        matrix4.setRectToRect(rectBallM,rectFBallM, Matrix.ScaleToFit.FILL);
        matrix5.setRectToRect(rectBallR,rectFBallR, Matrix.ScaleToFit.FILL);

        matrix.postRotate (treeAngle,(rectFBranch.left + rectFBranch.right)/2,rectFBranch.bottom);
        matrix3.postRotate(treeAngle,(rectFBranch.left + rectFBranch.right)/2,rectFBranch.bottom);
        matrix4.postRotate(treeAngle,(rectFBranch.left + rectFBranch.right)/2,rectFBranch.bottom);
        matrix5.postRotate(treeAngle,(rectFBranch.left + rectFBranch.right)/2,rectFBranch.bottom);

        canvas.drawBitmap(bitmapTrunk,rectTrunk,rectFTrunk,paint);
        canvas.drawBitmap(bitmapBranch,matrix,paint);
        canvas.drawBitmap(bitmapBallL ,matrix3 ,paint);
        canvas.drawBitmap(bitmapBallM ,matrix4,paint);
        canvas.drawBitmap(bitmapBallR ,matrix5,paint);
    }




}
