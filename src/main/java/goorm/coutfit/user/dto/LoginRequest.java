package goorm.coutfit.user.dto;

import lombok.Getter;
import org.springframework.stereotype.Service;

@Getter
@Service
public class LoginRequest {
    private String email;
    private String password;
}
