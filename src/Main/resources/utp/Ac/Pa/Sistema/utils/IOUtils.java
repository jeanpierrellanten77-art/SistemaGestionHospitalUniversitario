package utp.ac.pa.sistema.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class IOUtils {
    public static void saveText(String filename, String content) {
        Path p = Paths.get(filename);
        try { Files.write(p, content.getBytes()); }
        catch (IOException e) { }
    }

    public static String readText(String filename) {
        Path p = Paths.get(filename);
        try { return new String(Files.readAllBytes(p)); }
        catch (IOException e) { return null; }
    }
}
