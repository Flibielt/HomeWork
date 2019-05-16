package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Creates {@code JsonWriter} and {@code JsonReader} to the given file.
 */
@Slf4j
class FileOperations {

    /**
     * The file's destination.
     */
    private String destination;
    /**
     * Single instance of the {@code FileOperations} class
     */
    private static FileOperations fileOperationsInstance = null;

    /**
     * The constructor of the {@code FileOperations}, gets the destination of the current folder.
     */
    private FileOperations() {
        try {
            Path workingDirectory = FileSystems.getDefault().getPath("").toAbsolutePath();
            destination = workingDirectory.toString() + File.separator + "data" + File.separator;
            log.info("Working directory: {}", workingDirectory);
            checkDirectory();
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    static FileOperations getInstance() {
        if (fileOperationsInstance == null) {
            fileOperationsInstance = new FileOperations();
        }
        return fileOperationsInstance;
    }

    /**
     * Creates a file if it was not created yet.
     *
     * @param fileName the name of the file
     * @return a {@code JsonReader} to the file
     */
    JsonReader CopyFileFromJar(String fileName) {
        try {
            checkDirectory();
            return new JsonReader(new FileReader(createFilePath(fileName)));

        } catch (Exception e) {
            log.error(e.toString());
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            try {
                return new JsonReader(new FileReader(classLoader.getResource("table.json").getPath()));
            } catch (Exception ex) {
                log.error(ex.toString());
                return null;
            }
        }
    }

    /**
     * Creates if it was not created yet.
     *
     * @param fileName the name of the file
     * @return a {@code JsonWriter} to the file
     */
    JsonWriter CreateJsonWriter(String fileName) {
        try {
            checkDirectory();
            return new JsonWriter(new FileWriter(createFilePath(fileName)));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    /**
     * Creates the directory, if it was not existed yet.
     */
    private void checkDirectory() {
        try {
            File directory = new File(destination);
            if (directory.mkdir()) {
                log.info("Directory {} created", destination);
            }
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    /**
     * Creates an absolute path to the given file.
     *
     * @param fileName the name of the file
     * @return an absolute path
     */
    private String createFilePath(String fileName) {
        try {
            String fileLocation = destination + fileName;
            InputStream source = getClass().getResourceAsStream("/" + fileName);
            Path dest = Paths.get(fileLocation);
            if (Files.notExists(dest)) {
                Files.copy(source, Paths.get(fileLocation));
                log.info("File table.json copied from jar to {}", fileLocation);
            } else {
                log.info("{} was already existed", fileLocation);
            }
            return fileLocation;
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }
}
