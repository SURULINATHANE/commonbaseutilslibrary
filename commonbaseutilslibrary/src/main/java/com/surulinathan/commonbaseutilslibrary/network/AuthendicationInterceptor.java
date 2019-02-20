package com.surulinathan.commonbaseutilslibrary.network;

import android.support.annotation.NonNull;

import com.android.highlander.customer.utils.SharedPrefsUtils;

import java.io.IOException;

import io.reactivex.disposables.CompositeDisposable;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import static com.android.highlander.customer.constants.Constants.ACCOUNT_PREFS;
import static com.android.highlander.customer.constants.Constants.TOKEN;

public class AuthendicationInterceptor implements Interceptor {
    private static CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        String token = SharedPrefsUtils.getString(ACCOUNT_PREFS, TOKEN);
        if (token == null || token.isEmpty()) {
            return chain.proceed(request);
        } else {
            request = request.newBuilder()
                    .addHeader("Content-Type", "application/json")
                    .addHeader("Authorization", "Bearer " + token)
                    .build();
        }
        Response response = chain.proceed(request);
        if (response.code() == 404 || response.code() == 401) {
            return retryWithFreshToken(request, chain, token);
        }
        return response;
    }

    private static Response retryWithFreshToken(Request req, Chain
            chain, String token) throws IOException {
        //  Log.d(LOG_TAG, "Retrying with new token");

        //Observable<TokenRequest> newToken = getRefreshToken(token);
        /*newToken
                .subscribe(TokenRequest() ->
*/
        String refreshToken = SharedPrefsUtils.getString(ACCOUNT_PREFS, TOKEN);

        if (refreshToken == null) {
            return null;
            //  TokenRequest tokenRequest=newToken.;
        } else {
            Request newRequest;
            newRequest = req.newBuilder().header("Authorization", " Bearer " + refreshToken).build();
            return chain.proceed(newRequest);
        }
    }


    /*public static Observable<TokenRequest> getRefreshToken(String token) {
        OtpVerificationRequest request = new OtpVerificationRequest();
        //   String token = SharedPrefsUtils.getString(ACCOUNT_PREFS, TOKEN);
        request.setToken(token);

        return ApiClientServiceDevHost.getInstance().createService(OnBoardingApi.class)
                .getRefreshToken(request).
                        map(response -> {
                            if (response.isStatus())
                                saveToken(response.getToken());
                            return response;
                        }).observeOn(AndroidSchedulers.mainThread());
    }*/

    private static void saveToken(String token) {
        if (token != null) {
            SharedPrefsUtils.set(ACCOUNT_PREFS, TOKEN, token);
        }
    }
}