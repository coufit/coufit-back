package goorm.coutfit.auth;

import goorm.coutfit.user.domain.User;
import goorm.coutfit.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CurrentUserUtil {
    private final HttpServletRequest httpServletRequest;
    private final UserRepository userRepository;

    public User getCurrentUser() {
        String email = (String) httpServletRequest.getAttribute("email");
        if (email == null) {
            throw new RuntimeException("로그인 정보가 없습니다.");
        }
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 유저를 찾을 수 없습니다."));
    }
}
