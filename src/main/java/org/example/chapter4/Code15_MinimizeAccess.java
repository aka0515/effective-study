package org.example.chapter4;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
    最小化类和成员（字段和方法）的可访问性
    1.为什么这么做？
        隐藏内部数据和实现逻辑，为了实现组件间的解耦，降低组件的复杂性；

    2.如何实现？
        java中通过使用访问修饰符和内部类，等形式，来实现访问控制

    3.非嵌套的顶级类和接口只有两个可能的访问级别：
        包级私有（package-private） 无访问修饰符，可能的情况下尽量使用包级私有。
            这么做的好处是，修改其中的内容不用担心影响客户端的使用
        公共（public） 使用public作为访问修饰符
            对外公共的，则需要考虑可能有用户在使用，那么更新时就需要向后兼容；

    4. 如果一个包级私有的顶级类或接口仅被一个类使用，那么应该将该顶级类作为该类的私有静态嵌套类
        一个案例是HashMap中的Node类，就是 static 的类
        补充：而对于是否要设为static，我认为是需要根据实际情况的，比如需要访问外部类的实例属性和方法，则不能设为静态内部类
            内部类，是否需要静态，取决于是否需要外部类的实例，以及是否需要外部类的静态属性和方法

    5. 关于封装需要注意的
 */
public class Code15_MinimizeAccess {
    private int age;
    public static int idcard;
    // static final类型的数组，这样直接暴露是不安全的，任然可以被客户端修改
    // public static final Integer[] INT_ARR = {1,2,3};

    // 解决上述问题，首先将变量设为私有
    private static final Integer[] INT_ARR = {1,2,3};
    // 解决方案1，提供一个不可变的集合
    public static final List<Integer> VALUES = Collections.unmodifiableList(Arrays.asList(INT_ARR));
    // 解决方案2，在方法中提供副本
    public static Integer[] getIntArr(){
        return INT_ARR.clone();
    }


    private void m1(){
    }

    private static void m2(){
    }

    private void m3(){
        InnerStatic.f3();
        InnerStatic t1 = new InnerStatic();
        t1.f1();
    }

    // 访问内部类的实例方法和静态方法
    private static void m4(){
        InnerStatic.f3();
        InnerStatic.f4();
        InnerStatic t1 = new InnerStatic();
        t1.f3();
        t1.f4();

    }

    private static class InnerStatic {
        public void f1(){
            Code15_MinimizeAccess code15 = new Code15_MinimizeAccess();
            // 访问实例属性，需要外部类的实例
            System.out.println(code15.age);
            System.out.println(idcard);
        }

        public void f2(){
            Code15_MinimizeAccess code15 = new Code15_MinimizeAccess();
            // 访问实例方法，需要外部类的实例
            code15.m1();
            m2();
        }

        public static void f3(){
            System.out.println("this is inner class static method ");
        }

        // 修改外部类的 static final 的字段
        private static void f4(){
            // 先输出看看
            System.out.println(Arrays.toString(INT_ARR)); // [1, 2, 3]
            INT_ARR[0] = 10;
            // 修改之后
            System.out.println(Arrays.toString(INT_ARR)); // [10, 2, 3]
        }
        // 访问外部类的方法
        public void f5(){
            Code15_MinimizeAccess code15 = new Code15_MinimizeAccess();
            // 访问实例方法，需要外部类的实例
            code15.m1();
            m2();
        }
    }

    public static void main(String[] args) {
        InnerStatic.f4();
    }

}
