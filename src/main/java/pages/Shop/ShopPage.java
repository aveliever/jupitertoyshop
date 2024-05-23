package pages.Shop;

import org.openqa.selenium.By;
import org.testng.Assert;
import pages.Common.MenuPage;
import properties.ToyDetails;
import setup.elements.Button;
import setup.elements.Element;
import setup.logger.Log;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ShopPage {

//region LOCATORS

    public static Element TotalLbl = new Element("TotalLbl", By.cssSelector("strong.total"));
//endregion

//region PAGE ACTIONS

    /**
     * Select and buy the specified toys and quantities
     * @param toys the toy details to purchase
     * @return the Toy details purchased in Shop page
     */
    public static List<ToyDetails> BuyToys(List<ToyDetails> toys){
        List<ToyDetails> ADDED_TOYS = new ArrayList<>();
        int EXP_TOTAL = 0;

        for(ToyDetails toy: toys){
            String ITEM_NAME = toy.getItem();

            //Dynamic locators
            Element ProductPriceLbl = new Element("ProductPriceFor " + ITEM_NAME, By.xpath("//h4[text()='"+ ITEM_NAME +"']/ancestor::li//span"));
            Button BuyBtn = new Button("BuyBtnFor " + ITEM_NAME, By.xpath("//h4[text()='"+ ITEM_NAME +"']/ancestor::li//a"));

            //Click Buy button according to quantity
            for (int i = 0; i < toy.getQuantity(); i++) {
                BuyBtn.click();

                EXP_TOTAL += 1;
                int ACT_TOTAL = Integer.parseInt(MenuPage.CartTotalLbl.getText());
                Assert.assertEquals(ACT_TOTAL, EXP_TOTAL, "Incorrect Cart Total");
            }

            //Get the Expected Item Price and Subtotal within Shop page
            toy.setPrice(ProductPriceLbl.getText());
            toy.setSubTotal(ProductPriceLbl.getText(), toy.getQuantity());
            ADDED_TOYS.add(toy);
            Log.info("Successfully added to Cart: " + toy);
        }

        return ADDED_TOYS;
    }

    /**
     * Sum all subtotals of group of items
     * @param toys
     * @return
     */
    public static String SumSubtotals(List<ToyDetails> toys) {
        double sum = 0;
        for (ToyDetails toy : toys) {
            String subTotal = toy.getSubTotal().replace("$", "");
            sum += Double.parseDouble(subTotal);
        }

        DecimalFormat df = new DecimalFormat("0.##");  // Allows up to two decimal places without trailing zeros
        return df.format(sum);
    }

//endregion
}