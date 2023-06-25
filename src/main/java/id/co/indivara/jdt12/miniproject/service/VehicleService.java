package id.co.indivara.jdt12.miniproject.service;

import id.co.indivara.jdt12.miniproject.repo.VehicleRepository;
import id.co.indivara.jdt12.miniproject.entity.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VehicleService extends GenericService<Vehicle> {

    @Autowired
    private VehicleRepository vehicleRepository;

    public List<Vehicle> getAvailableVehicle(){
        return vehicleRepository.getAvailableVehicle();
    }
}
