/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package controllers;

import models.Account;
import play.data.validation.Validation;

/**
 *
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
