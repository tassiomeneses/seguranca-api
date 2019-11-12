package com.example.seguranca.service;


import com.example.seguranca.modal.ControlProfile;
import com.example.seguranca.modal.User;
import com.example.seguranca.repository.ControlProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ControlProfileService extends GenericService<User> {

    @Autowired
    private ControlProfileRepository repository;
//
//    @Autowired
//    private ControlFunctionalityService controlFunctionalityService;

    public void save(List<ControlProfile> profiles, User user) {
        if(Objects.nonNull(profiles)) {
            profiles.forEach(p -> {
                ControlProfile profile = repository.save(new ControlProfile(p.getApplication(), user, p.getProfile()));
                //controlFunctionalityService.save(p.getFunctionalityList(), profile);
            });
        }
    }
//
//    @Transactional
//    public void update (List<ControlProfile> profiles, User user) {
//        delete(profiles, user);
//
//        profiles.forEach(p -> {
//            p.setUser(user);
//            controlFunctionalityService.update(p.getFunctionalityList(), super.save(p));
//        });
//    }

    private void delete(List<ControlProfile> profiles, User user) {
        List<Long> mapProfiles = profiles
                .stream()
                .filter(p -> Objects.nonNull(p.getId()))
                .map(ControlProfile::getId)
                .collect(Collectors.toList());

        List<Long> profilesToDelete = repository.getByUser(user.getId())
                .stream()
                .filter(p -> !mapProfiles.contains(p))
                .collect(Collectors.toList());

        if (profilesToDelete.size() > 0) {
            //controlFunctionalityService.deleteByControlProfile(profilesToDelete);
            repository.deleteByIdIn(profilesToDelete);
        }
    }

//    public List<ControlProfile> findByUser(Long user) {
//        List<ControlProfile> controlProfiles;
//        if (AuthenticationUtil.isRoot()) {
//            controlProfiles = repository.findByUser(user);
//        } else {
//            controlProfiles = repository.findByUserAndApplication(user, AuthenticationUtil.getApplication());
//        }
//
//        return buildEntityToJson(
//            controlProfiles
//                .stream()
//                .map(this::getControlProfile)
//                .collect(Collectors.toList())
//        );
//    }
//
//    private ControlProfile getControlProfile(ControlProfile cp) {
//        if (Objects.nonNull(cp.getControlFunctionalityList()) && cp.getControlFunctionalityList().size() > 0) {
//            cp.setFunctionalityList(cp.getControlFunctionalityList().stream().map(ControlFunctionality::getFunctionality).collect(Collectors.toList()));
//        } else {
//            cp.setFunctionalityList(cp.getProfile().getFunctionalityList());
//        }
//
//        return cp;
//    }
//
//    @Override
//    public void getProperties() {
//        propertiesToReturn = new ControlProfile().getProperties().toArray(new String[0]);
//    }
}
