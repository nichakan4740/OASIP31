package sit.int221.oasip.Service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import sit.int221.oasip.DTO.ResponseRefreshTokenDTO;
import sit.int221.oasip.Error.ValidationHandler;
import sit.int221.oasip.config.JwtTokenUtil;

import javax.servlet.http.HttpServletRequest;

@Service
public class RefreshService {
    @Autowired
    private JwtTokenUtil jwtTokenUtill;

    @Autowired
    private JwtUserDetailsService jwtUserDetailsService;

    public ResponseEntity refreshToken(HttpServletRequest request){
        String requestRefreshToken = request.getHeader("Authorization").substring(7);
        String userRefreshToken = jwtTokenUtill.getUsernameFromToken(requestRefreshToken);
        UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userRefreshToken);

        if (checkExpired(requestRefreshToken).equals(true)) {
            String refreshToken = jwtTokenUtill.generateRefreshToken(userDetails);
            return ResponseEntity.ok(new ResponseRefreshTokenDTO("Refresh Token Success", refreshToken));
        }
        return ValidationHandler.ExceptionError(HttpStatus.NOT_FOUND, "Can't find Refresh Token");
    }

    private Boolean checkExpired(String request){
        if(!jwtTokenUtill.isTokenExpired(request)){
            return true;
        }
        return false;
    }

}
