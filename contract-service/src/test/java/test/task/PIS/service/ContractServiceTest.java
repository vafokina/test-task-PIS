package test.task.PIS.service;

import com.sun.org.apache.xerces.internal.jaxp.datatype.XMLGregorianCalendarImpl;
import java.util.Random;
import java.util.UUID;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import test.task.PIS.xsd.CreateNewContractRequest;

@SpringBootTest
@Disabled
public class ContractServiceTest {

    @Autowired
    private ContractService contractService;

    private final Random rnd = new Random();

    @Test
    void test() {
        CreateNewContractRequest request = getCreateNewContractRequest(UUID.randomUUID().toString(), "111.111.111-test");
        contractService.createNewContract(request);
    }

    @Test
    void testAllCasesWithDelay() throws InterruptedException {
        int smallDelay = 0;
        int bigDelay = 100;
        int repeats = 4;
        for (int i = 0; i < repeats; i++) {
            String uuid = UUID.randomUUID().toString();
            String contractNumber = getContractNumber();

            // created
            CreateNewContractRequest request = getCreateNewContractRequest(uuid, contractNumber);
            contractService.createNewContract(request);
            Thread.sleep(smallDelay);
            // contract_num_exists - 2
            CreateNewContractRequest request2 = getCreateNewContractRequest(UUID.randomUUID().toString(), contractNumber);
            contractService.createNewContract(request2);
            Thread.sleep(smallDelay);
            // id_exists - 1
            CreateNewContractRequest request1 = getCreateNewContractRequest(uuid, getContractNumber());
            contractService.createNewContract(request1);
            Thread.sleep(bigDelay);
        }
    }

    private String getContractNumber() {
        return rnd.nextInt(999) + "." + rnd.nextInt(999) + "." + rnd.nextInt(999);
    }

    private CreateNewContractRequest getCreateNewContractRequest(String uuid, String contractNumber) {
        CreateNewContractRequest.ContractualParties.ContractualParty contractualParty1 = new CreateNewContractRequest.ContractualParties.ContractualParty();
        contractualParty1.setName("test-1");
        contractualParty1.setBankAccount("123452346545427");
        contractualParty1.setBankBik("12345234");
        CreateNewContractRequest.ContractualParties.ContractualParty contractualParty2 = new CreateNewContractRequest.ContractualParties.ContractualParty();
        contractualParty2.setName("test-2");
        contractualParty2.setBankAccount("223452346545427");
        contractualParty2.setBankBik("22345234");
        CreateNewContractRequest.ContractualParties.ContractualParty contractualParty3 = new CreateNewContractRequest.ContractualParties.ContractualParty();
        contractualParty3.setName("test-3");
        contractualParty3.setBankAccount("323452346545427");
        contractualParty3.setBankBik("32345234");

        CreateNewContractRequest request = new CreateNewContractRequest();
        request.setId(uuid);
        request.setContractName("test-name");
        request.setContractNumber(contractNumber);
        request.setDateStart(XMLGregorianCalendarImpl.createDate(2022, 1, 25, 0));
        request.setDateEnd(XMLGregorianCalendarImpl.createDate(2022, 12, 25, 0));

        CreateNewContractRequest.ContractualParties contractualParties = new CreateNewContractRequest.ContractualParties();
        contractualParties.getContractualParty().add(contractualParty1);
        contractualParties.getContractualParty().add(contractualParty2);
        contractualParties.getContractualParty().add(contractualParty3);
        request.setContractualParties(contractualParties);
        return request;
    }
}
