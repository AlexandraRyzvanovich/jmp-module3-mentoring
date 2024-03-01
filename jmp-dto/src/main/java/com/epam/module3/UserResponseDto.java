package com.epam.module3;

import lombok.Data;
import org.springframework.hateoas.RepresentationModel;

@Data
public class UserResponseDto extends RepresentationModel<UserResponseDto>{
    private Long id;
    private String first_name;
    private String last_name;
    private String birthday;


}
