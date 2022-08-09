package test.task.PIS.service;

import test.task.PIS.xsd.CreateNewContractRequest;
import test.task.PIS.xsd.CreateNewContractResponse;

public interface ContractService {
    CreateNewContractResponse createNewContract(CreateNewContractRequest request);
}
