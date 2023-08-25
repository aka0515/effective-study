package org.example.chapter4.item25;

/**
 what is top-level in one source file? 什么是一个源文件中定义多个顶层类？
    在一个 .java 文件中，除了当前类之外，还有其他的 非内部类nested class，
    比如当前源文件是 Utensil.java，但是内部还有一个 Dessert的类，而这个类不是内部类

 解决方案：
    1.如果两个类之间有关联，可以尝试将 除主类之外的类，定义为内部类，而非 top-level
    2.分成多个 source file

 */
// Two classes defined in one file. Don't ever do this!  (Page 115)
class Utensil {
    static final String NAME = "pan";
}

class Dessert {
    static final String NAME = "cake";
}
