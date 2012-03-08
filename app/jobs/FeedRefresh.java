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
package jobs;

import com.sun.syndication.feed.synd.SyndContent;
import com.sun.syndication.feed.synd.SyndEntry;
import com.sun.syndication.feed.synd.SyndFeed;
import com.sun.syndication.io.SyndFeedInput;
import com.sun.syndication.io.XmlReader;
import java.net.URL;
import java.util.List;
import models.Feed;
import models.FeedEntry;
import play.jobs.Every;
import play.jobs.Job;

/**
 * Periodically refresh RSS feeds.
 * @author Arnaud Rolly
 */
@Every("1h")
public class FeedRefresh extends Job {

    @Override
    public void doJob() throws Exception {
        for (Feed feed : Feed.<Feed>findAll()) {
            updateFeed(feed);
        }
    }

    private void updateFeed(Feed feed) throws Exception {
        URL feedUrl = new URL(feed.url);
        SyndFeedInput input = new SyndFeedInput();
        SyndFeed feeed = input.build(new XmlReader(feedUrl));
        for (SyndEntry entry : (List<SyndEntry>) feeed.getEntries()) {
            String uri = entry.getUri();
            if (!FeedEntry.existsUri(uri)) {
                StringBuilder sbHtml = new StringBuilder();
                StringBuilder sbText = new StringBuilder();
                for (SyndContent content : (List<SyndContent>) entry.getContents()) {
                    if ("html".equals(content.getType())) {
                        sbHtml.append(content.getValue());
                    } else if ("text".equals(content.getType())) {
                        sbText.append(content.getValue());
                    }
                }
                FeedEntry feedEntry = new FeedEntry(feed, uri, entry.getPublishedDate(), entry.getTitle(), sbHtml.toString(), sbText.toString());
                feedEntry.save();
            }
        }
    }
}
