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
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import play.db.jpa.Model;

/**
 * Category model.
 * @author Arnaud Rolly
 */
@Entity
public class Category extends Model {

    @ManyToOne
    public Galaxy galaxy;
    @ManyToOne
    public Site site;
    @ManyToOne
    public Category parent;
    @Temporal(TemporalType.TIMESTAMP)
    public Date creation;
    public String name;
    @Lob
    public String description;
    @Lob
    public String htmlDescription;

    public Category(Site site, Category parent, String name, String description) {
        this.galaxy = site.galaxy;
        this.site = site;
        this.parent = parent;
        this.creation = new Date();
        this.name = name;
        this.description = description;
        this.htmlDescription = new MarkdownProcessor().markdown(this.description);
    }

    public List<Category> children() {
        return Category.find("parent = ? ORDER BY name", this).fetch();
    }

    public List<Page> lastPages() {
        return Page.find("category = ? ORDER BY created DESC", this).fetch(25);
    }

    public Category() {
    }

    public static List<Category> findRoot(Site site) {
        return Category.find("parent = NULL AND site = ? ORDER BY name", site).fetch();
    }

    public static List<Category> findChildren(Category category) {
        return Category.find("parent=? AND site = ? ORDER BY name", category, category.site).fetch();
    }
}
