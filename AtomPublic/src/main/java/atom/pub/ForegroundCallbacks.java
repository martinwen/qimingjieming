package atom.pub;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.umeng.analytics.MobclickAgent;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;
import timber.log.Timber;

public class ForegroundCallbacks implements Application.ActivityLifecycleCallbacks {

    static final String TAG = ForegroundCallbacks.class.getName();

    interface Listener {

        void onBecameForeground();

        void onBecameBackground();
    }

    protected static ForegroundCallbacks instance = new ForegroundCallbacks();

    public static ForegroundCallbacks getInstance() {
        return instance;
    }

    protected boolean foreground = false;

    protected boolean paused = true;

    protected List<Listener> listeners = new CopyOnWriteArrayList<Listener>();

    protected Subscription foregroundSubscription;

    public synchronized void setForeground(boolean foreground) {
        this.foreground = foreground;
    }

    public boolean isForeground() {
        return foreground;
    }

    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    public void removeListener(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void onActivityResumed(Activity activity) {
        MobclickAgent.onResume(activity);

        paused = false;
        boolean wasBackground = ! foreground;
        setForeground(true);

        if (null != foregroundSubscription) {
            foregroundSubscription.unsubscribe();
        }

        if (wasBackground) {
            Timber.tag(TAG).e("went foreground::%s", activity);
            for (Listener l : listeners) {
                try {
                    l.onBecameForeground();
                } catch (Exception exc) {
                    Timber.tag(TAG).e("Listener threw exception!:" + exc.toString());
                }
            }
        } else {
            Timber.tag(TAG).e("still foreground");
        }
    }

    @Override
    public void onActivityPaused(Activity activity) {
        MobclickAgent.onPause(activity);

        paused = true;
        foregroundSubscription = Observable.interval(1, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        if (foreground && paused) {
                            setForeground(false);
                            Timber.tag(TAG).e("went background");
                            for (Listener l : listeners) {
                                try {
                                    l.onBecameBackground();
                                } catch (Exception exc) {
                                    Timber.tag(TAG).e("Listener threw exception!:" + exc.toString());
                                }
                            }
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Timber.tag(TAG).e("Listener threw exception!:" + throwable.toString());
                    }
                });
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

    }

    @Override
    public void onActivityStarted(Activity activity) {

    }

    @Override
    public void onActivityStopped(Activity activity) {

    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

    }

    @Override
    public void onActivityDestroyed(Activity activity) {

    }
}