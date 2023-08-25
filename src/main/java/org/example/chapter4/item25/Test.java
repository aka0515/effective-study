package org.example.chapter4.item25;

// Static member classes instead of multiple top-level classes (Page 116)
public class Test {
    public static void main(String[] args) {
        System.out.println(Utensil.NAME + Dessert.NAME);
    }

    // 用静态内部类，代替 top-level的类
    private static class Utensil {
        static final String NAME = "pan";
    }

    private static class Dessert {
        static final String NAME = "cake";
    }
}
