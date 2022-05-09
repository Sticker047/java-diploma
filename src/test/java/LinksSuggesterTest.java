import exceptions.WrongLinksFormatException;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LinksSuggesterTest {

    private final String CONFIG_FILES = "C:\\Users\\gagil\\IdeaProjects\\java-diploma\\src\\test\\resources\\configFiles\\";
    private final String CONFIG_VALID = CONFIG_FILES + "configValid";
    private final String CONFIG_INVALID = CONFIG_FILES + "configInvalid";

    @Test
    public void testIsConfigValid1() throws IOException {
        File configFile = new File(CONFIG_VALID);
        Assert.assertTrue(LinksSuggester.isConfigValid(configFile));
    }

    @Test
    public void testIsConfigValid2(){
        File configFile = new File(CONFIG_INVALID);
        Throwable thrown = assertThrows(WrongLinksFormatException.class, () -> LinksSuggester.isConfigValid(configFile));
        Assert.assertNotNull(thrown.getMessage());
    }

    @Test
    public void testGetSuggests() throws IOException {
        LinksSuggester linksSuggester = new LinksSuggester(new File(CONFIG_VALID));
        String text = "\n" +
                "1/19/22, 11:23 AM ☕ Разбираемся, почему в Java утекает память несмотря на сборщик мусора\n" +
                "Больше информации по Java тут\n" +
                "26 ноября 2021\n" +
                "Java 1 \uD83D\uDD25 1 \uD83D\uDCA7 0 \uD83D\uDCA9 0\n" +
                "☕ Разбираемся, почему в Java утекает память несмотря\n" +
                "на сборщик мусора\n" +
                "Filipp Voronov\n" +
                "Telegram: @ voronov\n" +
                "Сборщик мусора облегчает написание кода и справляется с основными проблемами, но\n" +
                "не гарантирует полного отсутствия утечек памяти. Изучите базовые принципы его\n" +
                "работы, чтобы понять, какими видами мусора он заниматься не будет.\n" +
                "3 1\n" +
                "Наш сайт использует файлы cookie для вашего максимального\n" +
                "удобства. Пользуясь сайтом, вы даете свое согласие с условиями Согласен\n" +
                "пользования cookie\n" +
                "https://proglib.io/p/razbiraemsya-pochemu-v-java-utekaet-pamyat-nesmotrya-na-sborshchik-musora-2021-11-26 1/10";


        List<Suggest> suggestList = new ArrayList<>();
        suggestList.add(new Suggest("java", "The Best Java course", "http://example.org/java"));

        Assert.assertEquals(suggestList, linksSuggester.getSuggests(text, 0));

    }
}