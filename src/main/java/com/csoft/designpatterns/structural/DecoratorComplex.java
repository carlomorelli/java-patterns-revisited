package com.csoft.designpatterns.structural;

import java.util.Base64;

/**
 * This is a complex scenario involving the {@link Decorator} pattern.
 * <p>
 * Assume that we have {@link DataSource} type and {@link FileDataSource} implementaiton
 * from a IO library, which we cannot extend.
 * <p>
 * We can then define decorators that allows the extension:
 * - a base, abstract decorator called the 'wrapper'
 * - multiple concrete decorators extending the abstract one, and potentially chaining the effects over the
 * original IO DataSource.
 * (for example, in this scenario we combine an EncryptionDecorator and CompressionDecorator).
 */
public class DecoratorComplex {

    // The baseline IO library
    // -----------------------

    /**
     * Ancillary: the type needed by the abstract data source.
     */
    public record Data(String content) {
    }

    /**
     * The base data type: in this case, it abstracts a 'data source' and IO operations.
     */
    public interface DataSource {
        void writeData(Data data);

        Data readData();
    }

    /**
     * An implementation of the base data type. Both this and the base type cannot be extended.
     *
     */
    static final class FileDataSource implements DataSource {
        private final String filename;

        FileDataSource(String filename) {
            this.filename = filename;
        }

        public void writeData(Data data) {
            IO.println("Writing data to %s".formatted(filename));
        }

        public Data readData() {
            IO.println("Reading data from %s".formatted(filename));
            return new Data("content");
        }

    }


    // The decorator extensions
    // ------------------------

    /**
     * Base decorator abstract class wrapping the base DataSource type ('wrappee')
     */
    public static class DataSourceDecorator implements DataSource {

        protected DataSource wrappee;

        DataSourceDecorator(DataSource source) {
            wrappee = source;
        }

        public void writeData(Data data) {
            wrappee.writeData(data);
        }

        public Data readData() {
            return wrappee.readData();
        }
    }

    /**
     * Concrete deccorators that expand the original DataSource capability with
     * additional IO functionality (compression, encryption, etc...)
     */
    static class EncryptionDecorator extends DataSourceDecorator {

        private static final Base64.Encoder enc = Base64.getEncoder();
        private static final Base64.Decoder dec = Base64.getDecoder();

        EncryptionDecorator(DataSource source) {
            super(source);
        }

        public void writeData(Data data) {
            IO.println("Encrypting the data...");
            var encryptedData = new Data(enc.encodeToString(data.content().getBytes()));
            super.writeData(encryptedData);
        }

        public Data readData() {
            var encryptedData = super.readData();
            try {
                IO.println("Decrypting the data...");
                return new Data(new String(dec.decode(encryptedData.content())));
            } catch (Exception e) {
                throw new RuntimeException("Trouble decoding!", e);
            }
        }
    }

    static class CompressionDecorator extends DataSourceDecorator {
        CompressionDecorator(DataSource source) {
            super(source);
        }

        public void writeData(Data data) {
            IO.println("Compressing the data...");
            var compressedData = data; //Assume there is a compression step here
            super.writeData(compressedData);
        }

        public Data readData() {
            IO.println("Inflating the data...");
            var compressedData = super.readData();
            var data = compressedData; //Assume there is a decompression step here
            return data;
        }

    }


    /**
     * Main client application logic
     */
    void main() {

        // Normal usage of the base final DataSource type
        IO.println("---- Original DataSource ----");
        DataSource dataSource = new FileDataSource("somefile.dat");
        Data salaryRecords = new Data("content");
        dataSource.writeData(salaryRecords);

        // Extension: DataSource processor that adds compression
        IO.println("---- DataSource with compression ----");
        DataSource dataSourceWithCompression = new CompressionDecorator(dataSource);
        dataSourceWithCompression.writeData(salaryRecords);

        // Extension: DataSource processor that adds compression and encryption
        IO.println("---- DataSource with compression and encryption ----");
        DataSource dataSourceWithCompressionAndEncryption = new EncryptionDecorator(new CompressionDecorator(dataSource));
        dataSourceWithCompressionAndEncryption.writeData(salaryRecords);

    }

}
