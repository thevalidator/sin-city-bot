/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.entity.ad;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Entity
@Table(name="nlads")
public class NLAd extends AbstractAd implements Serializable {
    
    private final static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public NLAd() {
        super();
    }

    public NLAd(String offerId, String title, String text, String place, String contact, LocalDateTime timestamp, Gender creator) {
        super(offerId, title, text, place, contact, timestamp, creator);
    }
    

    @Override
    public String toString() {
        
        String s = "✅ [NL] "+ this.getTitle() + "\n\t\uD83D\uDD39 " + this.getText() + "\n\t\uD83D\uDD39 " +
                this.getContact() + "\n\t\uD83D\uDD39 " + this.getTimestamp()
                .format(formatter) + " опубликовал(а) " +
                getCreator().getTextName() + "\n\n";

        return s;
    }
    

    @Override
    public int compareTo(AbstractAd ad) {
        
        int result = this.getTimestamp().compareTo(ad.getTimestamp());
        if (result == 0) {
            result = this.getTitle().compareTo(ad.getTitle());
        }
        return result;
        
    }
}
