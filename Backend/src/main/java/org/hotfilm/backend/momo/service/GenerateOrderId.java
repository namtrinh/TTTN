package org.hotfilm.backend.momo.service;

import org.springframework.stereotype.Service;

import java.util.Random;

@Service
public class GenerateOrderId {

    public String generateOrderId() {
        String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        Random random = new Random();
        StringBuilder orderId = new StringBuilder();

        for (int i = 0; i < 12; i++) {
            int index = random.nextInt(characters.length());
            orderId.append(characters.charAt(index));
        }
        return orderId.toString();
    }
}