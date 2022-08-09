package test.task.PIS.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import test.task.PIS.entity.Contract;

@Repository
public interface ContractRepository extends CrudRepository<Contract, String> {
}
