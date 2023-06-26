package id.co.indivara.jdt12.miniproject.repo;

import id.co.indivara.jdt12.miniproject.entity.Transaction;


import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends GenericRepository<Transaction>{
    @Query(value = "SELECT t.id, t.duration, t.end_rent, t.is_already_paid, t.start_date, t.total_cost FROM trx_rentcar t, rent r WHERE t.id=r.id and r.driver=?1",nativeQuery = true)
    List<Transaction> findAllTransactionByDriverId(Long id);

}
