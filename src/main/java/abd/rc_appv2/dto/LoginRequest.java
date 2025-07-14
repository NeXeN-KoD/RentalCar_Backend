package abd.rc_appv2.dto;

import lombok.Data;

@Data
public class LoginRequest {
    private String email;
    private String motDePasse;
}
