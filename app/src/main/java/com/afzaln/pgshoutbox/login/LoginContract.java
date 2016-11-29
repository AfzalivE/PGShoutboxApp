package com.afzaln.pgshoutbox.login;

/**
 * Created by afzal on 2016-11-19.
 */
public interface LoginContract {
    interface View {
        void showError(String errorMsg);

        void showProgressBar(boolean show);

        void gotoShoutsActivity();
    }
}
