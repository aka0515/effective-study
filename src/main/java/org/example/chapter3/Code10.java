package org.example.chapter3;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
    补充：这部分内容不怎么理解，后续需要再补充

    第10条：重写equals方法需要注意的几个规则，以下每个规则讨论的对象都需要符合：非空引用
         1.自反性： x.equals(x) 必须返回 true。
         2.对称性： 如果且仅当 y.equals(x) 返回 true 时 x.equals(y) 必须返回 true。
         3.传递性： 如果 x.equals(y) 返回 true，y.equals(z) 返回 true，则 x.equals(z) 必须返回 true。
         4.一致性： 如果在 equals 比较中使用的信息没有修改，则 x.equals(y) 的多次调用必须始终返回 true 或始终返回 false。这里涉及到可变类和不可变类。
             可变类：
                 在 equals 方法中比较所有关键字段：确保 equals 方法比较所有可以影响对象相等性的关键字段。这样，只要对象的关键字段没有改变，equals 方法将始终返回相同的结果。
                 避免使用不稳定的字段：尽量不要将那些可能随时间变化的字段（如时间戳、序列号等）作为 equals 方法的判断依据。这将有助于确保在对象未被修改的情况下 equals 方法返回的结果保持一致。
                 考虑同步机制：如果类是线程安全的，可能需要使用同步机制来确保在多线程环境下 equals 方法的一致性。这可以通过使用 synchronized 关键字或者其他同步工具类来实现。
             不可变类：
                 确保所有字段是 final 的：在不可变类中，所有字段都应该被声明为 final，这样它们的值就不能在对象创建后被修改。这有助于确保对象的状态始终保持不变，从而保证 equals 方法的一致性。
                 比较所有关键字段：与可变类类似，确保 equals 方法比较所有关键字段。由于不可变类的字段值不会改变，这将确保相等的对象始终相等，不相等的对象永远不会相等。
                 禁止子类化：防止其他开发者创建你的不可变类的子类，从而可能破坏不可变性。这可以通过将类声明为 final 或者使用其他方法（如使构造函数私有并提供静态工厂方法）来实现。
         5.对于任何非空引用 x，x.equals(null) 必须返回 false。

    关于对称性，主要讨论的是，继承可实例化类并添加值组件（额外的属性）时，如何遵守equals()方法的通用契约
    解决方案：
        1.使用getClass()而非instanceof：在重写equals()方法时，可以使用getClass()测试代替instanceof测试
        2.优先使用组合而非继承：一个更好的解决方案是使用组合而非继承。（更推荐这种）

    补充：
        1.当从抽象类继承并添加值组件时，不会违反equals()约定，因为抽象类无法直接实例化，不会出现这些自定义equals带来的问题；
        2.Java平台类库中有一些类会违反上述约定，例如java.sql.Timestamp，它继承了java.util.Date并添加了一个nanoseconds字段。
            Timestamp的equals()方法违反了对称性，可能导致不稳定的行为。虽然可以通过避免混用Timestamp和Date来解决这个问题，但这种做法不应该被模仿
        3.在 equal 时方法声明中，不要将参数 Object 替换成其他类型，比如下面的equals方法，使用的参数就不是Object，因为这样做只是重载而非重写
             public boolean equals(MyClass o) {
             …
             }
 */
public class Code10 {
    public static void main(String[] args) {

    }
}

/**
 * 对称性的案例
 */
final class CaseInsensitiveString {
    private final String s;
    public CaseInsensitiveString(String s) {
        this.s = Objects.requireNonNull(s);
    }
    // Broken - violates symmetry!  违背对称性，原因是：存在与 String 类相互操作，而它和String的equals的规则不同；
    // @Override
    // public boolean equals(Object o) {
    //     if (o instanceof CaseInsensitiveString)
    //         return s.equalsIgnoreCase(
    //                 ((CaseInsensitiveString) o).s);
    //     if (o instanceof String)  // One-way interoperability! 单向互操作性
    //         return s.equalsIgnoreCase((String) o);
    //     return false;
    // }

