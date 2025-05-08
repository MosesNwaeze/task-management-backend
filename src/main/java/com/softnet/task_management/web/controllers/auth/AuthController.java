package com.softnet.task_management.web.controllers.auth;

import com.softnet.task_management.domains.user.UserService;
import com.softnet.task_management.exception.DuplicateEntityException;
import com.softnet.task_management.utils.JwtUtil;
import com.softnet.task_management.web.controllers.user.UserRequestDTO;
import com.softnet.task_management.web.controllers.user.UserResponseDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auths")
@RequiredArgsConstructor
public class AuthController {
  private final Logger LOGGER = LoggerFactory.getLogger(AuthController.class);

  private final AuthenticationManager authenticationManager;
  private final JwtUtil jwtUtil;
  private final UserService userService;

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDTO> authenticateUser(@Valid @RequestBody AuthRequestDTO user) {

    Authentication authentication = authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(
        user.email(),
        user.password()
      )
    );
    UserDetails userDetails = (UserDetails) authentication.getPrincipal();

    String token = jwtUtil.generateToken(userDetails.getUsername());

    AuthResponseDTO authResponseDTO = new AuthResponseDTO(token, userService.userByEmail(user.email()));

    return ResponseEntity.ok().body(authResponseDTO);
  }

  @PostMapping("/register")
  public ResponseEntity<UserResponseDTO> registerUser(@RequestBody @Valid UserRequestDTO user) throws DuplicateEntityException {
    return ResponseEntity.ok().body(userService.register(user));
  }


}
