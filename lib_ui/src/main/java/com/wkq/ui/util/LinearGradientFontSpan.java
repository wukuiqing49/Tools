package com.wkq.ui.util;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.text.style.ReplacementSpan;

/**
 * @Author: wkq
 * @Time: 2025/2/11 10:45
 * @Desc:
 */

public class LinearGradientFontSpan extends ReplacementSpan {

    // 文字宽度
    private int mSize;
    // 渐变开始颜色
    private int startColor = Color.BLUE;
    // 渐变结束颜色
    private int endColor = Color.RED;
    // 是否左右渐变
    private boolean isLeftToRight = true;

    public LinearGradientFontSpan() {
    }

    public LinearGradientFontSpan(int startColor, int endColor, boolean leftToRight) {
        this.startColor = startColor;
        this.endColor = endColor;
        this.isLeftToRight = leftToRight;
    }

    @Override
    public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fm) {
        mSize = (int) (paint.measureText(text, start, end));
        return mSize;
    }

    @Override
    public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
        // 修改y1的值从上到下渐变， 修改x1的值从左到右渐变
        LinearGradient lg;
        if (isLeftToRight) {
            lg = new LinearGradient(0, 0, mSize, 0,
                    endColor,
                    startColor,
                    Shader.TileMode.REPEAT);
        } else {
            lg = new LinearGradient(0, 0, 0, paint.descent() - paint.ascent(),
                    endColor,
                    startColor,
                    Shader.TileMode.REPEAT);
        }
        paint.setShader(lg);

        canvas.drawText(text, start, end, x, y, paint);//绘制文字
    }

    public void setLeftToRight(boolean leftToRight) {
        isLeftToRight = leftToRight;
    }

    public void setEndColor(int endColor) {
        this.endColor = endColor;
    }

    public void setStartColor(int startColor) {
        this.startColor = startColor;
    }
}