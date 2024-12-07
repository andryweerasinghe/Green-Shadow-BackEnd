package lk.ijse.greenshadow.secure;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class JWTAuthResponse {   //a dto used to send a JSON response with the jwt token
    private String token;
}
