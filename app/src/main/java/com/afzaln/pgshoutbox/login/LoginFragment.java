package com.afzaln.pgshoutbox.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.view.View;

import com.afzaln.pgshoutbox.R;
import com.afzaln.pgshoutbox.messagelist.MessageListActivity;
import com.afzaln.pgshoutbox.util.BaseFragment;
import com.afzaln.pgshoutbox.util.PresenterFactory;
import com.afzaln.pgshoutbox.util.Utils.ValidationUtils;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by afzal on 2016-11-19.
 */
public class LoginFragment extends BaseFragment<LoginPresenter, LoginContract.View> implements LoginContract.View {

    @BindView(R.id.username_layout)
    TextInputLayout usernameLayout;
    @BindView(R.id.password_layout)
    TextInputLayout passwordLayout;
    @BindView(R.id.username)
    TextInputEditText usernameV;
    @BindView(R.id.password)
    TextInputEditText passwordV;

    private LoginPresenter presenter;

    // Initializer methods

    public static LoginFragment newInstance() {
        LoginFragment fragment = new LoginFragment();
        // This layout is inflated in BaseFragment
        fragment.setLayout(R.layout.login_fragment);

        return fragment;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Get any intent extras here ...
    }

    // View Contract methods

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Initialize adapters etc here ...
    }

    @Override
    public void gotoShoutsActivity() {
        startActivity(new Intent(getActivity(), MessageListActivity.class));
        getActivity().finish();
    }

    @Override
    public void showError(String errorMsg) {
        Snackbar.make(getView(), errorMsg, Snackbar.LENGTH_SHORT).show();
    }

    // Internal methods

    @OnClick(R.id.login)
    void onLoginClicked() {
        String username = usernameV.getText().toString();
        String password = passwordV.getText().toString();
        boolean valid = validateFields(username, password);
        if (valid) {
            presenter.login(username, password);
        }
    }

    private boolean validateFields(String username, String password) {
        boolean check1 = ValidationUtils.check(username == null || username.isEmpty(), usernameLayout, "Username cannot be empty");
        boolean check2 = ValidationUtils.check(password == null || password.isEmpty(), passwordLayout, "Password cannot be empty");

        return ValidationUtils.validate(check1, check2);
    }

    // Overridden base methods

    @Override
    public void showProgressBar(boolean show) {
        // show progress bar here ...
    }

    @Override
    protected PresenterFactory<LoginPresenter> getPresenterFactory() {
        return new LoginPresenterFactory();
    }

    // static creation method with layout

    @Override
    protected void onPresenterPrepared(LoginPresenter presenter) {
        this.presenter = presenter;
        // load initial data from presenter here ...
    }
}
