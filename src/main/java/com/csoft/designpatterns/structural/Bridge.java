package com.csoft.designpatterns.structural;

/**
 * The Bridge Pattern is used when abstraction and implementation need to evolve independently.
 * <p>
 * - When you want to avoid permanent binding between abstraction and implementation.
 * - When changes in implementation should not affect client code.
 * - When you want to hide implementation details from the client.
 */
public class Bridge {

    /**
     * Implementer for bridge pattern with two concrete implementations:
     * {@link Produce} and {@link Assemble}
     */
    sealed interface Workshop {
        void work();

        final class Produce implements Workshop{
            @Override
            public void work() {
                IO.print("Produced");
            }
        }

        final class Assemble implements Workshop {
            @Override
            public void work() {
                IO.print(" And");
                IO.println(" Assembled.");
            }
        }

    }


    /**
     * Next, a base object and two concrete implementations of it.
     */
    abstract static class Vehicle {
        protected Workshop workShop1;
        protected Workshop workShop2;

        protected Vehicle(Workshop workShop1, Workshop workShop2) {
            this.workShop1 = workShop1;
            this.workShop2 = workShop2;
        }

        abstract public void manufacture();
    }

    static class Car extends Vehicle {
        public Car(Workshop workShop1, Workshop workShop2) {
            super(workShop1, workShop2);
        }

        @Override
        public void manufacture() {
            IO.print("Car ");
            workShop1.work();
            workShop2.work();
        }
    }

    static class Bike extends Vehicle {
        public Bike(Workshop workShop1, Workshop workShop2) {
            super(workShop1, workShop2);
        }

        @Override
        public void manufacture() {
            IO.print("Bike ");
            workShop1.work();
            workShop2.work();
        }
    }


    /**
     * Client code:
     * We have two vehicles and two workshop stages.
     * So there are 4 possible combinations of execution logic.
     * <p>
     * The bridge pattern models the 4 execution logic via injection of
     * the two possible workshops in each vehicle. In this way, if more
     * vehicles or workshop stages are added, the client change is limited.
     */
    void main() {
        Vehicle vehicle1 = new Car(new Workshop.Produce(), new Workshop.Assemble());
        vehicle1.manufacture();
        Vehicle vehicle2 = new Bike(new Workshop.Produce(), new Workshop.Assemble());
        vehicle2.manufacture();
    }

}
