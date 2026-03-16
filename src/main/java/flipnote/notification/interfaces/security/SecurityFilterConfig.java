package flipnote.notification.interfaces.security;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SecurityFilterConfig {

	@Bean
	public UserIdHeaderFilter userIdHeaderFilter() {
		return new UserIdHeaderFilter();
	}

	@Bean
	public FilterRegistrationBean<UserIdHeaderFilter> userIdFilterRegistration(
		UserIdHeaderFilter userIdHeaderFilter
	) {
		FilterRegistrationBean<UserIdHeaderFilter> registration = new FilterRegistrationBean<>();
		registration.setFilter(userIdHeaderFilter);
		registration.addUrlPatterns("/v1/*");
		registration.setOrder(1);
		return registration;
	}
}
