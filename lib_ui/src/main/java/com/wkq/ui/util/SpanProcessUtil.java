package com.wkq.ui.util;

import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class SpanProcessUtil {

    private static volatile SpanProcessUtil instance;

    public static SpanProcessUtil getInstance() {
        if (instance == null) {
            synchronized (SpanProcessUtil.class) {
                if (instance == null) instance = new SpanProcessUtil();
            }
        }
        return instance;
    }

    /**
     * 处理文字颜色图片展示
     * @param tv
     * @param strContent
     * @param hashMap 展示文字的规则
     */
    public void processText(TextView tv, String strContent, HashMap<String, String> hashMap) {
        if (tv == null || TextUtils.isEmpty(strContent) || hashMap == null) return;
        String key = "(?=" + "\\[" + ".*?" + "\\])" + ".*?(?>" + "\\[" + "\\/" + ".*?" + "\\])";
        Pattern pattern = Pattern.compile(key);
        Matcher matcher = pattern.matcher(strContent);
        ArrayList<String> dataList = new ArrayList<>();
        String newData = strContent;
        int w=50;
        int h=50;
        while (matcher.find()) {
            String content = matcher.group();
            int newIndex = newData.indexOf(content);
            String ss = newData.substring(0, newIndex);
            dataList.add(ss + "-" + "0");
            dataList.add(content + "-" + "1");
            newData = newData.substring(newIndex + content.length());
        }
        if (!TextUtils.isEmpty(newData))dataList.add(newData+"-"+0);
        SpannableStringBuilder spannableString = new SpannableStringBuilder();
        for (String s : dataList) {
            String[] data = s.split("-");
            String content = data[0];
            String type = data[1];
            String removeData = removeOther(content);
            if (removeData==null)break;
            String mapV = getKey(hashMap, content);
            if (type.equals("1") && !TextUtils.isEmpty(mapV)) {
                int len = spannableString.length();
                if (content.contains("[" + "ic" + "]")) {
                    //处理网络
                    if (removeData.toString().startsWith("http")) {
                        spannableString.append(removeData);
//                        processNetIcon(tv, spannableString, removeData, len,w,h);
                    } else {
                        // todo 处理本地 展示假数据
//                        Drawable drawable = tv.getContext().getResources().getDrawable(R.mipmap.empty_icon);
//                        if (drawable==null)return;
//                        drawable.setBounds(0, 0, w, h);
//                        spannableString.append(removeData);
//                        spannableString.setSpan(new ImageSpan(drawable), len, len + removeData.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                    }
                } else {
                    spannableString.append(removeData);
                    spannableString.setSpan(new ForegroundColorSpan(Color.parseColor(mapV)), len, len + removeData.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                }
            } else {
                spannableString.append(content);
            }
        }
        tv.setText(spannableString);
    }
    public String removeOther(String content) {
        String rex = "\\[" + ".*?" + "\\]";
        Pattern pattern = Pattern.compile(rex);
        Matcher matcher = pattern.matcher(content);
        return matcher.replaceAll("");
    }

//    private void processNetIcon(TextView tv, SpannableStringBuilder spannableString, String url, int startIndex, int w, int h) {
//        Observable.create(new ObservableOnSubscribe<Drawable>() {
//            @Override
//            public void subscribe(ObservableEmitter<Drawable> e) {
//                Drawable drawable = null;
//                try {
//                    drawable = Glide.with(tv.getContext()).asDrawable().load(url).submit(w, h).get();
//                    if (drawable!=null)  e.onNext(drawable);
//                } catch (Exception ex) {
//                    e.onError(ex);
//                }
//            }
//        }).subscribeOn(Schedulers.newThread()).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<Drawable>() {
//            @Override
//            public void onSubscribe(Disposable disposable) {
//
//            }
//
//            @Override
//            public void onNext(Drawable s) {
//                ImageSpan span = new ImageSpan(s, ImageSpan.ALIGN_CENTER);
//                s.setBounds(0, 0, 50, 50);
//                if (spannableString != null && tv != null) {
//                    spannableString.setSpan(span, startIndex, startIndex + url.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
//                    tv.setText(spannableString);
//                }
//            }
//
//            @Override
//            public void onError(Throwable throwable) {
//
//            }
//
//            @Override
//            public void onComplete() {
//
//            }
//        });
//    }

    /**
     * 获取key
     *
     * @param hashMap
     * @param text
     * @return
     */
    public String getKey(HashMap<String, String> hashMap, String text) {
        Iterator<String> iterator = hashMap.keySet().iterator();
        String isContains = "";
        while (iterator.hasNext()) {
            String obj = iterator.next();
            if (text.contains("[" + obj + "]")) {
                isContains = hashMap.get(obj);
                break;
            }
        }
        return isContains;
    }

}
