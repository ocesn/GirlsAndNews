package com.chinamade.hall.grilsandnews.Base;


import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import java.lang.reflect.Method;

import butterknife.ButterKnife;
import de.greenrobot.event.EventBus;

/**
 * Created by ivan on 2016/7/6.
 */
@SuppressWarnings("ALL")
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    protected static final String FRAGMENT_INDEX = "fragment_index";

    protected Context mContext;
    protected View rootView;
    protected String fragmentName;

    protected P mPresenter;

    protected int index = 0;
    protected boolean isCreated;
    protected boolean isVisible;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        if (bundle != null) {
            index = bundle.getInt(FRAGMENT_INDEX);
        }
        mContext = getContext();
        fragmentName = this.getClass().getName();

        if (containEventBusMethod()) {
            EventBus.getDefault().register(this);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (rootView == null) {
            rootView = inflater.inflate(setLayoutResource(), null, false);
            ButterKnife.bind(this, rootView);
        } else {
            ButterKnife.bind(this, rootView);
        }

        isCreated = true;

        mPresenter = initPresenter();
        if (mPresenter != null){
            mPresenter.onAttach(this);
            mPresenter.onCreate();
        }

        init(savedInstanceState);
        init();

        return rootView;
    }

    protected abstract int setLayoutResource();

    protected abstract P initPresenter();

    protected abstract void init(Bundle savedInstanceState);

    protected abstract void init();

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (getUserVisibleHint()) {
            isVisible = true;
            onVisible();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    protected void onVisible() {
        init();
    }

    protected void onInvisible() {
    }

    @Override
    public void onResume() {
        super.onResume();
        if (rootView != null) {
            setupUI(rootView);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        rootView = null;
        isCreated = false;
        ButterKnife.unbind(this);
        if (containEventBusMethod()) {
            EventBus.getDefault().unregister(this);
        }
        if (mPresenter != null){
            mPresenter.onDestroy();
        }
    }

    private boolean containEventBusMethod() {
        Method[] methods = this.getClass().getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("onEvent"))
                return true;
        }
        return false;
    }

    public void setupUI(View view) {
        if (!(view instanceof EditText)) {
            view.setOnTouchListener(new View.OnTouchListener() {
                public boolean onTouch(View v, MotionEvent event) {
                    hideSoftKeyboard(BaseFragment.this.getActivity());
                    return false;
                }
            });
        }

        if (view instanceof ViewGroup) {
            for (int i = 0; i < ((ViewGroup) view).getChildCount(); i++) {
                View innerView = ((ViewGroup) view).getChildAt(i);
                setupUI(innerView);
            }
        }
    }

    public boolean dispatchKey() {
        return false;
    }

    public static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);

        View view = activity.getCurrentFocus();
        if (view == null) {
            return;
        }
        IBinder token = view.getWindowToken();
        if (token == null) {
            return;
        }

        inputMethodManager.hideSoftInputFromWindow(token, 0);
    }

}
