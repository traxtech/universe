/*
 * Copyright (c) 2012 Arnaud Rolly
 * 
 * This file is part of Universe.
 *
 * Universe is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Universe is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Universe.  If not, see <http://www.gnu.org/licenses/>.
 */
package models;

import java.util.UUID;
import javax.persistence.Column;
import javax.persistence.Entity;
import org.jasypt.util.password.StrongPasswordEncryptor;
import play.db.jpa.Model;

/**
 * Account model.
 * @author Arnaud Rolly
 */
@Entity
public class Account extends Model {

    /**
     * Used to encrypt (salt+hash) and check passwords, from the Jasypt library.
     */
    private static final StrongPasswordEncryptor PWD_ENC = new StrongPasswordEncryptor();
    /**
     * Account login.
     */
    @Column(nullable = false, updatable = false, unique = true)
    public String login;
    /**
     * Account password, encrypted.
     */
    @Column(nullable = false)
    public String password;
    /**
     * Session token.
     */
    @Column(nullable = false, unique = true)
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

    public void setPlainPassword(String plainPassword) {
        this.password = PWD_ENC.encryptPassword(plainPassword);
    }

    public static Account findByAccess(String access) {
        return Account.find("access = ?", access).first();
    }

    public static Account findByLogin(String login) {
        return Account.find("login = ?", login).first();
    }
}
