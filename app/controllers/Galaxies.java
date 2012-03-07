/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package controllers;

import java.util.List;
import models.Galaxy;

/**
 *
 * @author Arnaud Rolly
 */
public class Galaxies extends AuthController {

    public static void index() {
        List<Galaxy> galaxies = Galaxy.allOrdered();
        render(galaxies);
    }

    public static void create() {
        render();
    }

    public static void createPost(String name) {
        // Params check
        checkAuthenticity();
        validation.required(name);
        validation.minSize(name, 4);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create();
        }
        // Action
        Galaxy galaxy = new Galaxy(name);
        galaxy.save();
        index();
    }
    
    public static void read(Long galaxyId) {
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        render(galaxy);
    }
}