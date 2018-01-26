package com.tjyw.bbqmqd.activity;

import android.Manifest;
import android.animation.ObjectAnimator;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.brianjmelton.stanley.ProxyGenerator;
import com.facebook.drawee.view.SimpleDraweeView;
import com.tbruyelle.rxpermissions.RxPermissions;
import com.tjyw.atom.network.RxSchedulersHelper;
import com.tjyw.atom.network.interfaces.IPrefClient;
import com.tjyw.atom.network.interfaces.IPrefUser;
import com.tjyw.atom.network.model.ClientInit;
import com.tjyw.atom.network.model.UserInfo;
import com.tjyw.atom.network.presenter.UserPresenter;
import com.tjyw.atom.network.presenter.listener.OnApiClientPostListener;
import com.tjyw.atom.network.presenter.listener.OnApiPostErrorListener;
import com.tjyw.atom.network.presenter.listener.OnApiUserPostListener;
import com.tjyw.atom.network.result.RUserRegister;
import com.tjyw.atom.network.utils.DeviceUtil;
import com.tjyw.atom.network.utils.JsonUtil;
import com.tjyw.bbqmqd.R;
import com.tjyw.bbqmqd.adapter.ClientGuideAdapter;
import com.tjyw.bbqmqd.adapter.ClientMasterAdapter;
import com.tjyw.bbqmqd.factory.IClientActivityLaunchFactory;
import com.umeng.analytics.MobclickAgent;

import java.util.concurrent.TimeUnit;

import atom.pub.fresco.ImageFacade;
import atom.pub.inject.From;
import nucleus.factory.RequiresPresenter;
import rx.Observable;
import rx.functions.Action1;

/**
 * Created by stephen on 17-8-1.
 */
