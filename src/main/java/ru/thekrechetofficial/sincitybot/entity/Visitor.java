/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.entity;

import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Entity
public class Visitor implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "telegram_id", unique = true)
    private String telegramId;
    @Column(name = "joined_stamp")
    private LocalDateTime joinedStamp;
    @Embedded
    private Subscription subscription;
    
    private ScoutQuery scoutQuery;

    public Visitor() {
    }

    public Visitor(String telegramId, LocalDateTime joinedStamp, Subscription subscription, ScoutQuery scoutQuery) {
        this.telegramId = telegramId;
        this.joinedStamp = joinedStamp;
        this.subscription = subscription;
        this.scoutQuery = scoutQuery;
    }

    public long getId() {
        return id;
    }

    public String getTelegramId() {
        return telegramId;
    }

    public LocalDateTime getJoinedStamp() {
        return joinedStamp;
    }

    public Subscription getSubscription() {
        return subscription;
    }

    public ScoutQuery getScoutQuery() {
        return scoutQuery;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setTelegramId(String telegramId) {
        this.telegramId = telegramId;
    }

    public void setJoinedStamp(LocalDateTime joinedStamp) {
        this.joinedStamp = joinedStamp;
    }

    public void setSubscription(Subscription subscription) {
        this.subscription = subscription;
    }

    
    // What if no setters (not needed here some of them) 
    

}
