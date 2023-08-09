package org.example.chapter3;

import java.math.BigInteger;

/**
    关于重写toString方法的建议（ 如果父类没有重写toString的话 ）
    1.尽量重写toString，
    2.对象很大或者它不适合字符串表示，可以返回一个总结性的信息
    3.建议在 toString方法中，返回指定的格式，典型的是电话号码，数字
        但是注意，指定的格式可能不是适用于任何场景的
    4.工具类、枚举类不需要编写toString方法
    5.集合类的toString都来自抽象父类
        父类重写过的一般不再重写，常用的集合类都没有重写
 */
public class Code12_toString {
    public static void main(String[] args) {
        BigInteger integer = new BigInteger("103212312");
        System.out.println(integer.toString(16));
    }

    // 指定toString的返回格式
    public static void specifyFormat(){
        // BigInteger的toString方法，可以制定返回的进制
        BigInteger integer = new BigInteger("103212312");
        // 以16进制形式返回
        System.out.println(integer.toString(16)); // 626e518
    }
}
