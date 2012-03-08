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
package controllers;

import java.util.List;
import models.Account;
import models.Category;
import models.Hit;
import models.Page;
import models.Site;
import play.mvc.*;

/**
 * Renderer for all the sites of all the galaxies.
 * @author Arnaud Rolly
 */
public class Application extends Controller {

    private static Site getSite() {
        Site site = Site.findByDomain(request.host);
        if(site == null) {
            notFound();
        }
        return site;
    }
    
    public static void site() {
        Site site = getSite();
        Hit hit = new Hit(site, request);
        hit.save();
        List<Category> categories = Category.findRoot(site);
        render(site, categories);
    }

    public static void category(Long categoryId) {
        Site site = getSite();
        Category category = Category.findById(categoryId);
        Hit hit = new Hit(category, request);
        hit.save();
        List<Category> categories = Category.findRoot(site);
        if(category == null || !category.site.equals(site)) {
            site();
        }
        render(site, categories, category);
    }
    
    public static void page(Long pageId) {
        Site site = getSite();
        Page page = Page.findById(pageId);
        Hit hit = new Hit(page, request);
        hit.save();
        List<Category> categories = Category.findRoot(site);
        if(page == null || !page.site.equals(site)) {
            site();
        }
        render(site, categories, page);
    }
    
    public static void login() {
        render();
    }
    
    public static void loginPost(String login, String plainPassword) {
        checkAuthenticity();
        validation.required(login);
        validation.minSize(login, 2);
        validation.required(plainPassword);
        validation.minSize(plainPassword, 2);
        if (validation.hasErrors()) {
            params.flash();
            validation.keep();
            login();
        }
        Account account = Account.findByLogin(login);
        if (account == null) {
            validation.addError("login", "Invalid login and/or password");
            validation.addError("plainPassword", "Invalid login and/or password");
            params.flash();
            validation.keep();
            login();
        }
        if(!account.checkPassword(plainPassword)) {
            validation.addError("login", "Invalid login and/or password");
            validation.addError("plainPassword", "Invalid login and/or password");
            params.flash();
            validation.keep();
            login();
        }
        session.put(AuthController.SESSION_ACCESS_KEY, account.access);
        Galaxies.index();
    }
    
    public static void logout() {
        session.remove(AuthController.SESSION_ACCESS_KEY);
        Application.login();
    }
}