package com.megaorders.controllers;

import com.megaorders.dtos.CreatedUserRequestDTO;
import com.megaorders.dtos.CreatedUserResponseDTO;
import com.megaorders.models.User;
import com.megaorders.repositories.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/admin")
public class AdminController {
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final ModelMapper mapper;

    public AdminController(PasswordEncoder passwordEncoder, UserRepository userRepository, ModelMapper mapper) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    @PostMapping("/register-multiple")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<CreatedUserResponseDTO[]> registerMultipleUser(@RequestBody CreatedUserRequestDTO[] users) {
        List<CreatedUserResponseDTO> response = new ArrayList<>();
        try {
            for (CreatedUserRequestDTO createdUserRequestDTO : users) {
                User user = mapper.map(createdUserRequestDTO, User.class);
                user.setPassword(passwordEncoder.encode(user.getPassword()));
                CreatedUserResponseDTO res = mapper.map(userRepository.save(user), CreatedUserResponseDTO.class);
                response.add(res);
            }
            return ResponseEntity.status(HttpStatus.CREATED).body(response.toArray(new CreatedUserResponseDTO[0]));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }
    }
}

