package com.tonglxer.leetcode.other;

/**
 * @Author Tong LinXing
 * @date 2021/2/3
 */
public class BaseAndBitDemo {
    public static void main(String[] args) {
        System.out.println(thirtyHexadecimalAdd("1a", "z"));
    }

    /**
     * a-z: 97 - 122
     * A-Z: 65 - 90
     *
     * @return 三十六进制下字母代表的数字
     */
    private static int getIntFromChar(char ch) {
        int res = -1;
        if (ch >='0' && ch <= '9') {
            res = ch - '0';
        } else if (ch >= 'a' && ch <= 'z') {
            res = (ch - 'a') + 10;
        } else if (ch >= 'A' && ch <= 'Z') {
            res = (ch - 'A') + 10;
        }
        return res;
    }

    /**
     * 计算三十六进制 加法
     * @param s1 参数1
     * @param s2 参数2
     * @return 结果字符串（三十六进制表示）
     */
    private static String thirtyHexadecimalAdd(String s1, String s2) {
        StringBuffer sb = new StringBuffer();
        // 字符池，可通过十进制数字获取三十六进制符号
        String symbolPool = "0123456789abcdefghijklmnopqrstuvwxyz";
        int l1 = s1.length();
        int l2 = s2.length();
        // 从末尾向前
        int i = l1 - 1;
        int j = l2 - 1;
        // 若参数包含空串则返回null
        if (i < 0 || j < 0) {
            return null;
        }
        int flag = 0;
        // 当两个参数任意一个加完后则跳出循环
        while (i >= 0 && j >= 0) {
            // 获取索引对应的字符
            char c1 = s1.charAt(i);
            char c2 = s2.charAt(j);
            // 将字符转换为十进制数字
            int v1 = getIntFromChar(c1);
            int v2 = getIntFromChar(c2);
            // 十进制相加
            int res = v1 + v2;
            // 判断是否需要进位
            if (res >= 36) {
                // 减去进位的36
                int sum = res - 36 + flag;
                // 得到对应的三十二进制字符
                char sv = symbolPool.charAt(sum);
                sb.append(sv);
                // 进位
                flag = 1;
            } else {
                int sum = res + flag;
                char sv = symbolPool.charAt(sum);
                sb.append(sv);
                // 进位归0
                flag = 0;
            }
            i--;
            j--;
        }
        // TODO: 2021/2/3 重复代码可以抽出一个方法来
        // 第一个数位数多于第二个数的位数
        while (i >= 0) {
            int sum = getIntFromChar(s1.charAt(i)) + flag;
            if (sum >=36) {
                flag = 1;
                sum-=36;
                char sv = symbolPool.charAt(sum);
                sb.append(sv);
            } else {
                flag=0;
                char sv = symbolPool.charAt(sum);
                sb.append(sv);
            }
            i--;
        }
        // 第一个数位数小于第二个数的位数
        while (j >= 0) {
            int sum = getIntFromChar(s2.charAt(j)) + flag;
            if (sum >=36) {
                flag = 1;
                sum-=36;
                char sv = symbolPool.charAt(sum);
                sb.append(sv);
            } else {
                flag=0;
                char sv = symbolPool.charAt(sum);
                sb.append(sv);
            }
            j--;
        }
        //temp!=0说明有进位，这是需要把进位的1拼接
        if(flag == 1){
            sb.append('1');
        }
        // 反转格式
        StringBuffer res = sb.reverse();
        return res.toString();
    }

}
