package org.example.chapter2;

import java.io.*;

/**
 * 第一条：用静态工厂方法代替构造方法
 * 解释：这一条主要介绍的是，获取对象的时候，在特定场景下，使用静态构造方法，比构造方法更有优势；
 */
public class Code01 {

    /**
     优势1：【相比于构造方法】，静态工厂方法有名称
        如果不同对象的关键在于它内部的属性时，构造方法在创建对象时无法体现出特定属性的含义
        比如jdk中的 BigInteger这个类，probablePrime() 返回BigInteger对象，但是通过静态方法名告诉我们：可能是素数
     */
    /**
     优势2：【相比于构造方法】，静态工厂方法不必每次都创建对象；【适合用在创建相同的对象，且对象的创建成本很高。】
            构造方法每次使用都会创建对象，spring工厂则是省掉一切步骤，直接使用对象；
            静态工厂方法则是可以提创建好对象，然后用的时候获取
     */
    /**
     优势3：【相比于构造方法】，静态工厂方法可以返回子类型，同样也可以根据参数，来决定返回什么子类型对象；
            构造方法无法做到
     */
    /**
     优势4：【相比于构造方法】，静态工厂方法可以做到，返回的类，在创建静态方法时不存在，
            即：返回的是接口，并且此时也只有接口，比如 java的jdbc，都是接口，实现类由数据库厂商提供；
     */
    /**
     缺点：
        1.类如果不含有公有的、受保护的构造器，就不能被子类化；
            （子类化，指的是不在当前类而是在外部其他类中创建对象时，此时不能用静态工厂方法创建该对象）
        2.程序员很难发现它们；

     补充关于对象创建的知识：
        1.java中创建对象只有两种途径：
            通过构造方法，在JVM中开辟内存空间，加载类，然后初始化等等，最后获取对象；
            其他基于构造方法来创建对象：比如反射，底层也是要通过构造方法；
            通过clone方法，但是前提需要存在一个对象。克隆是一种浅拷贝的方式，即只会拷贝对象本身的值类型成员变量和引用类型成员变量的引用。
                如果原对象包含引用类型成员变量，克隆对象和原对象将共享同一个引用，对引用类型成员变量的修改会影响到两个对象。
        2.通过反序列化的方式创建对象，不需要构造函数；

        3.静态工厂方法，也是建立在构造方法的基础上，本质是一种封装。类似的封装：java相对于C语言将开辟内存的操作，用构造方法封装了；

     */
    public static void main(String[] args) {
        // 有业务方法名的静态工厂方法，可以提示一些信息，比构造方法使用方便。
        MyNum positive = MyNum.positive();

        // 直接返回已准在的对象，
        Car car = Car.getInstance();

        // 获取子类型
        Dog dog = Animal1.get();

        // 外部的类，如果是私有构造，则不能直接new，这样适合在类内部，通过静态方法构造对象，
        // PrivateClass privateClass = new PrivateClass(1);
        // 静态内部类，可以使用私有构造，可以使用，
        PrivateClass1 privateClass1 = new PrivateClass1(1);

        /**
         * 反序列化的方式创建对象
         */
        // 创建一个Person对象
        Person person = new Person("John", 25);

        // 将Person对象序列化到文件中
        serializeObject(person, "person.ser");

        // 从文件中反序列化Person对象
        Person deserializedPerson = (Person) deserializeObject("person.ser");

        // 打印反序列化后的Person对象
        System.out.println(deserializedPerson);
    }





    static class MyNum{
        int value;

        public MyNum(int value) {
            this.value = value;
        }

        public static MyNum positive(){
            return new MyNum(1);
        }

    }

    static class Car {
        private String name;

        public Car(String name) {
            this.name = name;
        }

        private static Car geely = new Car("geely");

        public static Car getInstance(){
            return geely;
        }
    }

    static class Animal1 {
        public static Dog get(){
            return new Dog();
        }
    }

    static class Dog extends Animal1{

    }

    static class PrivateClass1 {
        int a;
        private PrivateClass1(){
        }
        private PrivateClass1(int a){
            this.a = a;
        }
    }


    // 序列化对象到文件
    private static void serializeObject(Object obj, String fileName) {
        try {
            FileOutputStream fileOut = new FileOutputStream(fileName);
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
            objectOut.writeObject(obj);
            objectOut.close();
            fileOut.close();
            System.out.println("对象已序列化到文件 " + fileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 从文件中反序列化对象
    private static Object deserializeObject(String fileName) {
        try {
            FileInputStream fileIn = new FileInputStream(fileName);
            ObjectInputStream objectIn = new ObjectInputStream(fileIn);
            // 通过追踪源码，没看到调用构造方法的地方
            Object obj = objectIn.readObject();
            objectIn.close();
            fileIn.close();
            System.out.println("从文件 " + fileName + " 反序列化对象成功");
            return obj;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    // 定义一个可序列化的Person类
    static class Person implements Serializable {
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age +
                    '}';
        }
    }

}

class PrivateClass {
    int a;
    private PrivateClass(){
    }
    private PrivateClass(int a){
        this.a = a;
    }
}