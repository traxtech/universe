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
import javax.persistence.Entity;
import play.db.jpa.Model;

/**
 * Galaxy model.
 * @author Arnaud Rolly
 */
@Entity
public class Galaxy extends Model {

    public String name;

    public Galaxy(String name) {
        this.name = name;
    }

    public Galaxy() {
    }

    public static List<Galaxy> allOrdered() {
        return Galaxy.find("ORDER BY name").fetch();
    }
}
