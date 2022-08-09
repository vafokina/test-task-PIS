package test.task.PIS.service;

import test.task.PIS.dto.ContractStatus;
import test.task.PIS.dto.CreateNewContract;

public interface ContractProcessingService {
    ContractStatus processNewContract(CreateNewContract contractDto);
}
