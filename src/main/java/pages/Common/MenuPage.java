package pages.Common;


import org.openqa.selenium.By;
import org.testng.Assert;
import properties.Pages;
import setup.base.BasePage;
import setup.elements.Element;
import setup.logger.Log;


public class MenuPage extends BasePage {

//region LOCATORS

    public static Element HomeMenu = new Element("HomeMenu", By.id("nav-home"));
    public static Element ShopMenu = new Element("ShopMenu", By.id("nav-shop"));
    public static Element ContactMenu = new Element("ContactMenu", By.id("nav-contact"));
    public static Element LoginMenu = new Element("LoginMenu", By.id("nav-login"));
    public static Element CartMenu = new Element("CartMenu", By.id("nav-cart"));
    public static Element CartTotalLbl = new Element("CartTotalLbl", By.cssSelector("span.cart-count"));


//endregion


//region PAGE ACTIONS

    /**
     * Navigate to specified screen
     * @param page
     */
    public static void GoToPage(Pages page) {
        Element MENU;
        String MODULE;

        switch(page) {
            case HOME:
                MENU = HomeMenu;
                MODULE = "#/home";
                break;

            case SHOP:
                MENU = ShopMenu;
                MODULE = "#/shop";
                break;

            case CONTACT:
                MENU = ContactMenu;
                MODULE = "#/contact";
                break;

            case LOGIN:
                MENU = LoginMenu;
                MODULE = "#";
                break;

            case CART:
                MENU = CartMenu;
                MODULE = "/cart";
                break;

            default:
                MENU = null;
                MODULE = null;
                Log.warn("Page not found");
        }

        MENU.click();
        String actualUrl = getPageUrl();
        Assert.assertTrue(actualUrl.contains(MODULE), "Navigated to incorrect Page");

    }

//endregion

}