package com.csoft.designpatterns.behavioural;

/**
 * COR is used when multiple objects may handle a request and the concrete handler is not known in advance.
 *<p>
 * - Handling requests in multiple levels (e.g., approval systems).
 * - Implementing logging frameworks with different log levels.
 * - Designing event handling systems in GUI applications.
 * - Managing authentication and authorization chains.
 */
public class ChainOfResponsibility {

    /**
     * A Request type, representing the handled data.
     */
    public record Request(Priority priority) {
    }

    public enum Priority {
        BASIC,
        INTERMEDIATE,
        CRITICAL
    }

    /**
     * The handler interface and three possible implementations.
     * <p>
     * The interface designs the abstract method {@link setNextHandler(SupportHandler)} for the implementations
     * to provide indication of the next-in-line implementation.
     */
    public interface SupportHandler {
        void handleRequest(Request request);
        void setNextHandler(SupportHandler nextHandler);
    }

    public static class Level1SupportHandler implements SupportHandler {
        private SupportHandler nextHandler;

        public void setNextHandler(SupportHandler nextHandler) {
            this.nextHandler = nextHandler;
        }

        public void handleRequest(Request request) {
            if (request.priority() == Priority.BASIC) {
                IO.println("Level 1 Support handled the request.");
            } else if (nextHandler != null) {
                IO.println("Escalating...");
                nextHandler.handleRequest(request);
            }
        }
    }

    public static class Level2SupportHandler implements SupportHandler {
        private SupportHandler nextHandler;

        public void setNextHandler(SupportHandler nextHandler) {
            this.nextHandler = nextHandler;
        }

        public void handleRequest(Request request) {
            if (request.priority() == Priority.INTERMEDIATE) {
                IO.println("Level 2 Support handled the request.");
            } else if (nextHandler != null) {
                IO.println("Escalating...");
                nextHandler.handleRequest(request);
            }
        }
    }

    public static class Level3SupportHandler implements SupportHandler {
        public void handleRequest(Request request) {
            if (request.priority() == Priority.CRITICAL) {
                IO.println("Level 3 Support handled the request.");
            } else {
                IO.println("Request cannot be handled.");
            }
        }

        public void setNextHandler(SupportHandler nextHandler) {
            // Do nothing. This is the top-priority handler.
        }
    }

    void main() {
        SupportHandler level1Handler = new Level1SupportHandler();
        SupportHandler level2Handler = new Level2SupportHandler();
        SupportHandler level3Handler = new Level3SupportHandler();
        level1Handler.setNextHandler(level2Handler);
        level2Handler.setNextHandler(level3Handler);

        Request request1 = new Request(Priority.BASIC);
        Request request2 = new Request(Priority.INTERMEDIATE);
        Request request3 = new Request(Priority.CRITICAL);

        IO.println("Sending request1 of priority BASIC");
        level1Handler.handleRequest(request1);
        IO.println("Sending request2 of priority INTERMEDIATE");
        level1Handler.handleRequest(request2);
        IO.println("Sending request3 of priority CRITICAL");
        level1Handler.handleRequest(request3);
    }
}
