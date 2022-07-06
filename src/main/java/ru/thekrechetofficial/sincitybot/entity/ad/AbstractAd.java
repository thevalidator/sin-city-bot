/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.entity.ad;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@MappedSuperclass
public abstract class AbstractAd implements Comparable<AbstractAd>, Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "offerid")
    private String offerId;
    private String title;
    @Column(columnDefinition="TEXT", name = "txt")
    private String text;
    private String place;
    @Column(columnDefinition="TEXT")
    private String contact;
    @Column(name = "created_on")
    private LocalDateTime timestamp;
    @Enumerated(EnumType.STRING)
    private Gender creator;

    public AbstractAd() {
    }

    public AbstractAd(String offerId,
                        String title,
                        String text,
                        String place,
                        String contact,
                        LocalDateTime timestamp,
                        Gender creator) {
        
        this.offerId = offerId;
        this.title = title;
        this.text = text;
        this.place = place;
        this.contact = contact;
        this.timestamp = timestamp;
        this.creator = creator;
    }

    public long getId() {
        return id;
    }
    
    public String getOfferId() {
        return offerId;
    }

    public String getTitle() {
        return title;
    }

    public String getText() {
        return text;
    }

    public String getPlace() {
        return place;
    }

    public String getContact() {
        return contact;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public Gender getCreator() {
        return creator;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setOfferId(String offerId) {
        this.offerId = offerId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public void setCreator(Gender creator) {
        this.creator = creator;
    }

}
