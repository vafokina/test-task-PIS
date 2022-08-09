package test.task.PIS.service;

import test.task.PIS.dto.ContractStatus;

public interface ContractEventService {
    void processEvent(ContractStatus status);
}
