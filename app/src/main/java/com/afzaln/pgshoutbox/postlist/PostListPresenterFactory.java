package com.afzaln.pgshoutbox.postlist;

import android.content.Context;

import com.afzaln.pgshoutbox.Injection;
import com.afzaln.pgshoutbox.util.PresenterFactory;

/**
 * Created by afzal on 2016-11-19.
 */
class PostListPresenterFactory extends PresenterFactory<PostListPresenter> {
    @Override
    public PostListPresenter create(Context context) {
        return new PostListPresenter(
                Injection.provideDataRepository(context)
        );
    }
}
