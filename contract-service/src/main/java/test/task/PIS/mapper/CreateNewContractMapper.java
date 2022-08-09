package test.task.PIS.mapper;

import java.time.LocalDateTime;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import test.task.PIS.dto.ContractualParty;
import test.task.PIS.dto.CreateNewContract;
import test.task.PIS.xsd.CreateNewContractRequest;

@Mapper(imports = LocalDateTime.class)
public abstract class CreateNewContractMapper {

    @Mapping(target = "contractualParties", source = "contractualParties.contractualParty")
    @Mapping(target="dateSend", expression = "java(LocalDateTime.now())")
    @Mapping(target="clientApi", expression = "java(CreateNewContract.ClientApi.SOAP)")
    public abstract CreateNewContract requestToDto(CreateNewContractRequest request);

    @Mapping(target = "bankAccountNumber", source = "bankAccount")
    @Mapping(target = "bik", source = "bankBik")
    abstract ContractualParty contractualPartyToContractualParty(CreateNewContractRequest.ContractualParties.ContractualParty contractualParty);


}
