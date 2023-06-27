package id.co.indivara.jdt12.miniproject.controller;
import id.co.indivara.jdt12.miniproject.dto.RegRent;
import id.co.indivara.jdt12.miniproject.entity.Rent;
import id.co.indivara.jdt12.miniproject.service.RentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/rent")
public class RentController extends GenericController<Rent>{
        @Autowired
        private RentService rentService;

    @PostMapping("/withdriver")
    public ResponseEntity<Rent> RegWithDriver(@RequestBody RegRent rent){
        return new ResponseEntity<>(rentService.saveWithDriver(rent), HttpStatus.CREATED);
    }
    @PostMapping("/withoutdriver")
    public ResponseEntity<Rent> RegWithoutDriver(@RequestBody RegRent rent){
        return new ResponseEntity<>(rentService.withoutDriver(rent), HttpStatus.CREATED);
    }
}
