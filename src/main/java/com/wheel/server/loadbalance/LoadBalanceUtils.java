package com.wheel.server.loadbalance;

import java.nio.channels.SelectionKey;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA
 * Date: 2018/1/1
 * Time: 下午1:14
 *
 * @author 陈樟杰
 */
public class LoadBalanceUtils {
    private static List<ConnectionUnit> connectionUnitList;

    public static List<ConnectionUnit> getConnectionUnitList() {
        return connectionUnitList;
    }

    public static void setConnectionUnitList(List<ConnectionUnit> connectionUnitList) {
        LoadBalanceUtils.connectionUnitList = connectionUnitList;
    }

    public static void setConnectionUnitList(SocketChannel[] socketChannels) {
        connectionUnitList = new ArrayList<>(socketChannels.length);
        for (int i = 0; i < socketChannels.length; i++) {
            connectionUnitList.add(new ConnectionUnit(socketChannels[i]));
        }
    }

    /**
     * 执行负载均衡策略
     *
     * @param loadBalanceStrategy
     * @return
     */
    public static ConnectionUnit executeLoadBalanceStrategy(LoadBalanceStrategy loadBalanceStrategy, SelectionKey key) {
        return loadBalanceStrategy.getBestConnection(connectionUnitList, key);
    }
}
