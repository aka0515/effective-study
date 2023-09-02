package org.example.chapter3.item11;

import java.util.Objects;

public class Person {
    private int id;
    private String name;

    // 构造方法、getter 和 setter 省略

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Person person = (Person) obj;
        return id == person.id && Objects.equals(name, person.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }

}

/*
    补充：可变参数，可以作为数组形式传递
    public static int hash(Object... values) {
        return Arrays.hashCode(values);
    }

    // 使用数组接收
    public static int hashCode(Object a[]) {
        if (a == null)
            return 0;

        int result = 1;

        for (Object element : a)
            result = 31 * result + (element == null ? 0 : element.hashCode());

        return result;
    }



 */