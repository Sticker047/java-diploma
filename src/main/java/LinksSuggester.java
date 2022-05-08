import exceptions.WrongLinksFormatException;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Getter
public class LinksSuggester {

    private final File configFile;
    private final List<Suggest> suggestsConfig = new ArrayList<>();

    public LinksSuggester(File configFile) throws IOException, WrongLinksFormatException {
        this.configFile = configFile;
        if (!this.configFile.exists()) {
            log.error("конфигурационный файл не найден - {}", configFile.getAbsolutePath());
            throw new FileNotFoundException("Файл не найден");
        }
        try {
            if (isConfigValid(configFile)) {
                log.info("Рекомендации созданы успешно - {}", configFile.getPath());
            } else return;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        try {
            List<String> lines = Files.readAllLines(configFile.toPath());
            for (String line : lines) {
                String[] temp = line.split("\\t");
                suggestsConfig.add(new Suggest(temp[0].toLowerCase(), temp[1], temp[2]));
            }
        } catch (IOException e) {
            log.error("ошибка ввода-вывода при чтении конфигурационного файла - {}", configFile.getAbsolutePath());
        }
    }

    public List<Suggest> getSuggests(String text, int numberOfPage) {
        List<Suggest> suggestsInText = new ArrayList<>();
        String textLowerCase = text.toLowerCase();
        for (Suggest suggest : suggestsConfig) {
            int index = textLowerCase.indexOf(suggest.getKeyWord());
            if (index >= 0) {
                suggestsInText.add(suggest);
            }
        }
        if (suggestsInText.isEmpty()) {
            log.info("В тексте не найдено ключевых слов");
        } else {
            log.info("ключевые слова обработаны. Страница - {}", numberOfPage);
        }
        return suggestsInText;
    }

    public static boolean isConfigValid(File configFile) throws WrongLinksFormatException, IOException {
        List<String> lines = Files.readAllLines(configFile.toPath());
        if (lines.isEmpty()) {
            log.error("Файл конфигурации пуст - {}", configFile.getPath());
            throw new WrongLinksFormatException("Файл конфигурации пуст");
        }
        for (String value : lines) {
            String[] temp = value.split("\\t");
            if (temp.length != 3) {
                log.error("Файл конфигурации имеет неверный формат", configFile.getPath());
                throw new WrongLinksFormatException("Файл конфигурации имеет неверный формат");
            }
        }
        return true;
    }
}
