package org.example.chapter4.item22_23;

/**


 item 22: use interface only to define types 接口应该仅仅用来定义类型，而不是其他用途

    1. 一个常见的不合适使用：使用接口作为常量类，即接口中没有方法，全部是public static final的常量；实现这样的接口可以省去使用 class.constant的方式使用常量类；
        但是这样会对实现类的子类，造成命名常见的污染，也可能会暴露原本不该暴露的细节（接口中定义的常量）；
        就算后续不再使用这些常量，也要保留它（常量接口），因为需要保持二进制代码的一致性（比如常量接口的实现类的子类，使用到了常量接口中的变量）

    2. 用来暴露常量的几个比较合理的选择：
        如何常量和类有紧密的关系，可以定义在类中，比如Integer类的  MIN_VALUE and MAX_VALUE
        使用静态工具类来暴露常量。如果想避免 使用类名类限定常量，可以使用静态引入  static import

    3.java的类库中有一些不合理使用接口的案例：java.io.ObjectStreamConstants，这不应该被效仿

 */

public class Code22 {
}
