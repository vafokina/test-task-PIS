package test.task.PIS.service.impl;

import org.springframework.amqp.AmqpException;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import test.task.PIS.dto.CreateNewContract;
import test.task.PIS.mapper.CreateNewContractMapper;
import test.task.PIS.service.ContractService;
import test.task.PIS.xsd.CreateNewContractRequest;
import test.task.PIS.xsd.CreateNewContractResponse;
import test.task.PIS.xsd.ObjectFactory;

@Service
public class ContractServiceImpl implements ContractService {

    private CreateNewContractMapper mapper;
    private RabbitTemplate rabbitTemplate;
    private ObjectFactory objectFactory;

    public ContractServiceImpl(CreateNewContractMapper mapper,
                               RabbitTemplate rabbitTemplate) {
        this.mapper = mapper;
        this.rabbitTemplate = rabbitTemplate;
        this.objectFactory = new ObjectFactory();
    }

    @Override
    public CreateNewContractResponse createNewContract(CreateNewContractRequest request) {
        CreateNewContract dto = mapper.requestToDto(request);
        try {
            rabbitTemplate.convertAndSend(dto);
            CreateNewContractResponse response = objectFactory.createCreateNewContractResponse();
            response.setStatus(CreateNewContractResponse.Status.REQUEST_IS_QUEUED);
            return response;
        } catch (AmqpException ex)  {
            CreateNewContractResponse response = objectFactory.createCreateNewContractResponse();
            response.setStatus(CreateNewContractResponse.Status.ERROR);
            response.setErrorMessage(ex.getLocalizedMessage());
            return response;
        }
    }
}
