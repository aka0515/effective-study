package org.example.chapter4;

/**
    如何处理字段的访问性，以下是一些建议： how to setup access range of class field, here is some advices:
        1.当涉及到公共类时，如果它可以在包外访问，那么应该使用访问器来访问字段，而不是直接暴露字段；
            特例：如果字段是不可变的，那么是可以考虑直接暴露字段

    补充：这里讨论的内容属于软件设计的部分，在平常的业务开发中，很少需要这么

 */
public class Code16_AccessField {
    // 不可变字段可以随意暴露
    public static final String addr="localhost";

    /**
     直接访问数据字段意味着数据的表示方式是固定的，如果需要更改表示方式，就需要同时修改使用该类的代码。
     这违反了“面向接口而非实现编程”的原则
     备注：面向接口，接口是稳定的，而实现是可变的，如果想要改变age字段，那么势必会影响所有使用它的客户端
        如果使用
     */
    public void f1(){
        AccessDemo inner = new AccessDemo();
        System.out.println(inner.age);
    }


}
// 这里是模拟公共类，实际上这个类不是
class AccessDemo{
    public int age;
    public String name;

    // 通过方法对外提供name，那么可以修改name这个变量，而不影响使用的客户端/调用方
    public String getName() {
        return name;
    }
}