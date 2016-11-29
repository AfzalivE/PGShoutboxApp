package com.afzaln.pgshoutbox.login;

import android.content.Context;

import com.afzaln.pgshoutbox.Injection;
import com.afzaln.pgshoutbox.util.PresenterFactory;

/**
 * Created by afzal on 2016-11-19.
 */
class LoginPresenterFactory extends PresenterFactory<LoginPresenter> {
    @Override
    public LoginPresenter create(Context context) {
        return new LoginPresenter(
                Injection.provideDataRepository(context)
        );
    }
}
