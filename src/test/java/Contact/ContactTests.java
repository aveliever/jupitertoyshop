package Contact;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import pages.Common.MenuPage;
import pages.Contact.ContactPage;
import properties.ContactDetails;
import properties.Environment;
import properties.Pages;
import setup.base.BaseTest;
import setup.utilities.ImplicitWait;
import setup.utilities.RandomDataGenerator;

public class ContactTests extends BaseTest {

    String FORENAME, EMAIL, MESSAGE;
    ContactDetails REQUIRED_INPUT;

    @BeforeClass
    public void SetupData(){
        FORENAME = RandomDataGenerator.GenerateRandomString(10);
        EMAIL = FORENAME.toLowerCase() + "@gmail.com";
        MESSAGE = RandomDataGenerator.GenerateRandomString(20);
        REQUIRED_INPUT = new ContactDetails(FORENAME, "", EMAIL, "", MESSAGE);
    }

    @Test
    public void TestCase1(){

        openUrl(environment.getUrl());

        //From the home page go to contact page
        MenuPage.GoToPage(Pages.CONTACT);

        //Click submit button
        ContactPage.SubmitBtn.click();

        //Verify error messages
        Assert.assertEquals(ContactPage.AlertInfoLbl.getAttributeValue("outerText"), ContactPage.ALERT_INFO_NOK, "Incorrect Negative Alert Info");
        Assert.assertEquals(ContactPage.ForenameErrorLbl.getText(), ContactPage.FORENAME_REQUIRED_ERROR, "Incorrect Forename Error");
        Assert.assertEquals(ContactPage.EmailErrorLbl.getText(), ContactPage.EMAIL_REQUIRED_ERROR, "Incorrect Email Error");
        Assert.assertEquals(ContactPage.MessageErrorLbl.getText(), ContactPage.MESSAGE_REQUIRED_ERROR, "Incorrect Message Error");

        //Populate mandatory fields
        ContactPage.EnterFeedbackDetails(REQUIRED_INPUT);

        //Validate errors are gone
        Assert.assertEquals(ContactPage.AlertInfoLbl.getAttributeValue("outerText"), ContactPage.ALERT_INFO_OK, "Incorrect Positive Alert Info");
        Assert.assertTrue(ContactPage.ForenameErrorLbl.isInvisible(), "Forename Error is still displayed");
        Assert.assertTrue(ContactPage.EmailErrorLbl.isInvisible(), "Forename Error is still displayed");
        Assert.assertTrue(ContactPage.MessageErrorLbl.isInvisible(), "Forename Error is still displayed");
    }

    @Test
    public void TestCase2(){

        openUrl(environment.getUrl());

        //From the home page go to contact page
        MenuPage.GoToPage(Pages.CONTACT);

        //Populate mandatory fields
        ContactPage.EnterFeedbackDetails(REQUIRED_INPUT);

        //Click submit button
        ContactPage.SubmitBtn.click();
        ImplicitWait.waitForInvisibilityElement(ContactPage.SendFeedbackModal.getLocator());

        //Validate successful submission message
        String ACT_ALERT_SUCCESS = "Thanks " + REQUIRED_INPUT.getForename() + ", we appreciate your feedback.";
        Assert.assertEquals(ContactPage.AlertInfoLbl.getAttributeValue("outerText"), ACT_ALERT_SUCCESS, "Incorrect Alert Info");
    }
}
