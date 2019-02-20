package com.surulinathan.commonbaseutilslibrary.network;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.android.highlander.customer.HighlanderCustomerApp;
import com.android.highlander.customer.network.response.onBoarding.RefreshTokenResponse;
import com.android.highlander.customer.ui.activities.onBoarding.MobileNumberLoginActivity;
import com.android.highlander.customer.utils.SharedPrefsUtils;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import retrofit2.Call;

import static com.android.highlander.customer.constants.Constants.ACCOUNT_PREFS;
import static com.android.highlander.customer.services.OnBoardingService.getRefreshToken;
import static com.android.highlander.customer.services.OnBoardingService.saveToken;

public class TokenAuthenticator implements Authenticator {

    @Override
    public synchronized Request authenticate(@NonNull Route route, @NonNull Response response) throws IOException {
        if (responseCount(response) >= 2) {
            return null;
        }
        if (response.code() == 401) {
            Call<RefreshTokenResponse> refreshTokenResponse = getRefreshToken();
            retrofit2.Response res = refreshTokenResponse.execute();
            if (res.code() == 400) {
                // Similar to logout functionality
                SharedPrefsUtils.clearPreferences(ACCOUNT_PREFS, HighlanderCustomerApp.getStaticContext());
                // Move user to login activity
                HighlanderCustomerApp.getStaticContext().startActivity(new Intent(HighlanderCustomerApp.getStaticContext(), MobileNumberLoginActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                                Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NEW_TASK)
                );
                return null;
            }
            if (res.isSuccessful()) {
                RefreshTokenResponse tokenResponse = (RefreshTokenResponse) res.body();
                if (tokenResponse != null && tokenResponse.getToken() != null) {
                    saveToken(tokenResponse.getToken());
                    return response.request().newBuilder()
                            .header("Content-Type", "application/json")
                            .header("Authorization", "Bearer " + tokenResponse.getToken())
                            .build();
                } else {
                   /* // Similar to logout functionality
                    SharedPrefsUtils.clearPreferences(ACCOUNT_PREFS, HighlanderCustomerApp.getStaticContext());*/
                    // Move user to login activity
                    Intent intent = new Intent(HighlanderCustomerApp.getStaticContext(), MobileNumberLoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    HighlanderCustomerApp.getStaticContext().startActivity(intent);
                }
            }
        }
        return null;
    }

    private int responseCount(Response response) {
        int result = 1;
        while ((response = response.priorResponse()) != null) {
            result++;
        }
        return result;
    }
}