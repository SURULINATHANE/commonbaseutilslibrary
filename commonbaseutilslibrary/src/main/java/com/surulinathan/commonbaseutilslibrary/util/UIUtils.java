package com.surulinathan.commonbaseutilslibrary.util;

import android.app.Activity;
import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v4.content.res.ResourcesCompat;
import android.util.DisplayMetrics;
import android.util.TypedValue;

import java.util.concurrent.atomic.AtomicInteger;

import static com.surulinathan.commonbaseutilslibrary.util.LogUtils.makeLogTag;

public class UIUtils {
    private static final String TAG = makeLogTag("UIUtils");

    private static final AtomicInteger sNextGeneratedId = new AtomicInteger(1);

    public static String getLetterAvatarText(String name) {
        if (name == null)
            return "";
        String[] split = name.split(" |_");
        try {
            if (split.length == 1) {
                return "" + split[0].charAt(0);
            } else if (split.length > 1) {
                return "" + split[0].charAt(0) + split[1].charAt(0);
            }
        } catch (Exception e) {
            return "CC";
        }
        return "";
    }

    public static int dpToPx(Context context, int dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static float dpToPx(Context context, float dp) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int pxToDp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        return Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
    }

    public static int spToPx(float sp, Context context) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, context.getResources().getDisplayMetrics());
    }

    public static int generateViewId() {
        for (; ; ) {
            final int result = sNextGeneratedId.get();
            // aapt-generated IDs have the high byte nonzero; clamp to the range under that.
            int newValue = result + 1;
            if (newValue > 0x00FFFFFF) newValue = 1; // Roll over to 1, not 0.
            if (sNextGeneratedId.compareAndSet(result, newValue)) {
                return result;
            }
        }
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.widthPixels;
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics displaymetrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        return displaymetrics.heightPixels;
    }

    public static void setTextInputLayoutTypeFace(Activity activity, TextInputLayout view, int font) {
        try {
            view.setTypeface(ResourcesCompat.getFont(activity, font));
        } catch (Exception ignored) {

        }
    }
}
