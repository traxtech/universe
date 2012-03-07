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
package jobs;

import models.Account;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 * Initialization code : setup the admin account if needed.
 * @author Arnaud Rolly
 */
@OnApplicationStart
public class Init extends Job {

    @Override
    public void doJob() throws Exception {
        if (Account.count() == 0) {
            Account adminAccount = new Account("admin", "admin");
            adminAccount.save();
        }
    }
}
