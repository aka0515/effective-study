package org.example.chapter4.item24;

/**
 匿名内部类
    1.匿名类没有名字，它也不是外部类的成员

    2.它是在使用的使用，被声明和实例化

    3.匿名类的另外一个用途：在静态工厂方法中
        静态工厂方法是用来创建对象的，它并不关心类 是否有名字；

 */
public class Code24_3 {

    private int id;

    private void outM1(){
        System.out.println("this is out member method");
    }

    public void test(){
        // 匿名类也是内部类，但它不是外部类的成员
        AnonyDemo anonyDemo = new AnonyDemo() {
            @Override
            public void f1() {
                Code24_3 outer = Code24_3.this;
                // 这样会产生递归调用
                // outer.test();
                outer.outM1();
            }
        };

        anonyDemo.f1();
    }

    public static void main(String[] args) {
        Code24_3 demo = new Code24_3();
        demo.test();
    }
}
