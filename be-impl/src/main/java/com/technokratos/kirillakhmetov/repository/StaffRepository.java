package com.technokratos.kirillakhmetov.repository;

import com.technokratos.kirillakhmetov.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StaffRepository extends JpaRepository<Employee, Long> {
    @Query(
            """
                    SELECT
                            e.owner.id AS ownerId,
                            e.firstName AS firstName,
                            e.lastName AS lastName,
                            e.patronymic AS patronymic,
                            e.effectiveDate AS effectiveDate,
                            e.salary AS salary,
                            e.id AS id,
                            GROUP_CONCAT(p.name) AS positions
                        FROM Employee e
                        JOIN e.employeePositions ep
                        JOIN ep.position p
                        WHERE e.owner.id = :ownerId
                        GROUP BY e.id, e.effectiveDate
                        ORDER BY e.effectiveDate
                    """
    )
    List<Employee> findAllByOwnerId(@Param("ownerId") Long ownerId);
}
