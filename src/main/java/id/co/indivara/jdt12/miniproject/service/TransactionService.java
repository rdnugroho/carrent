package id.co.indivara.jdt12.miniproject.service;

import id.co.indivara.jdt12.miniproject.entity.Driver;
import id.co.indivara.jdt12.miniproject.entity.Rent;
import id.co.indivara.jdt12.miniproject.entity.Transaction;
import id.co.indivara.jdt12.miniproject.entity.Vehicle;
import id.co.indivara.jdt12.miniproject.error.FinishingTheRentException;
import id.co.indivara.jdt12.miniproject.repo.DriverRepository;
import id.co.indivara.jdt12.miniproject.repo.RentRepository;
import id.co.indivara.jdt12.miniproject.repo.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.Temporal;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;


@Service
public class TransactionService extends GenericService <Transaction> {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private RentRepository rentCarRepository;

    @Override
    public Transaction update(Long id, Transaction entity) {
        return super.update(id, entity);
    }

    public Transaction finish(Long id) {
        Transaction transaction = this.findById(id);
        if (!transaction.getRent().getIsCurrentlyRented()) {
            throw new FinishingTheRentException("Maaf, Anda telah mengakhiri penyewaan mobil");
        }
        Rent rent = transaction.getRent();
        Driver driver = rent.getDriver();
        Vehicle vehicle = rent.getVehicle();
        Date startDate = transaction.getStartDate();
        Date endDate = new Date(); // Menggunakan tanggal dan waktu saat ini

        transaction.setEndRent(endDate);

        Long totalHours = (TimeUnit.MILLISECONDS.toHours(endDate.getTime() - startDate.getTime())) == 0
                ? 1 :  (TimeUnit.MILLISECONDS.toHours(endDate.getTime() - startDate.getTime()));
        BigDecimal totalPrice = rent.getDriver() == null ?
                BigDecimal.valueOf(totalHours).multiply(rent.getVehicle().getCostPerHour()) :
                BigDecimal.valueOf(totalHours)
                        .multiply(rent.getVehicle().getCostPerHour())
                        .add(BigDecimal.valueOf(totalHours).multiply(rent.getDriver().getCostPerHour()));
        if (driver != null) {
            driver.setIsAvailable(true);
            driver.setCountOfRented(driver.getCountOfRented() + 1);
            rent.setDriver(driver);
            BigDecimal totalDriverCost = driver.getCostPerHour().multiply(BigDecimal.valueOf(totalHours));
            transaction.setDriverCost(totalDriverCost);
        }


        vehicle.setIsAvailable(true);
        rent.setIsCurrentlyRented(false);
        rent.setVehicle(vehicle);

        transaction.setDuration(Math.toIntExact(totalHours));
        transaction.setTotalCost(totalPrice);
        transaction.setRent(rent);

        return transactionRepository.save(transaction);
    }

    public List<Transaction> findAllTransaction() {
        return transactionRepository.findAll();
    }
    public List<Transaction> findAllTransactionByCustomerId(Long id){
        return this.getAll().stream().filter(transaction -> transaction.getRent().getCustomer().getId() == id).collect(Collectors.toList());
    }
    public List<Transaction> findAllTransactionByDriverId(Long id){
        return this.getAll().stream().filter(transaction -> transaction.getRent().getDriver().getId() == id).collect(Collectors.toList());
    }
//    public List<Transaction> findAllTransactionByDriverId(Long id){
//        return transactionRepository.findAllTransactionByDriverId(id);
//    }
}
