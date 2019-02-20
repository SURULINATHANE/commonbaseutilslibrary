package com.surulinathan.commonbaseutilslibrary.network;

import android.content.Context;
import android.support.annotation.NonNull;

import com.surulinathan.commonbaseutilslibrary.util.SharedPrefsUtils;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.surulinathan.commonbaseutilslibrary.application.Initializer.getStaticContext;
import static com.surulinathan.commonbaseutilslibrary.constants.Constants.ACCOUNT_PREFS;
import static com.surulinathan.commonbaseutilslibrary.constants.Constants.TOKEN;

public class ApiInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        Context context = getStaticContext();

        if (SharedPrefsUtils.getString(context, ACCOUNT_PREFS, TOKEN) == null ||
                SharedPrefsUtils.getString(context, ACCOUNT_PREFS, TOKEN).isEmpty()) {
            return chain.proceed(request);
        }

        String token = SharedPrefsUtils.getString(context, ACCOUNT_PREFS, TOKEN);

        // Adding token as Authorization header for every request
        request = request.newBuilder()
                .addHeader("Content-Type", "application/json")
                .addHeader("Authorization", "Bearer " + token)
                .build();

        return chain.proceed(request);
    }
}