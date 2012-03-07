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
import models.FeedEntry;
import models.Galaxy;

/**
 * Feeds management.
 * @author Arnaud Rolly
 */
public class Feeds extends AuthController {

    public static void index(Long galaxyId) {
        // Path check
        Galaxy galaxy = getGalaxy(galaxyId);
        // Action
        List<Feed> feeds = Feed.findByGalaxy(galaxy);
        render(galaxy, feeds);
    }

    public static void create(Long galaxyId) {
        // Path check
        Galaxy galaxy = getGalaxy(galaxyId);
        // Action
        render(galaxy);
    }

    public static void createPost(Long galaxyId, String name, String url) {
        // Path check
        Galaxy galaxy = getGalaxy(galaxyId);
        // Parameter check
        validation.required(name);
        validation.minSize(name, 4);
        validation.required(url);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            create(galaxyId);
        }
        // Action
        Feed feed = new Feed(galaxy, name, url);
        feed.save();
        index(galaxyId);
    }

    public static void read(Long galaxyId, Long feedId) {
        // Path check
        Galaxy galaxy = getGalaxy(galaxyId);
        Feed feed = getFeed(galaxy, feedId);
        // Action
        List<FeedEntry> entries = FeedEntry.findByFeed(feed);
        render(galaxy, feed, entries);
    }
}
