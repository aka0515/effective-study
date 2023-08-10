package org.example.chapter4;


/**
 最小化可变性
    1.不可变类的5个准则（非常严格，可以根据实际情况来调整以获取最佳性能）
        1.不提供修改字段的方法；
        2.类使用final，避免被继承；
        3.字段声明为final，避免被修改；同时final字段在不使用同步的情况下，也是线程安全的；
        4.字段声明为private；
        5.可变组件，不要让客户端访问；

    2.函数式方法
        不修改原始对象（包括修改原始对象状态），而是返回一个新的对象

    3.不可变类可以考虑提供静态工厂，来缓存实例
        Integer类中提供了 IntegerCache 类，可以缓存-128~127之间所有的实例

    4.因为每个值都要提供一个新的对象，因此某些场景下，可以考虑提供伴随类
        1.如果能够准确预测客户端在不变类上执行的复杂操作，那么可以考虑提供包私有的可变伴随类
            java.math的MutableBigInteger和SignedMutableBigInteger类 都是默认修饰符
        2.如果不能预测客户端会有哪些复杂操作，可以提供外部的公共伴随类：String的伴随类：StringBuilder

    5.因为当时对于不可变类的概念理解不够深入，所以在编写 BigInteger 和 BigDecimal 时，没有将这些类定义为有效 final
        所以在使用它们的时候需要注意是否存在可能被修改的情况；


 */
public class Code17_MinimizeMutable {

    /**
        Integer 中缓存的说明
     */
    // Integer中负责缓存的内部类
    private static class IntegerCache {
        static final int low = -128;
        static final int high;
        static final Integer cache[];

        static {
            int h = 127;
            // 表示可以从JVM的配置中获取high的值，或者使用默认值
            String integerCacheHighPropValue =
                    sun.misc.VM.getSavedProperty("java.lang.Integer.IntegerCache.high");
            if (integerCacheHighPropValue != null) {
                try {
                    // .... 省略
                } catch( NumberFormatException nfe) { }
            }
            high = h;

            cache = new Integer[(high - low) + 1];
            int j = low;
            for(int k = 0; k < cache.length; k++)
                cache[k] = new Integer(j++);
        }
        private IntegerCache() {}
    }
    /*
        Returns an Integer instance representing the specified
        int value.  If a new Integer instance is not
        required, this method should generally be used in preference to
        the constructor #Integer(int) , as this method is likely
        to yield significantly better space and time performance by
        caching frequently requested values.

        This method will always cache values in the range -128 to 127,
        inclusive, and may cache other values outside of this range.
        @since  1.5
    */
    // 使用这个方法会触发缓存
    public static Integer valueOf(int i) {
        if (i >= IntegerCache.low && i <= IntegerCache.high)
            return IntegerCache.cache[i + (-IntegerCache.low)];
        return new Integer(i);
    }
}
