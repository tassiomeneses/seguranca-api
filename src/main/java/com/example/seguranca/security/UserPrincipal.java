package com.example.seguranca.security;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

    private Long id;

    private String name;

    private String username;

    private String cpf;

    @JsonIgnore
    private String email;

    @JsonIgnore
    private String password;

    private Collection<? extends GrantedAuthority> authorities;

    private Map<String, Object> sector;

    private Boolean firstAccess;

    private LocalDateTime lastAccess;

    private Long app;

    public UserPrincipal(
            Long id, String name, String username, String email, String password, Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> sector, String cpf, Boolean firstAccess, LocalDateTime lastAccess, Long app
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.authorities = authorities;
        this.sector = sector;
        this.cpf = cpf;
        this.firstAccess = firstAccess;
        this.lastAccess = lastAccess;
        this.app = app;
    }

    public UserPrincipal(
            Long id, String name, String username, String email, Collection<? extends GrantedAuthority> authorities,
            Map<String, Object> sector, String cpf, Boolean firstAccess, LocalDateTime lastAccess, Long app
    ) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.authorities = authorities;
        this.sector = sector;
        this.cpf = cpf;
        this.firstAccess = firstAccess;
        this.lastAccess = lastAccess;
        this.app = app;
    }

    public static UserPrincipal create(
            Long id, String name, String username, String email, String password, List<String> authorities,
            Map<String, Object> sector, String cpf, Boolean firstAccess, LocalDateTime lastAccess, Long app
    ) {
        return new UserPrincipal(
                id,
                name,
                username,
                email,
                password,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                sector,
                cpf,
                firstAccess,
                lastAccess,
                app
        );
    }

    public static UserPrincipal create(
            Long id, String name, String username, String email, List<String> authorities,
            Map<String, Object> sector, String cpf, Boolean firstAccess, LocalDateTime lastAccess, Long app
    ) {
        return new UserPrincipal(
                id,
                name,
                username,
                email,
                authorities.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList()),
                sector,
                cpf,
                firstAccess,
                lastAccess,
                app
        );
    }

    public UserPrincipal(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public UserPrincipal(Long id, String name, String username, String email) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
    }

    public UserPrincipal(Long id, String name, String username, String email, String password) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public Map<String, Object> getSector() {
        return sector;
    }

    public void setSector(Map<String, Object> sector) {
        this.sector = sector;
    }

    public Boolean getFirstAccess() {
        return firstAccess;
    }

    public Long getApp() {
        return app;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserPrincipal that = (UserPrincipal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
