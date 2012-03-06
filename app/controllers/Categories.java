/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package controllers;

import java.util.List;
import models.Category;
import models.Galaxy;
import models.Site;

/**
 *
 * @author Arnaud Rolly
 */
public class Categories extends AuthController {
    
    public static void index(Long galaxyId, Long siteId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if(site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        List<Category> categories = Category.findRoot(site);
        render(galaxy, site, categories);
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
        Category category = null;
        if(categoryId != null) {
            category = Category.findById(categoryId);
        }
        render(galaxy, site, category);
    }
    
    public static void createPost(Long galaxyId, Long siteId, Long categoryId, String name, String description) {
        // Path check
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if(site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        Category parent = null;
        if(categoryId != null) {
            parent = Category.findById(categoryId);
            if(parent == null || !parent.site.equals(site)) {
                notFound();
            }
        }
        // Params check
        checkAuthenticity();
        validation.required(name);
        validation.minSize(name, 4);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create(galaxyId, siteId, categoryId);
        }
        // Action
        Category category = new Category(site, parent, name, description);
        category.save();
        if(categoryId == null) {
            index(galaxyId, siteId);
        } else {
            read(galaxyId, siteId, categoryId);
        }
    }
    
    public static void read(Long galaxyId, Long siteId, Long categoryId) {
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
        List<Category> categories = Category.findChildren(category);
        render(galaxy, site, category, categories);
    }
}
