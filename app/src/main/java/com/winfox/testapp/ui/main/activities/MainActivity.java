package com.winfox.testapp.ui.main.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.winfox.testapp.R;
import com.winfox.testapp.base.BaseActivity;
import com.winfox.testapp.base.KeyboardInterface;
import com.winfox.testapp.ui.authorization.activities.AuthorizationActivity;
import com.winfox.testapp.ui.customViews.CustomToast;
import com.winfox.testapp.ui.main.interfaces.RegistrationInterface;
import com.winfox.testapp.ui.main.presenters.MainPresenter;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class MainActivity extends BaseActivity implements RegistrationInterface, KeyboardInterface {

    @BindView(R.id.containerView)
    RelativeLayout containerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.nameInputLayout)
    TextInputLayout nameInputLayout;

    @BindView(R.id.emailInputLayout)
    TextInputLayout emailInputLayout;

    @BindView(R.id.firstPasswordInputLayout)
    TextInputLayout firstPasswordInputLayout;

    @BindView(R.id.secondPasswordInputLayout)
    TextInputLayout secondPasswordInputLayout;

    @BindView(R.id.registrationBtn)
    MaterialButton registrationBtn;

    private MainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = new MainPresenter(this, this);

    }

    @OnTextChanged(R.id.nameEdText)
    public void onNameChanged(CharSequence s, int start, int before, int count) {
        if(s != null) presenter.onNameChanged(s.toString());
    }

    @Override
    public void validName() {
        nameInputLayout.setError(null);
    }

    @Override
    public void invalidName() {
        nameInputLayout.setError(getString(R.string.registration_activity_name_not_valid));
    }

    @OnTextChanged(R.id.emailEdText)
    public void onEmailChanged(CharSequence s, int start, int before, int count) {
        if(s != null) presenter.onEmailChanged(s.toString());
    }

    @Override
    public void validEmail() {
        emailInputLayout.setError(null);
    }

    @Override
    public void invalidEmail() {
        emailInputLayout.setError(getString(R.string.registration_activity_email_not_valid));
    }

    @OnTextChanged(R.id.firstPasswordEdText)
    public void onFirstPasswordChanged(CharSequence s, int start, int before, int count) {
        if(s != null) presenter.onFirstPasswordChanged(s.toString());
    }

    @Override
    public void validPassword() {
        firstPasswordInputLayout.setError(null);
    }

    @Override
    public void invalidPassword() {
        firstPasswordInputLayout.setError(getString(R.string.registration_activity_first_password_not_valid));
    }

    @OnTextChanged(R.id.secondPasswordEdText)
    public void onSecondPasswordChanged(CharSequence s, int start, int before, int count) {
        if(s != null) presenter.onSecondPasswordChanged(s.toString());
    }

    @Override
    public void passwordsEquals() {
        secondPasswordInputLayout.setError(null);
    }

    @Override
    public void passwordsNotEquals() {
        secondPasswordInputLayout.setError(getString(R.string.registration_activity_second_password_not_valid));
    }

    @Override
    public void registrationBtnEnable() {
        registrationBtn.setEnabled(true);
    }

    @Override
    public void registrationBtnDisable() {
        registrationBtn.setEnabled(false);
    }

    @Override
    public void showProgress() {
        containerView.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideProgress() {
        containerView.setVisibility(View.VISIBLE);
        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showKeyboard() {

    }

    @Override
    public void hideKeyboard() {
        hideKeyboardForce();
    }

    @OnClick(R.id.registrationBtn)
    void onClickRegister() {
        presenter.registration();
    }

    @Override
    public void showMessage(String message) {
        CustomToast.makeText(this, message).show();
    }

    @Override
    public void showAuthorizationScreen(String email) {

        Intent intent = new Intent(this, AuthorizationActivity.class);
        intent.putExtra("email", email);
        startActivity(intent);
        finish();

    }

}