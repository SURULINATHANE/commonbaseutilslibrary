package com.surulinathan.commonbaseutilslibrary.base;

import nucleus5.presenter.Presenter;
import nucleus5.view.NucleusAppCompatActivity;

public abstract class BaseActivity<P extends Presenter> extends NucleusAppCompatActivity<P> {
    private static final String TAG = "BaseActivity";

    /*@Nullable
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @Nullable
    @BindView(R.id.tvToolbarTitle)
    TextView tvToolbarTitle;

    @Nullable
    @BindView(R.id.llProgress)
    LinearLayout llProgress;


    private static final String DEBUG = "com.surulinathan.commonbaseutilslibrary.debug";
    private static final String BETA = "com.surulinathan.commonbaseutilslibrary.beta";
    private static final String RELEASE = "com.surulinathan.commonbaseutilslibrary";
    Menu defaultMenu;
    private Unbinder viewUnBinder;
    private BroadcastReceiver onComplete;
    private String ALERT_DIALOG = "Dialog";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Set activity orientation to portrait
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        // Hide edit text keyboard pop up
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
    }


    private void generateKeyHash() {
        // Key hash code
        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    DEBUG, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.e("Hash key", "" + Base64.encodeToString(md.digest(), Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            Log.d(TAG, e.getMessage(), e);
        }
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        viewUnBinder = ButterKnife.bind(this);
        setToolbar();
    }

    @Override
    public void setContentView(View view) {
        super.setContentView(view);
        viewUnBinder = ButterKnife.bind(this);
        setToolbar();
    }

    @Override
    public void setContentView(View view, ViewGroup.LayoutParams params) {
        super.setContentView(view, params);
        viewUnBinder = ButterKnife.bind(this);
        setToolbar();
    }

    @Override
    protected void onDestroy() {
        if (viewUnBinder != null)
            viewUnBinder.unbind();
        hideKeyBoard();
        super.onDestroy();
    }

    private void setToolbar() {
        if (toolbar != null) {
            setToolbarEmptyTitle();
            setSupportActionBar(toolbar);
            if (getSupportActionBar() != null) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                getSupportActionBar().setHomeButtonEnabled(true);
            }
        }
    }

    public void setToolbarEmptyTitle() {
        if (toolbar != null) {
            toolbar.setTitle("");
        }
    }

    public void setToolbarTitle(int id) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText(id);
        }
    }

    public void setToolbarTitle(String title) {
        if (tvToolbarTitle != null) {
            tvToolbarTitle.setText(title);
        }
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void changeActivity(Class clz) {
        Intent i = new Intent(this, clz);
        startActivity(i);
    }

    public void changeActivityClearBackStack(Class clz) {
        Intent intent = new Intent(this, clz);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                Intent.FLAG_ACTIVITY_NEW_TASK |
                Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    protected void DisabkleLockScreen() {
        KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Activity.KEYGUARD_SERVICE);
        KeyguardManager.KeyguardLock lock = keyguardManager.newKeyguardLock(KEYGUARD_SERVICE);
        lock.disableKeyguard();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : getSupportFragmentManager().getFragments()) {
                if (fragment != null) fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    public void clearBackStackEntries() {
        getSupportFragmentManager()
                .popBackStack(null, FragmentManager.POP_BACK_STACK_INCLUSIVE);
    }

    public void hideKeyBoard() {
        if (getCurrentFocus() != null) {
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
            }
        }
    }

    public void showKeyBoard() {
        if (getCurrentFocus() != null) {
            final InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, InputMethodManager.HIDE_IMPLICIT_ONLY);
            }
        }
    }

    public void showProgressbar() {
        if (llProgress != null) {
            llProgress.setVisibility(View.VISIBLE);
        }
    }

    public void hideProgressbar() {
        if (llProgress != null) {
            llProgress.setVisibility(View.GONE);
        }
    }

    public void onFailure(String error) {
        hideProgressbar();
        if (!error.equals("Expired token") && !error.equals("Token absent") && !error.equals("Token expired")) {
            showToast(error);
        }
    }

    public void onFailure(int strRes) {
        hideProgressbar();
        showToast(strRes);
    }

    public void shareButton(String shareBody) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }

    //Programmatically get the current version Name
    public String getApplicationVersionName() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            return "(" + packageInfo.versionName + ")";
        } catch (Exception ignored) {
        }
        return "";
    }

    public void setImage(String imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load( imagePath)
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        (source).setBackground(resource);
                    }
                });
    }

    public void setImageSrc(String imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load( imagePath)
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .centerCrop()
                .into(source);
    }

    public void setImageSrc(int imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load(imagePath)
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .into(source);
    }

    public void setImageSrcDirectPath(String imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load(imagePath)
                .circleCrop()
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .into(source);
    }

    public void setImageSrcDirectPathWithOutCenterCrop(String imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load(imagePath)
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .into(new SimpleTarget<Drawable>() {
                    @Override
                    public void onResourceReady(@NonNull Drawable resource, @Nullable Transition<? super Drawable> transition) {
                        (source).setBackground(resource);
                    }
                });
    }

    public String setFormattedRegNo(String regNo) {
        String formatRegNumber = "";
        if (!regNo.isEmpty()) {
            // set first two fields of the reg number
            if (regNo.length() == 9 || regNo.length() == 10) {
                formatRegNumber += regNo.substring(0, 2) + " ";
                formatRegNumber += regNo.substring(2, 4) + " ";
            }
            if (regNo.length() == 12 || regNo.length() == 13) {
                formatRegNumber += regNo.substring(0, 2) + " ";
                formatRegNumber += regNo.substring(3, 6) + " ";
            }
            // set last two fields of the reg number
            if (regNo.length() == 9) {
                formatRegNumber += String.valueOf(regNo.charAt(4)) + " ";
                formatRegNumber += regNo.substring(5, 9);
            }
            if (regNo.length() == 10) {
                formatRegNumber += regNo.substring(4, 6) + " ";
                formatRegNumber += regNo.substring(6, 10);
            }
            if (regNo.length() == 12) {
                formatRegNumber += regNo.charAt(6) + " ";
                formatRegNumber += regNo.substring(8, 12);
            }
            if (regNo.length() == 13) {
                formatRegNumber += regNo.substring(6, 9) + " ";
                formatRegNumber += regNo.substring(9, 13);
            }
            if (regNo.length() < 9) {
                formatRegNumber = regNo;
            }
        }
        return formatRegNumber;
    }

    public static String getLastName(String fullName) {
        if (fullName != null) {
            int index = fullName.lastIndexOf(" ");
            if (index > -1 && index > 0) {
                return fullName.substring(index + 1, fullName.length());
            } else if (index == -1) {
                return "";
            } else {
                return "";
            }
        } else {
            return "";
        }
    }

    public static String getFirstName(String fullName) {
        if (fullName != null) {
            int index = fullName.lastIndexOf(" ");
            if (index > -1) {
                return fullName.substring(0, index);
            }
            return fullName;
        }
        return "";
    }

    public void doCall(String number) {
        Intent i = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + number));
        try {
            startActivity(i);
        } catch (Exception e) {
            // this can happen if the device can't make phone calls
            // for example, a tablet
        }
    }

    public void callMap(Double lat, Double lon) {
       *//* if (lat != null && lon != null) {
            Uri gmmIntentUri = Uri.parse("geo:" + lat + "," + lon);
            Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
            mapIntent.setPackage("com.google.android.apps.maps");
            if (mapIntent.resolveActivity(getPackageManager()) != null) {
                startActivity(mapIntent);
            }
        }*//*
        String address = "Highlander Site No 1597, 20th Main Rd, Agara Village, 1st Sector, HSR Layout, Bengaluru, Karnataka 560102";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("geo:" + lat + "," + lon + "?q=" + address));
        startActivity(intent);
    }

    public static String fileName, pdfType;

    public void doUrlPdfDownload(String url, String fileName, String pdfType) {
        this.fileName = fileName;
        this.pdfType = pdfType;

        DownloadManager downloadManager = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);

        Uri Download_Uri = Uri.parse(url);

        DownloadManager.Request request = new DownloadManager.Request(Download_Uri);
        request.setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI | DownloadManager.Request.NETWORK_MOBILE);
        request.setAllowedOverRoaming(false);
        request.setTitle(*//*"Hl_bikes_" +*//* fileName *//*+ pdfType *//* + System.currentTimeMillis() + ".pdf");
        request.setDescription(*//*"Hl_bikes_" + *//**//*pdfType*//*fileName + ".pdf");
        request.setVisibleInDownloadsUi(true);
        request.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, "/downloads/" + fileName + pdfType + System.currentTimeMillis() + ".pdf");
        if (downloadManager != null) {
            long refid = downloadManager.enqueue(request);
            //Log.e("INSIDE", " refid " + refid);
        }
    }

    public BroadcastReceiver onCompleteDownload() {
        onComplete = new BroadcastReceiver() {

            public void onReceive(Context ctxt, Intent intent) {
                // get the refid from the download manager
                long referenceId = intent.getLongExtra(DownloadManager.EXTRA_DOWNLOAD_ID, -1);
                // show a notification
                Log.e("INSIDE", " " + referenceId);

                final int NOTIFY_ID = 0; // ID of notification
                String id = "default_notification_channel_id"; // default_channel_id
                String title = "default_notification_channel_title"; // Default Channel
                intent = null;
                PendingIntent pendingIntent;
                NotificationCompat.Builder builder = null;
                NotificationManager notificationManager = null;
                notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    int importance = NotificationManager.IMPORTANCE_HIGH;
                    NotificationChannel mChannel = null;
                    if (notificationManager != null) {
                        mChannel = notificationManager.getNotificationChannel(id);
                    }
                    if (mChannel == null) {
                        mChannel = new NotificationChannel(id, title, importance);
                        mChannel.enableVibration(true);
                        mChannel.setVibrationPattern(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});
                        notificationManager.createNotificationChannel(mChannel);
                    }
                    builder = new NotificationCompat.Builder(getApplicationContext(), id);
                    intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                    pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    builder.setContentTitle(fileName + " " *//**//* *//*pdfType*//* + " Pdf")  // required
                            .setSmallIcon(android.R.drawable.stat_sys_download) // required
                            .setContentText(fileName + " Pdf Download completed")  // required
                            .setDefaults(Notification.DEFAULT_ALL)
                            .setAutoCancel(true)
                            .setContentIntent(pendingIntent)
                            .setTicker("Download pdf")
                            .setVibrate(new long[]{100, 200, 300, 400, 500, 400, 300, 200, 400});

                    if (!isFinishing()) {
                       *//* AlertDialogFragment alertDialogFragment = AlertDialogFragment
                                .newInstance("Confirmation",
                                        getString(R.string.open_pdf_folder_confirmation_message));
                        alertDialogFragment.setListener(new AlertDialogFragment.AlertDialogListener() {
                            @Override
                            public void onYesClicked() {
                                alertDialogFragment.dismiss();
                                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                            }

                            @Override
                            public void onNoClicked() {
                                alertDialogFragment.dismiss();
                            }
                        });
                        try {
                            alertDialogFragment.show(getSupportFragmentManager(), ALERT_DIALOG);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*//*
                    } else {
                        showToast("Pdf downloaded completed");
                    }
                } else {
                    builder =
                            new NotificationCompat.Builder(BaseActivity.this, String.valueOf(referenceId))
                                    .setSmallIcon(android.R.drawable.stat_sys_download)
                                    .setContentTitle(fileName + " " *//*+ pdfType*//* + " Pdf")
                                    .setOngoing(true)
                                    .setChannelId(id)
                                    .setContentText(fileName + " Pdf Download completed")
                                    .setAutoCancel(true);

                    intent = new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS);
                    pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, intent, 0);
                    builder.setContentIntent(pendingIntent);
                    if (!isFinishing()) {
                      *//*  AlertDialogFragment alertDialogFragment = AlertDialogFragment
                                .newInstance(getString(R.string.confirm),
                                        getString(R.string.open_pdf_folder_confirmation_message));
                        alertDialogFragment.setListener(new AlertDialogFragment.AlertDialogListener() {
                            @Override
                            public void onYesClicked() {
                                alertDialogFragment.dismiss();
                                startActivity(new Intent(DownloadManager.ACTION_VIEW_DOWNLOADS));
                            }

                            @Override
                            public void onNoClicked() {
                                alertDialogFragment.dismiss();
                            }
                        });
                        try {
                            alertDialogFragment.show(getSupportFragmentManager(), ALERT_DIALOG);
                            alertDialogFragment.setCancelable(true);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }*//*
                    } else {
                        showToast("Pdf downloaded completed");
                    }
                }
                Notification notification = null;
                notification = builder.build();
                if (notificationManager != null) {
                    notificationManager.notify(NOTIFY_ID, notification);
                }
            }
        };
        return onComplete;
    }

    public String formatUrl(String url) {
        String formattedUrl = "";
        if (!url.contains("www.")) {
            String formatUrl[] = url.split("https://");
            formattedUrl = formatUrl[0] + "www." + formatUrl[1];
        }
        return formattedUrl;
    }

    public void unRegister() {
        if (onComplete != null)
            // unregister Receiver
            unregisterReceiver(onComplete);
    }

    public void slideAnimation(View view, int fromX, int fromY, int toX, int toY, int duration) {
        // isUp = true;
        if (view != null) {
            TranslateAnimation animate = new TranslateAnimation(
                    fromX,                 // fromXDelta
                    toX,                 // toXDelta
                    fromY,  // fromYDelta
                    toY);                // toYDelta
            animate.setDuration(duration);
            animate.setFillAfter(true);
            view.startAnimation(animate);

        }

    }*/
}