package com.tjyw.qmjm.activity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.mobsandgeeks.saripaar.Validator;
import com.mobsandgeeks.saripaar.annotation.Length;
import com.mobsandgeeks.saripaar.annotation.Order;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.pub.inject.From;
import com.tjyw.atom.pub.interfaces.AtomPubValidationListener;
import com.tjyw.qmjm.R;

import java.util.concurrent.TimeUnit;

import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.Subscription;
import rx.functions.Action1;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

/**
 * Created by stephen on 24/08/2017.
 */
@RequiresPresenter(UserPresenter.class)
public class UserSignInActivity extends BaseToolbarActivity<UserPresenter<UserSignInActivity>> {

    @Order(1)
    @Length(min = 11, messageResId = R.string.atom_pub_resStringUserSignMobileIllegal)
    @From(R.id.userSignInMobile)
    protected EditText userSignInMobile;

    @Order(2)
    @Length(min = 6, messageResId = R.string.atom_pub_resStringUserSignAuthCodeIllegal)
    @From(R.id.userSignInAuthCode)
    protected EditText userSignInAuthCode;

    @From(R.id.userSignInAuthCodeGet)
    protected TextView userSignInAuthCodeGet;

    @From(R.id.atom_pub_resIdsOK)
    protected TextView atom_pub_resIdsOK;

    protected Subscription countDownSubscribe;

    protected Validator signInValidator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_user_sign_in);
        tSetToolBar(getString(R.string.atom_pub_resStringUserSignIn));

        immersionBarWith()
                .fitsSystemWindows(true)
                .statusBarColor(R.color.colorPrimary)
                .statusBarDarkFont(true)
                .init();

        userSignInAuthCodeGet.setOnClickListener(this);
        atom_pub_resIdsOK.setOnClickListener(this);

        signInValidator = new Validator(this);
        signInValidator.setValidationListener(new AtomPubValidationListener(getApplicationContext()) {
            @Override
            public void onValidationSucceeded() {
                maskerShowProgressView(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (null != countDownSubscribe && ! countDownSubscribe.isUnsubscribed()) {
            countDownSubscribe.unsubscribe();
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.userSignInAuthCodeGet:
                if (userSignInMobile.length() < 11) {
                    showToast(R.string.atom_pub_resStringUserSignMobileIllegal);
                } else {
                    sendCodeCountDown();
                }
                break ;
            case R.id.atom_pub_resIdsOK:
                signInValidator.validate();
                break ;
            default:
                super.onClick(v);
        }
    }

    protected void sendCodeCountDown() {
        if (null != countDownSubscribe && ! countDownSubscribe.isUnsubscribed()) {
            countDownSubscribe.unsubscribe();
        }

        countDownSubscribe = Observable.interval(1, 1, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        int countDown = (int) (59 - aLong);
                        if (countDown > 0) {
                            userSignInAuthCodeGet.setText(getString(R.string.atom_pub_resStringUserSignAuthCodeCounting, countDown));
                        } else {
                            userSignInAuthCodeGet.setText(R.string.atom_pub_resStringUserSignAuthCodeReSend);
                            userSignInAuthCodeGet.setEnabled(true);
                            countDownSubscribe.unsubscribe();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        userSignInAuthCodeGet.setText(R.string.atom_pub_resStringUserSignAuthCodeReSend);
                        userSignInAuthCodeGet.setEnabled(true);
                    }
                });
    }
}
