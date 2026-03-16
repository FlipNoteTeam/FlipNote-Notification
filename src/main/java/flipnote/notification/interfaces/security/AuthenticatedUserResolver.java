package flipnote.notification.interfaces.security;

import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import flipnote.notification.common.exception.BizException;
import flipnote.notification.common.exception.CommonErrorCode;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuthenticatedUserResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		return parameter.hasParameterAnnotation(CurrentUser.class)
			&& AuthenticatedUser.class.isAssignableFrom(parameter.getParameterType());
	}

	@Override
	public Object resolveArgument(
		MethodParameter parameter,
		ModelAndViewContainer mavContainer,
		NativeWebRequest webRequest,
		WebDataBinderFactory binderFactory
	) {
		HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
		if (request == null) {
			throw new BizException(CommonErrorCode.INTERNAL_SERVER_ERROR);
		}

		AuthenticatedUser user = (AuthenticatedUser) request.getAttribute(
			UserIdHeaderFilter.AUTHENTICATED_USER_ATTRIBUTE
		);

		if (user == null) {
			throw new UnauthorizedException();
		}

		return user;
	}
}
