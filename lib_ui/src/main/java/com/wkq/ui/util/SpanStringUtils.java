package com.wkq.ui.util;

import android.content.Context;
import android.text.ParcelableSpan;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.ScaleXSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * @Author: wkq
 * @Time: 2025/2/11 17:29
 * @Desc:
 */

//取值范围
//Spanned.SPAN_EXCLUSIVE_EXCLUSIVE ——(a,b)
//Spanned.SPAN_EXCLUSIVE_INCLUSIVE ——(a,b]
//Spanned.SPAN_INCLUSIVE_EXCLUSIVE ——[a,b)
//Spanned.SPAN_INCLUSIVE_INCLUSIVE ——[a,b]


//ForegroundColorSpan：文本颜色
//BackgroundColorSpan：背景颜色
//AbsoluteSizeSpan：字体的大小（绝对值）
//RelativeSizeSpan：字体的大小（相对值，xx倍）
//StyleSpan：字体的风格
//UnderlineSpan：下划线
//StrikethroughSpan：删除线


public class SpanStringUtils {

    private Context context;
    private SpannableStringBuilder spannableStringBuilder;
    private int lp = 0;
    private int rp = 0;
    private List<Span> list = new ArrayList<>();

    public SpanStringUtils(Context context) {
        this.context = context;
        spannableStringBuilder = new SpannableStringBuilder();
    }

    public SpanStringUtils append(Span span) {
        list.add(span);
        return this;
    }

    public CharSequence create() {
        if (list == null || list.isEmpty()) {
            return null;
        }

        for (int i = 0; i < list.size(); i++) {
            Span span = list.get(i);
            createSpan(span);
        }

        return spannableStringBuilder;
    }

    private void createSpan(Span span) {
        if (span != null) {
            String str = span.textRId == -1 ? span.text : context.getString(span.textRId);
            rp = lp + str.length();

            spannableStringBuilder.append(str);
            if (span.spannable != null) {
                spannableStringBuilder.setSpan(span.spannable, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.textColor != -1) {
                ForegroundColorSpan foregroundColorSpan = new ForegroundColorSpan(context.getResources().getColor(span.textColor));
                spannableStringBuilder.setSpan(foregroundColorSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.backgroundColor != -1) {
                BackgroundColorSpan backgroundColorSpan = new BackgroundColorSpan(context.getResources().getColor(span.backgroundColor));
                spannableStringBuilder.setSpan(backgroundColorSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.relativeSize > 0) {
                RelativeSizeSpan relativeSizeSpan = new RelativeSizeSpan(span.relativeSize);
                spannableStringBuilder.setSpan(relativeSizeSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.xSpan > 0) {
                ScaleXSpan scaleXSpan = new ScaleXSpan(span.xSpan);
                spannableStringBuilder.setSpan(scaleXSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.style != -1) {
                StyleSpan styleSpan = new StyleSpan(span.style);
                spannableStringBuilder.setSpan(styleSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.isUnderline) {
                UnderlineSpan underlineSpan = new UnderlineSpan();
                spannableStringBuilder.setSpan(underlineSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            if (span.isStrikethrough) {
                StrikethroughSpan strikethroughSpan = new StrikethroughSpan();
                spannableStringBuilder.setSpan(strikethroughSpan, lp, rp, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            }

            lp = rp;
        }
    }

    public static class Span {

        private String text;
        private int textRId = -1;
        private int textColor = -1;
        private int backgroundColor = -1;
        private float relativeSize;
        private float xSpan;
        private int style = -1;
        private boolean isUnderline;
        private boolean isStrikethrough;
        private ParcelableSpan spannable;

        public Span setText(String text) {
            this.text = text;
            return this;
        }

        public Span setText(int rId) {
            this.textRId = rId;
            return this;
        }

        public Span setTextColor(int textColor) {
            this.textColor = textColor;
            return this;
        }

        public Span setBackgroundColor(int backgroundColor) {
            this.backgroundColor = backgroundColor;
            return this;
        }

        public Span setRelativeSize(float relativeSize) {
            this.relativeSize = relativeSize;
            return this;
        }

        public Span setxSpan(float xSpan) {
            this.xSpan = xSpan;
            return this;
        }

        public Span setStyle(int style) {
            this.style = style;
            return this;
        }

        public Span setUnderline(boolean underline) {
            isUnderline = underline;
            return this;
        }

        public Span setStrikethrough(boolean strikethrough) {
            isStrikethrough = strikethrough;
            return this;
        }

        public Span setSpannable(ParcelableSpan spannable) {
            this.spannable = spannable;
            return this;
        }

    }

}
