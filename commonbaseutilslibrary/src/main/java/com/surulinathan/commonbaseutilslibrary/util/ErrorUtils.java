package com.surulinathan.commonbaseutilslibrary.util;

import com.surulinathan.commonbaseutilslibrary.network.ApiClient;
import com.surulinathan.commonbaseutilslibrary.network.ApiError;

import java.io.IOException;
import java.lang.annotation.Annotation;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Response;

public class ErrorUtils {

    public static ApiError parseError(Response<?> response) {
        Converter<ResponseBody, ApiError> converter =
                ApiClient.getInstance().
                        retrofit().responseBodyConverter(ApiError.class, new Annotation[0]);

        ApiError error = null;

        try {
            error = converter.convert(response.errorBody());
        } catch (IOException e) {
            return error;
        }

        return error;
    }
}