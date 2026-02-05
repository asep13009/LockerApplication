package com.asep.lockerapplication;

import com.asep.lockerapplication.entity.Locker;
import com.asep.lockerapplication.repository.LockerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class LockerApplication  implements CommandLineRunner {

    @Autowired
    private LockerRepository lockerRepository;

    public static void main(String[] args) {
        SpringApplication.run(LockerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        try{
            if(lockerRepository.count()<1){
                for (int i = 1; i <= 10; i++) {
                    Locker locker = new Locker();
                    locker.setLockerNumber("L" + String.format("%03d", i));
                    lockerRepository.save(locker);
                    System.out.println("create locker = "+ locker.getLockerNumber());
                }
            }
        } catch (Exception e) {
           e.printStackTrace();
        }
        System.out.println("Locker available =  10");


    }

}
