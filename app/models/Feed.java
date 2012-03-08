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
package models;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import play.db.jpa.Model;

/**
 * RSS feed model.
 * @author Arnaud Rolly
 */
@Entity
public class Feed extends Model {

    /**
     * Galaxy that contains the feed.
     */
    @ManyToOne
    @JoinColumn(nullable = false, updatable = false)
    public Galaxy galaxy;
    /**
     * Feed name.
     */
    @Column(nullable = false)
    public String name;
    /**
     * Feed URL.
     */
    @Column(nullable = false)
    public String url;

    public Feed(Galaxy galaxy, String name, String url) {
        this.galaxy = galaxy;
        this.name = name;
        this.url = url;
    }

    public Feed() {
    }

    public static List<Feed> findByGalaxy(Galaxy galaxy) {
        return find("galaxy = ? ORDER BY name", galaxy).fetch();
    }
}
