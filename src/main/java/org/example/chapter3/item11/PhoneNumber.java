package org.example.chapter3.item11;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// Shows the need for overriding hashcode when you override equals (Pages 50-53 )
public final class PhoneNumber {
    private final short areaCode, prefix, lineNum;

    public PhoneNumber(int areaCode, int prefix, int lineNum) {
        this.areaCode = rangeCheck(areaCode, 999, "area code");
        this.prefix   = rangeCheck(prefix,   999, "prefix");
        this.lineNum  = rangeCheck(lineNum, 9999, "line num");
    }

    private static short rangeCheck(int val, int max, String arg) {
        if (val < 0 || val > max)
            throw new IllegalArgumentException(arg + ": " + val);
        return (short) val;
    }

    @Override public boolean equals(Object o) {
        if (o == this)
            return true;
        if (!(o instanceof PhoneNumber))
            return false;
        PhoneNumber pn = (PhoneNumber)o;
        return pn.lineNum == lineNum && pn.prefix == prefix
                && pn.areaCode == areaCode;
    }


    // Broken with no hashCode; works with any of the three below
   //  以下三种Hashcode方法都可以

   // Typical hashCode method (Page 52)
   // @Override
   public int hashCode1() {
       int result = Short.hashCode(areaCode);
       result = 31 * result + Short.hashCode(prefix);
       result = 31 * result + Short.hashCode(lineNum);
       return result;
   }

   // One-line hashCode method - mediocre（中等） performance  (page 53) why？
   // they run more slowly because they entail array creation to pass a variable number of arguments,
   //  as well as boxing and unboxing if any of the arguments are of primitive type
   // @Override
   public int hashCode2() {
       return Objects.hash(lineNum, prefix, areaCode);
   }

   // hashCode method with lazily initialized cached hash code  (page 53)
   private int hashCode; // Automatically initialized to 0

   // @Override
   public int hashCode() {
       int result = hashCode;
       if (result == 0) {
           result = Short.hashCode(areaCode);
           result = 31 * result + Short.hashCode(prefix);
           result = 31 * result + Short.hashCode(lineNum);
           hashCode = result;
       }
       return result;
   }

    /**
     about how to compute hashcode:
     1.If the field is of a primitive type, compute Type.hashCode(f), where Type is the boxed primitive class corresponding to f’s type.
        比如：Short.hashCode(prefix);
     2.If the field is an object reference and this class’s equals method compares the field by recursively invoking equals,
        recursively invoke hashCode on the field.
        对象类型的字段，需要递归调用字段的Hashcode
     3.If a more complex comparison is required, compute a “canonical representation” for this field and invoke hashCode on the canonical representation.
        If the value of the field is null, use 0 (or some other constant, but 0 is traditional)
        解释见Student类
     4.If the field is an array, treat it as if each significant element were a separate field.
        针对数组，对每个元素计算哈希，然后累加到一起，Arrays.hashcode 就是这个逻辑
        for (Object element : a){
            result = 31 * result + (element == null ? 0 : element.hashCode());
        }

     If you have a bona fide need for hash functions less likely to produce collisions,
     see Guava’s com.google.common.hash.Hashing [Guava].

     */

    public static void main(String[] args) {
        Map<PhoneNumber, String> m = new HashMap<>();
        // 如果不重写 Hashcode，那么map获取的是null，因为new了两次，是两个对象
        // 注意，Hashcode方法没有 @override不影响
        m.put(new PhoneNumber(707, 867, 5309), "Jenny");
        System.out.println(m.get(new PhoneNumber(707, 867, 5309)));
    }
}
