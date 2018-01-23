package com.wheel.utils;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA
 * Date: 2017/11/7
 * Time: 下午5:14
 *
 * @author 陈樟杰
 */
public class ThreadPoolFactory {
    private static final Double MULTIPLE = 1.5;

    /**
     * 获取线程池
     *
     * @param poolSize
     * @param maxPoolSize
     * @param multiple
     * @return
     */
    public static ExecutorService getThreadPool(Integer poolSize, Integer maxPoolSize, Double multiple) {
        return new ThreadPoolExecutor(poolSize, maxPoolSize,
                1L, TimeUnit.MINUTES,
                new LinkedBlockingQueue<Runnable>((int) (poolSize * multiple)));
    }

    /**
     * 获取一个固定数量的线程池
     *
     * @param poolSize
     * @return
     */
    public static ExecutorService getFixedThreadPool(Integer poolSize) {
        return getThreadPool(poolSize, poolSize, MULTIPLE);
    }
}
