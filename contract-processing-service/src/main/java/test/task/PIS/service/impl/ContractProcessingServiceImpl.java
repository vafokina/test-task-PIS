package test.task.PIS.service.impl;

import org.springframework.dao.DuplicateKeyException;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.stereotype.Service;
import test.task.PIS.constants.ErrorCodes;
import test.task.PIS.dto.ContractStatus;
import test.task.PIS.dto.CreateNewContract;
import test.task.PIS.entity.Contract;
import test.task.PIS.mapper.ContractMapper;
import test.task.PIS.repository.ContractRepository;
import test.task.PIS.service.ContractProcessingService;

@Service
public class ContractProcessingServiceImpl implements ContractProcessingService {

    private ContractRepository contractRepository;
    private ContractMapper mapper;

    public ContractProcessingServiceImpl(ContractRepository contractRepository,
                                         ContractMapper mapper) {
        this.contractRepository = contractRepository;
        this.mapper = mapper;
    }

    @Override
    public ContractStatus processNewContract(CreateNewContract contractDto) {
        Contract contract = mapper.dtoToEntity(contractDto);
        assert contract.getId() != null;
        if (contractRepository.existsById(contract.getId().toString())) {
            return mapper.entityToStatusError(contract)
                    .withErrorCode(ErrorCodes.ID_EXISTS);
        }
        try {
            Contract savedContract = contractRepository.save(contract);
            return mapper.entityToStatusCreated(savedContract);
        } catch (DbActionExecutionException ex) {
            Throwable cause = ex.getCause();
            if (cause instanceof DuplicateKeyException) {
                return mapper.entityToStatusError(contract)
                        .withErrorCode(ErrorCodes.CONTRACT_NUM_EXISTS);
            }
            return mapper.entityToStatusError(contract)
                    .withErrorCode(ErrorCodes.OTHER);
        } catch (Exception ex) {
            return mapper.entityToStatusError(contract)
                    .withErrorCode(ErrorCodes.OTHER);
        }
    }
}
