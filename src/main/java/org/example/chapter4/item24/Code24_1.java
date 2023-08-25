package org.example.chapter4.item24;

/**
 静态内部类
    1.最简单的内部类；
        可以使用外部类的所有field、method，包括private

    2.常用的用途是：作为公共的辅助类
        必须和外部类一起使用：Out.StaticInner.test()

    3.私有静态成员类，用于表示或代表其外部类所表示的对象的各个组件
        public class Car {
            private static class Engine {}
        }

    4.HashMap类中的 Node类，它的方法不需要外部类的实例，因此定义为静态内部类

    5.API对外暴露的类，它的内部类选择 static、nonstatic 需要慎重，
        如果后续版本，需要将内部类从 nonstatic 改为 static，会影响向后兼容性；
        从 static 改为 nonstatic ，书上没说，应该也是影响的。
 */
public class Code24_1 {
    private int id;
    private String name;
    private static int card;

    private void privateMemberField(){
        System.out.println("this is a private member method be invoking! ");
    }

    // public的静态内部类
    public static class StaticPublicInner {

        public static void test(){
            // access to static field
            System.out.println(card);

            // access to member field, though their modifier is private
            Code24_1 outer = new Code24_1();
            // access member field and method of outer class
            System.out.println(outer.id);
            outer.privateMemberField();
        }
    }

    // 私有的静态内部类，外部无法访问
    private static class StaticPrivateInner {
        private static int num;

        public static void test(){
            // access to static field
            System.out.println(card);

            // access to member field, though their modifier is private
            Code24_1 item1 = new Code24_1();
            System.out.println(item1.id);
        }
    }

    public static void main(String[] args) {

    }
}
