package com.example.springappleapi.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class CartUpdatePayload {
    @Getter@Setter
    private String operation;
    @Getter@Setter
    private long productId;
    @Getter@Setter
    private int quantity;
}
