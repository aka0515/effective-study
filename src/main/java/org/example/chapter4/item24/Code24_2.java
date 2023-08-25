package org.example.chapter4.item24;

/**
 成员内部类
 1.每个成员内部类中，都有和外部类实例的隐含关联
    可以通过：Outer.this 来获取外部类的实例

 2.成员内部类可以直接访问外部类实例的，成员方法，成员属性，
    虽然它有外部类实例的隐含关联，但是实际使用外部类实例属性和方法时，不需要这么用

 3.成员内部类实例的创建，必须依赖于外部类的实例
    如果内部类的实例，可以独立于外部类的实例，那么它一定是静态的内部类，

 4.创建成员内部类实例的两种途径：
    在外部类的成员方法中，调用成员内部类的构造方法；
    创建外部类实例，然后使用 outer.new MemberInner();

 5.成员内部类的一个常见用途：定义适配器，以让外部类实例，可以被视为其他不相关的类的实例
    HashMap中，使用EntrySet、KeySet等内部类，通过对应的方法，就可以得到和HashMap 无关联的实例；
    List,Set 实现类中，使用成员内部类来实现迭代器

 6.如果申明一个内部类，它不需要外部类的实例，那么总是将它声明为 static ；
    因为成员内部类总是隐藏的持有外部类的 引用，这会带来 空间和时间 的占用；
    更严重的是，如果外部类可以被GC，但是因为被内部类持有引用，导致无法被回收，可能带来内存泄漏；


 */
public class Code24_2 {
    private int id;
    private String name;


    public void outM1(){
        Code24_2 code242 = new Code24_2();
        MemberInner inner = new MemberInner();
        inner.innerM1();
    }

    // public的静态内部类
    public class MemberInner {

        // 成员内部类，不允许定义静态方法，idea提示在jdk16才可以用
        // public static void test(){}

        //
        public void innerM1(){
            // Each instance of a nonstatic member class is implicitly associated with an enclosing instance of its containing class
            // 每个非静态成员类的实例，都会隐式关联到其包含类的一个实例
            Code24_2 outer = Code24_2.this;  // 获取对外部实例的引用

            System.out.println(id == outer.id);
            System.out.println(outer.id);

        }

    }

    public static void main(String[] args) {
        Code24_2 demo = new Code24_2();
        demo.id = 12;
        demo.outM1();

    }
}
