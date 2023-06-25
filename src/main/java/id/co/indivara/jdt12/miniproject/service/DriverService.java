package id.co.indivara.jdt12.miniproject.service;

import id.co.indivara.jdt12.miniproject.entity.Driver;
import id.co.indivara.jdt12.miniproject.error.GetDriverByLevelAndLowestCountException;
import id.co.indivara.jdt12.miniproject.repo.DriverRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class DriverService extends GenericService<Driver> {
    @Autowired
    private DriverRepository driverRepository;

    //mengatur level dan menghitung berapa kali driver mendapat pesanan
    public Driver getDriverByLevelandLowCount(Integer level){
        if(driverRepository.filteringTheDriverByLevelAndCount(level) == null) throw new GetDriverByLevelAndLowestCountException("Maaf, Driver yang anda cari tidak tersedia atau sedang menjalankan rental");
        return driverRepository.filteringTheDriverByLevelAndCount(level);
    }

//    public Driver getDriverByLevelandLowCount(Integer level){
//        return driverRepository.filteringTheDriverByLevelAndCount(level) != null ? driverRepository.filteringTheDriverByLevelAndCount(level) : new Driver();
//    }
}
