package com.wkq.net.gson;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.wkq.net.NetConstant;
import com.wkq.net.model.ResultInfo;
import com.wkq.net.model.SpecialCodeBean;
import com.wkq.net.observable.SpecialCodeObservable;
import com.wkq.net.util.MMKVUtils;
import com.wkq.net.util.NetHelper;
import com.wkq.net.util.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.HashSet;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 统一封装请求返回的数据 格式成项目需要的格式
 *
 * @param <T>
 */
final class GsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private boolean decode;
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter, boolean decode) {
        this.gson = gson;
        this.adapter = adapter;
        this.decode = decode;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
//       String content= "{\"data\":[{\"desc\":\"我们支持订阅啦~\",\"id\":30,\"imagePath\":\"https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png\",\"isVisible\":1,\"order\":2,\"title\":\"我们支持订阅啦~\",\"type\":0,\"url\":\"https://www.wanandroid.com/blog/show/3352\"},{\"desc\":\"\",\"id\":6,\"imagePath\":\"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png\",\"isVisible\":1,\"order\":1,\"title\":\"我们新增了一个常用导航Tab~\",\"type\":1,\"url\":\"https://www.wanandroid.com/navi\"},{\"desc\":\"一起来做个App吧\",\"id\":10,\"imagePath\":\"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png\",\"isVisible\":1,\"order\":1,\"title\":\"一起来做个App吧\",\"type\":1,\"url\":\"https://www.wanandroid.com/blog/show/2\"}],\"errorCode\":0,\"errorMsg\":\"\"}";
//       String content= "{\"data\":[{\"desc\":\"我们支持订阅啦~\",\"id\":30,\"imagePath\":\"https://www.wanandroid.com/blogimgs/42da12d8-de56-4439-b40c-eab66c227a4b.png\",\"isVisible\":1,\"order\":2,\"title\":\"我们支持订阅啦~\",\"type\":0,\"url\":\"https://www.wanandroid.com/blog/show/3352\"},{\"desc\":\"\",\"id\":6,\"imagePath\":\"https://www.wanandroid.com/blogimgs/62c1bd68-b5f3-4a3c-a649-7ca8c7dfabe6.png\",\"isVisible\":1,\"order\":1,\"title\":\"我们新增了一个常用导航Tab~\",\"type\":1,\"url\":\"https://www.wanandroid.com/navi\"},{\"desc\":\"一起来做个App吧\",\"id\":10,\"imagePath\":\"https://www.wanandroid.com/blogimgs/50c115c2-cf6c-4802-aa7b-a4334de444cd.png\",\"isVisible\":1,\"order\":1,\"title\":\"一起来做个App吧\",\"type\":null,\"url\":\"https://www.wanandroid.com/blog/show/2\"}],\"errorCode\":103,\"errorMsg\":\"\"}";
//        String content = "{\"data\":null,\"errorCode\":0,\"errorMsg\":\"\"}";
//        String content = "{\"errorCode\":0,\"errorMsg\":\"\"}";
//        String content = "{}";
//        String content = "[]";
        String content = value.string();
        JsonReader jsonReader = gson.newJsonReader(new StringReader(content));

        T t = adapter.read(jsonReader);
        ResultInfo resultInfo = null;
        if (t instanceof ResultInfo) {
            resultInfo = (ResultInfo) t;
        }
        Object tData = resultInfo.getData();
        JsonElement je = JsonParser.parseReader(gson.newJsonReader(new StringReader(content)));
        String code = NetHelper.INSTANCE.getNetCode((JsonObject) je);
        String msg = NetHelper.INSTANCE.getNetMsg((JsonObject) je);
        boolean isSuccess = NetHelper.INSTANCE.processSuccessStatus((JsonObject) je);
        resultInfo.setCode(code);
        resultInfo.setMsg(msg);
        resultInfo.setData(tData);
        resultInfo.setSuccess(isSuccess);
        NetHelper.INSTANCE.processSpecialCode(resultInfo);
        return (T) resultInfo;


//        if (je.isJsonObject()) {
//            JsonObject jsonObject = (JsonObject) je;
//            T info = (T) processContentByKey(jsonObject);
//            return info;
//        } else if (je.isJsonArray()) {
//            //array
//            return (T) new ResultInfo(NetConstant.INSTANCE.getSUCESSCODE() + "", NetConstant.INSTANCE.getMESSAGE_SUCCESS(), je.getAsJsonArray());
//
//        } else {
//            errorCode = NetConstant.INSTANCE.getMESSAGE_DATA_FORMAT_ERROR() + "";
//            errorMsg = NetConstant.INSTANCE.getMESSAGE_DATA_FORMAT_ERROR();
//            return (T) (new ResultInfo(errorCode, errorMsg, data));
//        }
    }

    /**
     * 统一数据结构
     *
     * @param jsonObject
     * @return
     */
    private ResultInfo processContentByKey(JsonObject jsonObject) {

        String errorCode = NetConstant.INSTANCE.getSUCESSCODE() + "";
        String errorMsg = "";
        Object data = null;
        HashSet<String> CodeKey = (HashSet<String>) MMKVUtils.INSTANCE.getStringSet("CodeKey");
        HashSet<String> MsgKey = (HashSet<String>) MMKVUtils.INSTANCE.getStringSet("MsgKey");
        HashSet<String> DataKey = (HashSet<String>) MMKVUtils.INSTANCE.getStringSet("DataKey");
        HashSet<String> SuccessCodeKey = (HashSet<String>) MMKVUtils.INSTANCE.getStringSet("SuccessCodeKey");
        HashSet<String> SpecialCodeKey = (HashSet<String>) MMKVUtils.INSTANCE.getStringSet("SpecialCodeKey");
        ResultInfo info = new ResultInfo(errorCode, errorMsg, data);

        // 处理 code
        for (String cKey : CodeKey) {
            if (jsonObject.has(cKey)) {
                if (SuccessCodeKey.contains(jsonObject.get(cKey).getAsString())) {
                    errorCode = NetConstant.INSTANCE.getSUCESSCODE() + "";
                    info.setSuccess(true);
                } else {
                    errorCode = jsonObject.get(cKey).getAsString();
                }
                break;
            }
        }
        // 处理msg
        for (String mKey : MsgKey) {
            if (jsonObject.has(mKey)) {
                errorMsg = jsonObject.get(mKey).getAsString();
                break;
            }
        }
        // 处理data
        for (String mKey : DataKey) {
            if (jsonObject.has(mKey)) {
                if (jsonObject.get(mKey).isJsonArray()) {
                    data = jsonObject.get(mKey).getAsJsonArray();
                } else if (jsonObject.get(mKey).isJsonObject()) {
                    data = jsonObject.get(mKey).getAsJsonObject();
                } else {
                    data = null;
                }
                break;
            }
        }
        if (TextUtils.isEmpty(errorCode)) {
            errorCode = NetConstant.INSTANCE.getMESSAGE_DATA_FORMAT_ERROR() + "";
        }

        //特殊code处理
        if (SpecialCodeKey.contains(errorCode)) {
            SpecialCodeObservable.Companion.newInstance().updateSpecialCode(new SpecialCodeBean(errorCode, errorMsg, info));
        }
        info.setCode(errorCode);
        info.setMsg(errorMsg);
        info.setData(data);
        return info;
    }


    private static String convert(Reader reader) throws IOException {
        BufferedReader r = new BufferedReader(reader);
        StringBuilder b = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) b.append(line);
        return StringUtils.decode(b.toString());
    }


}