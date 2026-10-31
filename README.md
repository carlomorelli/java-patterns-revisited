# Java Patterns Revisited

A collection of Java examples demonstrating classic **design patterns** in their
traditional object-oriented form, alongside **functional reimplementations** that
show how many of these patterns can be simplified with lambdas, method
references, predicates, and the Streams API.

The project targets **Java 25** and is built with **Maven**.

## Why?

Many GoF patterns exist to work around language limitations that modern Java has
addressed. This repository is a study companion that lets you compare, side by
side, the "classic" OOP solution with a more concise functional alternative.

## Patterns covered

### Creational

| Pattern | Class | Notes |
| --- | --- | --- |
| Singleton (lazy, synchronized) | `creational.Singleton` | Lazy initialization with a synchronized accessor. |
| Singleton (double-checked locking) | `creational.SingletonWithLock` | Thread-safe lazy instance. |
| Singleton (eager) | `creational.SingletonEager` | Instance created at class load. |
| Singleton (enum) | `creational.SingletonAsEnum` | Preferred, serialization-safe singleton. |

### Behavioural

| Pattern | Class | Notes |
| --- | --- | --- |
| Chain of Responsibility | `behavioural.ChainOfResponsibility` | Classic handler chain. |
| Chain of Responsibility (functional) | `behavioural.ChainOfResponsibilityFunctional` | Handlers as `Predicate` composed with `or`. |
| Visitor | `behavioural.Visitor` | Classic `accept`/`visit` double dispatch. |
| Visitor (functional) | `behavioural.VisitorFunctional` | Aggregate operations via `Stream.mapToDouble().sum()`. |

### Structural

| Pattern | Class | Notes |
| --- | --- | --- |
| Adapter | `structural.Adapter` | Wraps a legacy `printDocument()` behind a `Printer`. |
| Bridge | `structural.Bridge` | Decouples vehicles from workshop stages. |
| Composite | `structural.Composite` | Uniform handling of files and directories. |
| Decorator | `structural.Decorator` | Coffee toppings composed at runtime. |
| Decorator (complex) | `structural.DecoratorComplex` | Compression + encryption over an I/O `DataSource`. |
| Flyweight Factory | `structural.FlyweightFactory` | Cached/immutable icon instances, simple and keyed variants. |
| Proxy | `structural.Proxy` | Lazy-loading image proxy. |

## Project structure

```
src
├── main/java/com/csoft/designpatterns
│   ├── behavioural
│   ├── creational
│   └── structural
└── test/java/com/csoft/designpatterns
    ├── behavioural
    └── creational
```

Each pattern lives in a single `main` class (with nested types where useful) and
ships with a `void main()` demo. Open any class in your IDE and run it to see the
pattern in action.

## Requirements

- JDK 25
- Maven 3.9+

## Build and test

```bash
mvn clean test
```

## Running an example

After compiling, run any demo from your IDE. From the command line:

```bash
mvn -q compile
java -cp target/classes com.csoft.designpatterns.structural.Decorator
```

## Testing stack

- [JUnit Jupiter](https://junit.org/junit5/)
- [AssertJ](https://assertj.github.io/doc/)
- [Mockito](https://site.mockito.org/)

## License

No license has been specified for this project.
