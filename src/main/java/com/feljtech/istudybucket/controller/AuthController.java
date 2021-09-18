package com.feljtech.istudybucket.controller;

import com.feljtech.istudybucket.dto.form.LoginForm;
import com.feljtech.istudybucket.dto.form.RegisterForm;
import com.feljtech.istudybucket.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author Elroy Kanye
 *
 * Modified on: 12-09-2021
 * Modified by: Elroy Kanye
 *
 * Description: Handles all user authentication services
 */

@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * registers a user from a RegisterForm object, consumed from JSON.
     * typically, control is passed to the userService bean.
     * @param registerForm: the form as an object - spring auto converts based on input names.
     * @return http status of the process
     */
    @PostMapping(value = "/register")
    public ResponseEntity<String> register(@RequestBody RegisterForm registerForm) {
        authService.registerAccount(registerForm);
        return new ResponseEntity<>("User registration successful", HttpStatus.OK);
    }

    /**
     * Activates the user verification process using the sent token via email
     * @param verificationToken: the string token received by the user
     * @param username: username of the associated user
     * @return response entity object describing success of operation
     */
    @GetMapping(value = "/verify/{verToken}")
    public ResponseEntity<String> verify(@PathVariable(value = "verToken") String verificationToken, String username) {
        return authService.verifyAccount(verificationToken, username) ?
                new ResponseEntity<>("User verification successful", HttpStatus.ACCEPTED):
                new ResponseEntity<>("User verification unsuccessful", HttpStatus.BAD_REQUEST);
    }

    @PostMapping(value = "/login")
    public ResponseBody login(@RequestBody LoginForm loginForm) {
        return (ResponseBody) authService.loginUser(loginForm);
    }
}
