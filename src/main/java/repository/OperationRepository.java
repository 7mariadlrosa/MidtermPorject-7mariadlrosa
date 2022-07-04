package repository;

import model.Operation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OperationRepository extends JpaRepository<Operation, Long> {

    @Query( nativeQuery = true)
    public List<Operation> operationsWithinOneSecond(@Param("accountId") Long accountId);

}
