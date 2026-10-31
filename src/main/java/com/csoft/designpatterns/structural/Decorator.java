package com.csoft.designpatterns.structural;

/**
 * he Decorator Pattern is used when:
 * <p>
 * - We want to add responsibilities to objects *without* subclassing, avoiding the need to create multiple derived classes.
 * - We need to combine behaviors flexibly at runtime, allowing features to be added or removed dynamically as needed.
 * - We want to extend functionality of a class in a transparent way, without modifying its original code or structure.
 * <p>
 * This pattern is useful in case where we need to extend/subclass a base data structure (in the example: type {@link Coffee}
 * and its implementation {@link PlainCoffee}), but we can't, e.g. because they belong to a library, or because they
 * are a sealed interface.
 */
public class Decorator {

    /**
     * A base type and a possible implementation.
     */
    public interface Coffee {
        String getDescription();
        double getCost();
    }

    public static class PlainCoffee implements Coffee {
        @Override
        public String getDescription() {
            return "Plain Coffee";
        }

        @Override
        public double getCost() {
            return 2.0;
        }
    }

    /**
     * First 'decorator' of the base type:
     * - it implements the base
     * - however, it also injects the base type as internal attribute
     * - The internal attribute is protected to make it accessible from the concrete impelmentations
     * We then implement the base type methods by simply delegating the calls to the internal attribute.
     */
    public abstract static class CoffeeDecorator implements Coffee {
        protected Coffee decoratedCoffee;
        public CoffeeDecorator(Coffee decoratedCoffee) {
            this.decoratedCoffee = decoratedCoffee;
        }
        public String getDescription() {
            return decoratedCoffee.getDescription();
        }
        public double getCost() {
            return decoratedCoffee.getCost();
        }
    }

    /**
     * Second decorator: extends the behaviour of the base type
     */
    public static class MilkDecorator extends CoffeeDecorator {
        public MilkDecorator(Coffee decoratedCoffee) {
            super(decoratedCoffee);
        }
        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription() + ", Milk";
        }
        @Override
        public double getCost() {
            return decoratedCoffee.getCost() + 0.5;
        }
    }

    /**
     * Third decorator: extends the behaviour of the base type
     */
    public static class SugarDecorator extends CoffeeDecorator {
        public SugarDecorator(Coffee decoratedCoffee) {
            super(decoratedCoffee);
        }
        @Override
        public String getDescription() {
            return decoratedCoffee.getDescription() + ", Sugar";
        }
        @Override
        public double getCost() {
            return decoratedCoffee.getCost() + 0.2;
        }
    }

    /**
     * Client code can call decorators in chain to extend the features of the base type
     * without owning it.
     */
    void main() {
        // Plain Coffee
        Coffee coffee = new PlainCoffee();
        IO.println("Description: " + coffee.getDescription());
        IO.println("Cost: $" + coffee.getCost());

        // Coffee with Milk
        Coffee milkCoffee = new MilkDecorator(new PlainCoffee());
        IO.println("Description: " + milkCoffee.getDescription());
        IO.println("Cost: $" + milkCoffee.getCost());

        // Coffee with Sugar and Milk
        Coffee sugarMilkCoffee = new SugarDecorator(new MilkDecorator(new PlainCoffee()));
        IO.println("Description: " + sugarMilkCoffee.getDescription());
        IO.println("Cost: $" + sugarMilkCoffee.getCost());
    }
}
