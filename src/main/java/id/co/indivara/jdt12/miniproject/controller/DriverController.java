package id.co.indivara.jdt12.miniproject.controller;

import id.co.indivara.jdt12.miniproject.entity.Driver;
import id.co.indivara.jdt12.miniproject.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/driver")
public class DriverController extends GenericController<Driver>{

    @Autowired
    private DriverService driverService;

    @GetMapping("/levelandcount/{level}")
    public ResponseEntity<Driver> getDriverByLevelandLowCount(@PathVariable Integer level){
        return new ResponseEntity<>(driverService.getDriverByLevelandLowCount(level), HttpStatus.OK);
    }
}
