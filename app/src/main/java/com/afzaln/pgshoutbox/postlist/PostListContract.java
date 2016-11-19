package com.afzaln.pgshoutbox.postlist;

import com.afzaln.pgshoutbox.data.models.Post;

import java.util.List;

/**
 * Created by afzal on 2016-11-19.
 */

public interface PostListContract {
    interface View {
        void addButtonClicked();
        void showError(String errorMsg);
        void showPosts(List<Post> posts);
        void showProgressBar(boolean show);
    }
}
