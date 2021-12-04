package com.example.springappleapi.Utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class SignupPayload {
    @Getter@Setter
    private String username;
    @Getter@Setter
    private String password;
    @Getter@Setter
    private String email;
}
