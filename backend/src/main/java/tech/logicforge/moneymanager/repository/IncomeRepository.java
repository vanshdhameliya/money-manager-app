package tech.logicforge.moneymanager.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tech.logicforge.moneymanager.entity.IncomeEntity;

public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {


}
