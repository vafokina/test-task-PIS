package test.task.PIS.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import test.task.PIS.dto.ContractStatus;
import test.task.PIS.dto.CreateNewContract;
import test.task.PIS.entity.Contract;

@Mapper
public abstract class ContractMapper {

    @Mapping(target = "contractualParties.contractualParties", source = "contractualParties")
    abstract public Contract dtoToEntity(CreateNewContract dto);

    @Mapping(target="status", expression = "java(ContractStatus.Status.CREATED)")
    abstract public ContractStatus entityToStatusCreated(Contract contract);

    @Mapping(target="status", expression = "java(ContractStatus.Status.ERROR)")
    @Mapping(ignore = true, target = "dateCreate")
    abstract public ContractStatus entityToStatusError(Contract contract);
}
