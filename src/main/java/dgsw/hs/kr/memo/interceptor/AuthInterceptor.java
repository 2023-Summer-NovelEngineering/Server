package dgsw.hs.kr.memo.interceptor;

import dgsw.hs.kr.memo.annotation.CheckToken;
import dgsw.hs.kr.memo.entity.Member;
import dgsw.hs.kr.memo.jwt.JwtUtils;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Objects;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final JwtUtils jwtUtils;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        log.info("request url : {}", request.getRequestURI());
        System.out.println("interceptor");

        if (!(handler instanceof HandlerMethod handlerMethod)) {
            return true;
        }

        if (!handlerMethod.getMethod().isAnnotationPresent(CheckToken.class)) {
            return true;
        }
        System.out.println("here");

        String token = jwtUtils.extract(request, "Bearer");
        System.out.println("token : " + token);

        if (Objects.equals(token, "")) {
            throw new IllegalArgumentException("토큰이 입력되지 않았습니다.");
        }

        final Member member = jwtUtils.validateToken(token);
        request.setAttribute("member", member);

        return true;
    }
}