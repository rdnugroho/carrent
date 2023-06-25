package id.co.indivara.jdt12.miniproject.controller;

import id.co.indivara.jdt12.miniproject.entity.Vehicle;
import id.co.indivara.jdt12.miniproject.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/vehicle")
public class VehicleController extends GenericController<Vehicle>{

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("/available")
    public ResponseEntity<List<Vehicle>> getAvailableVehicle(){
        return new ResponseEntity<>(vehicleService.getAvailableVehicle(), HttpStatus.OK);
    }
}
