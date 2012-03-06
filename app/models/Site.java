/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package models;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;

/**
 *
 * @author Arnaud Rolly
 */
@Entity
public class Site extends Model {

    @ManyToOne
    public Galaxy galaxy;
    public String domain;
    public String name;

    public Site(Galaxy galaxy, String domain, String name) {
        this.galaxy = galaxy;
        this.domain = domain;
        this.name = name;
    }

    public List<Page> lastPages() {
        return Page.find("site = ? ORDER BY created DESC", this).fetch(25);
    }
    
    public Site() {
        
    }
    
    public static List<Site> findByGalaxy(Galaxy galaxy) {
        return Site.find("galaxy = ?", galaxy).fetch();
    }
    
    public static Site findByDomain(String domain) {
        return Site.find("domain = ?", domain).first();
    }
}
