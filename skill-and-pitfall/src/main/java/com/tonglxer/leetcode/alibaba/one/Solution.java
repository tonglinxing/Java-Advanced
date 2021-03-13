package com.tonglxer.leetcode.alibaba.one;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Set;

/**
 * @Author Tong LinXing
 * @date 2021/3/13
 */
class Solution {
    public int numBusesToDestination(int[][] routes, int S, int T) {
        if(S == T) {
            return 0;
        }
        int length=routes.length;
        // 线路 0 到 length-1 与其他线路的相交信息
        List<List<Integer>> connect=new ArrayList<>();
        for(int i=0; i<length; i++){
            // 排序是为了更快的找到两条线路是否有交集
            Arrays.sort(routes[i]);
            connect.add(new ArrayList<>());
        }
        for(int i=0; i<length-1; i++){
            for(int j=i+1; j<length; j++){
                if(find(routes[i],routes[j])){
                    // 记录线路之间的相交信息
                    // 无向图！！！
                    connect.get(i).add(j);
                    connect.get(j).add(i);
                }
            }
        }

        // 已访问的路线
        Set<Integer> visited=new HashSet<>();
        // 包含终点的路线
        Set<Integer> target=new HashSet<>();
        // BFS队列
        Queue<Integer> queue=new LinkedList<>();

        // 记录包含起点、终点的路线；并从包含起点路线开始BFS
        for(int i=0; i<length; i++){
            if(Arrays.binarySearch(routes[i], S)>=0) {
                // 设为已乘坐并入队列
                visited.add(i);
                queue.offer(i);
            }
            if(Arrays.binarySearch(routes[i], T)>=0){
                target.add(i);
            }
        }
        // 记录换乘次数
        int sum = 0;
        while(!queue.isEmpty()){
            int n = queue.size();
            for (int i=0; i<n; i++) {
                // 当前的线路
                int cur = queue.poll();
                if (target.contains(cur)) {
                    // 换乘到该线路即可到达终点，故+1
                    return sum+1;
                }
                for (int c : connect.get(cur)) {
                    if (!visited.contains(c)) {
                        visited.add(c);
                        queue.offer(c);
                    }
                }
            }
            sum++;
        }
        return -1;
    }

    // 查找两个线路是否有交集
    public boolean find(int[] a, int[] b){
        int i=0, j=0;
        while(i<a.length &&j<b.length){
            if(a[i] == b[j]) {
                return true;
            } else if (a[i] < b[j]) {
                i++;
            } else {
                j++;
            }
        }
        return false;
    }
}
