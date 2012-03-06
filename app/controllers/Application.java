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
        Account account = Account.findByLogin(login);
        if (account == null) {
            Logger.info("NO ACCOUNT " + login);
            login();
        }
        if(!account.checkPassword(plainPassword)) {
            Logger.info("BAD PASSWORD");
            login();
        }
        session.put(AuthController.SESSION_CLEF_ACCESS, account.access);
        Galaxies.index();
    }
}