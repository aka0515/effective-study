package org.example.chapter4.item22_23.hierarchy;

// Class hierarchy replacement for a tagged class  (Page 110-11)
class Circle extends Figure {
    // TODO 可以使用final来修饰字段了
    final double radius;

    Circle(double radius) {
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * (radius * radius);
    }
}
