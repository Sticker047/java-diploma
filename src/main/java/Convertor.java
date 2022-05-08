import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.*;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.annot.PdfLinkAnnotation;
import com.itextpdf.kernel.pdf.canvas.parser.PdfTextExtractor;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.element.*;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Getter
@Slf4j
public class Convertor {

    private final File fileIn;
    private final File fileOut;
    private final LinksSuggester linksSuggester;
    private final List<Suggest> suggestList = new ArrayList<>();

    public Convertor(File fileIn, LinksSuggester linksSuggester) throws FileNotFoundException {
        this.fileIn = fileIn;
        if (!fileIn.exists()) {
            log.error("Файл не найден - {}", fileIn.getPath());
            throw new FileNotFoundException("Файл не найден");
        }
        fileOut = new File(Main.PATH_OUT + "\\" + fileIn.getName());

        log.info("Файл для обработки получен {}", fileIn.getPath());
        this.linksSuggester = linksSuggester;
    }


    public void Converting() {
        try (PdfDocument pdfDocument = new PdfDocument(new PdfReader(fileIn), new PdfWriter(fileOut))) {
            log.info("Файл создан {}", fileOut.getPath());
            int numberOfPages = pdfDocument.getNumberOfPages();
            log.info("Редактирование файла, число страниц - {}", numberOfPages);
            for (int i = 1; i < numberOfPages; i++) {
                PdfPage page = pdfDocument.getPage(i);
                String text = PdfTextExtractor.getTextFromPage(page);
                List<Suggest> suggests = linksSuggester.getSuggests(text, i);
                addNewRec(suggests);
                if (!suggests.isEmpty()) {
                    PdfPage newPage = pdfDocument.addNewPage(++i);
                    appendSuggestsBoxToPage(newPage, suggests);
                    numberOfPages++;
                }
            }
            log.info("Файл отредактирован, число страниц - {}", numberOfPages++);
        } catch (Exception ex) {
            log.info("Не удалось отредактировать файл - {}", fileIn.getPath());
            ex.printStackTrace();
        }
    }

    public void addNewRec(List<Suggest> suggestsInNewPage) {
        for (int i = 0; i < suggestsInNewPage.size(); i++) {
            Suggest suggest = suggestsInNewPage.get(i);
            if (suggestList.contains(suggest)) {
                suggestsInNewPage.remove(i--);
            } else {
                suggestList.add(suggest);
            }
        }
    }

    private void appendSuggestsBoxToPage(PdfPage page, List<Suggest> suggests) {
        Rectangle rectangle = new Rectangle(page.getPageSize()).moveRight(10).moveDown(10);
        Canvas canvas = new Canvas(page, rectangle);
        Paragraph paragraph = new Paragraph("Suggestions:\n");
        paragraph.setFontSize(25);
        for (Suggest suggest : suggests) {
            PdfAction action = PdfAction.createURI(suggest.getUrl());
            PdfLinkAnnotation annotation = new PdfLinkAnnotation(rectangle);
            annotation.setAction(action);
            Link link = new Link(suggest.getTitle(), annotation);
            paragraph.add(link.setUnderline());
            paragraph.add("\n");
        }
        canvas.add(paragraph);

    }
}
