```java
package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

}

package com.example.demo.controller;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import com.example.demo.service.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class LoginController {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * Handle user login with Multi-Factor Authentication.
     * @param loginRequest contains user's login credentials.
     * @return a response indicating success or failure of the login process.
     */
    @PostMapping("/login")
    public LoginResponse loginUser(@RequestBody LoginRequest loginRequest) {
        return authenticationService.authenticate(loginRequest);
    }
}

package com.example.demo.model;

public class LoginRequest {
    private String username;
    private String password;
    private String mfaToken;

    // Getters and setters
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMfaToken() {
        return mfaToken;
    }

    public void setMfaToken(String mfaToken) {
        this.mfaToken = mfaToken;
    }
}

package com.example.demo.model;

public class LoginResponse {
    private boolean success;
    private String message;

    // Getters and setters
    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}

package com.example.demo.service;

import com.example.demo.model.LoginRequest;
import com.example.demo.model.LoginResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    /**
     * Authenticate user login request with Multi-Factor Authentication.
     * @param loginRequest contains user's login credentials.
     * @return a response indicating success or failure of the login process.
     */
    public LoginResponse authenticate(LoginRequest loginRequest) {
        LoginResponse loginResponse = new LoginResponse();

        // Security: Validate user input
        if (isValidLoginRequest(loginRequest)) {
            // Implement MFA verification logic here
            boolean isMfaValid = verifyMfaToken(loginRequest.getMfaToken());
            if (isMfaValid) {
                loginResponse.setSuccess(true);
                loginResponse.setMessage("Login successful.");
            } else {
                loginResponse.setSuccess(false);
                loginResponse.setMessage("Invalid MFA token.");
            }
        } else {
            loginResponse.setSuccess(false);
            loginResponse.setMessage("Invalid credentials.");
        }

        return loginResponse;
    }

    private boolean isValidLoginRequest(LoginRequest loginRequest) {
        return loginRequest.getUsername() != null && !loginRequest.getUsername().isEmpty() &&
               loginRequest.getPassword() != null && !loginRequest.getPassword().isEmpty();
    }

    private boolean verifyMfaToken(String mfaToken) {
        // Implement actual MFA verification logic here
        return "123456".equals(mfaToken); // Dummy implementation
    }
}
```
