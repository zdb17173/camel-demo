package org.fran.cameldemo.camelspringboot.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StopWatch;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;


@Configuration
@EnableSwagger2
//@EnableWebMvc
@ConfigurationProperties(prefix = "swaggerConfig")
public class SwaggerConfig extends WebMvcConfigurerAdapter {
	private final Logger logger = LoggerFactory.getLogger(SwaggerConfig.class);

	String host;

	@Bean
	public Docket swaggerSpringfoxDocket() {
		logger.debug("Starting Swagger");
		String packagePath = this.getClass().getPackage().getName();
		packagePath = packagePath.substring(0, packagePath.lastIndexOf("."));
		packagePath = packagePath.substring(0, packagePath.lastIndexOf("."));
		logger.debug("base package:" + packagePath);
		StopWatch watch = new StopWatch();
		watch.start();
		Docket swaggerSpringMvcPlugin = new Docket(DocumentationType.SWAGGER_2)
				.host(host)
				.apiInfo(apiInfo())
				.genericModelSubstitutes(ResponseEntity.class)
				.select()
				.apis(RequestHandlerSelectors.basePackage(packagePath))
				.paths(PathSelectors.any())
				.build();
		watch.stop();
		logger.debug("Started Swagger in {} ms", watch.getTotalTimeMillis());
		return swaggerSpringMvcPlugin;
	}

	private ApiInfo apiInfo(){
		return new ApiInfoBuilder()
				.title("cms api")
				.description("cms api")
				.termsOfServiceUrl("serviceTerms")
				.contact(new Contact("fran",
						"zhang.dongbo@cgtn.com",
						"zhang.dongbo@cgtn.com"))
				.license("Apache License Version 2.0")
				.licenseUrl("https://github.com/springfox/springfox/blob/master/LICENSE")
				.version("version")
				.build();
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurerAdapter() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
						.allowedOrigins("*")
						.allowedMethods("POST","GET","HEAD","OPTIONS");
			}
			@Override
			public void addViewControllers(ViewControllerRegistry registry) {
				registry.addViewController("/").setViewName("redirect:/swagger-ui.html");
			}
		};
	}

	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}
}