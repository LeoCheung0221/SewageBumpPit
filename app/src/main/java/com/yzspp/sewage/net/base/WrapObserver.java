package com.yzspp.sewage.net.base;

import android.text.TextUtils;
import android.util.Log;

import com.orhanobut.logger.Logger;
import com.vondear.rxtool.view.RxToast;
import com.yzspp.sewage.net.base.convert.ApiException;
import com.yzspp.sewage.net.base.disposable.SubscriptionManager;
import com.yzspp.sewage.utils.GsonConvertUtils;


import java.io.IOException;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import retrofit2.HttpException;
import timber.log.Timber;

/**
 * Created by LeoCheung4ever on 2018/7/23.
 *
 * @See
 * @Description 基类观察者封装
 */

public abstract class WrapObserver<T> implements Observer<T> {

    private Disposable mDisposable;
//    private LoadingDialog.Builder builder2 = new LoadingDialog.Builder(Core.currentActivity())
//            .setShowMessage(false)
//            .setCancelable(false);
//    private final LoadingDialog dialog2 = builder2.create();

    @Override
    public void onSubscribe(Disposable d) {
//        dialog2.show();
        //添加订阅关系
        OnDisposable(d);
        mDisposable = d;
        SubscriptionManager.getInstance().add(d);
    }

    @Override
    public void onNext(T t) {
        Logger.e("WrapObserver---> :  " + GsonConvertUtils.toJSONString(t));
        OnSuccess(t);
    }

    @Override
    public void onError(final Throwable e) {
        //自定义异常的传递
        Log.w("Subscriber onError", e);
        if (e instanceof HttpException) { // We had non-2XX http error
            RxToast.showToast("服务端异常啦");
        } else if (e instanceof IOException) { // A network or conversion error happened
            RxToast.showToast("服务器繁忙，请稍候再试。");
        } else if (e instanceof ApiException) {
            final ApiException exception = (ApiException) e;
        }
    }

    @Override
    public void onComplete() {
        OnCompleted();
//        dialog2.dismiss();
        SubscriptionManager.getInstance().cancel(mDisposable);
    }

    public abstract void OnSuccess(T t);

    public void OnCompleted() {
    }

    public void OnDisposable(Disposable d) {
    }

    public interface IDisposableDestroyListener {
        void destroy(Disposable d);
    }

    public IDisposableDestroyListener destroyListener;

    public IDisposableDestroyListener getDestroyListener() {
        return destroyListener;
    }

    public void setDestroyListener(IDisposableDestroyListener destroyListener) {
        this.destroyListener = destroyListener;
    }
}
