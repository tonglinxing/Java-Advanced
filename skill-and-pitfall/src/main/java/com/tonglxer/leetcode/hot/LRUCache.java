package com.tonglxer.leetcode.hot;


import java.util.LinkedHashMap;

/**
 * Least Recently Used
 *
 * @Author Tong LinXing
 * @date 2021/2/10
 */
public class LRUCache {
    // 缓存容量
    private int cap;

    // 双向链表 + map 按插入顺序排序
    private LinkedHashMap<String, Integer> cache = new LinkedHashMap<>();

    public LRUCache (int capacity) {
        this.cap = capacity;
    }

    /**
     * 通过key获取值
     * 并将该节点提至链表尾部
     *
     * @param key
     * @return
     */
    public int get(String key) {
        // 缓存未命中则直接返回-1
        if (!cache.containsKey(key)) {
            return -1;
        }
        makeNodeFresh(key);
        return cache.get(key);
    }

    /**
     * 若缓存已满，则删去最久未使用的元素
     *
     * @param key
     * @param val
     */
    public void put(String key, int val) {
        // key已存在则更新，并提至尾部
        if (cache.containsKey(key)) {
            cache.put(key, val);
            makeNodeFresh(key);
            return;
        }
        if (cache.size() >= this.cap) {
            // 获取链表第一个元素并移除
            String old = cache.keySet().iterator().next();
            cache.remove(old);
        }
        // 最后再插入元素
        cache.put(key, val);
    }

    /**
     * 将节点提到表尾
     *
     * @param key
     */
    private void makeNodeFresh(String key) {
        // 先删除，再重新插入尾部
        int val = cache.get(key);
        cache.remove(key);
        cache.put(key, val);
    }
}
