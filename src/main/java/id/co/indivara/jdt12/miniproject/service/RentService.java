package id.co.indivara.jdt12.miniproject.service;

import id.co.indivara.jdt12.miniproject.dto.RegRent;
import id.co.indivara.jdt12.miniproject.entity.*;
import id.co.indivara.jdt12.miniproject.repo.RentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.NoSuchElementException;


@Service
public class RentService extends GenericService<Rent> {
    private RentRepository rentRepository;
    private DriverService driverService;
    private CustomerService customerService;
    private VehicleService vehicleService;

    @Autowired
    public RentService(
            RentRepository rentRepository,
            DriverService driverService,
            CustomerService customerService,
            VehicleService vehicleService) {

        this.rentRepository = rentRepository;
        this.driverService = driverService;
        this.customerService = customerService;
        this.vehicleService = vehicleService;
    }

    public Rent saveWithDriver(RegRent entity) {
        Transaction transaction = new Transaction();
        //ini dibuat != null karena driver bisa di isi ataupun tidak
        Rent rent = new Rent();
        //mengambil id custumer,kendaraan, kemudian mengubah mobil menjadi false, dan status bel dibayar
        Customer customer = customerService.findById(entity.getCustomerId());
        Vehicle vehicle = vehicleService.findById(entity.getVehicleId());
        Driver driver = driverService.getDriverByLevelandLowCount(vehicle.getLevel());

        vehicle.setIsAvailable(false);
        driver.setIsAvailable(false);
        rent.setVehicle(vehicle);
        rent.setCustomer(customer);
        rent.setDriver(driver);

        transaction.setStartDate(new Date());
        transaction.setRent(rent);
        rent.setTransaction(transaction);
        rent.setIsCurrentlyRented(true);
        transaction.setIsAlreadyPaid(false);
        rent.setTransaction(transaction);
        return rentRepository.save(rent);
    }
    public Rent withoutDriver(RegRent entity) {
        Transaction transaction = new Transaction();
        //mengambil id custumer,kendaraan
        Customer customer = customerService.findById(entity.getCustomerId());
        Vehicle vehicle = vehicleService.findById(entity.getVehicleId());

        //mengubah driver,mobil menjadi tidak  dapat dipesan lagi selama sedang pesan
        vehicle.setIsAvailable(false);

        Rent rent = new Rent();
        rent.setVehicle(vehicle);
        rent.setCustomer(customer);

        transaction.setStartDate(new Date());
        transaction.setIsAlreadyPaid(false);
        transaction.setRent(rent);
        rent.setIsCurrentlyRented(true);
        rent.setTransaction(transaction);

        return rentRepository.save(rent);
    }
    public void delete(Long id) {
        Rent rent = rentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Rent not found with ID: " + id));
        Vehicle vehicle = rent.getVehicle();
        Driver driver = rent.getDriver();
        if (driver != null) {
            driver.setIsAvailable(true);
        }
        vehicle.setIsAvailable(true);
        super.delete(id);
    }

}

