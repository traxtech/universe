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
package controllers;

import java.util.List;
import models.Category;
import models.Galaxy;
import models.Site;

/**
 * Categories management.
 * @author Arnaud Rolly
 */
public class Categories extends AuthController {

    public static void index(Long galaxyId, Long siteId) {
        Galaxy galaxy = getGalaxy(galaxyId);
        Site site = Site.findById(siteId);
        if (site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        List<Category> categories = Category.findRoot(site);
        render(galaxy, site, categories);
    }

    public static void create(Long galaxyId, Long siteId, Long categoryId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        Site site = getSite(galaxy, siteId);
        Category category = null;
        if (categoryId != null) {
            category = Category.findById(categoryId);
        }
        render(galaxy, site, category);
    }

    public static void createPost(Long galaxyId, Long siteId, Long categoryId, String name, String description) {
        // Path check
        Galaxy galaxy = getGalaxy(galaxyId);
        Site site = getSite(galaxy, siteId);
        Category parent = null;
        if (categoryId != null) {
            parent = Category.findById(categoryId);
            if (parent == null || !parent.site.equals(site)) {
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
        if (categoryId == null) {
            index(galaxyId, siteId);
        } else {
            read(galaxyId, siteId, categoryId);
        }
    }

    public static void read(Long galaxyId, Long siteId, Long categoryId) {
        Galaxy galaxy = getGalaxy(galaxyId);
        Site site = getSite(galaxy, siteId);
        Category category = getCategory(site, categoryId);
        List<Category> children = Category.findChildren(category);
        render(galaxy, site, category, children);
    }
}
