package test.task.PIS.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private Queues queues;
    private HttpClient httpClient;

    public Queues getQueues() {
        return queues;
    }

    public void setQueues(Queues queues) {
        this.queues = queues;
    }

    public HttpClient getHttpClient() {
        return httpClient;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public static class Queues {
        private String contractEvent;

        public String getContractEvent() {
            return contractEvent;
        }

        public void setContractEvent(String contractEvent) {
            this.contractEvent = contractEvent;
        }
    }

    public static class HttpClient {
        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
