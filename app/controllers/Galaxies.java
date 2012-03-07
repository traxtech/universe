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
import models.Galaxy;

/**
 * Galaxies management.
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
