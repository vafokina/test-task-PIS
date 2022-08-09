package test.task.PIS.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.domain.Persistable;
import test.task.PIS.dto.CreateNewContract;

public class Contract implements Persistable {

    @Id
    private UUID id = null;
    private LocalDate dateStart;
    private LocalDate dateEnd;
    private LocalDateTime dateSend;
    private LocalDateTime dateCreate;
    private String contractNumber;
    private String contractName;
    private CreateNewContract.ClientApi clientApi;
    private ContractualParties contractualParties;

    @Transient
    private boolean _new = true;

    @Override
    public boolean isNew() {
        return _new;
    }

    public void setNew(boolean _new) {
        this._new = _new;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }

    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }

    public LocalDate getDateStart() {
        return dateStart;
    }

    public void setDateStart(LocalDate dateStart) {
        this.dateStart = dateStart;
    }

    public LocalDate getDateEnd() {
        return dateEnd;
    }

    public void setDateEnd(LocalDate dateEnd) {
        this.dateEnd = dateEnd;
    }

    public LocalDateTime getDateSend() {
        return dateSend;
    }

    public void setDateSend(LocalDateTime dateSend) {
        this.dateSend = dateSend;
    }

    public String getContractNumber() {
        return contractNumber;
    }

    public void setContractNumber(String contractNumber) {
        this.contractNumber = contractNumber;
    }

    public String getContractName() {
        return contractName;
    }

    public void setContractName(String contractName) {
        this.contractName = contractName;
    }

    public CreateNewContract.ClientApi getClientApi() {
        return clientApi;
    }

    public void setClientApi(CreateNewContract.ClientApi clientApi) {
        this.clientApi = clientApi;
    }

    public ContractualParties getContractualParties() {
        return contractualParties;
    }

    public void setContractualParties(ContractualParties contractualParties) {
        this.contractualParties = contractualParties;
    }


}
