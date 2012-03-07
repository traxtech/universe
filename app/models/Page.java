/*
 * Copyright (c) 2012 Arnaud Rolly
 * 
 * This file is part of Universe.
 *
 *   Universe is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Universe is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Universe.  If not, see <http://www.gnu.org/licenses/>.
 */
package models;

import com.petebevin.markdown.MarkdownProcessor;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Lob;
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

    @ManyToOne
    public Galaxy galaxy;
    @ManyToOne
    public Site site;
    @ManyToOne
    public Category category;
    @Temporal(TemporalType.TIMESTAMP)
    public Date created;
    @Temporal(TemporalType.TIMESTAMP)
    public Date updated;
    public String title;
    @Lob
    public String excerpt;
    @Lob
    public String htmlExcerpt;
    @Lob
    public String content;
    @Lob
    public String htmlContent;

    public Page(Category category, String title, String excerpt, String content) {
        this.galaxy = category.galaxy;
        this.site = category.site;
        this.category = category;
        this.created = new Date();
        this.updated = this.created;
        this.title = title;
        this.excerpt = excerpt;
        this.htmlExcerpt = new MarkdownProcessor().markdown(this.excerpt);
        this.content = content;
        this.htmlContent = new MarkdownProcessor().markdown(this.content);
    }

    public Page() {
    }

    public static List<Page> findByCategory(Category category) {
        return Page.find("category = ?", category).fetch();
    }
}
