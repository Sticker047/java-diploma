import lombok.extern.slf4j.Slf4j;

import java.io.File;

@Slf4j
public class Main {

    public static final String DATA = "C:\\Users\\gagil\\IdeaProjects\\java-diploma\\data\\";
    public static final String PATH_IN = DATA + "pdfs";
    public static final String PATH_OUT = DATA + "converted";
    public static final String CONFIG = DATA + "config";

    public static void main(String[] args) {
        try {
            File config = new File(CONFIG);
            System.out.println(config.exists());
            LinksSuggester linksSuggester = new LinksSuggester(config);

            File dirIn = new File(PATH_IN);
            File[] files = dirIn.listFiles();
            log.debug("Число файлов в папке - {}", files.length);
            if (files != null) {
                for (File file : files) {
                    Convertor program = new Convertor(file, linksSuggester);
                    program.Converting();
                }
            } else {
                log.info("Папка с файлами для редактирования пуста - {}", PATH_IN);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
