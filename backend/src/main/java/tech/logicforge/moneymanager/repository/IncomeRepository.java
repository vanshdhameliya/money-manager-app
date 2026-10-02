package tech.logicforge.moneymanager.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tech.logicforge.moneymanager.dto.IncomeDto;
import tech.logicforge.moneymanager.entity.ExpenseEntity;
import tech.logicforge.moneymanager.entity.IncomeEntity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {

    List<ExpenseEntity> findByProfileEntityIdOrderByDateDesc(Long profileId);

    List<IncomeEntity> findTop5ByProfileEntityIdOrderByDateDesc(Long profileId);

    @Query("select SUM(e.amount) from ExpenseEntity e where e.profileEntity.id = :profileId")
    BigDecimal findTotalExpenseByProfileId(@Param("profileId") Long profileId);

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

    List<IncomeEntity> findByProfileEntityIdAndDateBetween(Long profileId, LocalDate startDate, LocalDate endDate);

    // Finds the income record only if it belongs to the specified profile
    Optional<IncomeEntity> findByIdAndProfileEntityId(Long id, Long profileId);
}
