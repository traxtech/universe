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

    public static final String SESSION_CLEF_ACCESS = "access";

    private static final String RARGS_KEY_ACCOUNT = "account";
    
    @Before
    public static void checkAuth() {
        String access = session.get(SESSION_CLEF_ACCESS);
        if (access == null) {
            Application.login();
        }
        Account account = Account.findByAccess(access);
        if (account == null) {
            Application.login();
        }
        renderArgs.put(RARGS_KEY_ACCOUNT, account);
    }
    
    protected static Account getAccount() {
        return renderArgs.get(RARGS_KEY_ACCOUNT, Account.class);
    }
}
