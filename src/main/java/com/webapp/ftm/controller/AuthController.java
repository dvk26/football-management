package com.webapp.ftm.controller;

import com.webapp.ftm.dto.RoleDTO;
import com.webapp.ftm.dto.UserDTO;
import com.webapp.ftm.dto.request.ReqLoginDTO;
import com.webapp.ftm.dto.response.ResLoginDTO;
import com.webapp.ftm.exception.custom.InvalidTournamentSeasonException;
import com.webapp.ftm.model.UserEntity;
import com.webapp.ftm.service.UserService;
import com.webapp.ftm.utility.mapper.SecurityUtil;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/v1/auth/")
public class AuthController {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    private final SecurityUtil securityUtil;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenExpiration;
    public AuthController(AuthenticationManagerBuilder authenticationManagerBuilder, SecurityUtil securityUtil, UserService userService, ModelMapper modelMapper) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.securityUtil=securityUtil;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("login")
    public ResponseEntity login(@Valid @RequestBody ReqLoginDTO reqLoginDTO){
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken(reqLoginDTO.getUsername(), reqLoginDTO.getPassword());
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);

        SecurityContextHolder.getContext().setAuthentication(authentication);


        // find the current login user
        UserEntity currentLoginUser= userService.findByEmail(reqLoginDTO.getUsername());

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        ResLoginDTO.UserLogin userLogin = new ResLoginDTO.UserLogin( currentLoginUser.getId(),
                currentLoginUser.getEmail(),
                currentLoginUser.getName(),
                null
        );
        if (currentLoginUser.getRole() == null) {
            userLogin.setRole(null);
        } else {
            userLogin.setRole(modelMapper.map(currentLoginUser.getRole(), RoleDTO.class));
        }
        resLoginDTO.setUser(userLogin);

        String accessToken = securityUtil.createAccessToken(authentication.getName(),userLogin);
        resLoginDTO.setAccessToken(accessToken);
        //save refresh token for user
        String refreshToken= securityUtil.createRefreshToken(currentLoginUser.getEmail(), resLoginDTO);
        System.out.println(refreshToken);
        userService.updateUserToken(refreshToken, currentLoginUser.getEmail());



        //set cookies
        ResponseCookie responseCookie=ResponseCookie
                .from("refresh_token", refreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
                .body(resLoginDTO);

    }

    @GetMapping("account")
    public ResponseEntity<ResLoginDTO.UserGetAccount> getAccount(){
        String email = SecurityUtil.getCurrentUserLogin().isPresent()?
                SecurityUtil.getCurrentUserLogin().get(): "";
        UserEntity userEntity = userService.findByEmail(email);
        RoleDTO role= null;
        if(userEntity.getRole()!=null) {
            role=modelMapper.map(userEntity.getRole(),RoleDTO.class);
        }

        return ResponseEntity.ok().body(new ResLoginDTO.UserGetAccount(new ResLoginDTO.UserLogin( userEntity.getId(),userEntity.getEmail(), userEntity.getName(),
                role)));
    }

    @GetMapping("refresh")
    public  ResponseEntity<ResLoginDTO> getRefreshToken(
            @CookieValue(name="refresh_token") String refreshToken
    ){
        Jwt decodedToken=securityUtil.checkValidateRefreshToken(refreshToken);
        String email = decodedToken.getSubject();
        UserEntity currentUser= userService.findByRefreshTokenAndEmail(refreshToken,email);
        if(currentUser==null){
            throw new InvalidTournamentSeasonException("Refresh Token không phù hợp!",null);
        }

        //issue new token/set refresh token as cookies
        ResLoginDTO res=new ResLoginDTO();
        UserEntity currentUserDB= userService.findByEmail(email);

        ResLoginDTO resLoginDTO = new ResLoginDTO();
        ResLoginDTO.UserLogin user = new ResLoginDTO.UserLogin(
                currentUserDB.getId(),
                currentUserDB.getEmail(),
                currentUserDB.getName(),
                null
        );
        if (currentUserDB.getRole() == null) {
            user.setRole(null);
        } else {
            user.setRole(modelMapper.map(currentUserDB.getRole(), RoleDTO.class));
        }
        resLoginDTO.setUser(user);

        String accessToken= securityUtil.createAccessToken(email, resLoginDTO.getUser());

        String newRefreshToken= securityUtil.createRefreshToken(email, resLoginDTO);
        userService.updateUserToken(newRefreshToken,email);

        resLoginDTO.setAccessToken(accessToken);
        //set cookies
        ResponseCookie responseCookie=ResponseCookie
                .from("refresh_token", newRefreshToken)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(refreshTokenExpiration)
                .build();

        return ResponseEntity.ok()
                .header(HttpHeaders.SET_COOKIE,responseCookie.toString())
                .body(resLoginDTO);

    }

    @PostMapping("logout")
    public ResponseEntity logout(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.updateUserToken(null,authentication.getName());
        ResponseCookie deleteSpringCookie=ResponseCookie
                .from("refresh_token", null)
                .httpOnly(true)
                .secure(true)
                .path("/")
                .maxAge(0)
                .build();

        return ResponseEntity
                .ok()
                .header(HttpHeaders.SET_COOKIE, deleteSpringCookie.toString())
                .build();
    }

    @PostMapping("register")
    private ResponseEntity register(@RequestBody UserDTO userDTO){
        userService.handleCreateUser(userDTO);
        return ResponseEntity.ok().body(userDTO);
    }

}
