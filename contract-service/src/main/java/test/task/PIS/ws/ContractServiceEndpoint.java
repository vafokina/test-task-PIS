package test.task.PIS.ws;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;
import test.task.PIS.service.ContractService;
import test.task.PIS.xsd.CreateNewContractRequest;
import test.task.PIS.xsd.CreateNewContractResponse;

@Endpoint
public class ContractServiceEndpoint {

    private static final String NAMESPACE_URI = "http://xmlns.esb.ru/ext/ContractService/";
    private static final String EXAMPLE_NAMESPACE_URI = "http://tempuri.org/PurchaseOrderSchema.xsd";

    private final ContractService contractService;

    @Autowired
    public ContractServiceEndpoint(ContractService contractService) {
        this.contractService = contractService;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateNewContractRequest")
    @ResponsePayload
    public CreateNewContractResponse createNewContract(@RequestPayload CreateNewContractRequest createNewContractRequest) {
        return contractService.createNewContract(createNewContractRequest);
    }
}
