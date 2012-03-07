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
import models.Feed;

/**
 * Feeds management.
 * @author Arnaud Rolly
 */
public class Feeds extends AuthController {
    
    public static void index() {
        List<Feed> feeds = Feed.findAll();
        render(feeds);
    }
    
    public static void create() {
        render();
    }

    public static void createPost(String name, String url) {
        validation.required(name);
        validation.minSize(name, 4);
        validation.required(url);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create();
        }
        Feed feed = new Feed(name, url);
        feed.save();
        index();
    }

}
