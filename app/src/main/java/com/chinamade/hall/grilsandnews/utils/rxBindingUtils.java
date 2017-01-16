package com.chinamade.hall.grilsandnews.utils;

import android.view.View;

import com.jakewharton.rxbinding.view.RxView;

import java.util.concurrent.TimeUnit;

import rx.functions.Action1;

/**
 * Created by ivan on 2016/7/6.
 */
@SuppressWarnings("ALL")
public class rxBindingUtils {

    //防手抖
    public static void clicks(View view, final RxBinding rxBinding) {
        RxView.clicks(view)
                .throttleFirst(600, TimeUnit.MILLISECONDS)
                .subscribe(new Action1<Void>() {
                    @Override
                    public void call(Void aVoid) {
                        rxBinding.clicks();
                    }
                });

    }

    public interface RxBinding {
        void clicks();
    }
}
