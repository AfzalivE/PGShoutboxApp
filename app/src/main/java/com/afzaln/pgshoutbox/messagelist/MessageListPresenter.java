package com.afzaln.pgshoutbox.messagelist;

import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;
import com.afzaln.pgshoutbox.data.source.DataRepository;
import com.afzaln.pgshoutbox.messagelist.MessageListContract.View;
import com.afzaln.pgshoutbox.util.BasePresenter;

import rx.Observer;

import static com.afzaln.pgshoutbox.util.RxUtils.applySchedulers;

/**
 * Created by afzal on 2016-11-19.
 */
class MessageListPresenter implements BasePresenter<View> {
    private final DataRepository dataRepository;
    private View view;

    MessageListPresenter(@NonNull DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    void loadMessages() {
        showProgressBar(true);
        dataRepository.getMessages()
                .compose(applySchedulers())
                .subscribe(updateViewObserver);
    }

    void postMessage(String message) {
        dataRepository.postMessage(message)
                .subscribe(updateViewObserver);
    }

    // internal methods

    private void showMessages(ShoutboxData shoutboxData) {
        if (view != null) {
            view.showMessages(shoutboxData);
        }
    }

    private void showError(Throwable throwable) {
        if (view != null) {
            view.showError(throwable.getMessage());
        }
    }


    private void showProgressBar(boolean show) {
        if (view != null) {
            view.showProgressBar(show);
        }
    }

    /**
     * The observer that updates the view based on the view state, or shows an error
     */
    private Observer<ShoutboxData> updateViewObserver = new Observer<ShoutboxData>() {
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
        public void onNext(ShoutboxData shoutboxData) {
            // onNext
            showMessages(shoutboxData);
            showProgressBar(false);
        }
    };


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
