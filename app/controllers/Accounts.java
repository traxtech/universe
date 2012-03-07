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

/**
 * Accounts management.
 * @author Arnaud Rolly
 */
public class Accounts extends AuthController {
    
    public static void index() {
        render();
    }
    
    public static void update() {
        render();
    }
    
    public static void updatePost(String oldPassword, String newPassword, String confirmation) {
        // Params check
        validation.required(oldPassword);
        validation.required(newPassword);
        validation.minSize(newPassword, 6);
        validation.required(confirmation);
        validation.equals(confirmation, newPassword);
        Account account = getAccount();
        if(!account.checkPassword(oldPassword)) {
            validation.addError("oldPassword", "Incorrect");
        }
        if(validation.hasErrors()) {
            params.flash();
            validation.keep();
            update();
        }
        account.setPlainPassword(newPassword);
        account.save();
        index();
    }
}
