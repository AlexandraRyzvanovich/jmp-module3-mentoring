package com.epam.module3;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDate;

@Data
public class UserRequestDto extends RepresentationModel<UserRequestDto>{
    private Long id;

    private String first_name;

    private String last_name;

    private LocalDate birthday;

}
