package com.csoft.designpatterns.behavioural;

import org.assertj.core.data.Percentage;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class VisitorFunctionalTest {

    @Test
    void testVisitsDifferentShapes() {
        List<VisitorFunctional.Shape> shapes = List.of(
                new VisitorFunctional.Shape.Circle(new VisitorFunctional.Point(0, 0), 5),
                new VisitorFunctional.Shape.Square(new VisitorFunctional.Point(10, 1), 4),
                new VisitorFunctional.Shape.Rectangle(new VisitorFunctional.Point(5, 7), 3, 6)
        );

        var totalArea = VisitorFunctional.getTotalArea(shapes);

        assertThat(totalArea).isCloseTo(112, Percentage.withPercentage(1));
    }

}