package com.example.seguranca.util;


import com.example.seguranca.enumeration.ProfileEnum;
import com.example.seguranca.security.UserPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;

public class AuthenticationUtil {

    public static Boolean isRoot() {
        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .anyMatch(auth -> auth.getAuthority().equals(ProfileEnum.ROOT.getValue()));
    }

    public static Boolean isAdmin() {
        return SecurityContextHolder.getContext()
            .getAuthentication()
            .getAuthorities()
            .stream()
            .anyMatch(auth -> auth.getAuthority().equals(ProfileEnum.ADMIN.getValue()));
    }

    public static Long getIdUser() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getId();
    }

    public static Long getApplication() {
        UserPrincipal principal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return principal.getApp();
    }

}
