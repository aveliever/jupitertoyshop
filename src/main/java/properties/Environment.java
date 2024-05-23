package properties;

public enum Environment {

    TEST("https://jupiter.cloud.planittesting.com/");

    private final String url;

    public String getUrl() {
        return url;
    }

    Environment(String url) {
        this.url = url;
    }
}