@RequiresPresenter(UserPresenter.class)
public class ClientWelcomeActivity extends BaseActivity<UserPresenter<ClientWelcomeActivity>> implements
        OnApiPostErrorListener,
        OnApiClientPostListener.PostClientInitListener,
        OnApiUserPostListener.PostUserRegisterListener,
        ClientGuideAdapter.OnClientGuideClickListener {

    static final int DELAY_TO_LAUNCH_MASTER = 2000;

    protected long createMillisTime;

    @From(R.id.welcomeGuideRoot)
    protected ViewGroup welcomeGuideRoot;

    @From(R.id.welcomeGuideContainer)
    protected ViewPager welcomeGuideContainer;

    @From(R.id.welcomeGuideLaunch)
    protected TextView welcomeGuideLaunch;

    @From(R.id.welcomeGuideIndicateLeft)
    protected ImageView welcomeGuideIndicateLeft;

    @From(R.id.welcomeGuideIndicateCenter)
    protected ImageView welcomeGuideIndicateCenter;

    @From(R.id.welcomeGuideIndicateRight)
    protected ImageView welcomeGuideIndicateRight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.atom_master_welcome);
        immersionBarWith()
                .statusBarDarkFont(STATUSBAR_DARK_FONT)
                .init();

        createMillisTime = System.currentTimeMillis();
        IPrefUser user = new ProxyGenerator().create(getApplicationContext(), IPrefUser.class);
        if (null != user && ! TextUtils.isEmpty(user.getUserSession())) { // 判断本地是否有用户信息
            getPresenter().postClientInit();
        } else { // 没有用户信息时，先请求register接口创建用户，再访问init接口初始化
            getPresenter().postNewUserRegister();
        }
    }

    @Override
    public void onBackPressed() {
        IClientActivityLaunchFactory.launchClientMasterActivity(ClientWelcomeActivity.this, ClientMasterAdapter.POSITION.NAMING, false);
        super.onBackPressed();
    }

    @Override
    public void postOnExplainError(int postId, Throwable throwable) {
        throwable.printStackTrace();
        IPrefUser user = new ProxyGenerator().create(getApplicationContext(), IPrefUser.class);
        if (null != user && ! TextUtils.isEmpty(user.getUserSession())) {
            requestPhoneStatePermission();
        } else { // 否则说明注册接口出现异常，弹窗提示并退出应用
            new AlertDialog.Builder(ClientWelcomeActivity.this)
                    .setTitle(R.string.atom_pub_resStringTip)
                    .setMessage(R.string.atom_pub_resStringNetworkBroken)
                    .setPositiveButton(R.string.atom_pub_resStringOK, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            MobclickAgent.onKillProcess(getApplicationContext());
                            finish();
                        }
                    }).show();
        }
    }

    @Override
    public void postOnClientInitSuccess(ClientInit clientInit) {
        clientInit.saveInstance(getApplicationContext());
        requestPhoneStatePermission();
    }

    @Override
    public void postOnUserRegisterSuccess(RUserRegister result) {
        if (null != result.user) {
            saveUserInfo(result.user);
        }

        if (null != result.init) {
            result.init.saveInstance(getApplicationContext());
        }

        Observable.timer(1, TimeUnit.SECONDS)
                .compose(RxSchedulersHelper.<Long>io_main())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        requestPhoneStatePermission();
                    }
                });
    }

    @Override
    public void guideOnLaunchClick(View view) {
        if (welcomeGuideContainer.getCurrentItem() == 2) {
            IClientActivityLaunchFactory.launchClientMasterActivity(this, ClientMasterAdapter.POSITION.NAMING, false);
            finish();
        }
    }

    protected void saveUserInfo(UserInfo userInfo) {
        if (null != userInfo) {
            IPrefUser user = new ProxyGenerator().create(getApplicationContext(), IPrefUser.class);
            if (null != user) {
                user.setUserSession(userInfo.sessionKey);
                user.setUserInfo(JsonUtil.getInstance().toJsonString(userInfo));
            }
        }
    }

    protected void requestPhoneStatePermission() {
        RxPermissions permissions = null;
        if (isFinishing()) {
            return ;
        } else {
            try {
                permissions = new RxPermissions(this);
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
        }

        if (null == permissions) {
            return ;
        }

        if (permissions.isGranted(Manifest.permission.READ_PHONE_STATE)) {
            launchMasterActivity();
        } else {
            permissions
                    .request(Manifest.permission.READ_PHONE_STATE)
                    .compose(RxSchedulersHelper.<Boolean>io_main())
                    .subscribe(new Action1<Boolean>() {
                        @Override
                        public void call(Boolean granted) {
                            launchMasterActivity();
                            if (! granted) {
                                showToast(R.string.atom_pub_resStringPermissionByUserDeny);
                            }
                        }
                    }, new Action1<Throwable>() {
                        @Override
                        public void call(Throwable throwable) {
                            throwable.printStackTrace();
                            launchMasterActivity();
                            showToast(R.string.atom_pub_resStringPermissionByUserDeny);
                        }
                    });
        }
    }

    protected boolean needShowAppGuideFrame() {
        IPrefClient iPrefClient = new ProxyGenerator().create(getApplicationContext(), IPrefClient.class);
        if (null != iPrefClient) {
            int version = DeviceUtil.getClientVersionCode(getApplicationContext());
            if (version > iPrefClient.getShowAppGuideVersion()) {
                iPrefClient.setShowAppGuideVersion(version);

                welcomeGuideContainer.setOffscreenPageLimit(3);
                welcomeGuideContainer.setAdapter(new ClientGuideAdapter(this));
                welcomeGuideContainer.addOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {

                    @Override
                    public void onPageSelected(int position) {
                        welcomeGuideIndicateLeft.setImageResource(position == 0 ? R.drawable.atom_ic_guide_indicate_select : R.drawable.atom_ic_guide_indicate_default);
                        welcomeGuideIndicateCenter.setImageResource(position == 1 ? R.drawable.atom_ic_guide_indicate_select : R.drawable.atom_ic_guide_indicate_default);
                        welcomeGuideIndicateRight.setImageResource(position == 2 ? R.drawable.atom_ic_guide_indicate_select : R.drawable.atom_ic_guide_indicate_default);

                        if (position == 2) {
                            welcomeGuideLaunch.setVisibility(View.VISIBLE);
                            ObjectAnimator animator = ObjectAnimator.ofFloat(welcomeGuideLaunch, "alpha", 0, 1, 1).setDuration(500);
                            animator.start();
                        } else {
                            welcomeGuideLaunch.setVisibility(View.INVISIBLE);
                        }
                    }
                });

                welcomeGuideRoot.setVisibility(View.VISIBLE);
                ObjectAnimator animator = ObjectAnimator.ofFloat(welcomeGuideRoot, "alpha", 0, 1, 1);
                animator.start();

                return true;
            }
        }

        return false;
    }

    protected void launchMasterActivity() {
        createMillisTime = Math.abs(createMillisTime - System.currentTimeMillis());
        if (createMillisTime < DELAY_TO_LAUNCH_MASTER) {
            Observable.timer((DELAY_TO_LAUNCH_MASTER - createMillisTime), TimeUnit.MILLISECONDS)
                    .compose(RxSchedulersHelper.<Long>io_main())
                    .subscribe(new Action1<Long>() {
                        @Override
                        public void call(Long aLong) {
                            if (! needShowAppGuideFrame()) {
                                IClientActivityLaunchFactory.launchClientMasterActivity(ClientWelcomeActivity.this, ClientMasterAdapter.POSITION.NAMING, false);
                                finish();
                            }
                        }
                    });
        } else if (! needShowAppGuideFrame()) {
            IClientActivityLaunchFactory.launchClientMasterActivity(this, ClientMasterAdapter.POSITION.NAMING, false);
            finish();
        }
    }
}
