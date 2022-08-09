package test.task.PIS.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.UUID;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.task.PIS.constants.ErrorCodes;
import test.task.PIS.dto.ContractStatus;
import test.task.PIS.dto.ContractualParty;
import test.task.PIS.dto.CreateNewContract;
import test.task.PIS.repository.ContractRepository;

@Disabled
@SpringBootTest
public class ContractProcessingServiceTest {

    @Autowired
    private ContractProcessingService contractProcessingService;

    @Autowired
    private ContractRepository contractRepository;

    @Test
    void test() {
        // The good thing is to create a base for tests, but that's not part of the assignment
        contractRepository.deleteAll();

        UUID uuid1 = UUID.randomUUID();
        UUID uuid2 = UUID.randomUUID();

        ContractualParty contractualParty1 = new ContractualParty();
        contractualParty1.setName("test-1");
        contractualParty1.setBankAccountNumber("123452346545427");
        contractualParty1.setBik("12345234");
        ContractualParty contractualParty2 = new ContractualParty();
        contractualParty2.setName("test-2");
        contractualParty2.setBankAccountNumber("223452346545427");
        contractualParty2.setBik("22345234");
        ContractualParty contractualParty3 = new ContractualParty();
        contractualParty3.setName("test-3");
        contractualParty3.setBankAccountNumber("323452346545427");
        contractualParty3.setBik("32345234");

        CreateNewContract dto = new CreateNewContract().withId(uuid1)
                .withContractName("test-name")
                .withContractNumber("111.111.111")
                .withClientApi(CreateNewContract.ClientApi.SOAP)
                .withDateEnd(LocalDate.now())
                .withDateStart(LocalDate.now())
                .withDateSend(LocalDateTime.now())
                .withContractualParties(Arrays.asList(contractualParty1, contractualParty2, contractualParty3));
        ContractStatus status1 = contractProcessingService.processNewContract(dto);

        Assertions.assertThat(status1.getId()).isEqualTo(uuid1);
        Assertions.assertThat(status1.getDateCreate()).isNotNull();
        Assertions.assertThat(status1.getStatus()).isEqualTo(ContractStatus.Status.CREATED);
        Assertions.assertThat(status1.getErrorCode()).isNull();

        ContractStatus status2 = contractProcessingService.processNewContract(dto
                .withContractNumber("111.111.112"));

        Assertions.assertThat(status2.getId()).isEqualTo(uuid1);
        Assertions.assertThat(status2.getDateCreate()).isNull();
        Assertions.assertThat(status2.getStatus()).isEqualTo(ContractStatus.Status.ERROR);
        Assertions.assertThat(status2.getErrorCode()).isEqualTo(ErrorCodes.ID_EXISTS);

        ContractStatus status3 = contractProcessingService.processNewContract(dto
                .withId(uuid2)
                .withContractNumber("111.111.111"));

        Assertions.assertThat(status3.getId()).isEqualTo(uuid2);
        Assertions.assertThat(status3.getDateCreate()).isNull();
        Assertions.assertThat(status3.getStatus()).isEqualTo(ContractStatus.Status.ERROR);
        Assertions.assertThat(status3.getErrorCode()).isEqualTo(ErrorCodes.CONTRACT_NUM_EXISTS);
    }
}
