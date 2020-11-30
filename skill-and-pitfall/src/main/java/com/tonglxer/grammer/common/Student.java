package com.tonglxer.grammer.common;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 *
 * @Author Tong LinXing
 * @date 2020/11/26
 */
@AllArgsConstructor
@NoArgsConstructor
public class Student {
    private int age;

    private String name;

    private void setAge(int age) {
        this.age = age;
    }

    private void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    private void show() {
        System.out.println("Name: " + name + " Age: " + age);
    }
}
