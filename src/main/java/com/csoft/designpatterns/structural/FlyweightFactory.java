package com.csoft.designpatterns.structural;


import java.util.HashMap;
import java.util.Map;

/**
 * The Flyweight Factory Pattern is used when an application needs to handle a very large number of similar objects
 * efficiently. By embedding creation of an object hierarchy to a special Factory, we can cache pre-created objects
 * in the factory itself at first creation and return the same object (same identity) at subsequent requests.
 * <p>
 * We use this pattern:
 * - When memory optimization is critical due to a large number of objects.
 * - When many objects share common data that can be reused.
 * - When object creation cost is high and sharing can improve performance.
 */
public class FlyweightFactory {

    /**
     * A base type and couple of implementations.
     */
    public interface Icon {
        void draw(int x, int y);

        IconType getType();

        enum IconType {
            FILE,
            FOLDER
        }
    }

    public static class FileIcon implements Icon {
        private static final IconType type = IconType.FILE;
        private final String label;
        private final String imageName;

        public FileIcon(String label, String imageName) {
            this.label = label;
            this.imageName = imageName;
        }

        @Override
        public IconType getType() {
            return type;
        }

        @Override
        public void draw(int x, int y) {
            IO.println("Drawing " + type + " icon with label " + label + " and image " + imageName + " at position (" + x + ", " + y + ")");
        }
    }

    public static class FolderIcon implements Icon {
        private static final IconType type = IconType.FOLDER;
        private final String color;
        private final String imageName;

        public FolderIcon(String color, String imageName) {
            this.color = color;
            this.imageName = imageName;
        }

        public IconType getType() {
            return type;
        }

        @Override
        public void draw(int x, int y) {
            IO.println("Drawing " + type + " icon with color " + color + " and image " + imageName + " at position (" + x + ", " + y + ")");
        }
    }

    /**
     * The FlyweightFactory for Icon type: allows generation on demand of {@link Icon} implementations and
     * caches them in a local map for faster bootstraps.
     * <p>
     * Caveat: all returned types will have same identity (a concept similar to the Singleton pattern).
     * This is find if the objects under use are immutable and don't bring a special state, but may be a problem
     * if the objects need to mutate and need independent identity.
     */
    public static class IconFactory {
        private static final Map<Icon.IconType, Icon> iconCache = new HashMap<>();

        public static Icon generateIcon(Icon.IconType type) {

            // Check if the icon already exists in the cache
            if (iconCache.containsKey(type)) {
                IO.println("Retrieving from cache object of type " + type);
                return iconCache.get(type);
            }

            // Create a new icon based on the type, and cache it
            IO.println("Bootstrapping and caching object of type " + type);
            Icon icon = switch (type) {
                case FILE -> new FileIcon("document", "document.png");
                case FOLDER -> new FolderIcon("blue", "folder.png");
                case null -> throw new IllegalArgumentException("Unsupported icon type: " + type);
            };
            iconCache.put(type, icon);
            return icon;

        }
    }


    /**
     * A variant on the FlyweightFactory for Icon type.
     * <p>
     * We can generate variants depending on the business need. In this case, we want to cache not just Icon objects
     * by type, but also by the associated descriptor ('label' for {@link FileIcon}, 'color' for {@link FolderIcon}).
     * <p>
     * In order to achieve this, we define an internal record type (or class implementing Comparable) that is used
     * as hashable key for the cache hashmap.
     */
    public static class ComplexIconFactory {
        private static final Map<Key, Icon> iconCache = new HashMap<>();

        private record Key(Icon.IconType type, String descriptor) {}

        public static Icon generateIcon(Icon.IconType type, String descriptor) {

            var key = new Key(type, descriptor);

            // Check if the icon already exists in the cache
            if (iconCache.containsKey(key)) {
                IO.println("Retrieving from cache object of type " + type + " and descriptor '" + descriptor + "'");
                return iconCache.get(key);
            }

            // Create a new icon based on the type, and cache it
            IO.println("Bootstrapping and caching object of type " + type + " and descriptor '" + descriptor + "'");
            Icon icon = switch (type) {
                case FILE -> new FileIcon(descriptor, "document.png");
                case FOLDER -> new FolderIcon(descriptor, "folder.png");
                case null -> throw new IllegalArgumentException("Unsupported icon type: " + type);
            };
            iconCache.put(key, icon);
            return icon;

        }
    }



    /**
     * Client code to use the flyweight objects (icons).
     * <p>
     * It can be seen that the first generation attempt of an object of a certain type will show the
     * message "Bootstrapping and caching ...", while the next generation attempts will show the
     * message "Retrieving from cache ..."
     */
    void main() {

        IO.println("==== Using base factory ====");

        Icon fileIcon1 = IconFactory.generateIcon(Icon.IconType.FILE);
        fileIcon1.draw(100, 100);
        Icon fileIcon2 = IconFactory.generateIcon(Icon.IconType.FILE);
        fileIcon2.draw(150, 150);
        Icon folderIcon1 = IconFactory.generateIcon(Icon.IconType.FOLDER);
        folderIcon1.draw(200, 200);
        Icon folderIcon2 = IconFactory.generateIcon(Icon.IconType.FOLDER);
        folderIcon2.draw(250, 250);

        IO.println("==== Using complex factory ====");

        Icon fileIcon11 = ComplexIconFactory.generateIcon(Icon.IconType.FILE, "document");
        fileIcon11.draw(100, 100);
        Icon fileIcon12 = ComplexIconFactory.generateIcon(Icon.IconType.FILE, "image");
        fileIcon12.draw(150, 150);
        Icon fileIcon13 = ComplexIconFactory.generateIcon(Icon.IconType.FILE, "document"); //uses cache
        fileIcon13.draw(120, 100);
        Icon fileIcon14 = ComplexIconFactory.generateIcon(Icon.IconType.FILE, "json");
        fileIcon14.draw(150, 170);
        Icon folderIcon11 = ComplexIconFactory.generateIcon(Icon.IconType.FOLDER, "blue");
        folderIcon11.draw(200, 200);
        Icon folderIcon12 = ComplexIconFactory.generateIcon(Icon.IconType.FOLDER, "blue"); //uses cache
        folderIcon12.draw(250, 250);
        Icon folderIcon13 = ComplexIconFactory.generateIcon(Icon.IconType.FOLDER, "red");
        folderIcon13.draw(220, 200);
        Icon folderIcon14 = ComplexIconFactory.generateIcon(Icon.IconType.FOLDER, "orange");
        folderIcon14.draw(250, 270);

    }
}
