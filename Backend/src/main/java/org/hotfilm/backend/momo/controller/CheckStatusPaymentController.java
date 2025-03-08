package org.hotfilm.backend.momo.controller;

import org.hotfilm.backend.momo.model.OrderRequestDTO;
import org.hotfilm.backend.momo.service.GetPaymentStatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

@RestController
public class CheckStatusPaymentController {

    @Autowired
    private GetPaymentStatusService paymentStatusService;

    @PostMapping("/api/v1/get-status")
    public ResponseEntity<Map<String, Object>> getStatus(@RequestBody OrderRequestDTO requestDTO) throws IOException {

        Map<String, Object> result = this.paymentStatusService.getStatus(requestDTO);
        return new ResponseEntity<>(result, HttpStatus.OK);

    }

}
