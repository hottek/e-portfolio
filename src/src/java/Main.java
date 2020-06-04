import scan.ActiveScan;
import spider.Spider;

public class Main {

    private static final String ZAP_ADDRESS = "localhost";
    private static final int ZAP_PORT = 8080;
    private static final String TARGET = "https://public-firing-range.appspot.com";

    private static final String ZAP_API_KEY = "8aoj5h49gsv5o1a9d30a5ql4kc";

    public static void main(String[] args) {

        Spider spider = new Spider(ZAP_ADDRESS, ZAP_PORT, TARGET, ZAP_API_KEY);
        spider.start();

        ActiveScan activeScan = new ActiveScan(ZAP_ADDRESS, ZAP_PORT, TARGET, ZAP_API_KEY);
        activeScan.start();
    }
}
