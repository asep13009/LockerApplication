package com.asep.lockerapplication.service;

import com.asep.lockerapplication.entity.LockerPassword;

import java.util.List;

public interface EmailService {



    void sendLockerCredential(
            String to,
            List<LockerPassword> lps
    );
}