    @Override
    public boolean equals(Object o) {
        return o instanceof CaseInsensitiveString &&
                ((CaseInsensitiveString) o).s.equalsIgnoreCase(s);
    }
    // Remainder omitted 省略其余代码
    public static void main(String[] args) {
        CaseInsensitiveString cis = new CaseInsensitiveString("Polish");
        String s = "polish";
        System.out.println(cis.equals(s)); // true
        System.out.println(s.equals(cis)); // false

        List<CaseInsensitiveString> list = new ArrayList<>();
        list.add(cis);
        System.out.println(list.contains(s)); // false

        // 更新了 CaseInsensitiveString 的equals方法后，可以实现预期的逻辑
        CaseInsensitiveString hello1 = new CaseInsensitiveString("Hello");
        CaseInsensitiveString hello2 = new CaseInsensitiveString("hello");
        System.out.println("hello1 equals hello2 =  " +hello2.equals(hello1));
        list.add(hello1);
        System.out.println("list.contains(hello2) = "+list.contains(hello2)); // true
    }
}

/**
 传递性的案例
 */
class Point {
    private final int x;
    private final int y;
    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }
    /* @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        Point p = (Point) o;
        return p.x == x && p.y == y;
    } */
    // Remainder omitted

    // 用来解决子类的equals问题的方案1，使用 getClass 代码 instanceOf
    @Override
    public boolean equals(Object o) {
        if (o == null || o.getClass() != getClass())
            return false;
        Point p = (Point) o;
        return p.x == x && p.y == y;
    }
}
class ColorPoint extends Point {
    private final Color color;
    public ColorPoint(int x, int y, Color color) {
        super(x, y);
        this.color = color;
    }
    // Remainder omitted

    // 第一种equals方法 Broken - violates symmetry!
    // @Override
    // public boolean equals(Object o) {
    //     if (!(o instanceof ColorPoint))
    //         return false;
    //     return super.equals(o) && ((ColorPoint) o).color == color;
    // }

    // 第二种equals方法，解决了对称性，但是失去了传递性
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        // If o is a normal Point, do a color-blind comparison
        if (!(o instanceof ColorPoint))
            return o.equals(this);
        // o is a ColorPoint; do a full comparison
        return super.equals(o) && ((ColorPoint) o).color == color;
    }


    public static void main(String[] args) {
        // 第一种equals方法的测试，失去了对称性
        Point p = new Point(1, 2);
        ColorPoint cp = new ColorPoint(1, 2, Color.RED);
        System.out.println(p.equals(cp)); // true
        System.out.println(cp.equals(p)); // false

        // 第二种equals方法的测试，实现了对称，但是失去了传递性
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        Point p2 = new Point(1, 2);
        ColorPoint p3 = new ColorPoint(1, 2, Color.BLUE);
        System.out.println(p1.equals(p2)); // true
        System.out.println("p2 equals p1: "+p2.equals(p1));
        System.out.println(p2.equals(p3)); // true
        System.out.println(p1.equals(p3)); // false 因为根据传递性：p1==p2,p2==p3,因此p1==p3

    }
}

class SmellPoint extends Point {
    String smell;
    public SmellPoint(int x, int y, String smell) {
        super(x, y);
        this.smell = smell;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Point))
            return false;
        if (!(o instanceof SmellPoint))
            // 产生递归的入口在这里
            return o.equals(this);
        return super.equals(o) && ((SmellPoint) o).smell.equals(smell);
    }

    public static void main(String[] args) {
        ColorPoint p1 = new ColorPoint(1, 2, Color.RED);
        SmellPoint s1= new SmellPoint(1,2,"oops");
        // 用来演示第二种 equals方法，可能产生递归的情况，通过执行代码，确实会产生递归
        System.out.println(s1.equals(p1));
    }
}

// 重新定义的ColorPoint类，这是解决子类equals的另外一个方法
// 优先使用组合而非继承：一个更好的解决方案是使用组合而非继承。在这种情况下，例如将颜色添加到Point类中，可以创建一个包含私有Point属性的ColorPoint类，而不是直接继承Point类
class ColorPoint1 {
    private final Point point;
    private final Color color;
    public ColorPoint1(int x, int y, Color color) {
        point = new Point(x, y);
        this.color = Objects.requireNonNull(color);
    }
    /**
     * Returns the point-view of this color point.
     */
    public Point asPoint() {
        return point;
    }
    @Override
    public boolean equals(Object o) {
        if (!(o instanceof ColorPoint1))
            return false;
        ColorPoint1 cp = (ColorPoint1) o;
        return cp.point.equals(point) && cp.color.equals(color);
    }

    // 这样只是重载，而非重写
    public boolean equals(ColorPoint1 o) {
        return false;
    }
}