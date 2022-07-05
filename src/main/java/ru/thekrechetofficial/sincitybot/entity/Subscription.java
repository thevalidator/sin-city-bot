/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.entity;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Embeddable
public class Subscription implements Serializable {

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private SUBSCRIPTION_TYPE type;
    private int requests = 5;

    public Subscription() {
    }

    public Subscription(SUBSCRIPTION_TYPE type) {
        this.type = type;
    }
    

    public int getRequests() {
        return requests;
    }

    public void setRequests(int requests) {
        this.requests = requests;
    }

    public SUBSCRIPTION_TYPE getType() {
        return type;
    }

    public void setType(SUBSCRIPTION_TYPE type) {
        this.type = type;
    }    

}
