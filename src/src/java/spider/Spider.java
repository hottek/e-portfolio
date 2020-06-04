package spider;

import org.zaproxy.clientapi.core.ApiResponse;
import org.zaproxy.clientapi.core.ApiResponseElement;
import org.zaproxy.clientapi.core.ApiResponseList;
import org.zaproxy.clientapi.core.ClientApi;

import java.util.List;

public class Spider {

    private final String ZAP_ADDRESS;
    private final int ZAP_PORT;
    private final String TARGET;
    private final String ZAP_API_KEY;

    public Spider(String ZAP_ADDRESS, int ZAP_PORT, String TARGET, String ZAP_API_KEY) {
        this.ZAP_ADDRESS = ZAP_ADDRESS;
        this.ZAP_PORT = ZAP_PORT;
        this.TARGET = TARGET;
        this.ZAP_API_KEY = ZAP_API_KEY;
    }

    public void start() {
        ClientApi api = new ClientApi(ZAP_ADDRESS, ZAP_PORT, ZAP_API_KEY);

        try {
            // Start spidering the target
            System.out.println("Spidering target : " + TARGET);
            ApiResponse resp = api.spider.scan(TARGET, null, null, null, null);
            String scanID;
            int progress;

            // The scan returns a scan id to support concurrent scanning
            scanID = ((ApiResponseElement) resp).getValue();
            // Poll the status until it completes
            while (true) {
                Thread.sleep(1000);
                progress = Integer.parseInt(((ApiResponseElement) api.spider.status(scanID)).getValue());
                System.out.println("Spider progress : " + progress + "%");
                if (progress >= 100) {
                    break;
                }
            }
            System.out.println("Spider completed");
            // If required post process the spider results
            List<ApiResponse> spiderResults = ((ApiResponseList) api.spider.results(scanID)).getItems();
            for (ApiResponse response: spiderResults) {
                System.out.println(response.toString());
            }

        } catch (Exception e) {
            System.out.println("Exception : " + e.getMessage());
            e.printStackTrace();
        }

    }
}
