package com.tonglxer.grammer.common;

import lombok.AllArgsConstructor;

/**
 * 泛型：
 * 类、成员方法、静态方法
 *
 * @Author Tong LinXing
 * @date 2020/12/8
 */
@AllArgsConstructor
public class Generic<T> {
    private T mark;

    /**
     * <note>这不是泛型方法</note>
     * 只是泛型类中的一个普通方法
     * 这里的T和类中的T保持一致
     *
     * */
    public void setMark(T t) {
        System.out.println("我不是泛型方法：" + t.toString());
    }


    /**
     * 这是一个泛型方法
     * <note>这里的T和类中的T可以不一样</note>
     *
     * */
    public <T> void showParameter(T t) {
        System.out.println("我是泛型方法：" + t.toString());
    }

    public static void main(String[] args) {
        Generic<Integer> instance = new Generic(520);
//        instance.setMark("tonglxer"); // 这里会报错，传入的参数要与类实例化时的Integer一致
        instance.setMark(521);
        instance.showParameter(new Student(24, "tonglxer"));
    }


}
