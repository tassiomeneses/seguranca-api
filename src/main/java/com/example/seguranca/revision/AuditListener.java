package com.example.seguranca.revision;


import com.example.seguranca.modal.audit.AuditInfoEntity;
import com.example.seguranca.security.UserPrincipal;
import org.hibernate.envers.RevisionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

public class AuditListener implements RevisionListener {

    @Autowired
    private HttpServletRequest request;

    @Override
    public void newRevision(Object o) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal principal;

        if (auth.getPrincipal() instanceof UserPrincipal) {
            principal = (UserPrincipal) auth.getPrincipal();
        } else {
            principal = new UserPrincipal(
                (Long) request.getSession().getAttribute("user.id"),
                (String) request.getSession().getAttribute("user.username")
            );
        }

        AuditInfoEntity audEntity = (AuditInfoEntity) o;
        audEntity.setDateOperation(LocalDateTime.now());

        if(Objects.nonNull(principal)) {
            audEntity.setIdUser(principal.getId());
            audEntity.setName(Optional.ofNullable(principal.getName()).orElse(principal.getUsername()));
        }
    }
}
