package Shop;

import org.testng.Assert;
import org.testng.annotations.Test;
import pages.Cart.CartPage;
import pages.Common.MenuPage;
import pages.Shop.ShopPage;
import properties.Environment;
import properties.Pages;
import properties.ToyDetails;
import setup.base.BaseTest;

import java.util.ArrayList;
import java.util.List;

public class ShopTests extends BaseTest {

    @Test
    public void TestCase3(){

        List<ToyDetails> TOYS = new ArrayList<>();
        TOYS.add(new ToyDetails("Stuffed Frog", 2));
        TOYS.add(new ToyDetails("Fluffy Bunny", 5));
        TOYS.add(new ToyDetails("Valentine Bear", 3));

        openUrl(environment.getUrl());

        //Go to the shop page
        MenuPage.GoToPage(Pages.SHOP);

        //Buy 2 Stuffed Frog, 5 Fluffy Bunny, 3 Valentine Bear
        List<ToyDetails> EXP_TOYS = ShopPage.BuyToys(TOYS);

        //Go to the cart page
        MenuPage.GoToPage(Pages.CART);

        //Get Items in Cart
        List<ToyDetails> ACT_TOYS = CartPage.GetCartItems();

        //Verify the subtotal for each product is correct
        //Verify the price for each product
        Assert.assertEquals(ACT_TOYS, EXP_TOYS, "Items added to Cart did not matched");

        //Verify that total = sum(subtotals)
        String EXP_TOYS_TOTAL = ShopPage.SumSubtotals(ACT_TOYS);
        String ACT_TOYS_TOTAL = ShopPage.TotalLbl.getText().replace("Total: ", "");
        Assert.assertEquals(ACT_TOYS_TOTAL, EXP_TOYS_TOTAL, "Total is incorrect");
    }
}
