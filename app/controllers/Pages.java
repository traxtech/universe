/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package controllers;

import java.util.List;
import models.Category;
import models.Galaxy;
import models.Page;
import models.Site;

/**
 *
 * @author Arnaud Rolly
 */
public class Pages extends AuthController {
    
    public static void index(Long galaxyId, Long siteId, Long categoryId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if(site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        Category category = Category.findById(categoryId);
        if(category == null || !category.site.equals(site)) {
            notFound();
        }
        List<Page> pages = Page.findByCategory(category);
        render(galaxy, site, category, pages);
    }
    
    public static void create(Long galaxyId, Long siteId, Long categoryId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if(site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        Category category = Category.findById(categoryId);
        if(category == null || !category.site.equals(site)) {
            notFound();
        }
        render(galaxy, site, category);
    }
    
    public static void createPost(Long galaxyId, Long siteId, Long categoryId, String title, String excerpt, String content) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if(site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        Category category = Category.findById(categoryId);
        if(category == null || !category.site.equals(site)) {
            notFound();
        }
        Page page = new Page(category, title, excerpt, content);
        page.save();
        index(galaxyId, siteId, categoryId);
    }
    
    public static void read(Long galaxyId, Long siteId, Long categoryId, Long pageId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if(site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        Category category = Category.findById(categoryId);
        if(category == null || !category.site.equals(site)) {
            notFound();
        }
        Page page = Page.findById(pageId);
        if(page == null || !page.category.equals(category)) {
            notFound();
        }
        render(galaxy, site, category, page);
    }
}
