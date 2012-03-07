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
package controllers;

import java.util.List;
import models.Category;
import models.Galaxy;
import models.Page;
import models.Site;

/**
 * Pages management.
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
        // Path check
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
        // Params check
        checkAuthenticity();
        validation.required(title);
        validation.minSize(title, 4);
        validation.required(excerpt);
        validation.minSize(excerpt, 10);
        validation.required(content);
        validation.minSize(content, 100);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create(galaxyId, siteId, categoryId);
        }
        // Action
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
