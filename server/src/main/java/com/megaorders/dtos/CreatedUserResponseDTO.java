package com.megaorders.dtos;

import com.megaorders.models.enums.Role;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreatedUserResponseDTO {
    String firstName;
    String middleName;
    String lastName;
    String email;
    Role role;
    String username;
    LocalDate registeredDate;
}
