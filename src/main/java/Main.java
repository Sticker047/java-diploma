import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String PATH_IN = "C:\\Users\\gagil\\IdeaProjects\\java-diploma\\data\\pdfs";
    public static final String PATH_OUT = "C:\\Users\\gagil\\IdeaProjects\\java-diploma\\data\\converted";
    public static final List<PdfDocument> pdfDocumentList = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        // создаём конфиг
        LinksSuggester linksSuggester = new LinksSuggester(new File("data/config"));

        // перебираем пдфки в data/pdfs
        File dir = new File(PATH_IN);
        for (File fileIn : dir.listFiles()) {
            File fileOut = new File(PATH_OUT + "\\" + fileIn.getName());
            //pdfDocumentList.add(new PdfDocument(new PdfReader(fileIn), new PdfWriter(fileOut)));
        }

        // для каждой пдфки создаём новую в data/converted

        // перебираем страницы pdf

        // если в странице есть неиспользованные ключевые слова, создаём новую страницу за ней

        // вставляем туда рекомендуемые ссылки из конфига
    }
}
