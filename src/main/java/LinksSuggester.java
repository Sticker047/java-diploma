import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class LinksSuggester {

    private final List<Suggest> suggest = new ArrayList<>();

    public LinksSuggester(File file) throws IOException, WrongLinksFormatException {
        FileReader fileReader = new FileReader(file);

        Scanner scanner = new Scanner(fileReader);

        while (scanner.hasNextLine()) {
            String[] temp = scanner.nextLine().split("\t");
            suggest.add(new Suggest(temp[0], temp[1], temp[2]));
        }

        for (Suggest value : suggest) {
            System.out.println(value);
        }

        fileReader.close();
    }

    public List<Suggest> suggest(String text) {
        return suggest;
    }
}
