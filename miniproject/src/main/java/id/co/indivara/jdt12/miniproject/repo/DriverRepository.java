package id.co.indivara.jdt12.miniproject.repo;

import id.co.indivara.jdt12.miniproject.entity.Driver;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepository extends GenericRepository<Driver>{

    @Query(value = "SELECT * FROM public.driver WHERE level=?1 and is_available='true' ORDER BY count_of_rented ASC LIMIT 1" ,nativeQuery = true)
    Driver filteringTheDriverByLevelAndCount(Integer level);
}
