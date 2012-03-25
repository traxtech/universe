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

import com.petebevin.markdown.MarkdownProcessor;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;

/**
 * Page model.
 * @author Arnaud Rolly
 */
@Entity
public class Page extends Model {

    /**
     * Galaxy that contains the page (calculated field).
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Galaxy galaxy;
    /**
     * Site that contains the page (calculated field).
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Site site;
    /**
     * Galaxy that contains the page.
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Category category;
    /**
     * Page creation date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false, updatable = false)
    public Date created;
    /**
     * Last page update date.
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(nullable = false)
    public Date updated;
    /**
     * Page title.
     */
    public String title;
    /**
     * Page excerpt in Markdown format.
     */
    @Column(columnDefinition = "TEXT")
    public String excerpt;
    /**
     * Page excerpt in Html format.
     */
    @Column(columnDefinition = "TEXT")
    public String htmlExcerpt;
    /**
     * Page content in Markdown format.
     */
    @Column(columnDefinition = "TEXT")
    public String content;
    /**
     * Page content in Html format.
     */
    @Column(columnDefinition = "TEXT")
    public String htmlContent;

    public Page(Category category, String title, String excerpt, String content) {
        this.galaxy = category.galaxy;
        this.site = category.site;
        this.category = category;
        this.created = new Date();
        this.title = title;
        this.excerpt = excerpt;
        this.content = content;
    }

    public String getCompactHtmlExcerpt() {
        if (htmlExcerpt != null && htmlExcerpt.startsWith("<p>") && htmlExcerpt.endsWith("</p>\n")) {
            return htmlExcerpt.substring(3, htmlExcerpt.length() - 5);
        } else {
            return htmlExcerpt;
        }
    }
    
    @Override
    public Page save() {
        if (updated == null) {
            updated = created;
        } else {
            updated = new Date();
        }
        MarkdownProcessor proc = new MarkdownProcessor();
        htmlExcerpt = proc.markdown(excerpt);
        htmlContent = proc.markdown(content);
        return super.save();
    }

    public Page() {
    }

    public static List<Page> findByCategory(Category category) {
        return Page.find("category = ?", category).fetch();
    }
}
