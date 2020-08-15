package project.controls;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatImageView;

public class PageIndicator extends AppCompatImageView {
    private static final int CIRCLE_RADIUS = 15;
    private static final int CIRCLE_SPACE = 10;
    private static final int CIRCLE_STROKE_COLOR = Color.GRAY;
    private static final int CIRCLE_FILL_COLOR = Color.LTGRAY;
    private Paint fillPaint;
    private Paint strokePaint;
    private int count;
    private int indicatorWidth;
    private int screenWidth;
    private float offsetX;
    private int currentPageIndex;
    private Context context;

    public PageIndicator(Context context) {
        super(context);
        this.context = context;
        initialize();
    }

    public PageIndicator(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        initialize();
    }

    public PageIndicator(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.context = context;
        initialize();
    }

    private void initialize() {
        fillPaint = new Paint();
        fillPaint.setStyle(Paint.Style.FILL);
        fillPaint.setColor(CIRCLE_FILL_COLOR);
        fillPaint.setAntiAlias(true);
        strokePaint = new Paint();
        strokePaint.setStyle(Paint.Style.STROKE);
        strokePaint.setColor(CIRCLE_STROKE_COLOR);
        strokePaint.setAntiAlias(true);
        screenWidth = context.getResources().getDisplayMetrics().widthPixels;
    }

    public void setIndicatorsCount(int value) {
        count = value;
        computeIndicatorWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        for (int i = 0; i < count; i++) {
            Paint paint = strokePaint;
            float radius = CIRCLE_RADIUS;
            if (i == currentPageIndex) {
                canvas.drawCircle(offsetX + i * (CIRCLE_RADIUS + CIRCLE_SPACE), 10, radius / 2.0f, fillPaint);
            } else {
                canvas.drawCircle(offsetX + i * (CIRCLE_RADIUS + CIRCLE_SPACE), 10, radius / 2.0f, strokePaint);
            }
        }
    }

    private void computeIndicatorWidth() {
        indicatorWidth = count * (CIRCLE_RADIUS + CIRCLE_SPACE);
        offsetX = (screenWidth - indicatorWidth) / 2;
    }

    public int getCurrentPage() {
        return currentPageIndex;
    }

    public void setCurrentPage(int value) {
        currentPageIndex = value;
        postInvalidate();
    }
}
