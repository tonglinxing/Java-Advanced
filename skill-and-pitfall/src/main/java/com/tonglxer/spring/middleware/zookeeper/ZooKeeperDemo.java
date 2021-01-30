package com.tonglxer.spring.middleware.zookeeper;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;

/**
 * 1. 持久（PERSISTENT）节点：
 *    一旦创建就一直存在即使 ZooKeeper 集群宕机，直到将其删除。
 *
 * 2. 临时（EPHEMERAL）节点：
 *    临时节点的生命周期是与 客户端会话（session）绑定的，会话消失则节点消失。
 *    并且临时节点只能做叶子节点，不能创建子节点。
 *
 * 3. 持久顺序（PERSISTENT_SEQUENTIAL）节点：
 *    除了具有持久（PERSISTENT）节点的特性之外， 子节点的名称还具有顺序性。
 *    比如 /node1/app0000000001 、/node1/app0000000002。
 *
 * 4. 临时顺序（EPHEMERAL_SEQUENTIAL）节点：
 *    除了具备临时（EPHEMERAL）节点的特性之外，子节点的名称还具有顺序性
 *
 *
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
        // 重试策略：初试时间为1s，重试3次
        RetryPolicy retryPolicy = new ExponentialBackoffRetry(BASE_SLEEP_TIME, MAX_RETIES);
        // 建造者模式
        CuratorFramework zkClient = CuratorFrameworkFactory.builder()
                // 服务端ip和端口
                .connectString("127.0.0.1:2181")
                .retryPolicy(retryPolicy)
                .build();
        zkClient.start();
        // 创建永久节点
        zkClient.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT)
                .forPath("/node", "tonglxer".getBytes());
        // 更新节点数据内容
        zkClient.setData().forPath("/node", "hello".getBytes());
        // 检测节点是否存在
        zkClient.checkExists().forPath("/node");
        // 获取节点内容，获取到的是byte[]
        String data = new String(zkClient.getData().forPath("/node"));
        System.out.println("The zookeeper node /node is : " + data);
        // 删除指定节点
        zkClient.delete().forPath("/node");
        // 删除一个节点以及其子节点
//        zkClient.delete().deletingChildrenIfNeeded().forPath("/node");
        zkClient.close();
    }

}
