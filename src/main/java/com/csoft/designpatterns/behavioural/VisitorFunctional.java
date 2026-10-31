package com.csoft.designpatterns.behavioural;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * The Visitor pattern is commonly used when an aggregate operation is required on a fixed set of classes.
 * <p>
 * - Compiler design (syntax tree traversal).
 * - Calculating taxes, discounts, or reports on objects.
 * - File system operations (size calculation, backup).
 * - Processing elements in complex object structures.
 * <p>
 * In this example, we show how the Visitor pattern can be superseded when using Stream -> map -> sum strategy.
 * The reader can notice that no Visitor interface not aggregate logic is needed beyond the main() method.
 */
public class VisitorFunctional {

    /**
     * Ancillary: a point in cartesian coordinates
     */
    public record Point(double x, double y) {
    }

    /**
     * We have a base type Shape and a number of implementations.
     * <p>
     * The type has an 'accept(visitor)' method.
     */
    public sealed interface Shape {

        double area();

        record Circle(Point center, double radius) implements Shape {
            @Override
            public double area() {
                return Math.PI * Math.pow(radius, 2);
            }
        }

        record Square(Point lowerLeftCorner, double side) implements Shape {
            @Override
            public double area() {
                return Math.pow(side, 2);
            }
        }

        record Rectangle(Point lowerLeftCorner, double base, double height) implements Shape {
            @Override
            public double area() {
                return base * height;
            }
        }
    }

    public static double getTotalArea(Collection<Shape> shapes) {
        return shapes.stream()
                .mapToDouble(Shape::area)
                .sum();
    }

    void main() {
        List<Shape> shapes = List.of(
                new Shape.Circle(new Point(0, 0), 5),
                new Shape.Square(new Point(10, 1), 4),
                new Shape.Rectangle(new Point(5, 7), 3, 6)
        );

        var totalArea = getTotalArea(shapes);

        IO.println("Total area: " + totalArea);
    }

}
