package com.surulinathan.commonbaseutilslibrary.base;

import nucleus5.presenter.Presenter;
import nucleus5.view.NucleusSupportFragment;

public class BaseFragment<P extends Presenter> extends NucleusSupportFragment<P> {
    private static final String TAG = "BaseFragment";

 /*   @Nullable
    @BindView(R.id.llProgress)
    LinearLayout llProgress;

    private Unbinder viewUnBinder;

    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewUnBinder = ButterKnife.bind(this, view);
        hideKeyBoard(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (viewUnBinder != null) {
            viewUnBinder.unbind();
        }
    }


    public void showToast(String message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    public void showToast(int message) {
        Toast.makeText(requireActivity(), message, Toast.LENGTH_SHORT).show();
    }

    protected void hideKeyBoard(View view) {
        final InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public void changeActivity(Class clz) {
        Intent i = new Intent(getActivity(), clz);
        startActivity(i);
    }

    protected void changeActivityClearBackStack(Class cls) {
        startActivity(new Intent(getActivity(), cls)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_CLEAR_TASK |
                        Intent.FLAG_ACTIVITY_NEW_TASK)
        );
    }

    public void popFragment() {
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    public void destroyFragmentAndActivity() {
        popFragment();
        requireActivity().finish();
    }

    public FragmentManager getSupportFragmentManager() {
        return getActivity().getSupportFragmentManager();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        List<Fragment> fragments = getChildFragmentManager().getFragments();
        if (fragments != null && !fragments.isEmpty()) {
            for (Fragment fragment : getChildFragmentManager().getFragments()) {
                if (fragment != null) fragment.onActivityResult(requestCode, resultCode, data);
            }
        }
    }

    protected void showProgress() {
        if (llProgress != null) {
            llProgress.setVisibility(View.VISIBLE);
        }
    }

    protected void hideProgress() {
        if (llProgress != null) {
            llProgress.setVisibility(View.GONE);
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

    public void setImageSrcDirectPath(String imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load(imagePath)
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .into(source);
    }

    public void setImage(String imagePath, int error_bg_view, ImageView source) {
        GlideApp.with(this)
                .load( imagePath)
                // .apply(bitmapTransform(new BlurTransformation(1, 4)))
                .error(error_bg_view)
                .into(source);
    }

    protected void hideKeyBoard() {
        Activity activity = getActivity();
        if (isAdded() && activity != null) {
            if (activity.getCurrentFocus() != null) {
                final InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (imm != null) {
                    imm.hideSoftInputFromWindow(activity.getCurrentFocus().getWindowToken(), 0);
                }
            }
        }
    }*/
}
