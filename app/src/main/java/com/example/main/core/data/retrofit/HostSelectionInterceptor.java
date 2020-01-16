package com.example.main.core.data.retrofit;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class HostSelectionInterceptor implements Interceptor {
    private volatile String host;
    private volatile int port;

    public void setHost(String host) {
        this.host = HttpUrl.parse(host).host();
        this.port = HttpUrl.parse(host).port();
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        String host = this.host;
        int port = this.port;
        if(host!=null){
            HttpUrl newUrl = request.url().newBuilder()
                    .host(host)
                    .port(port)
                    .build();
            request = request.newBuilder()
                    .url(newUrl)
                    .build();
        }


        return chain.proceed(request);
    }
}
