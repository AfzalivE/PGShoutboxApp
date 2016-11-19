package com.afzaln.pgshoutbox.postlist;

import android.support.annotation.NonNull;

import com.afzaln.pgshoutbox.data.models.Post;
import com.afzaln.pgshoutbox.data.source.DataRepository;
import com.afzaln.pgshoutbox.postlist.PostListContract.View;
import com.afzaln.pgshoutbox.util.BasePresenter;

import java.util.List;

import rx.Observer;

import static com.afzaln.pgshoutbox.util.RxUtils.applySchedulers;

/**
 * Created by afzal on 2016-11-19.
 */
class PostListPresenter implements BasePresenter<View> {
    private final DataRepository dataRepository;
    private View view;

    PostListPresenter(@NonNull DataRepository dataRepository) {
        this.dataRepository = dataRepository;
    }

    void loadPosts() {
        showProgressBar(true);
        dataRepository.getPosts()
                .compose(applySchedulers())
                .subscribe(updateViewObserver);
    }

    // internal methods

    private void showPosts(List<Post> posts) {
        if (view != null) {
            view.showPosts(posts);
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
    private Observer<List<Post>> updateViewObserver = new Observer<List<Post>>() {
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
        public void onNext(List<Post> posts) {
            // onNext
            showPosts(posts);
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
