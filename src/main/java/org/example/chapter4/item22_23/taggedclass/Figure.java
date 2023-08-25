package org.example.chapter4.item22_23.taggedclass;

// 这是标签类
// Tagged class - vastly inferior to a class hierarchy! (Page 109)
class Figure {
    enum Shape { RECTANGLE, CIRCLE };

    // Tag field - the shape of this figure
    // 标签字段，用来区分该类的不同实例
    final Shape shape;

    // These fields are used only if shape is RECTANGLE
    double length;
    double width;
    // TODO 不能用final，因为需要在多个构造器中同时实例化它，而这是不可能的
    // final double width1;

    // This field is used only if shape is CIRCLE
    double radius;

    // Constructor for circle
    Figure(double radius) {
        shape = Shape.CIRCLE;
        this.radius = radius;
    }

    // Constructor for rectangle
    Figure(double length, double width) {
        shape = Shape.RECTANGLE;
        this.length = length;
        this.width = width;
    }

    double area() {
        switch(shape) {
            case RECTANGLE:
                return length * width;
            case CIRCLE:
                return Math.PI * (radius * radius);
            default:
                throw new AssertionError(shape);
        }
    }
}
