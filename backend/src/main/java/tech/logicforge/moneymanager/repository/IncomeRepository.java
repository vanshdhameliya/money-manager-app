package tech.logicforge.moneymanager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.logicforge.moneymanager.entity.ExpenseEntity;
import tech.logicforge.moneymanager.entity.IncomeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {

    List<ExpenseEntity> findByProfileEntityIdOrderByDateDesc(Long profileId);

    List<ExpenseEntity> findTop5ByProfileEntityIdOrderByDateDesc(Long profileId);

    @Query("select SUM(e.amount) from ExpenseEntity e where e.profileEntity.id = :profileId")
    Double findTotalExpenseByProfileId(@Param("profileId") Long profileId);

    @Query("SELECT e FROM ExpenseEntity e " +
            "WHERE e.profileEntity.id = :profileId " +
            "AND e.date BETWEEN :startDate AND :endDate " +
            "AND LOWER(e.name) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<ExpenseEntity> findByProfileEntityIdAndDateBetweenAndNameContainingIgnoreCase(
            @Param("profileId") Long profileId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("name") String name,
            Sort sort);

    List<ExpenseEntity> findByProfileEntityIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);
}
