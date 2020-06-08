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

    }
}
