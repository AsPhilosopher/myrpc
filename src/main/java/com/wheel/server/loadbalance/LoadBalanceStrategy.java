package com.wheel.server.loadbalance;

import java.nio.channels.SelectionKey;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午1:14
 *
 * @author 陈樟杰
 */
public interface LoadBalanceStrategy {
    /**
     * 获取连接
     *
     * @param connectionUnitList
     * @param key
     * @return
     */
    ConnectionUnit getBestConnection(List<ConnectionUnit> connectionUnitList, SelectionKey key);
}
