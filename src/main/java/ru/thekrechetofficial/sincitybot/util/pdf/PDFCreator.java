/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.util.pdf;

import com.lowagie.text.Chunk;
import com.lowagie.text.Document;
import com.lowagie.text.Element;
import com.lowagie.text.Font;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.BaseFont;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTemplate;
import com.lowagie.text.pdf.PdfWriter;
import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import ru.thekrechetofficial.sincitybot.entity.ad.AbstractAd;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
public class PDFCreator {
    
    public static ByteArrayOutputStream createAdsPdf(List<? extends AbstractAd> adsList, String query, String userId, long total) {

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        
        
        //595 pageWidth
        //580 tableWidth /2= 290
        Rectangle rectangle = new Rectangle(PageSize.A4);
        try (Document document = new Document(rectangle, 10f,10f,30,30)) {
            
            final PdfWriter writer = PdfWriter.getInstance(document, outputStream);
            document.open();
            BackgroundHandler handler = new BackgroundHandler();
            writer.setPageEvent(handler);

            document.open();
            document.addAuthor("Sin City Nights");
            document.addCreationDate();
            document.addCreator("@sincitynights_bot");
            document.addTitle("Объявления для " + userId);
            document.addSubject("Выборка объявлений по запросу");

            BaseFont baseFont = BaseFont.createFont("NotoSans-Regular.ttf",
                    BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
            Font font = new Font(baseFont, 10, Font.NORMAL, Color.WHITE);
            Font fontBold = new Font(baseFont, 10, Font.BOLD, Color.WHITE);
            Font fontData = new Font(baseFont, 10, Font.BOLD, new Color(0xEE, 0xE9, 0xE9));

            document.newPage();

            PdfContentByte cbf = writer.getDirectContent();
            PdfTemplate template1 = cbf.createTemplate(550.f, 100.f);

            template1.beginText();
            template1.setFontAndSize(baseFont, 16.f);
            template1.setTextMatrix(0,20);
            template1.setRGBColorFill(0xFF, 0xFF, 0xFF);
            template1.setTextRise(3.f);
            template1.showText("ОТЧЁТ ПО ЗАПРОСУ: " + query);
            template1.endText();

            template1.beginText();
            template1.setFontAndSize(baseFont, 14.f);
            template1.setTextMatrix(0,0);
            template1.setTextRise(3.f);
            template1.showText("Показано/найдено объявлений: " + adsList.size() + "/" + total);
            template1.endText();
            template1.fill();
            template1.sanityCheck();

            template1.beginText();
            template1.setFontAndSize(baseFont, 10.f);
            template1.setTextMatrix(80,50);
            template1.setTextRise(3.f);
            template1.showText("[" + userId + "] Отчет сгенерирован в " +
                    LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy в HH:mm")) +
                    " телеграм ботом @sincitynights_bot");
            template1.endText();
            template1.fill();
            template1.sanityCheck();

            cbf.addTemplate(template1, 35, document.getPageSize().getHeight() - 76);

            document.add(new Chunk(" "));
            Paragraph firstPar = new Paragraph(" ");
            firstPar.setSpacingBefore(30.f);
            document.add(firstPar);


            for (AbstractAd ad: adsList) {
                Paragraph adsPar = new Paragraph();
                adsPar.setSpacingBefore(15.f);
                PdfPTable adTable = new PdfPTable(1);
                adTable.getDefaultCell().setBackgroundColor(Color.BLACK);
                adTable.getDefaultCell().setBorderColor(Color.GRAY);
                adTable.getDefaultCell().setBorderWidth(1.1f);

                adTable.setLockedWidth(true);
                adTable.setTotalWidth(550.f);
                PdfPTable mainTable = new PdfPTable(2);
                mainTable.getDefaultCell().setBorder(0);

                mainTable.getDefaultCell().setBorderWidth(1.5f);
                mainTable.getDefaultCell().setBorderColor(Color.GRAY);

                PdfPCell dataCell = new PdfPCell();
                dataCell.setBorder(Rectangle.LEFT | Rectangle.TOP);
                dataCell.setPaddingTop(3.0f);
                dataCell.setPaddingLeft(6.0f);
                dataCell.setPaddingBottom(6.0f);
                dataCell.setHorizontalAlignment(Element.ALIGN_LEFT);
                dataCell.setVerticalAlignment(Element.ALIGN_MIDDLE);

                Chunk author = new Chunk("Автор: " + ad.getCreator().getTextName());
                author.setFont(fontData);
                dataCell.setPhrase(new Phrase(author));
                mainTable.addCell(dataCell);

                dataCell.setBorder(Rectangle.RIGHT | Rectangle.TOP);
                dataCell.setPaddingLeft(0.0f);
                String website = " на Intimcity";

                Chunk date = new Chunk("Опубликовано: " +
                        ad.getTimestamp().format(DateTimeFormatter.ofPattern("dd-MM-yyyy в HH:mm")) +
                        website);
                date.setFont(fontData);
                dataCell.setPhrase(new Phrase(date));
                mainTable.addCell(dataCell);

                dataCell.setBorder(Rectangle.LEFT | Rectangle.BOTTOM);
                dataCell.setPaddingLeft(6.0f);
                Chunk contact = new Chunk("Контакты : " + ad.getContact());
                contact.setFont(fontData);
                dataCell.setPhrase(new Phrase(contact));
                mainTable.addCell(dataCell);

                dataCell.setBorder(Rectangle.RIGHT | Rectangle.BOTTOM);
                dataCell.setPaddingLeft(0.0f);
                Chunk place = new Chunk("Район : " + ad.getPlace());
                place.setFont(fontData);
                dataCell.setPhrase(new Phrase(place));
                mainTable.addCell(dataCell);

                PdfPCell cell = new PdfPCell();
                cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT);
                cell.setColspan(2);
                cell.setPaddingTop(10.0f);
                cell.setPaddingLeft(10.0f);
                cell.setPaddingRight(10.0f);
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                Chunk title = new Chunk(ad.getTitle());
                title.setFont(fontBold);
                Phrase titleText = new Phrase(title);
                cell.setPhrase(titleText);
                mainTable.addCell(cell);
                cell.setPaddingBottom(10.0f);

                Chunk adText = new Chunk(ad.getText());
                adText.setFont(font);
                cell.setBorder(Rectangle.LEFT | Rectangle.RIGHT | Rectangle.BOTTOM);
                cell.setPhrase(new Phrase(adText));
                cell.setHorizontalAlignment(Element.ALIGN_JUSTIFIED);
                mainTable.addCell(cell);

                adTable.addCell(mainTable);
                adsPar.add(adTable);

                document.add(adsPar);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        
        return outputStream;
    }

}
