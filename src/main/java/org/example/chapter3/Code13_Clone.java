package org.example.chapter3;

import lombok.Data;

import java.io.*;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 浅拷贝和深拷贝
    浅拷贝：基本类型+String，拷贝值，引用类型拷贝地址
    深拷贝：引用类型拷贝整个对象
            apache commons lang 中的SerializationUtils类，它的clone方法通过序列化和反序列化实现深拷贝
            这个应该是一个比较通用的方法，hutool中的ObjectUtil提供的clone方法也用到了序列化
 原型模式
    HashMap中的clone方法是浅拷贝，

 数组的复制
    直接使用数组调用 clone 方法
 */
public class Code13_Clone {
    public static void main(String[] args) {
        CloneDemo demo = new CloneDemo();
        Object clone = demo.clone();
        System.out.println(clone.equals(demo));

        cloneArray();
        deepCloneArray();
        deepCopyArraySerial();

        cloneHashMap();
        testCopyOf();
    }

    // 虽然 Cloneable 接口没有任何方法，但是，没有它，就算重写了clone方法，调用也会抛出异常CloneNotSupportedException
    public static class CloneDemo implements Cloneable{
        // 原本的返回类型是 Object，但是可以使用子类来代替，也可以将 super.clone()的返回值进行强转；
        @Override
        protected CloneDemo clone() {
            // 可以使用 try-catch，因为它是首检异常
            try{
                return (CloneDemo)super.clone();
            }catch(CloneNotSupportedException e){
                throw new RuntimeException();
            }
        }
    }

    // 克隆数组，在数组上调用 `clone` 将返回一个运行时和编译时类型与被克隆的数组相同的数组。这是复制数组的首选方式
    public static void cloneArray(){
        String[] arr = new String[10];
        arr[0] = "java";
        String[] clone = arr.clone();
        // 注意，上面的拷贝是浅拷贝，基本类型+String是拷贝值
        String[] clone1 = arr.clone();
        clone1[0] = "python";
        System.out.println(arr[0]);

        Stu[] sts = new Stu[34];
        sts[0] = new Stu(1);
        Stu[] stsclone = sts.clone();
        System.out.println(stsclone[0]); // id=1
        sts[0].setId(10);
        System.out.println(stsclone[0]); // id =10
    }

    // 实现数组的深拷贝方法1，Stu类提供一个deepCopy方法
    public static void deepCloneArray(){
        Stu[] sourceArray = {new Stu("Alice"), new Stu("Bob")};
        Stu[] destinationArray = new Stu[sourceArray.length];

        for (int i = 0; i < sourceArray.length; i++) {
            destinationArray[i] = sourceArray[i].deepCopy();
        }
    }

    // 实现数组的深拷贝方法1，通过序列化，相对来看，序列化的效率没有 递归拷贝的方式效率高
    public static void deepCopyArraySerial(){
        Stu[] sourceArray = {new Stu("Alice"), new Stu("Bob")};
        Stu[] destinationArray = null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(bos)) {

            // 序列化
            oos.writeObject(sourceArray);

        } catch (IOException e) {
            e.printStackTrace();
        }
        // 如果两个try合并，都放在一个括号中则会发生  java.io.EOFException 异常，
        // 因为 new ByteArrayInputStream(bos.toByteArray()) 在构造的时候 bos 还没数据
        try(ByteArrayInputStream bis = new ByteArrayInputStream(bos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bis)) {

            // 反序列化
            destinationArray = (Stu[]) ois.readObject();
            System.out.println("克隆后的数组"+Arrays.toString(destinationArray));
            // 修改原数组
            sourceArray[0] = new Stu("java");
            System.out.println("原数组："+Arrays.toString(sourceArray));
            System.out.println("克隆后的数组是否有变化："+Arrays.toString(destinationArray));

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    // clone 哈希表
    // 必须使用HashMap作为类型，才能使用 clone方法，而且这是一个 shallow clone
    public static void cloneHashMap(){
        HashMap<String,Stu> map = new HashMap<>();
        map.put("first",new Stu(1,"first"));
        map.put("sec",new Stu(2,"sec"));
        // 前克隆，底层还是使用for循环来实现的
        HashMap<String,Stu> cloneMap = (HashMap<String,Stu>)map.clone();

        HashMap<String,Stu> cmap = new HashMap<>();
        for (Map.Entry<String, Stu> entry : map.entrySet()) {
            cmap.put(entry.getKey(), entry.getValue());
        }
        map.get("first").setName("one");
        map.put("first", map.get("first"));
        for (Stu value : cmap.values()) {
            System.out.println(value.toString());
        }
    }

    /**
        copyOf 方法，也是shadow clone，那么在CopyOnWriteArrayList类中，写时复制采用的就是这个技术，
        这样就存在，数据的不一致性，但是这个不一致，和ArrayList在多线程环境下的数据线程安全问题，不是一回事，
        因为：cow中存在的数据不一致是暂时的，且数据是确定的，而线程安全却是 不可控的。
     */
    public static void testCopyOf(){
        Stu[] sourceArray = {new Stu("Alice"), new Stu("Bob")};
        Stu[] stus = Arrays.copyOf(sourceArray, 2);
        Stu stu = sourceArray[0];
        stu.setName("java");
        System.out.println(Arrays.toString(sourceArray)); // name=java
        System.out.println(Arrays.toString(stus)); // name=java
    }

    // 来自极客时间-设计模式-原型模式，最佳的深拷贝：
    // 先浅拷贝。对于需要更新的元素，再使用深度拷贝的方式创建，然后替换老对象
    class Demo{
        private HashMap<String, SearchWord> currentKeywords=new HashMap<>();
        private long lastUpdateTime = -1;

        public void refresh() {
            // Shallow copy
            HashMap<String, SearchWord> newKeywords = (HashMap<String, SearchWord>) currentKeywords.clone();

            // 从数据库中取出更新时间>lastUpdateTime的数据
            List<SearchWord> toBeUpdatedSearchWords = getSearchWords(lastUpdateTime);
            long maxNewUpdatedTime = lastUpdateTime;
            for (SearchWord searchWord : toBeUpdatedSearchWords) {
                if (searchWord.getLastUpdateTime() > maxNewUpdatedTime) {
                    maxNewUpdatedTime = searchWord.getLastUpdateTime();
                }
                // 重点看这个，需要的更新
                if (newKeywords.containsKey(searchWord.getKeyword())) {
                    newKeywords.remove(searchWord.getKeyword());
                }
                newKeywords.put(searchWord.getKeyword(), searchWord);
            }

            lastUpdateTime = maxNewUpdatedTime;
            currentKeywords = newKeywords;
        }

        // 模拟从数据中取数据
        private List<SearchWord> getSearchWords(long lastUpdateTime) {
            // TODO: 从数据库中取出更新时间>lastUpdateTime的数据
            return null;
        }
    }

    @Data
    class SearchWord{
        long lastUpdateTime;
        String keyword;
    }


    @Data
    public static class Stu implements Serializable{
        public int id;
        public String name;

        public Stu(int id) {
            this.id = id;
        }

        public Stu(String name) {
            this.name = name;
        }

        public Stu(int id, String name) {
            this.id = id;
            this.name = name;
        }

        public Stu deepCopy(){
            return new Stu(id);
        }

        @Override
        public String toString() {
            return "{id=" + id +
                    ", name='" + name +"}";
        }
    }
}
