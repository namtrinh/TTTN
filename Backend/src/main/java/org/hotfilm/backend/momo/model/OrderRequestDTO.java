package org.hotfilm.backend.momo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderRequestDTO {

    private Long amount;
    private String orderId;
    private String orderInfo;
    private String extraData;

}
