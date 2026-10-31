package com.csoft.designpatterns.structural;

import java.util.ArrayList;
import java.util.List;

/**
 * The Composite Pattern is used when dealing with hierarchical structures.
 * <p>
 * - To represent part–whole hierarchies such as trees or nested structures.
 * - To allow clients to treat individual objects and collections uniformly.
 * - To simplify client code by avoiding checks for leaf or composite objects.
 */
public class Composite {

    /**
     * Base type and two implementations
     */
    public interface FileSystemComponent {
        void display();
    }

    public static class File implements FileSystemComponent {
        private final String name;
        private final int size;

        public File(String name, int size) {
            this.name = name;
            this.size = size;
        }

        @Override
        public void display() {
            IO.println("File: " + name + " (" + size + " bytes)");
        }
    }

    public static class Directory implements FileSystemComponent {
        private final String name;
        private final List<FileSystemComponent> components = new ArrayList<>();

        public Directory(String name) {
            this.name = name;
        }

        @Override
        public void display() {
            IO.println("Directory: " + name);
            for (FileSystemComponent component : components) {
                component.display();
            }
        }

        public void addComponent(FileSystemComponent component) {
            components.add(component);
        }
    }


    void main() {

        // Create leaf objects (files)
        FileSystemComponent file1 = new File("document.txt", 1024);
        FileSystemComponent file2 = new File("image.jpg", 2048);

        // Create a composite object (directory)
        Directory directory = new Directory("My Documents");

        // Add leaf objects to the directory
        directory.addComponent(file1);
        directory.addComponent(file2);

        // Display the directory (including its contents)
        directory.display();

    }

}
