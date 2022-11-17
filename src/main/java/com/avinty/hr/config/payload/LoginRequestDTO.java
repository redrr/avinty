package com.avinty.hr.config.payload;

import lombok.Getter;
import lombok.Setter;

/**
 * DTO for login operation
 * @author mredly
 */
@Getter
@Setter
public class LoginRequestDTO {

    private String email;
    private String password;
}
