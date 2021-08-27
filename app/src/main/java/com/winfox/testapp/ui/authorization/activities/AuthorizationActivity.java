package com.winfox.testapp.ui.authorization.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.google.android.material.button.MaterialButton;
import com.winfox.testapp.R;
import com.winfox.testapp.base.BaseActivity;
import com.winfox.testapp.base.KeyboardInterface;
import com.winfox.testapp.ui.authorization.interfaces.AuthorizationInterface;
import com.winfox.testapp.ui.authorization.presenters.AuthorizationPresenter;
import com.winfox.testapp.ui.customViews.CustomToast;
import com.winfox.testapp.ui.profile.activities.ProfileActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnTextChanged;

public class AuthorizationActivity extends BaseActivity implements AuthorizationInterface, KeyboardInterface {

    @BindView(R.id.containerView)
    RelativeLayout containerView;

    @BindView(R.id.progressBar)
    ProgressBar progressBar;

    @BindView(R.id.authorizationBtn)
    MaterialButton authorizationBtn;

    private AuthorizationPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_authorization);
        ButterKnife.bind(this);

        presenter = new AuthorizationPresenter(this, this);

    }

    @OnTextChanged(R.id.emailEdText)
    public void onEmailChanged(CharSequence s, int start, int before, int count) {
        if(s != null) presenter.onEmailChanged(s.toString());
    }

    @OnTextChanged(R.id.passwordEdText)
    public void onPasswordChanged(CharSequence s, int start, int before, int count) {
        if(s != null) presenter.onPasswordChanged(s.toString());
    }

    @Override
    public void authorizationBtnEnable() {
        authorizationBtn.setEnabled(true);
    }

    @Override
    public void authorizationBtnDisable() {
        authorizationBtn.setEnabled(false);
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

    @OnClick(R.id.authorizationBtn)
    void onClickAuth() {
        presenter.authorization();
    }

    @Override
    public void showMessage(String message) {
        CustomToast.makeText(this, message).show();
    }

    @Override
    public void showProfileScreen() {

        Intent intent = new Intent(this, ProfileActivity.class);
        startActivity(intent);
        finish();

    }

}
