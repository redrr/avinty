package com.avinty.hr.config.payload;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldNameConstants;

/**
 * Login response DTO - JWT token
 * @author mredly
 */
@Getter
@Setter
@AllArgsConstructor
@FieldNameConstants
public class JwtResponseDTO {

    private String token;
}
