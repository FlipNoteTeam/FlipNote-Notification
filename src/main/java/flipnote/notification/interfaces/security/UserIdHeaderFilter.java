package flipnote.notification.interfaces.security;

import java.io.IOException;

import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class UserIdHeaderFilter extends OncePerRequestFilter {

	public static final String USER_ID_HEADER = "X-User-Id";
	static final String AUTHENTICATED_USER_ATTRIBUTE = "authenticatedUser";

	@Override
	protected void doFilterInternal(
		HttpServletRequest request,
		HttpServletResponse response,
		FilterChain filterChain
	) throws ServletException, IOException {
		String userIdHeader = request.getHeader(USER_ID_HEADER);

		if (StringUtils.hasText(userIdHeader)) {
			try {
				Long userId = Long.valueOf(userIdHeader.trim());
				request.setAttribute(AUTHENTICATED_USER_ATTRIBUTE, new AuthenticatedUser(userId));
			} catch (NumberFormatException ex) {
				log.debug("Invalid X-User-Id header value: {}", userIdHeader);
			}
		}

		filterChain.doFilter(request, response);
	}
}
