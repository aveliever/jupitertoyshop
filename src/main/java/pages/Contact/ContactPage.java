package pages.Contact;

import org.openqa.selenium.By;
import properties.ContactDetails;
import setup.elements.Button;
import setup.elements.Element;
import setup.elements.TextField;
import setup.logger.Log;

public class ContactPage {

    public static String ALERT_INFO_OK = "We welcome your feedback - tell it how it is.";
    public static String ALERT_INFO_NOK = "We welcome your feedback - but we won't get it unless you complete the form correctly.";
    public static String FORENAME_REQUIRED_ERROR = "Forename is required";
    public static String EMAIL_REQUIRED_ERROR = "Email is required";
    public static String MESSAGE_REQUIRED_ERROR = "Message is required";

//region LOCATORS

    public static Element AlertInfoLbl = new Element("AlertInfoLbl", By.cssSelector("div.alert"));
    public static TextField ForenameTxt = new TextField("ForenameTxt", By.id("forename"));
    public static TextField SurnameTxt = new TextField("SurnameTxt", By.id("surname"));
    public static TextField EmailTxt = new TextField("EmailTxt", By.id("email"));
    public static TextField TelephoneTxt = new TextField("TelephoneTxt", By.id("telephone"));
    public static TextField MessageTxt = new TextField("MessageTxt", By.id("message"));
    public static Button SubmitBtn = new Button("SubmitBtn", By.cssSelector("a.btn-contact"));
    public static Element ForenameErrorLbl = new Element("ForenameErrorLbl", By.id("forename-err"));
    public static Element EmailErrorLbl = new Element("EmailErrorLbl", By.id("email-err"));
    public static Element MessageErrorLbl = new Element("MessageErrorLbl", By.id("message-err"));
    public static Element SendFeedbackModal = new Element("SendFeedbackModal", By.cssSelector("div.popup"));

//endregion

//region PAGE ACTIONS

    /**
     * Populate values on fields in Contact Page
     * @param feedback the feedback details
     */
    public static void EnterFeedbackDetails(ContactDetails feedback) {
        ForenameTxt.enterText(feedback.getForename());
        SurnameTxt.enterText(feedback.getSurname());
        EmailTxt.enterText(feedback.getEmail());
        TelephoneTxt.enterText(feedback.getTelephone());
        MessageTxt.enterText(feedback.getMessage());

        Log.info(feedback.toString());
    }

//endregion
}