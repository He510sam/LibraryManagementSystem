import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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


}
