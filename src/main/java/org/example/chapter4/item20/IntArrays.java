package org.example.chapter4.item20;

import java.util.AbstractList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

// Concrete implementation built atop skeletal implementation (Page 101)
public class IntArrays {


    // 该方法实现，将数组转换为 List
    static List<Integer> intArrayAsList(int[] a) {
        Objects.requireNonNull(a);

        // new AbstractList<>()是JDK9及以后的语法
        // 之前的版本需要加上Integer，否则会报错
        return new AbstractList<Integer>() {
            @Override public Integer get(int i) {
                return a[i];  // Autoboxing (Item 6)
            }

            @Override public Integer set(int i, Integer val) {
                int oldVal = a[i];
                a[i] = val;     // Auto-unboxing
                return oldVal;  // Autoboxing
            }

            @Override public int size() {
                return a.length;
            }
        };
    }

    public static void main(String[] args) {
        int[] a = new int[10];
        for (int i = 0; i < a.length; i++)
            a[i] = i;

        List<Integer> list = intArrayAsList(a);
        Collections.shuffle(list);
        System.out.println(list);
    }
}
