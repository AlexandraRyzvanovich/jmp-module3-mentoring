package com.epam.module3;

import lombok.Data;import org.springframework.hateoas.RepresentationModel;

@Data
public class SubscriptionResponseDto extends RepresentationModel<SubscriptionResponseDto>{
    private Long id;
    private Long user_id;
    private String startDate;

}
