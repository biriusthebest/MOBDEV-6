package ua.kpi.comsys.IO8323;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;
import java.lang.Math;

/**
 * TODO: document your custom view class.
 */
public class DrawingView extends View {
    private String mExampleString = "Test"; // TODO: use a default from R.string...
    private int mExampleColor = Color.RED; // TODO: use a default from R.color...
    private float mExampleDimension = 0; // TODO: use a default from R.dimen...
    private Drawable mExampleDrawable;

    private int mNumberOfDrawing = 0;

    private TextPaint mTextPaint;
    private float mTextWidth;
    private float mTextHeight;

    private Paint paintBlack = new Paint();
    private Paint paintCyan = new Paint();
    private Paint paintPurple = new Paint();
    private Paint paintYellow = new Paint();
    private Paint paintGray = new Paint();

    public DrawingView(Context context) {
        super(context);
        init(null, 0);
    }

    public DrawingView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public DrawingView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DrawingView, defStyle, 0);

        mExampleString = a.getString(
                R.styleable.DrawingView_exampleString);
        mExampleColor = a.getColor(
                R.styleable.DrawingView_exampleColor,
                mExampleColor);
        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        mExampleDimension = a.getDimension(
                R.styleable.DrawingView_exampleDimension,
                mExampleDimension);

        if (a.hasValue(R.styleable.DrawingView_exampleDrawable)) {
            mExampleDrawable = a.getDrawable(
                    R.styleable.DrawingView_exampleDrawable);
            mExampleDrawable.setCallback(this);
        }

        paintBlack.setColor(Color.BLACK);
        paintCyan.setColor(Color.CYAN);
        paintPurple.setColor(0xff6a0dad);
        paintYellow.setColor(Color.YELLOW);
        paintGray.setColor(Color.GRAY);

        paintBlack.setStrokeWidth(5);
        paintPurple.setStrokeWidth(5);

        a.recycle();

        // Set up a default TextPaint object
        mTextPaint = new TextPaint();
        mTextPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextAlign(Paint.Align.LEFT);

        // Update TextPaint and text measurements from attributes
        invalidateTextPaintAndMeasurements();
    }

    private void invalidateTextPaintAndMeasurements() {
        mTextPaint.setTextSize(mExampleDimension);
        mTextPaint.setColor(mExampleColor);
        mTextWidth = mTextPaint.measureText(mExampleString);

        Paint.FontMetrics fontMetrics = mTextPaint.getFontMetrics();
        mTextHeight = fontMetrics.bottom;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        if (mNumberOfDrawing == 0) {
            int n = 50;
            float xPast = 0;
            float yPast = 0;
            paintPurple.setStyle(Paint.Style.FILL);
            paintPurple.setStrokeWidth(5);
            for (int i = 0; i < n; i++) {
                float y = contentHeight -
                        (float) (Math.cos(Math.PI * (2 * (double) i / (n - 1) - 1)) + 1 + 1)
                                * contentHeight / 4 + paddingTop;
                float x = (float) i / (n - 1) * contentWidth + paddingLeft;
                if (i == 0) {
                    xPast = x;
                    yPast = y;
                } else {
                    canvas.drawLine(xPast, yPast, x, y, paintPurple);
                    xPast = x;
                    yPast = y;
                }
            }

            canvas.drawLine(0, (float) contentHeight / 2 + paddingTop,
                    contentWidth + paddingLeft + paddingRight,
                    (float) contentHeight / 2 + paddingTop, paintBlack);

            canvas.drawLine((float) contentWidth / 2 + paddingLeft, 0,
                    (float) contentWidth / 2 + paddingLeft,
                    contentHeight + paddingTop + paddingBottom, paintBlack);

            canvas.drawLine((float) contentWidth / 2 + paddingLeft, 0,
                    (float) contentWidth / 2 + paddingLeft - 25, 50, paintBlack);

            canvas.drawLine((float) contentWidth / 2 + paddingLeft, 0,
                    (float) contentWidth / 2 + paddingLeft + 25, 50, paintBlack);

            canvas.drawLine(contentWidth + paddingLeft + paddingRight,
                    (float) contentHeight / 2 + paddingTop,
                    contentWidth + paddingLeft + paddingRight - 50,
                    (float) contentHeight / 2 + paddingTop - 25, paintBlack);

            canvas.drawLine(contentWidth + paddingLeft + paddingRight,
                    (float) contentHeight / 2 + paddingTop,
                    contentWidth + paddingLeft + paddingRight - 50,
                    (float) contentHeight / 2 + paddingTop + 25, paintBlack);
        } else if (mNumberOfDrawing == 1){
            int size = Math.min(contentWidth, contentHeight);
            int xStart = paddingLeft + (contentWidth - size) / 2;
            int yStart = paddingTop + (contentHeight - size) / 2;
            RectF oval = new RectF(xStart,
                    yStart,
                    xStart + size, yStart + size);
            paintCyan.setStyle(Paint.Style.STROKE);
            paintPurple.setStyle(Paint.Style.STROKE);
            paintYellow.setStyle(Paint.Style.STROKE);
            paintGray.setStyle(Paint.Style.STROKE);
            paintCyan.setStrokeWidth(size / 10);
            paintPurple.setStrokeWidth(size / 10);
            paintYellow.setStrokeWidth(size / 10);
            paintGray.setStrokeWidth(size / 10);
            canvas.drawArc(oval, 0F, 162F, false, paintCyan);
            canvas.drawArc(oval, 162F, 18F, false, paintPurple);
            canvas.drawArc(oval, 180F, 90F, false, paintYellow);
            canvas.drawArc(oval, 270F, 90F, false, paintGray);
          }
    }

    /**
     * Gets the number of drawing attribute value.
     *
     * @return The number of drawing attribute value.
     */
    public int getNumberOfDrawing() {
        return mNumberOfDrawing;
    }

    /**
     * Sets the number of drawing attribute value.
     *
     * @param numberOfDrawing The number of drawing attribute value to use.
     */
    public void setNumberOfDrawing(int numberOfDrawing) {
        mNumberOfDrawing = numberOfDrawing;
    }

    /**
     * Gets the example string attribute value.
     *
     * @return The example string attribute value.
     */
    public String getExampleString() {
        return mExampleString;
    }

    /**
     * Sets the view"s example string attribute value. In the example view, this string
     * is the text to draw.
     *
     * @param exampleString The example string attribute value to use.
     */
    public void setExampleString(String exampleString) {
        mExampleString = exampleString;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example color attribute value.
     *
     * @return The example color attribute value.
     */
    public int getExampleColor() {
        return mExampleColor;
    }

    /**
     * Sets the view"s example color attribute value. In the example view, this color
     * is the font color.
     *
     * @param exampleColor The example color attribute value to use.
     */
    public void setExampleColor(int exampleColor) {
        mExampleColor = exampleColor;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example dimension attribute value.
     *
     * @return The example dimension attribute value.
     */
    public float getExampleDimension() {
        return mExampleDimension;
    }

    /**
     * Sets the view"s example dimension attribute value. In the example view, this dimension
     * is the font size.
     *
     * @param exampleDimension The example dimension attribute value to use.
     */
    public void setExampleDimension(float exampleDimension) {
        mExampleDimension = exampleDimension;
        invalidateTextPaintAndMeasurements();
    }

    /**
     * Gets the example drawable attribute value.
     *
     * @return The example drawable attribute value.
     */
    public Drawable getExampleDrawable() {
        return mExampleDrawable;
    }

    /**
     * Sets the view"s example drawable attribute value. In the example view, this drawable is
     * drawn above the text.
     *
     * @param exampleDrawable The example drawable attribute value to use.
     */
    public void setExampleDrawable(Drawable exampleDrawable) {
        mExampleDrawable = exampleDrawable;
    }
}