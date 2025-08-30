package com.megaorders.dtos;

import com.megaorders.models.enums.Role;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class CreatedUserRequestDTO {
    String username;
    String firstName;
    String middleName;
    String lastName;
    String email;
    Role role;
    String password;
//    Boolean isPresent;
}
