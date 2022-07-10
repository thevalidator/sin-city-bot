/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.util.pdf;

import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.Image;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfGState;
import com.lowagie.text.pdf.PdfWriter;
import com.lowagie.text.pdf.events.PdfPageEventForwarder;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
public class BackgroundHandler extends PdfPageEventForwarder {
    
    Image label;

    public BackgroundHandler() {
        
        try {
            label = Image.getInstance("classpath:bck.jpg");
        } catch (BadElementException | IOException ex) {
            //TODO: log4j2
            Logger.getLogger(BackgroundHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }

    @Override
    public void onEndPage(PdfWriter writer, Document document) {
        super.onStartPage(writer, document);
        PdfContentByte canvas = writer.getDirectContentUnder();
        label.scaleToFit(document.getPageSize().getWidth(), document.getPageSize().getHeight());
        label.setAlignment(Image.UNDERLYING);
        label.setAbsolutePosition(0,0);PdfContentByte cbu = writer.getDirectContentUnder();
        cbu.setRGBColorFill(0x1E, 0x1E, 0x1E); //0x00, 0x00, 0x00
        cbu.rectangle(0.0f,  0.0f, document.getPageSize().getWidth(), document.getPageSize().getHeight());
        cbu.fill();
        cbu.sanityCheck();
        canvas.saveState();
        PdfGState state = new PdfGState();
        state.setFillOpacity(0.15f);
        canvas.setGState(state);
        canvas.addImage(label);

        canvas.restoreState();
    }

}
