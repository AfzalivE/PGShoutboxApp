package com.afzaln.pgshoutbox.postlist;

import com.afzaln.pgshoutbox.data.models.ShoutboxData;

/**
 * Created by afzal on 2016-11-19.
 */

public interface PostListContract {
    interface View {
        void addButtonClicked();
        void showError(String errorMsg);
        void showMessages(ShoutboxData shoutboxData);
        void showProgressBar(boolean show);
    }
}
