package com.XHWJ.xiaoAiTongXue;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

public class ChartView extends View {
    private static final String TAG = "ChartView";
    private Paint chartPaint;
    private Drawable barImage;//进度条的
    private Drawable numberImage;//成长值图片
    private Drawable vipImage;//等级图片
    private Drawable baseLineImage;//线的图片
    private Drawable dotImage;
    private Path path;
    private List<ChartItemBean> datas = new ArrayList<ChartItemBean>();
    private List<CoordinateBean> coordinate = new ArrayList<CoordinateBean>();

    private int maxLine = 5;
    private int minH = 1;
    private int minW = 10;
    private int baseLine = 30;

    private float startX = 0.0f;
    private float startY = 0.0f;
    private float endX = 0.0f;
    private float endY = 0.0f;
    private float centerX = 0.0f;
    private float centerY = 0.0f;

    private int position = 0;


    public ChartView(Context context) {
        super(context);

    }

    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public ChartView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    private void initPaint() {
        chartPaint = new Paint();
        chartPaint.setColor(Color.GRAY);
        chartPaint.setStrokeWidth(Transform.dip2px(getContext(), minH));
        chartPaint.setStyle(Paint.Style.STROKE);
        path = new Path();
    }

    private void init() {
        datas.add(new ChartItemBean(0, R.drawable.v0, R.drawable.v1));
        datas.add(new ChartItemBean(60, R.drawable.v60, R.drawable.v2));
        datas.add(new ChartItemBean(120, R.drawable.v140, R.drawable.v3));
        datas.add(new ChartItemBean(200, R.drawable.v300, R.drawable.v4));
        datas.add(new ChartItemBean(380, R.drawable.v600, R.drawable.v5));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        init();
        initPaint();
        drawBaseLine(canvas);
        for (int i = 0; i < datas.size(); i++) {
            drawChart(i, datas.get(i), canvas);
        }
        path.quadTo(centerX, centerY, endX, endY);
        canvas.drawPath(path, chartPaint);

        drawDot(canvas);
        drawTitle(canvas);
    }

    private void drawDot(Canvas canvas) {
        dotImage = getContext().getResources().getDrawable(R.drawable.huangseyuan);
        BitmapDrawable bd = (BitmapDrawable) dotImage;
        Bitmap mBitmap = bd.getBitmap();
        Paint mPaint = new Paint();
        canvas.drawBitmap(
                mBitmap,
                getDotX(position) - (mBitmap.getWidth() / 2),
                getDotY(position) - (mBitmap.getHeight() / 2),
                mPaint
        );
        drawImg(canvas);

    }

    private void drawTitle(Canvas canvas) {
        Drawable titleImage = getContext().getResources().getDrawable(R.drawable.shandian);
        BitmapDrawable bd = (BitmapDrawable) titleImage;
        Bitmap mBitmap = bd.getBitmap();
        Paint mPaint = new Paint();
        canvas.drawBitmap(
                mBitmap,
                Transform.dip2px(getContext(), 10),
                Transform.dip2px(getContext(), 10),
                mPaint
        );
    }

    private void drawImg(Canvas canvas) {
        Bitmap b = BitmapFactory.decodeResource(getResources(), R.drawable.e66e817a7f3631fc74404b828356af91_1);
        float scale = Transform.dip2px(getContext(), 50) / b.getWidth();
        Paint bgPaint = new Paint();

        bgPaint.setColor(getResources().getColor(R.color.color_ffffff));
        float bian = Transform.dip2px(getContext(), 3);
        canvas.drawCircle(
                getDotX(position),
                getDotY(position) - Transform.dip2px(getContext(), 60),
                (Transform.dip2px(getContext(), 50) + bian) / 2,
                bgPaint
        );
        Log.e("变量值", "X==" + getDotX(position) + "Y==" + (getDotY(position) - Transform.dip2px(getContext(), 60)));
        Matrix matrix = getMatrix();
        matrix.postScale(scale, scale);
        matrix.postTranslate(
                getDotX(position) - (b.getWidth() * scale / 2),
                getDotY(position) - (b.getHeight() * scale / 2) - Transform.dip2px(getContext(), 60)
        );
        canvas.drawBitmap(getCircleBitmap(b), matrix, null);
    }

