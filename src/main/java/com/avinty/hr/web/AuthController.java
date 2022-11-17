package com.avinty.hr.web;

import com.avinty.hr.config.Constants;
import com.avinty.hr.config.exception.AvintyException;
import com.avinty.hr.config.payload.JwtResponseDTO;
import com.avinty.hr.config.payload.LoginRequestDTO;
import com.avinty.hr.config.security.jwt.AuthTokenFilter;
import com.avinty.hr.config.security.jwt.JwtUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * Authentication controller to implement JWT token based communication
 * @author mredly
 */
@RestController
@RequiredArgsConstructor
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    // Login for employees
    @PostMapping(Constants.LOGIN_URL)
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequestDTO loginRequest) throws AvintyException {
        try {
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication);
            return ResponseEntity.ok(new JwtResponseDTO(AuthTokenFilter.JWT_PREFIX.concat(jwt)));
        } catch (Exception e) {
            throw new AvintyException("Bad credentials");
        }
    }
}
