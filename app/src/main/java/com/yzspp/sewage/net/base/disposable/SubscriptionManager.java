package com.yzspp.sewage.net.base.disposable;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by LeoCheung4ever on 2018/7/23.
 *
 * @See
 * @Description 订阅处理工具类
 */

public class SubscriptionManager implements SubscriptionHelper<Object> {

    public static SubscriptionManager subscriptionManager;
    private CompositeDisposable mDisposables;

    public SubscriptionManager() {
        if (mDisposables == null) {
            mDisposables = new CompositeDisposable();
        }
    }

    @Override
    public void add(Disposable disposable) {
        if (disposable == null) return;
        mDisposables.add(disposable);
    }

    @Override
    public void cancel(Disposable disposable) {
        if (mDisposables != null) {
            mDisposables.delete(disposable);
        }
    }

    @Override
    public void cancelall() {
        if (mDisposables != null) {
            mDisposables.clear();
        }
    }

    public static SubscriptionManager getInstance() {
        if (subscriptionManager == null) {
            synchronized (SubscriptionManager.class) {
                if (subscriptionManager == null) {
                    subscriptionManager = new SubscriptionManager();
                }
            }
        }
        return subscriptionManager;
    }

}
