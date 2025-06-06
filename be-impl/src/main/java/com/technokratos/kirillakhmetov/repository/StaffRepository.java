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
                            e,
                            STRING_AGG(p.name, ', ') AS positions
                    FROM Employee e
                        INNER JOIN e.employeePositions ep
                    INNER JOIN ep.position p
                    WHERE e.owner.id = :ownerId
                    GROUP BY e.id, e.effectiveDate
                    ORDER BY e.effectiveDate
                    """
    )
    List<Employee> findAllByOwnerId(@Param("ownerId") Long ownerId);
}
