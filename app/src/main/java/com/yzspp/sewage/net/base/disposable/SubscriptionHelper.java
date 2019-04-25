package com.yzspp.sewage.net.base.disposable;

import io.reactivex.disposables.Disposable;

/**
 * Created by LeoCheung4ever on 2018/7/23.
 *
 * @See
 * @Description 订阅关系的处理
 */

public interface SubscriptionHelper<T> {

    /**
     * 添加订阅
     *
     * @param subscription 订阅对象
     */
    void add(Disposable subscription);

    /**
     * 解除订阅关系
     *
     * @param t 订阅对象
     */
    void cancel(Disposable t);

    /**
     * 解除所有相关的订阅关系
     */
    void cancelall();
}
