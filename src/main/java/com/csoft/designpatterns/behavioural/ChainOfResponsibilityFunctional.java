package com.csoft.designpatterns.behavioural;

import java.util.function.Predicate;

/**
 * COR is used when multiple objects may handle a request and the concrete handler is not known in advance.
 * <p>
 * - Handling requests in multiple levels (e.g., approval systems).
 * - Implementing logging frameworks with different log levels.
 * - Designing event handling systems in GUI applications.
 * - Managing authentication and authorization chains.
 * <p>
 * In this example, we reimplement the COR pattern in functional style. Each handler is reduced to a side-effect
 * producer function.
 */
public class ChainOfResponsibilityFunctional {

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
     * The base SupportHanlder interface is now gone. The three level implementations are now
     * predicate functions (functions that take a {@link Request} and return a bool) which
     * can be chained in a global 'or' predicate.
     */

    public static boolean handleLevel1Request(Request request) {
        if (request.priority() == Priority.BASIC) {
            IO.println("Level 1 Support handled the request.");
            return true;
        } else {
            IO.println("Escalating...");
            return false;
        }
    }

    public static boolean handleLevel2Request(Request request) {
        if (request.priority() == Priority.INTERMEDIATE) {
            IO.println("Level 2 Support handled the request.");
            return true;
        } else {
            IO.println("Escalating...");
            return false;
        }
    }

    public static boolean handleLevel3Request(Request request) {
        if (request.priority() == Priority.CRITICAL) {
            IO.println("Level 3 Support handled the request.");
            return true;
        } else {
            IO.println("Request cannot be handled.");
            return false;
        }
    }

    void main() {
        Predicate<Request> handler1 = ChainOfResponsibilityFunctional::handleLevel1Request;
        Predicate<Request> handler2 = ChainOfResponsibilityFunctional::handleLevel2Request;
        Predicate<Request> handler3 = ChainOfResponsibilityFunctional::handleLevel3Request;

        Predicate<Request> chainOfResp = handler1.or(handler2).or(handler3);

        Request request1 = new Request(Priority.BASIC);
        Request request2 = new Request(Priority.INTERMEDIATE);
        Request request3 = new Request(Priority.CRITICAL);

        IO.println("Sending request1 of priority BASIC");
        chainOfResp.test(request1);
        IO.println("Sending request2 of priority INTERMEDIATE");
        chainOfResp.test(request2);
        IO.println("Sending request3 of priority CRITICAL");
        chainOfResp.test(request3);
    }
}
