package com.csoft.designpatterns.behavioural;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ChainOfResponsibilityTest {

    @Spy
    ChainOfResponsibility.Level1SupportHandler level1Handler = new ChainOfResponsibility.Level1SupportHandler();

    @Spy
    ChainOfResponsibility.Level2SupportHandler level2Handler = new ChainOfResponsibility.Level2SupportHandler();

    @Spy
    ChainOfResponsibility.Level3SupportHandler level3Handler = new ChainOfResponsibility.Level3SupportHandler();


    @Test
    void testPerformsCorOnDifferentHandlers() {
        level1Handler.setNextHandler(level2Handler);
        level2Handler.setNextHandler(level3Handler);

        var request1 = new ChainOfResponsibility.Request(ChainOfResponsibility.Priority.BASIC);
        var request2 = new ChainOfResponsibility.Request(ChainOfResponsibility.Priority.INTERMEDIATE);
        var request3 = new ChainOfResponsibility.Request(ChainOfResponsibility.Priority.CRITICAL);

        // verify that request1 is handled only by level1handler
        level1Handler.handleRequest(request1);
        verify(level1Handler).handleRequest(any());
        verify(level2Handler, never()).handleRequest(any());
        verifyNoInteractions(level3Handler);

        // verify that request2 is handled only by level1handler and level2handler
        level2Handler.handleRequest(request2);
        verify(level1Handler).handleRequest(any());
        verify(level2Handler).handleRequest(any());
        verifyNoInteractions(level3Handler);

        // verify that request3 is handled only by all three handlers
        level3Handler.handleRequest(request3);
        verify(level1Handler).handleRequest(any());
        verify(level2Handler).handleRequest(any());
        verify(level3Handler).handleRequest(any());

    }
}