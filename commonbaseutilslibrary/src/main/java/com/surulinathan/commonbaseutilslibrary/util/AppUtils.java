package com.surulinathan.commonbaseutilslibrary.util;

import android.net.Uri;

import com.surulinathan.commonbaseutilslibrary.network.ApiError;

import java.io.File;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.HttpException;

public class AppUtils {
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";

    public static boolean isEmpty(String text) {
        return text == null || text.equals("") || text.matches(" *") || text.contains("null");
    }

    public static boolean isValidPassword(String password) {
        return (password == null || password.length() < 8);
    }

    public static boolean isValidMobileNo(String mobileNo) {
        return !(mobileNo != null && mobileNo.length() == 10);
    }

    public static boolean isValidLength(String text, int length) {
        return !(text != null && text.length() == length);
    }

    public static boolean isValidEmail(String email) {
        return email == null || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    public static boolean isValidGSTIN(String gstin) {
        Pattern pattern;
        Matcher matcher;

        final String GSTIN_PATTERN = "\\d{2}[A-Z]{3}[C/P/H/F/A/T/B/L/J/G]{1}[A-Z]{1}\\d{4}[A-Z]{1}[A-Z\\d]{1}[Z]{1}[A-Z\\d]{1}";

        pattern = Pattern.compile(GSTIN_PATTERN);
        matcher = pattern.matcher(gstin);

        return matcher.matches();
    }

    public static boolean isValidPAN(String pan) {
        Pattern pattern;
        Matcher matcher;

        final String PAN_PATTERN = "[A-Z]{3}[C/P/H/F/A/T/B/L/J/G]{1}[A-Z]{1}\\d{4}[A-Z]{1}";

        pattern = Pattern.compile(PAN_PATTERN);
        matcher = pattern.matcher(pan);

        return matcher.matches();
    }

    public static boolean isValidPan(String pan) {
        return !(pan != null || pan.length() == 10);
    }

    // Check throwable
    public static String checkThrowable(Throwable t) {

        // Check for internet connection
        if (t instanceof UnknownHostException ||
                t instanceof SocketTimeoutException) {
            return "Please check your internet connection";
        } else if (t instanceof ConnectException) {
            return "Could not connect to Server";
        }
        // Check for server http error
        else if (t instanceof HttpException) {
            // Check for server http error
            HttpException e = (HttpException) t;
            int responseCode = e.code();
            switch (responseCode) {
                case 401:
                    ApiError error = ErrorUtils.parseError(((HttpException) t).response());
                    try {
                        if (error != null && error.getCode() != null && error.getCode().equalsIgnoreCase("invalid_credentials")) {
                            return "Invalid credentials";
                        } else if (error != null && error.getCode() != null && error.getCode().equalsIgnoreCase("invalid_email")) {
                            return "Invalid email";
                        } else {
                            if (error != null && error.getMessage() != null)
                                return error.getMessage();
                        }
                    } catch (Exception ignored) {
                        return error.getError();
                    }
                case 404:
                    return "URL not found";
                case 405:
                    return "Method Not Allowed";
                case 500:
                    return "Server broken";
                default:
                    ApiError err = ErrorUtils.parseError(((HttpException) t).response());
                    if (err.getMessage() != null)
                        return err.getMessage();

                    return err.getError();
            }
        } else {
            return "Something went wrong.Please try again later.";
        }
    }

    // Create multi part form data
    public static RequestBody createPartFromString(String descriptionString) {
        return RequestBody.create(
                MediaType.parse(MULTIPART_FORM_DATA), descriptionString);
    }

    // Create multi part file
    public static MultipartBody.Part prepareFilePart(String partName, Uri fileUri) {
        // use the FileUtils to get the actual file by uri
        File file = new File(fileUri.getPath());
        // create RequestBody instance from file
        RequestBody requestFile = RequestBody.create(MediaType.parse(MULTIPART_FORM_DATA), file);

        // MultipartBody.Part is used to send also the actual file name
        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

    public static String rupeesFormat(String rupees) {
        if (rupees != null && !rupees.equalsIgnoreCase("null") && !rupees.isEmpty()) {
            Format format = NumberFormat.getCurrencyInstance(new Locale("en", "in"));
            return format.format(Double.valueOf(rupees.replace(",", "")));
        } else {
            return "";
        }
    }

    public static String numberFormat(String number) {
        if (number != null && !number.equalsIgnoreCase("null") && !number.isEmpty()) {
            return NumberFormat.getNumberInstance(new Locale("en", "in")).format(Long.parseLong(number));
        } else {
            return "";
        }
    }

    public static String[] splitStringBySeparator(String string, String separatorString) {
        return string.split(separatorString);
    }

    public static String capitalize(String capString) {
        StringBuffer capBuffer = new StringBuffer();
        Matcher capMatcher = Pattern.compile("([a-z])([a-z]*)", Pattern.CASE_INSENSITIVE).matcher(capString);
        while (capMatcher.find()) {
            capMatcher.appendReplacement(capBuffer, capMatcher.group(1).toUpperCase() + capMatcher.group(2).toLowerCase());
        }

        return capMatcher.appendTail(capBuffer).toString();
    }

    public static double roundTwoDecimals(double value) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(value));
    }

    public static double roundNearestWholeNumber(double value) {
        return Math.round(value);
    }
}