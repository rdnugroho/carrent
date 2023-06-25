package id.co.indivara.jdt12.miniproject.repo;


import id.co.indivara.jdt12.miniproject.entity.Vehicle;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface VehicleRepository extends GenericRepository<Vehicle>{
    @Query("SELECT v from Vehicle v WHERE v.isAvailable= 'true'")
    List<Vehicle> getAvailableVehicle();

}
