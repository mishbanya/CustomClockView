package com.example.customclockview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class ClockView extends View {

    private Paint paint;
    private float radius;
    private float centerX;
    private float centerY;
    private float hourHandLength;
    private float minuteHandLength;
    private float secondHandLength;

    private SimpleDateFormat dateFormat = new SimpleDateFormat("h:mm:ss a", Locale.getDefault());

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(5);
        paint.setAntiAlias(true);

        hourHandLength = 100;
        minuteHandLength = 150;
        secondHandLength =200;
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        radius = Math.min(w, h) * 0.4f;
        centerX = w / 2f;
        centerY = h / 2f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        paint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(centerX, centerY, radius, paint);

        paint.setTextSize(40);
        paint.setStyle(Paint.Style.FILL);
        paint.setTextAlign(Paint.Align.CENTER);
        for (int i = 1; i <= 12; i++) {
            double angle = Math.PI / 6 * (i - 3);
            float x = (float) (centerX + Math.cos(angle) * radius * 0.85);
            float y = (float) (centerY + Math.sin(angle) * radius * 0.85);
            canvas.drawText(String.valueOf(i), x, y + 20, paint);
        }

        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);

        drawHand(canvas, (hour % 12 + minute / 60f) * 5f, hourHandLength, Color.BLACK);
        drawHand(canvas, minute + second / 60f, minuteHandLength, Color.BLACK);
        drawHand(canvas, second, secondHandLength, Color.RED);

        paint.setTextSize(50);
        canvas.drawText(dateFormat.format(calendar.getTime()), centerX, centerY * 2, paint);

        postInvalidateDelayed(1000);
    }

    private void drawHand(Canvas canvas, double angle, float length, int color) {
        double angleRadians = Math.toRadians(angle * 360 / 60 - 90);
        float startX = centerX;
        float startY = centerY;
        float endX = (float) (centerX + Math.cos(angleRadians) * length);
        float endY = (float) (centerY + Math.sin(angleRadians) * length);

        int InitialColor = paint.getColor();
        paint.setColor(color);
        canvas.drawLine(startX, startY, endX, endY, paint);
        paint.setColor(InitialColor);
    }
}