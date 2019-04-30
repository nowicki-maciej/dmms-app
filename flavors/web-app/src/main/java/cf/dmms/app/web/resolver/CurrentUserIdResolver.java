package cf.dmms.app.web.resolver;

import cf.dmms.app.usermanagement.user.principal.UserPrincipal;
import org.springframework.core.MethodParameter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

class CurrentUserIdResolver implements HandlerMethodArgumentResolver {

	@Override
	public boolean supportsParameter(MethodParameter methodParameter) {
		return methodParameter.getParameterAnnotation(CurrentUserId.class) != null;
	}

	@Override
	public Object resolveArgument(
			MethodParameter methodParameter,
			ModelAndViewContainer modelAndViewContainer,
			NativeWebRequest nativeWebRequest,
			WebDataBinderFactory webDataBinderFactory) {

		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder
				.getContext()
				.getAuthentication()
				.getPrincipal();

		return userPrincipal.getId();
	}
}
