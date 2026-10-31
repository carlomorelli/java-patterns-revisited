package com.csoft.designpatterns.behavioural;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VisitorTest {

    @Test
    void testVisitsDifferentShapes() {
        List<Visitor.Shape> shapes = List.of(
                new Visitor.Shape.Circle(new Visitor.Point(0, 0), 5),
                new Visitor.Shape.Square(new Visitor.Point(10, 1), 4),
                new Visitor.Shape.Rectangle(new Visitor.Point(5, 7), 3, 6)
        );

        Visitor.TotalAreaCalculator areaCalculator = new Visitor.TotalAreaCalculator();
        for (Visitor.Shape shape : shapes) {
            shape.accept(areaCalculator);
        }

        assertThat(areaCalculator.getTotalArea()).isCloseTo(112, Percentage.withPercentage(1));
    }

}