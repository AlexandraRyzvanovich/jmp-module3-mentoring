package com.epam.module3;

import io.swagger.v3.oas.models.info.Info;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.actuate.autoconfigure.endpoint.web.WebEndpointProperties;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import static org.springdoc.core.utils.Constants.ALL_PATTERN;

@SpringBootApplication
public class JmpApplication {

  @Bean
  public SubscriptionConverter subscriptionConverter() {
    return new SubscriptionConverter();
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper mapper = new ModelMapper();

    mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.LOOSE);
    mapper.addConverter(subscriptionConverter().entityToResponseDto);
    mapper.addConverter(subscriptionConverter().requestDtoToEntity);

    return mapper;
  }

//  @Bean
//  public GroupedOpenApi actuatorApi(
//      OperationCustomizer actuatorCustomizer,
//      WebEndpointProperties endpointProperties,
//      @Value("${springdoc.version}") String appVersion) {
//    return GroupedOpenApi.builder()
//        .group("Actuator")
//        .pathsToMatch(endpointProperties.getBasePath() + ALL_PATTERN)
//        .addOpenApiCustomizer(
//            openApi -> openApi.info(new Info().title("Actuator API").version(appVersion)))
//        .addOperationCustomizer(actuatorCustomizer)
//        .pathsToExclude("/health/*")
//        .build();
//  }

  public static void main(String[] args) {
    SpringApplication.run(JmpApplication.class, args);
  }
}
