package com.tonglxer.middleware.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * @Author Tong LinXing
 * @date 2020/12/6
 */
public class ZooKeeperDemo {

    private static final int BASE_SLEEP_TIME = 1000;
    // 重试次数
    private static final int MAX_RETIES = 3;

    public static void main(String[] args) throws Exception {
       zookeeperTest();
    }

    private static void zookeeperTest() throws Exception {
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETIES);
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // 服务端ip和端口
                .connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        // 创建节点
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/node", "hello".getBytes());

        zkClient.close();
    }

}
