package pages.Cart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import properties.ToyDetails;
import setup.elements.Table;
import setup.logger.Log;

import java.util.ArrayList;
import java.util.List;

import static org.openqa.selenium.By.tagName;

public class CartPage {

//region LOCATORS

    public static Table CartItemsTbl = new Table("CartItemsTbl", By.cssSelector("table.cart-items > tbody"));

//endregion

//region PAGE ACTIONS

    public static List<ToyDetails> GetCartItems(){

        List<ToyDetails> ITEM_LIST = new ArrayList<>();
        List<WebElement> rows = CartItemsTbl.getWebElement().findElements(tagName("tr"));

        for (int i = 0; i < rows.size(); i++) {
            List<WebElement> cells = rows.get(i).findElements(By.tagName("td"));

            if (cells.size() == 5) { //Make sure table has 5 columns
                String ITEM = cells.get(0).getText().trim();
                String PRICE = cells.get(1).getText().trim();
                int QUANTITY = Integer.parseInt(cells.get(2).findElement(By.tagName("input")).getAttribute("value"));
                String SUBTOTAL = cells.get(3).getText().trim();

                //Add each table row in the List
                ITEM_LIST.add(new ToyDetails(ITEM, PRICE, QUANTITY, SUBTOTAL));
            }
        }

        Log.info("Total Item(s) in Cart Table: " + (ITEM_LIST.size()));
        for (ToyDetails ITEM : ITEM_LIST) {
            Log.info(ITEM.toString());
        }

        return ITEM_LIST;
    }

//endregion
}