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

import models.Feed;
import models.FeedEntry;
import models.Galaxy;

/**
 *
 * @author arnaud
 */
public class FeedEntries extends AuthController {

    public static void hideAjax(Long galaxyId, Long feedId, Long feedEntryId) {
        // Path check
        Galaxy galaxy = Galaxy.findById(galaxyId);
        if(galaxy == null) {
            notFound();
        }
        Feed feed = Feed.findById(feedId);
        if(feed == null) {
            notFound();
        }
        FeedEntry entry = FeedEntry.findById(feedEntryId);
        if (entry == null || !entry.feed.equals(feed)) {
            notFound();
        }
        // Action
        entry.visible = false;
        entry.save();
        ok();
    }
}
