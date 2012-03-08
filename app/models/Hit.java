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

import java.util.Calendar;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.JPA;
import play.db.jpa.Model;
import play.mvc.Http.Header;
import play.mvc.Http.Request;

/**
 *
 * @author Arnaud Rolly
 */
@Entity
public class Hit extends Model {

    /**
     * Galaxy that contains the site where the hit occured.
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Galaxy galaxy;
    /**
     * Site where the hit occured.
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Site site;
    /**
     * Category where the hit occured.
     */
    @ManyToOne
    @JoinColumn(updatable = false)
    public Category category;
    /**
     * Page whete the hit occured.
     */
    @ManyToOne
    @JoinColumn(updatable = false)
    public Page page;
    /**
     * Hit date, extracted from the Http request.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    public Date created;
    /**
     * Hit host, extracted from the Http request.
     */
    @Column(nullable = false, updatable = false)
    public String host;
    /**
     * Hit path, extracted from the Http request.
     */
    @Column(nullable = false, updatable = false)
    public String path;
    /**
     * Hit referer, extracted from the Http request (optional).
     */
    @Column(updatable = false)
    public String referer;
    /**
     * Hit user agent, extracted from the Http request (optional).
     */
    @Column(updatable = false)
    public String userAgent;

    public Hit(Site site, Request request) {
        this.galaxy = site.galaxy;
        this.site = site;
        this.created = request.date;
        this.host = request.host;
        this.path = request.path;
        Header referrerHeader = request.headers.get("referer");
        if (referrerHeader != null) {
            this.referer = referrerHeader.value();
        }
        Header userAgentHeader = request.headers.get("user-agent");
        if (userAgentHeader != null) {
            this.userAgent = userAgentHeader.value();
        }
    }

    public Hit(Category category, Request request) {
        this(category.site, request);
        this.category = category;
    }

    public Hit(Page page, Request request) {
        this(page.category, request);
        this.page = page;
    }

    public Hit() {
    }

    public static Long today(Site site) {
        Query q = JPA.em().createQuery("SELECT COUNT(id) FROM Hit WHERE site = :site AND created > :startCount");
        q.setParameter("site", site);
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        q.setParameter("startCount", cal.getTime());
        return (Long) q.getSingleResult();
    }
}
