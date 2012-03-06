/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package jobs;

import models.Account;
import play.jobs.Job;
import play.jobs.OnApplicationStart;

/**
 *
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
