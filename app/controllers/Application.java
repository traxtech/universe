package controllers;

import java.util.List;
import models.Account;
import models.Category;
import models.Page;
import models.Site;
import play.Logger;
import play.mvc.*;

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
        List<Category> categories = Category.findRoot(site);
        render(site, categories);
    }

    public static void category(Long categoryId) {
        Site site = getSite();
        List<Category> categories = Category.findRoot(site);
        Category category = Category.findById(categoryId);
        if(category == null || !category.site.equals(site)) {
            site();
        }
        render(site, categories, category);
    }
    
    public static void page(Long pageId) {
        Site site = getSite();
        List<Category> categories = Category.findRoot(site);
        Page page = Page.findById(pageId);
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
        session.put(AuthController.SESSION_CLEF_ACCESS, account.access);
        Galaxies.index();
    }
}