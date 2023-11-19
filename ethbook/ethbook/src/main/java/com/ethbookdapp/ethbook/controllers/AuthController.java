package com.ethbookdapp.ethbook.controllers;

import com.ethbookdapp.ethbook.dao.UserRepository;
import com.ethbookdapp.ethbook.models.FormData;
import com.ethbookdapp.ethbook.models.JWTTokenModel;
import com.ethbookdapp.ethbook.models.ResponseStatus;
import com.ethbookdapp.ethbook.models.User;
import com.ethbookdapp.ethbook.services.AuthService;
import com.ethbookdapp.ethbook.utils.JWTUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.text.SimpleDateFormat;
import java.util.Calendar;

@RestController
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private AuthenticationManager authenticationManager;
    private Logger logger = LoggerFactory.getLogger(AuthController.class);
    @Autowired
    private JWTUtil jwtUtil;


    private String calculateExpiryTime () {
        SimpleDateFormat dateFormatter = new SimpleDateFormat("E-MM-dd'T'HH:mm:ss.SSSZ-yyyy");

        Calendar currentTime = Calendar.getInstance();
        currentTime.add(Calendar.MINUTE, 30);
        String currentTimeString = dateFormatter.format(currentTime.getTime()).toString();

        return currentTimeString;
    }

    @GetMapping(value = "/auth")
    public ResponseEntity<JWTTokenModel> auth(@RequestBody FormData formData) {
//        return ResponseEntity.ok(this.authService.auth(username,password));

        UserDetails userDetails = this.userDetailsService.loadUserByUsername(formData.getEmail());

        if (userDetails == null) {
            return null;
        }

        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(formData.getEmail(), formData.getPassword(),userDetails.getAuthorities());

        JWTTokenModel tokenModel = new JWTTokenModel();

        try {
            authenticationManager.authenticate(token);


            String jwtToken = this.jwtUtil.generateToken(userDetails);

            // this.bLackListedJWTTokenUtil.saveTheGeneratedToken(username, jwtToken, true);


            logger.info("token "+jwtToken);
            tokenModel.setBearer(jwtToken);
            tokenModel.setExpireDate(this.calculateExpiryTime());

        } catch (AuthenticationException e) {
            e.printStackTrace();
        }

        return ResponseEntity.ok(tokenModel);
    }

//    @GetMapping(value = "/register")
//    public ResponseEntity<ResponseStatus> register(@RequestBody User user) {
//        return ResponseEntity.ok(this.authService.register(user));
//    }

    @GetMapping(value  = "/register")
    public ResponseEntity<User> register(@RequestBody FormData formData, RedirectAttributes redirectAttributes) {
        User existingUser = this.userRepository.findUserByEmail(formData.getEmail());
        User newUser = new User();
//        User existingUserByUserId = this.userRepository.getUserById(formData.getUserId());

        if (existingUser != null) {
            this.logger.info("User is already exist by this cred please change the user id or email to register");
            redirectAttributes.addFlashAttribute("message","User is already exist by this cred please change the user id or email to register");

            return ResponseEntity.ok(existingUser);
        } else {
            newUser.setName(formData.getName());
            newUser.setUserId(formData.getUserId());
            newUser.setEmail(formData.getEmail());
            newUser.setPassword(passwordEncoder.encode(formData.getPassword()));

            this.userRepository.save(newUser);
        }

//        return "redirect:/login";
        return ResponseEntity.ok(newUser);
    }

}
