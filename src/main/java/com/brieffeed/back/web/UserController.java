package com.brieffeed.back.web;

import static com.brieffeed.back.security.SecurityContains.TOKEN_PREFIX;

import com.brieffeed.back.domain.User;
import com.brieffeed.back.payload.JWTLoginSuccessResponse;
import com.brieffeed.back.payload.LoginRequest;
import com.brieffeed.back.security.JwtTokenProvider;
import com.brieffeed.back.services.MapValidationErrorService;
import com.brieffeed.back.services.UserService;
import com.brieffeed.back.validator.UserValidator;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
public class UserController {

  @Autowired
  private MapValidationErrorService mapValidationErrorService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserValidator userValidator;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest,
      BindingResult result) {
    ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
    if (errorMap != null) {
      return errorMap;
    }
    Authentication authentication = authenticationManager.authenticate(
        new UsernamePasswordAuthenticationToken(loginRequest.getUsername(),
            loginRequest.getPassword())
    );
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);
    return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
  }

  @PostMapping("/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
    userValidator.validate(user, result);
    ResponseEntity<?> errorMap = mapValidationErrorService.getValidation(result);
    if (errorMap != null) {
      return errorMap;
    }
    User newUser = userService.saveUser(user);
    return new ResponseEntity<>(newUser, HttpStatus.CREATED);
  }
}
