package com.webapp.ftm.utility.mapper;

import com.nimbusds.jose.util.Base64;
import com.webapp.ftm.dto.response.ResLoginDTO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Optional;

@Service
public class SecurityUtil {
    private final JwtEncoder jwtEncoder;
    public SecurityUtil(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }
    public static final MacAlgorithm JWT_ALGORITHM = MacAlgorithm.HS512;

    @Value("${jwt.hoidanit.jwt.base64-secret}")
    private String jwtKey;

    @Value("${jwt.access-token-expiration}")
    private long accessTokenExpiration;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    public String createAccessToken(String email, ResLoginDTO.UserLogin dto){
        Instant now= Instant.now();
        Instant validate=now.plus(this.accessTokenExpiration, ChronoUnit.SECONDS);
//        List<String> authorities=new ArrayList<>();
//        authorities.add("ROLE_USER_CREATE");
//        authorities.add("ROLE_USER_UPDATE");
        ResLoginDTO.UserInsideToken userToken= new ResLoginDTO.UserInsideToken();
        userToken.setId(dto.getId());
        userToken.setEmail(dto.getEmail());
        userToken.setName(dto.getName());


        //Format claim
        JwtClaimsSet claims= JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validate)
                .subject(email)
                .claim("user",userToken)
//                .claim("permission",authorities)
                .claim("user",dto)
                .build();
        //Create header
        JwsHeader jwsHeader= JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }
    public String createRefreshToken(String email, ResLoginDTO resLoginDTO){
        Instant now= Instant.now();
        Instant validate=now.plus(this.refreshTokenExpiration, ChronoUnit.SECONDS);
        ResLoginDTO.UserInsideToken userToken= new ResLoginDTO.UserInsideToken();
        userToken.setId(resLoginDTO.getUser().getId());
        userToken.setEmail(resLoginDTO.getUser().getEmail());
        userToken.setName(resLoginDTO.getUser().getName());

        //Format claim
        JwtClaimsSet claims= JwtClaimsSet.builder()
                .issuedAt(now)
                .expiresAt(validate)
                .subject(email)
                .claim("user", userToken)
                .build();
        //Create header
        JwsHeader jwsHeader= JwsHeader.with(JWT_ALGORITHM).build();

        return this.jwtEncoder.encode(JwtEncoderParameters.from(jwsHeader,claims)).getTokenValue();
    }

    public Jwt checkValidateRefreshToken(String refreshToken){
        NimbusJwtDecoder jwtDecoder = NimbusJwtDecoder.withSecretKey(this.getSecretKey()).macAlgorithm(SecurityUtil.JWT_ALGORITHM)
                .build();

        try{
            return jwtDecoder.decode(refreshToken);
        }
        catch (Exception e){
            System.out.println("error: "+ e.getMessage());
            throw e;
        }

    }

    public static Optional<String> getCurrentUserLogin(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication){
        if(authentication==null){
            return null;
        } else if(authentication.getPrincipal() instanceof UserDetails springSecurityUser){
            return springSecurityUser.getUsername();
        } else if (authentication.getPrincipal() instanceof  Jwt jwt){
            return jwt.getSubject();
        } else if(authentication.getPrincipal() instanceof String s){
            return s;
        }
        return null;
    }

    public static Optional<String> getCurrentUserJWT(){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(securityContext.getAuthentication())
                .filter(authentication -> authentication.getCredentials() instanceof String)
                .map(authentication -> (String) authentication.getCredentials());
    }

    public SecretKey getSecretKey() {
        byte[] keyBytes = Base64.from(jwtKey).decode();
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, JWT_ALGORITHM.getName());
    }
//    public static boolean isAuthenticated(){
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        return authentication!=null && authentication.getAuthorities().stream().map(GrantedAuthority::getAuthority)
//                .noneMatch(AuthoritiesConstants.ANONYMOUS::equals)
//    }


}
