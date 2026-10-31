package com.csoft.designpatterns.behavioural;

import java.util.List;

/**
 * The Visitor pattern is commonly used when an aggregate operation is required on a fixed set of classes.
 * <p>
 * - Compiler design (syntax tree traversal).
 * - Calculating taxes, discounts, or reports on objects.
 * - File system operations (size calculation, backup).
 * - Processing elements in complex object structures.
 */
public class Visitor {

    /**
     * Ancillary: a point in cartesian coordinates
     */
    public record Point(double x, double y) {}

    /**
     * We have a base type Shape and a number of implementations.
     * <p>
     * The type has an 'accept(visitor)' method.
     */
    public sealed interface Shape {

        double area();

        void accept(ShapeVisitor visitor);

        record Circle(Point center, double radius) implements Shape {
            @Override
            public double area() {
                return Math.PI * Math.pow(radius, 2);
            }

            @Override
            public void accept(ShapeVisitor visitor) {
                visitor.visit(this);
            }
        }

        record Square(Point lowerLeftCorner, double side) implements Shape {
            @Override
            public double area() {
                return Math.pow(side, 2);
            }

            @Override
            public void accept(ShapeVisitor visitor) {
                visitor.visit(this);
            }
        }

        record Rectangle(Point lowerLeftCorner, double base, double height) implements Shape {
            @Override
            public double area() {
                return base * height;
            }

            @Override
            public void accept(ShapeVisitor visitor) {
                visitor.visit(this);
            }
        }
    }


    /**
     * The ShapeVisitor interface implementing the 'visit' function per each Shape impl.
     */
    public interface ShapeVisitor {
        void visit(Shape.Circle circle);
        void visit(Shape.Square square);
        void visit(Shape.Rectangle rectangle);
    }


    /**
     * A concrete ShapeVisitor impl
     */
    public static class TotalAreaCalculator implements ShapeVisitor {

        // This is a global reference that each visit method should update
        double totalArea = 0;

        @Override
        public void visit(Shape.Circle circle) {
            totalArea += circle.area();
        }

        @Override
        public void visit(Shape.Square square) {
            totalArea += square.area();
        }

        @Override
        public void visit(Shape.Rectangle rectangle) {
            totalArea += rectangle.area();
        }

        public double getTotalArea() {
            return totalArea;
        }
    }

    void main() {
        List<Shape> shapes = List.of(
                new Shape.Circle(new Point(0, 0), 5),
                new Shape.Square(new Point(10, 1), 4),
                new Shape.Rectangle(new Point(5, 7), 3, 6)
        );

        TotalAreaCalculator areaCalculator = new TotalAreaCalculator();
        for (Shape shape : shapes) {
            shape.accept(areaCalculator);
        }

        IO.println("Total area: " + areaCalculator.getTotalArea());
    }

}
