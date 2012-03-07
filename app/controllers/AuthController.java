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
package controllers;

import models.Account;
import play.mvc.Before;
import play.mvc.Controller;

/**
 * Controller that restrict the access to the logged-in users.
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
