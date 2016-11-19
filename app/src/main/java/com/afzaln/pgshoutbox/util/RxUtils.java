package com.afzaln.pgshoutbox.util;

import rx.Observable.Transformer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RxUtils {
    /**
     * Apply schedulers to make sure things run in the thread they should
     *
     * @param <T> Type from observable
     *
     * @return Same observable with specified schedulers
     */
    public static <T> Transformer<T, T> applySchedulers() {
        return observable -> observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    /**
     * Observe an observable on IO thread
     *
     * @param <T> Type from observable
     *
     * @return Same observable with specified schedulers
     */
    public static <T> Transformer<T, T> observeInBackground() {
        return observable -> observable
                .observeOn(Schedulers.io());
    }
}
