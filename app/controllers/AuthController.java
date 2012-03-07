/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package controllers;

import models.Account;
import play.mvc.Before;
import play.mvc.Controller;

/**
 *
 * @author Arnaud Rolly
 */
public class AuthController extends Controller {

    public static final String SESSION_ACCESS_KEY = "access";

    private static final String RARGS_ACCOUNT_KEY = "account";
    
    @Before
    public static void checkAuth() {
        String access = session.get(SESSION_ACCESS_KEY);
        if (access == null) {
            Application.login();
        }
        Account account = Account.findByAccess(access);
        if (account == null) {
            Application.login();
        }
        renderArgs.put(RARGS_ACCOUNT_KEY, account);
    }
    
    protected static Account getAccount() {
        return renderArgs.get(RARGS_ACCOUNT_KEY, Account.class);
    }
}
