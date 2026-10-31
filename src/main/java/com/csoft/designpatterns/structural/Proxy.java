package com.csoft.designpatterns.structural;

/**
 * The Proxy pattern is used when controlled access to an object is required.
 * <p>
 * - To implement lazy loading of heavy or resource-intensive objects.
 * - To provide access control or security checks before accessing an object.
 * - To add logging, caching, or monitoring without changing the real object.
 * <p>
 * This pattern is useful in case where we need to add additional side effects like caching to a base data structure
 * (in the example: type {@link Image} and its implementation {@link RealImage}), but we can't, e.g. because they belong
 * to a library, or because they are a sealed interface.
 */
public class Proxy {

    /**
     * Base type and implementation.
     */
    interface Image {
        void display();
    }

    static class RealImage implements Image {
        private final String filename;

        public RealImage(String filename) {
            this.filename = filename;
            loadImageFromDisk();
        }

        private void loadImageFromDisk() {
            IO.println("Loading image: " + filename);
        }

        @Override
        public void display() {
            IO.println("Displaying image: " + filename);
        }
    }


    /**
     * A custom Proxy type that extends the original {@link RealImage} with lazy-loading.
     */

    static class ProxyImage implements Image {
        private RealImage realImage = null;
        private final String filename;

        public ProxyImage(String filename) {
            this.filename = filename;
        }

        @Override
        public void display() {
            if (realImage == null) {
                realImage = new RealImage(filename);
            }
            realImage.display();
        }
    }


    /**
     * Client logic: we can observe that the Proxy only loads the image once at first display() call.
     */
    void main() {
        Image image = new ProxyImage("example.jpg");

        // Image will be loaded from disk only when display() is called
        image.display();

        // Image will not be loaded again, as it has been cached in the Proxy
        image.display();
    }

}

