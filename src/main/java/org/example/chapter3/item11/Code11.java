package org.example.chapter3.item11;

/**
    重写equals之后，关于重写 Hashcode 的问题
    1.Object 类中的hashcode 方法得到的值，是根据对象的内部存储地址生成一个哈希码。
        实际上，它将对象的内存地址转换为一个整数值，并返回该值作为哈希码。
        需要注意的是，由于每个对象的存储地址是实时分配的，所以每次程序启动时哈希码都会发生变化。
        目前的结论，理论上 Object 的Hashcode方法基于对象的内存地址来计算，所以Hashcode值不会相同；

    2.一旦重写了equals方法，那么需要重写Hashcode方法，为什么？
        为了维护一致性和准确性。
        因为equals()方法是用于比较两个对象是否相等的方法，而自Object类继承的 hashCode()方法是根据对象的内存地址来判断的，
        所以两个对象就算equals相同，Hashcode也不相同，所以为了维持一致性，需要根据equals来重写hashcode方法

    3.为什么要提供Hashcode这么一个东西？ 看 Object 类的 hashcode 方法的注释：
        Returns a hash code value for the object.
        This method is supported for the benefit of hash tables such as those provided by java.util.HashMap
        返回值是 int，调用native方法实现的。
 */
public class Code11 {

    public static class OverwriteHashcode{
        private int id;
        private String name;
    }

    public static void main(String[] args) {
        OverwriteHashcode entity = new OverwriteHashcode();
        System.out.println("hashcode = "+entity.hashCode());
    }
}
