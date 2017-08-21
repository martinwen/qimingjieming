package com.tjyw.atom.network.interceptor;

import com.brianjmelton.stanley.ProxyGenerator;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.tjyw.atom.network.Network;
import com.tjyw.atom.network.conf.IApiField;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.utils.DeviceUtil;
import com.tjyw.atom.network.utils.JsonUtil;

import java.io.IOException;

import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

/**
 * Created by stephen on 20/03/2017.
 */
public class RetroRequestInterceptor implements Interceptor {

    static final String TAG = RetroRequestInterceptor.class.getSimpleName();

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        builder.header("Content-type", "application/x-www-form-urlencoded;");
        builder.header("Connection", "Close");

        if (chain.request().body() instanceof FormBody) {
            return interceptFormBody(builder, chain);
        } else {
            return interceptNormalBody(builder, chain);
        }
    }


    protected Response interceptFormBody(Request.Builder builder, Chain chain) throws IOException {
        IPrefUser preferences = new ProxyGenerator().create(Network.getContext(), IPrefUser.class);
        String session = preferences.getUserSession();

        FormBody body = (FormBody) chain.request().body();
        ObjectNode params = JsonUtil.getInstance().getEmptyNode();
//        params.put(IApiField.S.sessionKey, session);

        int size = body.size();
        for (int i = 0; i < size; i ++) {
            params.put(body.encodedName(i), body.encodedValue(i));
        }

        Request newRequest = builder.post(
                new FormBody.Builder()
                        .addEncoded(IApiField.D.data, JsonUtil.getInstance().toJsonString(params))
                        .addEncoded(IApiField.P.packageName, Network.getContext().getPackageName())
                        .addEncoded(IApiField.P.platform, IApiField.A.ANDROID)
                        .addEncoded(IApiField.V.version, DeviceUtil.getClientVersionName(Network.getContext()))
                        .addEncoded(IApiField.V.versionCode, String.valueOf(DeviceUtil.getClientVersionCode(Network.getContext())))
                        .addEncoded(IApiField.S.screenWidth, String.valueOf(Network.getInstance().getScreenWidth()))
                        .addEncoded(IApiField.S.screenHeight, String.valueOf(Network.getInstance().getScreenHeight()))
                        .addEncoded(IApiField.U.uid, DeviceUtil.getDeviceID())
                        .addEncoded(IApiField.P.pid, Network.getInstance().getPid())
                        .addEncoded(IApiField.C.cid, Network.getInstance().getCid())
//                        .addEncoded(IApiField.S.sessionKey, session)
                        .add(IApiField.B.build, DeviceUtil.transformBuildInfo())
                        .add(IApiField.T.telephone, DeviceUtil.transformTelephonyManagerInfo())
                        .build()
        ).build();

        Response response = chain.proceed(newRequest);
        String responseString = response.body().string();
        Timber.tag(TAG).e("InterceptFormBody::Request Json::%s", responseString);
        return response.newBuilder()
                .body(ResponseBody.create(MediaType.parse("application/x-www-form-urlencoded;"), responseString))
                .build();
    }


    protected Response interceptNormalBody(Request.Builder builder, Chain chain) throws IOException {
        IPrefUser preferences = new ProxyGenerator().create(Network.getContext(), IPrefUser.class);
        String session = preferences.getUserSession();

        Request request = chain.request();
        HttpUrl requestUrl = request.url();
        HttpUrl.Builder newRequestUrlBuilder = requestUrl.newBuilder();

        ObjectNode params = JsonUtil.getInstance().getEmptyNode();
//        params.put(IApiField.S.sessionKey, session);
        for (String key : requestUrl.queryParameterNames()) {
            params.put(key, requestUrl.queryParameter(key));
        }

        Request newRequest = builder.url(
                newRequestUrlBuilder
                        .addEncodedQueryParameter(IApiField.D.data, JsonUtil.getInstance().toJsonString(params))
                        .addEncodedQueryParameter(IApiField.P.packageName, Network.getContext().getPackageName())
                        .addEncodedQueryParameter(IApiField.P.platform, IApiField.A.ANDROID)
                        .addEncodedQueryParameter(IApiField.V.version, DeviceUtil.getClientVersionName(Network.getContext()))
                        .addEncodedQueryParameter(IApiField.V.versionCode, String.valueOf(DeviceUtil.getClientVersionCode(Network.getContext())))
                        .addEncodedQueryParameter(IApiField.S.screenWidth, String.valueOf(Network.getInstance().getScreenWidth()))
                        .addEncodedQueryParameter(IApiField.S.screenHeight, String.valueOf(Network.getInstance().getScreenHeight()))
                        .addEncodedQueryParameter(IApiField.U.uid, DeviceUtil.getDeviceID())
                        .addEncodedQueryParameter(IApiField.P.pid, Network.getInstance().getPid())
                        .addEncodedQueryParameter(IApiField.C.cid, Network.getInstance().getCid())
//                        .addEncodedQueryParameter(IApiField.S.sessionKey, session)
                        .addQueryParameter(IApiField.B.build, DeviceUtil.transformBuildInfo())
                        .addQueryParameter(IApiField.T.telephone, DeviceUtil.transformTelephonyManagerInfo())
                        .build()
        ).build();

        Response response = chain.proceed(newRequest);
        String responseString = response.body().string();
        Timber.tag(TAG).e("InterceptNormalBody::Request Json::%s", responseString);
        return response.newBuilder()
                .body(ResponseBody.create(MediaType.parse("application/x-www-form-urlencoded;"), responseString))
                .build();
    }
}
