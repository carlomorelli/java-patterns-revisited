package com.csoft.designpatterns.behavioural;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ChainOfResponsibilityFunctionalTest {

    // NOTE:
    // Mockito spies don't support Lambdas or method references, so we have to "step back" and wrap each lambda function
    // in a class implementing the functional inteface :(

    static class Level1Handler implements Predicate<ChainOfResponsibilityFunctional.Request> {
        @Override
        public boolean test(ChainOfResponsibilityFunctional.Request request) {
            return ChainOfResponsibilityFunctional.handleLevel1Request(request);
        }
    }

    static class Level2Handler implements Predicate<ChainOfResponsibilityFunctional.Request> {
        @Override
        public boolean test(ChainOfResponsibilityFunctional.Request request) {
            return ChainOfResponsibilityFunctional.handleLevel2Request(request);
        }
    }

    static class Level3Handler implements Predicate<ChainOfResponsibilityFunctional.Request> {
        @Override
        public boolean test(ChainOfResponsibilityFunctional.Request request) {
            return ChainOfResponsibilityFunctional.handleLevel3Request(request);
        }
    }

    @Spy
    Level1Handler handler1predicate = new Level1Handler();

    @Spy
    Level2Handler handler2predicate = new Level2Handler();

    @Spy
    Level3Handler handler3predicate = new Level3Handler();


    @Test
    void testPerformsCorOnDifferentHandlers() {



        var chainOfRespPredicate = handler1predicate.or(handler2predicate).or(handler3predicate);

        var request1 = new ChainOfResponsibilityFunctional.Request(ChainOfResponsibilityFunctional.Priority.BASIC);
        var request2 = new ChainOfResponsibilityFunctional.Request(ChainOfResponsibilityFunctional.Priority.INTERMEDIATE);
        var request3 = new ChainOfResponsibilityFunctional.Request(ChainOfResponsibilityFunctional.Priority.CRITICAL);

        // verify that request1 is handled only by level1handler
        chainOfRespPredicate.test(request1);
        verify(handler1predicate).test(any());
        verifyNoInteractions(handler2predicate);
        verifyNoInteractions(handler3predicate);

        // verify that request2 is handled only by level1handler and level2handler
        clearInvocations(handler1predicate, handler2predicate, handler3predicate);
        chainOfRespPredicate.test(request2);
        verify(handler1predicate).test(any());
        verify(handler2predicate).test(any());
        verifyNoInteractions(handler3predicate);

        // verify that request3 is handled only by all three handlers
        clearInvocations(handler1predicate, handler2predicate, handler3predicate);
        chainOfRespPredicate.test(request3);
        verify(handler1predicate).test(any());
        verify(handler2predicate).test(any());
        verify(handler3predicate).test(any());
    }
}