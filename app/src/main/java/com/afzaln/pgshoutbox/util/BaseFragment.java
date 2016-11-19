package com.afzaln.pgshoutbox.util;

import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * Base class to load a view and create the presenter
 */
public abstract class BaseFragment<P extends BasePresenter<V>, V> extends Fragment {

    private static final int LOADER_ID = 101;
    private String TAG = getClass().getName();

    // boolean flag to avoid delivering the result twice. Calling initLoader in onActivityCreated makes
    // onLoadFinished will be called twice during configuration change.
    private boolean delivered = false;
    private BasePresenter<V> presenter;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        int layout = getArguments().getInt("layout");
        View view = inflater.inflate(layout, container, false);

        ButterKnife.bind(this, view);

        if (ViewConfiguration.get(getActivity()).hasPermanentMenuKey()) {
            setHasOptionsMenu(true);
        }

        return view;
    }

    protected void setLayout(@LayoutRes int layout) {
        Bundle args = new Bundle();
        args.putInt("layout", layout);
        setArguments(args);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        getLoaderManager().initLoader(LOADER_ID, null, new LoaderManager.LoaderCallbacks<P>() {
            @Override
            public Loader<P> onCreateLoader(int id, Bundle args) {
                return new PresenterLoader<>(getContext(), getPresenterFactory(), TAG);
            }

            @Override
            public void onLoadFinished(Loader<P> loader, P presenter) {
                if (!delivered) {
                    BaseFragment.this.presenter = presenter;
                    delivered = true;
                    onPresenterPrepared(presenter);
                }
            }

            @Override
            public void onLoaderReset(Loader<P> loader) {
                BaseFragment.this.presenter = null;
                onPresenterDestroyed();
            }
        });
    }

    @Override
    public void onResume() {
        onResume(true);
    }

    protected void onResume(boolean autoLoad) {
        super.onResume();
        presenter.onViewAttached(getPresenterView(), autoLoad);
    }

    @Override
    public void onPause() {
        presenter.onViewDetached();
        super.onPause();
    }

    protected abstract PresenterFactory<P> getPresenterFactory();

    protected void onPresenterDestroyed() {
        // hook for subclasses
    }

    protected abstract void onPresenterPrepared(P presenter);

    // Override in case from fragment not implementing Presenter<View> interface
    private V getPresenterView() {
        return (V) this;
    }
}
