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

import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Query;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.jsoup.Jsoup;
import play.db.jpa.JPA;
import play.db.jpa.Model;

/**
 * Feed entry model.
 * @author Arnaud Rolly
 */
@Entity
public class FeedEntry extends Model {

    /**
     * Galaxy that contains the feed entry (calculated field).
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Galaxy galaxy;
    /**
     * 
     */
    @ManyToOne
    @JoinColumn(nullable = false)
    public Feed feed;
    /**
     * Feed entry uri.
     */
    @Column(nullable = false, unique = true)
    public String uri;
    /**
     * Feed entry creation date (picked from the RSS feed).
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date created;
    /**
     * Feed entry title;
     */
    @Column(nullable = false)
    public String title;
    /**
     * Feed entry Html content.
     */
    @Column(columnDefinition = "TEXT")
    public String htmlContent;
    /**
     * Feed entry text content.
     */
    @Column(columnDefinition = "TEXT")
    public String textContent;
    /**
     * Determines if the feed entry is still visible to the user.
     * An invisible feed entry can be scheduled for deletion.
     */
    public boolean visible;

    public FeedEntry(Feed feed, String uri, Date created, String title, String htmlContent, String textContent) {
        this.galaxy = feed.galaxy;
        this.feed = feed;
        this.uri = uri.toLowerCase();
        this.created = created;
        this.title = title;
        this.htmlContent = htmlContent;
        this.textContent = textContent;
        this.visible = true;
    }

    @Override
    public FeedEntry save() {
        if ((textContent == null || textContent.isEmpty()) && htmlContent != null && !htmlContent.isEmpty()) {
            this.textContent = Jsoup.parse(htmlContent).text();
        }
        return super.save();
    }

    public FeedEntry() {
    }

    public static boolean existsUri(String uri) {
        Query q = JPA.em().createQuery("SELECT COUNT(id) FROM FeedEntry WHERE uri = :uri");
        q.setParameter("uri", uri.toLowerCase());
        return (Long) q.getSingleResult() > 0;
    }

    public static List<FeedEntry> findByFeed(Feed feed) {
        return find("feed = ? AND visible = TRUE", feed).fetch();
    }
}
