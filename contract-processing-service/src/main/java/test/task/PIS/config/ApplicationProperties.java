package test.task.PIS.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "application")
public class ApplicationProperties {
    private Queues queues;

    public Queues getQueues() {
        return queues;
    }

    public void setQueues(Queues queues) {
        this.queues = queues;
    }

    public static class Queues {
        private String contractCreate;
        private String contractEvent;

        public String getContractCreate() {
            return contractCreate;
        }

        public void setContractCreate(String contractCreate) {
            this.contractCreate = contractCreate;
        }

        public String getContractEvent() {
            return contractEvent;
        }

        public void setContractEvent(String contractEvent) {
            this.contractEvent = contractEvent;
        }
    }
}
