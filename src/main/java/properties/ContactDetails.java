package properties;

public class ContactDetails {
    private String Forename;
    private String Surname;
    private String Email;
    private String Telephone;
    private String Message;

    // Constructor
    public ContactDetails(String forename, String surname, String email, String telephone, String message) {
        this.Forename = forename;
        this.Surname = surname;
        this.Email = email;
        this.Telephone = telephone;
        this.Message = message;
    }

    // Getters
    public String getForename() {
        return Forename;
    }

    public String getSurname() {
        return Surname;
    }

    public String getEmail() {
        return Email;
    }

    public String getTelephone() {
        return Telephone;
    }

    public String getMessage() {
        return Message;
    }

    // Optionally, override the toString() method to make printing easier
    @Override
    public String toString() {
        return "FeedbackDetails{" +
                "Forename='" + Forename + '\'' +
                ", Surname='" + Surname + '\'' +
                ", Email='" + Email + '\'' +
                ", Telephone='" + Telephone + '\'' +
                ", Message='" + Message + '\'' +
                '}';
    }
}
