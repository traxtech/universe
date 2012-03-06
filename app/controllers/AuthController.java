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
    }
}
