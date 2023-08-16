package org.example.chapter4.item19;

// Class whose constructor invokes an overridable method. NEVER DO THIS! (Page 95)
public class Super {
    // Broken - constructor invokes an overridable method
    // supplement: why? 因为如果有继承的子类，且重写了 overrideMe 方法的话，new 子类的时候
    // 子类的构造会调用父类的构造，此时调用  overrideMe(); 等同于 this.overrideMe();
    // 又如果子类重写 overrideMe() 且调用子类的实例数据，那么会因为子类没有完成实例化，而得到null的数据
    public Super() {
        // new 子类的时候，这里其实是 this.overrideMe();
        // 子类重写的方法中，调用了 子类的实例变量，该变量在父类构造执行后执行，所以，在这里调用overrideMe，子类的那个变量为null
        overrideMe();
    }

    public void overrideMe() {
    }
}
