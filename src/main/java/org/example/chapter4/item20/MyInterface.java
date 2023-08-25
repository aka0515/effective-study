package org.example.chapter4.item20;

public interface MyInterface {

    public default void testDefaultMethod(){
        System.out.println("this is default method of interface since java 8");
    }
}
