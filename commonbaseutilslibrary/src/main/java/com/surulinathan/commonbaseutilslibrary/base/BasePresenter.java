package com.surulinathan.commonbaseutilslibrary.base;

import android.content.Context;

import com.surulinathan.commonbaseutilslibrary.application.Initializer;

import nucleus5.presenter.RxPresenter;

import static com.surulinathan.commonbaseutilslibrary.util.LogUtils.makeLogTag;


public class BasePresenter<View> extends RxPresenter<View> {
    protected static final String TAG = makeLogTag("BasePresenter");

    protected Context getContext() {
        return Initializer.getInstance().getApplicationContext();
    }
}

