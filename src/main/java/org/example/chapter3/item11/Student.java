package org.example.chapter3.item11;

import java.util.Objects;

/**
 用来演示 cononical representation
 */
public class Student {
    private String name;
    private int age;

    // Constructor and other methods...

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Student stu = (Student) obj;
        return Objects.equals(getCanonicalName(), stu.getCanonicalName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCanonicalName());
    }

    // 这里用来获取 canonical representation，因为name可能需要特殊的比较规则，因此用一个方法来获取
    private String getCanonicalName() {
        // Define your canonicalization logic here
        return name.trim().toLowerCase();
    }
}
