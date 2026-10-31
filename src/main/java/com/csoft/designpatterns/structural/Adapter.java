package com.csoft.designpatterns.structural;

/**
 * We can use adapter design pattern when:
 * <p>
 * - Reuses existing code or libraries without rewriting.
 * - Simplifies integration of new components, keeping the system flexible.
 * - Centralizes compatibility changes, making maintenance easier and safer.
 */
public class Adapter {

    /**
     * Generalization of the target type and methods.
     */
    interface Printer {
        void print();
    }

    /**
     * The 'adaptee': a legacy class with an incompatible method.
     */
    static class LegacyPrinter {
        public void printDocument() {
            IO.println("Legacy Printer is printing a document.");
        }
    }

    /**
     * The 'adapter': implements the target type.
     * <p>
     * It carries the adaptee as private field.
     */

    static class PrinterAdapter implements Printer {
        private final LegacyPrinter legacyPrinter;

        public PrinterAdapter(LegacyPrinter legacyPrinter) {
            this.legacyPrinter = legacyPrinter;
        }

        @Override
        public void print() {
            legacyPrinter.printDocument();
        }
    }

    /**
     * The client / caller
     */
    void main() {
        Printer printer = new PrinterAdapter(new LegacyPrinter());
        printer.print();
    }

}



