/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package models;

import java.util.List;
import javax.persistence.Entity;
import play.db.jpa.Model;

/**
 *
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
