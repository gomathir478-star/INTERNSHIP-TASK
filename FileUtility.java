import java.io.*;
import java.nio.file.*;
import java.util.*;

public class FileUtility  {

    private static final String FILE_PATH = "sample.txt";

    // Write content to a file
    public static void writeToFile(String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write(content);
            System.out.println("Content written successfully.");
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }

    // Read content from a file
    public static void readFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            System.out.println("Reading file content:");
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error reading file: " + e.getMessage());
        }
    }

    // Modify file content by replacing a word
    public static void modifyFile(String target, String replacement) {
        try {
            Path path = Paths.get(FILE_PATH);
            String content = new String(Files.readAllBytes(path));
            content = content.replaceAll(target, replacement);
            Files.write(path, content.getBytes());
            System.out.println("File modified successfully.");
        } catch (IOException e) {
            System.err.println("Error modifying file: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter content to write:");
        String content = scanner.nextLine();
        writeToFile(content);

        readFromFile();

        System.out.println("Enter word to replace:");
        String target = scanner.nextLine();
        System.out.println("Enter replacement word:");
        String replacement = scanner.nextLine();
        modifyFile(target, replacement);

        readFromFile();
        scanner.close();
    }
}