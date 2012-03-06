/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package controllers;

import java.util.List;
import models.Galaxy;
import models.Site;

/**
 *
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

    public static void createPost(Long galaxyId, String domain, String name) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if (galaxy == null) {
            notFound();
        }
        Site site = new Site(galaxy, domain, name);
        site.save();
        index(galaxyId);
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
