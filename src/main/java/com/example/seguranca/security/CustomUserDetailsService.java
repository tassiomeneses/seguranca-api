package com.example.seguranca.security;



import com.example.seguranca.exception.RuntimeException;
import com.example.seguranca.modal.ControlProfile;
import com.example.seguranca.modal.User;
import com.example.seguranca.repository.ControlProfileRepository;
import com.example.seguranca.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private ControlProfileRepository controlProfileRepository;

    @Autowired
    private HttpServletRequest request;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Long app = (Long) request.getSession().getAttribute("app");
        User user = repository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("error.user.notFound"));
        List<ControlProfile> profiles = controlProfileRepository.findByUserAndApplication(user.getId(), app);

        if (profiles.isEmpty()) {
            throw new RuntimeException("error.user.notAuthorized");
        }

        if (!user.getActive()) {
            throw new RuntimeException("error.user.inactive");
        }

        return UserPrincipal.create(
            user.getId(),
            user.getName(),
            user.getLogin(),
            user.getEmail(),
            user.getPassword(),
            getProfile(user.getControlProfileList()),
           null,
           // getSector(user),
            user.getCpf(),
            Objects.isNull(user.getLastAccess()),
            user.getLastAccess(),
            (Long) request.getSession().getAttribute("app")
        );
    }

    private List<String> getProfile(List<ControlProfile> controlProfiles) {
        Long app = (Long) request.getSession().getAttribute("app");

        return controlProfiles
                .stream()
                .filter(c -> Objects.equals(c.getApplication().getId(), app))
                .map(c -> c.getProfile().getKey())
                .collect(Collectors.toList());
    }

//    private Map<String, Object> getSector(User user) {
//        if (Objects.isNull(user.getSector())) {
//            return null;
//        }
//
//        Map<String, Object> sector = new HashMap<>();
//        sector.put("id", user.getSector().getId());
//        sector.put("name", user.getSector().getName());
//        sector.put("initials", user.getSector().getInitials());
//
//        return sector;
//    }

}
