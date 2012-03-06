/*
 * Copyright (c) 2012 Arnaud Rolly
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
 *
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
