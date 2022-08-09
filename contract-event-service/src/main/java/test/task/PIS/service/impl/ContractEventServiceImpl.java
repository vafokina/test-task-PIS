package test.task.PIS.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import test.task.PIS.config.ApplicationProperties;
import test.task.PIS.dto.ContractStatus;
import test.task.PIS.service.ContractEventService;

@Service
public class ContractEventServiceImpl implements ContractEventService {

    private static final Logger log = LoggerFactory.getLogger(ContractEventService.class);

    private final OkHttpClient httpClient;
    private final ObjectMapper objectMapper;
    private final String url;
    private final MediaType mediaType;

    public ContractEventServiceImpl(ObjectMapper objectMapper,
                                    ApplicationProperties applicationProperties) {
        this.objectMapper = objectMapper;
        this.httpClient = new OkHttpClient();
        this.url = applicationProperties.getHttpClient().getUrl();
        this.mediaType = MediaType.get("application/json; charset=utf-8");
    }

    @Override
    public void processEvent(ContractStatus status) {
        String body = "";
        try {
            body = objectMapper.writeValueAsString(status);
        } catch (JsonProcessingException e) {
            log.error("Error occurred", e);
        }

        Request request = new Request.Builder()
                .url(url)
                .post(RequestBody.create(body, mediaType))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            int statusCode = response.code();
            log.info("Received the response with the status " + statusCode + " to the request with the body " + body);
        } catch (IOException e) {
            log.error("Error occurred", e);
        }
    }
}
