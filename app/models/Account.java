/*
 * Copyright (c) 2012 Arnaud Rolly
 */
package models;

import java.util.UUID;
import javax.persistence.Entity;
import org.jasypt.util.password.StrongPasswordEncryptor;
import play.db.jpa.Model;

/**
 *
 * @author Arnaud Rolly
 */
@Entity
public class Account extends Model {
    
    private static final StrongPasswordEncryptor PWD_ENC = new StrongPasswordEncryptor();
    
    public String login;
    public String password;
    public String access;
    
    public Account(String login, String plainPassword) {
        this.login = login;
        this.password = PWD_ENC.encryptPassword(plainPassword);
        this.access = UUID.randomUUID().toString();
    }

    public Account() {
    }
    
    public boolean checkPassword(String plainPassword) {
        return PWD_ENC.checkPassword(plainPassword, password);
    }
    
    public static Account findByAccess(String access) {
        return Account.find("access = ?", access).first();
    }
    
    public static Account findByLogin(String login) {
        return Account.find("login = ?", login).first();
    }
}