    private Bitmap getCircleBitmap(Bitmap bmp) {
        int r = Math.min(bmp.getWidth(), bmp.getHeight());
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        Bitmap newBitmap = Bitmap.createBitmap(r, r, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(newBitmap);
        BitmapShader bitmapShader = new BitmapShader(bmp, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP);
        paint.setShader(bitmapShader);
        canvas.drawCircle(r / 2, r / 2, r / 2, paint);
        return newBitmap;
    }

    private float getDotX(int position) {
        return coordinate.get(position).x;
    }

    private float getDotY(int position) {
        return coordinate.get(position).y;
    }


    private void drawChart(int c, ChartItemBean i, Canvas canvas) {
        if (c <= position)
            barImage = getContext().getResources().getDrawable(R.drawable.columnar_bg);
        else
            barImage = getContext().getResources().getDrawable(R.drawable.un_columnar_bg);
        float a = getWidth() / maxLine;
        float b = (int) ((a - Transform.dip2px(getContext(), minW)) / 2 + (c * a));
        int left = (int) b;
        int top = (int) (getHeight() - Transform.dip2px(getContext(), baseLine) - i.itemNumber / 4 * Transform.dip2px(getContext(), minH));
        int right = (int) (b + Transform.dip2px(getContext(), minW));
        int height = (int) (getHeight() - Transform.dip2px(getContext(), baseLine));

        Rect barRect = new Rect(
                left
                , top
                , right
                , height
        );

        barImage.setBounds(barRect);
        barImage.draw(canvas);

        drawNumber(i, canvas, left, top);
        drawVip(i, canvas, left, height);
        getChartXY(c);
    }

    private void getChartXY(int c) {
        Log.e(TAG, "" + (datas.size() / 2 + 1) + "  C==" + c);
        switch (c) {
            case 3:
                centerX = barImage.getBounds().left + (barImage.getBounds().width() / 2);
                centerY = barImage.getBounds().top - Transform.dip2px(getContext(), 40);
                coordinate.add(
                        new CoordinateBean(
                                centerX,
                                barImage.getBounds().top - Transform.dip2px(getContext(), 70)
                        )
                );
                break;
            case 0:
                startX = barImage.getBounds().left + (barImage.getBounds().width() / 2);
                startY = barImage.getBounds().top - Transform.dip2px(getContext(), 50);
                path.moveTo(startX, startY);
                coordinate.add(
                        new CoordinateBean(
                                startX,
                                startY
                        )
                );
                break;
            case 4:
                endX = barImage.getBounds().left;
                endY = barImage.getBounds().top - Transform.dip2px(getContext(), 80);
                coordinate.add(
                        new CoordinateBean(
                                endX,
                                endY
                        )
                );
                break;
            case 1:
                coordinate.add(
                        new CoordinateBean(
                                barImage.getBounds().left + (barImage.getBounds().width() / 2),
                                barImage.getBounds().top - Transform.dip2px(getContext(), 50)
                        )
                );
                break;
            default:
                coordinate.add(
                        new CoordinateBean(
                                barImage.getBounds().left + (barImage.getBounds().width() / 2),
                                barImage.getBounds().top - Transform.dip2px(getContext(), 60)
                        )
                );
                break;

        }

    }

    /**
     * 传递的成长值
     */
    public void setDotPosition(int position) {
        if (position < 60) {
            this.position = 0;
        } else if (position >= 60 && position < 140) {
            this.position = 1;
        } else if (position >= 140 && position < 300) {
            this.position = 2;
        } else if (position >= 300 && position < 600) {
            this.position = 3;
        } else if (position == 600) {
            this.position = 4;
        } else {
            this.position = 0;
        }
//        this.growthValue - position;
        invalidate();
    }


    private void drawNumber(
            ChartItemBean i,
            Canvas canvas,
            int left,
            int top
    ) {
        numberImage = getContext().getResources().getDrawable(i.itemNumberImg);
        BitmapDrawable bd = (BitmapDrawable) numberImage;

        Bitmap mBitmap = bd.getBitmap();
        Paint mPaint = new Paint();
        canvas.drawBitmap(
                mBitmap,
                (left - (mBitmap.getWidth() - Transform.dip2px(getContext(), minW)) / 2),
                (top - Transform.dip2px(getContext(), 30)),
                mPaint
        );
    }

    private void drawVip(
            ChartItemBean i,
            Canvas canvas,
            int left,
            int height
    ) {
        vipImage = getContext().getResources().getDrawable(i.itemVIPImg);
        BitmapDrawable bd = (BitmapDrawable) vipImage;
        Bitmap mBitmap = bd.getBitmap();
        Paint mPaint = new Paint();
        canvas.drawBitmap(
                mBitmap,
                left - ((mBitmap.getWidth() - Transform.dip2px(getContext(), minW)) / 2),
                (height + Transform.dip2px(getContext(), (baseLine / 2)) - (mBitmap.getHeight() / 2)),
                mPaint
        );
    }

    private void drawBaseLine(Canvas canvas) {
        int height = (int) (getHeight() - Transform.dip2px(getContext(), baseLine));

        baseLineImage = getContext().getResources().getDrawable(R.drawable.xian);

        BitmapDrawable bd = (BitmapDrawable) baseLineImage;

        Bitmap mBitmap = bd.getBitmap();
        Paint mPaint = new Paint();
        canvas.drawBitmap(
                mBitmap,
                0f,
                (height + Transform.dip2px(getContext(), baseLine / 2)),
                mPaint
        );
    }
}
