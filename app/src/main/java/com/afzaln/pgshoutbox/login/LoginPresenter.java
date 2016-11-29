package com.afzaln.pgshoutbox.login;

import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.source.DataRepository;
import com.afzaln.pgshoutbox.login.LoginContract.View;
import com.afzaln.pgshoutbox.util.BasePresenter;

import rx.Observer;

import static com.afzaln.pgshoutbox.util.RxUtils.applySchedulers;

/**
 * Created by afzal on 2016-11-19.
 */
class LoginPresenter implements BasePresenter<View> {
    private final DataRepository dataRepository;
    private View view;
    /**
     * The observer that updates the view based on the view state, or shows an error
     */
    private Observer<Boolean> updateViewObserver = new Observer<Boolean>() {
        @Override
        public void onCompleted() {
            // onCompleted

        }

        @Override
        public void onError(Throwable throwable) {
            // onError
            showError(throwable);
        }

        @Override
        public void onNext(Boolean success) {
            // onNext
            if (success) {
                showShoutsActivity();
            } else {
                showError("Invalid password or username");
            }
            showProgressBar(false);
        }
    };

    LoginPresenter(@NonNull DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    void login(String username, String password) {
        dataRepository.login(username, password)
                .compose(applySchedulers())
                .subscribe(updateViewObserver);
        //        .subscribe(loggedIn -> {
        //            Timber.d("Logged in: " + loggedIn.toString());
        //        });
    }

    // internal methods

    private void showShoutsActivity() {
        if (view != null) {
            view.gotoShoutsActivity();
        }
    }

    private void showError(Throwable throwable) {
        if (view != null) {
            view.showError(throwable.getMessage());
        }
    }

    private void showError(String message) {
        if (view != null) {
            view.showError(message);
        }
    }

    private void showProgressBar(boolean show) {
        if (view != null) {
            view.showProgressBar(show);
        }
    }

    // Overridden base methods

    @Override
    public void onViewAttached(View view, boolean autoLoad) {
        this.view = view;
    }

    @Override
    public void onViewDetached() {
        view = null;
    }

    @Override
    public void onDestroyed() {
        // clean up listeners, unsubscribe from data here
    }
}
