package org.example.chapter4.item22_23;

/**


 item 23：使用层次类，而不是标签类
 1.标签类的定义：
    具有不同类型的实例，比如Figure类，它的子类有 Circle 和 Rectangle；
    类中有一个用来标识不同实例的字段，比如Figure的 shape字段， 它通常是一个枚举类型；

 2.标签类的缺点：
    需要有很多模板代码，比如枚举的声明，tagged字段，方法中的Switch表达式，影响代码的可读性
    增加了 类的实例的 内存占用，因为它有一些不相干的字段，比如 Figure类中，作为Circle来看的话，长度和宽度就是多余的字段；
    字段不能设为final，因为final的字段需要在 constructor中初始化，而 tagged 类无法同时实例化多个 flavor

 3.在OOP中，使用类的层次结构来代替 tagged class，比如继承
    定义抽象类；
    添加抽象方法，这个方法是原tagged中：行为依赖于 tagged字段的方法，且类的每个 flavor都需要的方法，比如Figure类的 area()；
    所有flavor都需要的 field，也放在抽象类中；
    为每个 flavor 定义一个子类，比如 Circle，Rectangle，并包含对应 flavor特有的方法和字段；




 */

public class Code23 {
}
