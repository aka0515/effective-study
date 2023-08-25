package org.example.chapter4.item24;

public class Code24 {

    public static void main(String[] args) {
        // 静态内部类
        // 非private的静态内部类，无法在外部访问
        Code24_1.StaticPublicInner.test();
        // 静态内部类的实例，不需要外部类的实例
        Code24_1.StaticPublicInner staticPublicInner = new Code24_1.StaticPublicInner();

        // 外部无法访问 private 静态内部类的方法和属性
        // Code24_1.StaticPrivateInner.test(); 报错
        // Code24_1.StaticPrivateInner staticPrivateInner = new Code24_1.StaticPrivateInner(); 报错


        // 成员内部类
        Code24_2 item24 = new Code24_2();
        // it is impossible to create an instance of a nonstatic member class without an enclosing instance
        // 成员内部类实例的创建，离不开外部类的实例
        Code24_2.MemberInner memberInner = item24.new MemberInner();
        memberInner.innerM1();


    }
}
