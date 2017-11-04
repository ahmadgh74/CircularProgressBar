package ir.zerotohero.ahmadghorbi.progressbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.SweepGradient;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by ahmad-sw on 09/10/2017.
 */


public class ZHProgress extends ImageView {

    private Paint backPaint, spinPaint;
    private int radius;
    private int startAngle = 0;
    private boolean stopProgress = false;
    private int cx, cy;
    private RectF circle;
    private SweepGradient gradient;
    private Matrix matrix;
    private int startColor = Color.TRANSPARENT;
    private int endColor;
    private int backColor;
    private float segmentWith;
    private long sleepTime = 5;

    public ZHProgress(Context context) {
        super(context);
        init();
    }

    public ZHProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ZHProgress(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ZHProgress(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init();
    }

    private void init() {
        spinPaint = new Paint();
        spinPaint.setAntiAlias(true);
        spinPaint.setStyle(Paint.Style.STROKE);
        backPaint = new Paint(spinPaint);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int minDimension = width < height ? width : height;
        setMeasuredDimension(minDimension, minDimension);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        cx = w / 2;
        cy = h / 2;
        radius = (int) (cx * 0.8f);
        circle = new RectF(cx - radius, cy - radius, cx + radius, cy + radius);
        backPaint.setColor(backColor);
        int color[] = {startColor, endColor};
        float angle[] = {0.6f, 1.0f};
        gradient = new SweepGradient(cx, cy, color, angle);
        matrix = new Matrix();
        int stroke = radius / 5;
        backPaint.setStrokeWidth(stroke);
        spinPaint.setStrokeWidth(stroke);
        segmentWith = radius / 108.0f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int segments = (int) Math.ceil(360 / segmentWith);

        matrix.setRotate(startAngle, cx, cy);
        gradient.setLocalMatrix(matrix);
        spinPaint.setShader(gradient);
        for (int i = 0; i < segments; i++) {
            if (i % 2 == 0) {
                canvas.drawArc(circle, i * (segmentWith * 2), segmentWith, false, backPaint);
                canvas.drawArc(circle, i * (segmentWith * 2), segmentWith, false, spinPaint);

            }
        }
    }


    public int getStartAngle() {
        return startAngle;
    }

    public void setStartAngle(int startAngle) {
        this.startAngle = startAngle;
        invalidate();
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
        invalidate();
    }

    public void startProgress() {
        stopProgress = false;
        new Thread(() -> {
            Handler mainHandler = new Handler(Looper.getMainLooper());
            while (!stopProgress) {
                mainHandler.post(() -> {
                    setStartAngle(startAngle);
                });

                try {
                    Thread.sleep(sleepTime);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                startAngle += 4;
            }
        }).start();
    }

    public void stopProgress() {
        stopProgress = true;
    }

    public void setCycleColor(int color) {
        this.startColor = Color.TRANSPARENT;
        this.endColor = color;
        if (gradient != null) {
            int[] colors = {startColor, color};
            float angle[] = {0.5f, 1.0f};
            gradient = new SweepGradient(cx, cy, colors, angle);
            Matrix m = new Matrix();
            m.setRotate(startAngle, cx, cy);
            gradient.setLocalMatrix(m);
            invalidate();
        }
    }

    public void setBackColor(int color) {
        this.backColor = color;
        requestLayout();
    }

    public void setSpeed(int fps) {
        if (fps > 200)
            fps = 200;
        this.sleepTime = 1000 / fps;
    }
}
