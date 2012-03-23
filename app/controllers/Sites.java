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
import models.Galaxy;
import models.Site;

/**
 * Sites management.
 * @author Arnaud Rolly
 */
public class Sites extends AuthController {

    public static void index(Long galaxyId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        List<Site> sites = Site.findByGalaxy(galaxy);
        render(galaxy, sites);
    }

    public static void create(Long galaxyId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        render(galaxy);
    }

    public static void createPost(Long galaxyId, String domain, String name, String analyticsAccount, String adSenseRef, String disqusShortname) {
        // Path check
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        // Params check
        checkAuthenticity();
        validation.required(name);
        validation.minSize(name, 4);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create(galaxyId);
        }
        // Action
        Site site = new Site(galaxy, domain, name, analyticsAccount, adSenseRef, disqusShortname);
        site.save();
        index(galaxyId);
    }

    public static void update(Long galaxyId, Long siteId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if (site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        // Action
        if(!flash.contains("domain")) {
            flash("domain", site.domain);
            flash("name", site.name);
            flash("analyticsAccount", site.analyticsAccount);
            flash("adSenseRef", site.adSenseRef);
            flash("disqusShortname", site.disqusShortname);
        }
        render(galaxy, site);
    }
    
    public static void updatePost(Long galaxyId, Long siteId, String domain, String name, String analyticsAccount, String adSenseRef, String disqusShortname) {
        // Path check
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if (site == null) {
            notFound();
        }
        // Params check
        checkAuthenticity();
        validation.required(name);
        validation.minSize(name, 4);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create(galaxyId);
        }
        // Action
        site.domain = domain;
        site.name = name;
        site.analyticsAccount = analyticsAccount;
        site.adSenseRef = adSenseRef;
        site.disqusShortname = disqusShortname;
        site.save();
        read(galaxyId,siteId);
    }
    
    public static void read(Long galaxyId, Long siteId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        Site site = Site.findById(siteId);
        if (site == null || !site.galaxy.equals(galaxy)) {
            notFound();
        }
        render(galaxy, site);
    }
}
