/*
 * The Krechet Software
 */

package ru.thekrechetofficial.sincitybot.entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;

/**
 * @author theValidator <the.validator@yandex.ru>
 */
@Embeddable
public class ScoutQuery implements Serializable {
    
    @ElementCollection
    @Column(name="offers")
    private final List<String> queryOffers = new ArrayList<>();
    @Column(name="query_stamp")
    private String queryStamp;

    public ScoutQuery() {
    }
    
    public List<String> getQueryOffers() {
        return queryOffers;
    }
    
    public void createQueryOffers(List<String> offers) {
        this.queryOffers.clear();
        this.queryOffers.addAll(offers);
    }

    public String getQueryStamp() {
        return queryStamp;
    }

    public void setQueryStamp(String queryStamp) {
        this.queryStamp = queryStamp;
    }
    

}
