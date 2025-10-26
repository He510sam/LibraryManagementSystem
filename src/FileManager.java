import java.io.*;
import java.util.ArrayList;
import java.util.List;

//Class for read and write in file
public class FileManager {

    //Public and static method to get the file name and return a list of file lines
    public static List<String> readAllLines(String filename) {
        List<String> lines = new ArrayList<>();
        File f = new File(filename);
        if (!f.exists()) return lines;
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;
            while ((line = br.readLine()) != null) lines.add(line);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lines;
    }

    //Method for completely overwriting a file (overwriting)
    public static void writeAllLines(String filename, List<String> lines) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename))) {
            for (String l : lines) {
                bw.write(l);
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //This method adds a line to the end of the file.
    public static void appendLine(String filename, String line) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filename, true))) {
            bw.write(line);
            bw.newLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
