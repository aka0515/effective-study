package org.example.chapter3;


import java.math.BigDecimal;
import java.util.Comparator;
import java.util.HashSet;
import java.util.TreeSet;

import static java.lang.System.out;
import static java.util.Comparator.comparingInt;

/*
    类已经实现Comparable接口，并且定义了comparaTo方法的逻辑，那么如果要对该类条件值组件（新的字段），可能会破坏原有的比较逻辑
        条件的做法是，创建一个新的类，将原类作为它的属性，并提供方法来访问原来的类的实例；
    如果类重写了 equals 方法，那么实现comparaTo方法的时候，推荐建议和equals保持一致，否则需要特殊说明；
        即comparaTo方法相等的时候，equals方法也要相等；
    Collection、Set或Map这些集合，判断是否存在某个元素，使用 equals
        而在排序集合，比如TreeSet，使用comparaTo代替equals
        new BigDecimal("1.0")和new BigDecimal("1.00")，使用equals方法比较时，这两个BigDecimal实例不相等。
        使用TreeSet执行相同的过程，集合将只包含一个元素，因为使用compareTo方法比较时，这两个BigDecimal实例是相等的
 */
public class Code14_Compara {

    public static void main(String[] args) {
        testStringCompara();

        testBidDecimal();
    }

    // String类的比较，实现 Comparable接口，它的compareTo方法是实例对象
    // 实例调用comparaTo方法，去比较另外一个对象
    public static void testStringCompara(){
        String s1 = "abc";
        String s2 = "bbc";
        // 按照从前到后的顺序，逐个比较
        System.out.println(s1.compareTo(s2)); // -1
        String s3= "abc";
        String s4 = "abcd";
        // 按照从前到后的顺序，更长的更大
        out.println(s3.compareTo(s4)); // -1
    }

    // BigDecimal 的equals 和 compare 的相等不一致
    public static void testBidDecimal(){
        BigDecimal first = new BigDecimal("1.0");
        BigDecimal sec = new BigDecimal("1.00");
        out.println(first.equals(sec)); // false

        HashSet<BigDecimal> set = new HashSet<>();
        set.add(first);
        set.add(sec);
        out.println(set.size()); // 2

        TreeSet<BigDecimal> treeSet = new TreeSet<>();
        treeSet.add(first);
        treeSet.add(sec);
        out.println(treeSet.size()); // 1
    }


    // 使用 Comparator 实例自定义比较规则
    class PhoneNumber implements Comparable<PhoneNumber> {
        public short areaCode;
        public short prefix;
        public short lineNum;

        // 手动定义比较器，相对繁琐，
        @Override
        public int compareTo(PhoneNumber pn) {
            int result = Short.compare(areaCode, pn.areaCode);
            if (result == 0) {
                result = Short.compare(prefix, pn.prefix);
                if (result == 0) {
                    result = Short.compare(lineNum, pn.lineNum);
                }
            }
            return result;
        }
        // 可以利用 Comparator 来实现comparaTo，但是会带来性能损耗，原因是添加了很多的方法调用，以及lambda的使用
        // 语法特性：静态引用，在import后面添加static，使用静态类中的方法和属性，不需要使用类型，比如这里的comparingInt
        public int compareTo1(PhoneNumber phoneNumber) {
            Comparator<PhoneNumber> COMPARATOR =
                    comparingInt((PhoneNumber pn) -> pn.areaCode)
                            .thenComparingInt(pn -> pn.prefix)
                            .thenComparingInt(pn -> pn.lineNum);
            // 静态引用的案例
            out.print("");
            return COMPARATOR.compare(this, phoneNumber);
        }
    }


    // 整数类型的比较，最好不要使用数值相减的方式，因为可能存在 整数溢出
    public static void testNumberCompara(){
        // 错误的案例，因为如果是Integer.MAX_VALUE - Integer.MIN_VALUE 就溢出了
        Comparator<Object> hashCodeOrder = new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return o1.hashCode() - o2.hashCode();
            }
        };
        // 正确的做法
        new Comparator<Object>() {
            public int compare(Object o1, Object o2) {
                return Integer.compare(o1.hashCode(), o2.hashCode());
            }
        };
        // 或者是这样的
        Comparator.comparingInt(o -> o.hashCode());
    }

    // 测试 Compartor的 链式构造器
    public void testComparator(){
        Comparator<Person> testComparator = comparingInt((Person p) -> p.id)
                .thenComparingLong(p -> p.idcard)
                // double类型的构造器，可以用于float
                .thenComparingDouble(p -> p.weght);
    }

    class Person {
        public int id;
        public long idcard;
        private float weght;
    }

}
