/*
 * Copyright (c) 2012 Arnaud Rolly
 * 
 * This file is part of Universe.
 *
 * Universe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Universe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Universe.  If not, see <http://www.gnu.org/licenses/>.
 */
package models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;

/**
 * Site model.
 * @author Arnaud Rolly
 */
@Entity
public class Site extends Model {

    /**
     * Galaxy that contains the site.
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Galaxy galaxy;
    /**
     * Domain name of the site (host).
     */
    @Column(nullable = false)
    public String domain;
    /**
     * Site name.
     */
    @Column(nullable = false)
    public String name;
    /**
     * Google Analytics account.
     */
    public String analyticsAccount;
    /**
     * Google AdSense account.
     */
    public String adSenseRef;
    /**
     * Disqus shortname (for comments).
     */
    public String disqusShortname;

    public Site(Galaxy galaxy, String domain, String name, String analyticsAccount, String adSenseRef, String disqusShortname) {
        this.galaxy = galaxy;
        this.domain = domain;
        this.name = name;
        this.analyticsAccount = analyticsAccount;
        this.adSenseRef = adSenseRef;
        this.disqusShortname = disqusShortname;
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
