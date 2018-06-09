package com.ogonek.eventsappserver;

import com.ogonek.eventsappserver.filter.CORSFilter;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;

/**
 * Главный класс приложения, содержащий Main метод
 */
@SpringBootApplication
public class EventsAppServerApplication {

	/**
	 * Main метод, запускающий SpringBootApp
	 */
	public static void main(String[] args) {
		SpringApplication.run(EventsAppServerApplication.class, args);
	}

	/**
	 * CORS фильтр, чтобы нормально работали запросы с других сайтов
	 */
	@Bean
	public FilterRegistrationBean corsFilterRegistration() {
		FilterRegistrationBean registrationBean =
				new FilterRegistrationBean(new CORSFilter());
		registrationBean.setName("CORS Filter");
		registrationBean.addUrlPatterns("/*");
		registrationBean.setOrder(1);
		return registrationBean;
	}
}
