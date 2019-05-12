package game.mechanics;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
public class FileOperations {

    private String destination;

    FileOperations() {
        try {
            destination = URLDecoder.decode(getClass().getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
            destination = destination.substring(0, destination.lastIndexOf("/")) + File.separator + "game" + File.separator;
        } catch (Exception e) {
            log.error(e.toString());
        }
    }

    public JsonReader CopyFileFromJar(String fileName) {
        try {
            File directory = new File(destination);
            if (directory.mkdir()) {
                log.info("Directory {} created", destination);
            }

            destination = destination + fileName;
            InputStream source = getClass().getResourceAsStream("/" + fileName);
            Path dest = Paths.get(destination);
            if (Files.notExists(dest)) {
                Files.copy(source, Paths.get(destination));
                log.info("File table.json copied from jar to {}", destination);
            } else {
                log.info("{} was already existed", destination);
            }
            return new JsonReader(new FileReader(destination));

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

    public JsonWriter CreateJsonWriter(String fileName) {
        try {
            if (destination.substring(destination.length() - 1).equals(File.separator)) {
                destination = destination.concat(fileName);
            }
            return new JsonWriter(new FileWriter(destination));
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }
}
