package com.example.seguranca.service;


import com.example.seguranca.enumeration.ProfileEnum;
import com.example.seguranca.exception.RuntimeException;
import com.example.seguranca.modal.User;
import com.example.seguranca.repository.UserRepository;
import com.example.seguranca.security.UserPrincipal;
import com.example.seguranca.util.AuthenticationUtil;
import com.example.seguranca.util.MessageUtil;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService extends GenericService<User> {

//	@Autowired
//	private CompanyService companyService;

//	@Autowired
//	private SectorService sectorService;

//	@Autowired
//	private EmailService emailService;

	@Autowired
	private ControlProfileService controlProfileService;

	@Autowired
	private UserRepository repository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Override
	@Transactional
	public User save(User entity) {
		List<String> listException = new ArrayList<>();
		entity.setPassword(RandomStringUtils.randomAlphanumeric(20));

		try {
			User user = save(entity, listException);
			controlProfileService.save(entity.getControlProfiles(), user);
			UserPrincipal principal = new UserPrincipal(user.getId(), user.getName(), user.getLogin(), user.getEmail(), entity.getPassword());

			return entity;
		} catch (java.lang.RuntimeException e) {
			e.printStackTrace();
			throw listException.isEmpty() ? e : new RuntimeException(MessageUtil.getMessgeBuilder(listException));
		}
	}

	private User save (User entity, List<String> listException) {

		return buildEntityToJson(
				super.save(new User(
						Optional.ofNullable(entity.getName()).orElseGet(()-> {
							listException.add("user.name.required");
							return null;
						}),
						entity.getLogin(),
						Optional.ofNullable(entity.getCpf()).orElseGet(()-> {
							listException.add("user.cpf.required");
							return null;
						}),
						passwordEncoder.encode(entity.getPassword()),
						Optional.ofNullable(entity.getEmail()).orElseGet(()-> {
							listException.add("user.email.required");
							return null;
						}),
						Optional.ofNullable(entity.getGender()).orElse(null),
						Optional.ofNullable(entity.getBirthPlace()).orElse(null),
						Optional.ofNullable(entity.getNationality()).orElse(null),
						Optional.ofNullable(entity.getBirthDate()).orElseGet(()-> {
                            listException.add("user.birthdate.required");
                            return null;
                        }),
						null,
						Boolean.TRUE
				))
		);
	}

	public User update(User entity) {
		return buildEntityToJson(super.save(entity));
	}

	public User getOne(Long id) {
		return buildEntityToJson(findById(id).orElseThrow(() -> new RuntimeException("error.user.notFound")));
	}

	public User findByEmail(String email) {
		return repository.findByEmail(email).orElseThrow(() -> new RuntimeException("error.user.notFound"));
	}

	    public List<User> findAllRest() {
        return buildEntityToJson(findAll(Sort.by(Sort.Direction.ASC, "name")));
    }

	public Page<User> pageFindBy(User user, Integer page, Integer size) {
		if(AuthenticationUtil.isRoot()) {
			return super.pageFindBy(user, page, size);
		}

		Long id = AuthenticationUtil.getIdUser();
		Pageable pageable = getPageable(user, page, size);

		if(AuthenticationUtil.isAdmin()) {
			List<Long> ids = repository.findBy(id, ProfileEnum.ADMIN.getValue());
			if (ids.isEmpty()) {
				ids.add(id);
			}

			return buildEntityToJson(page, size, repository.findByIdList(ids, pageable));
		} else {
			user.setId(id);
			return super.pageFindBy(user, page, size);
		}
	}

	public List<User> findByApplication(Long app, User user) {
		return buildEntityToJson(repository.findByApplication(app));
	}

	public User update(Long id, User entity) {
		User user = findById(id).orElseThrow(() -> new RuntimeException("error.user.notFound"));
		user.setLogin(Optional.ofNullable(entity.getLogin()).orElse(user.getLogin()));
		user.setName(Optional.ofNullable(entity.getName()).orElse(user.getName()));
		user.setCpf(Optional.ofNullable(entity.getCpf()).orElse(user.getCpf()));
		user.setEmail(Optional.ofNullable(entity.getEmail()).orElse(user.getEmail()));
		user.setGender(Optional.ofNullable(entity.getGender()).orElse(user.getGender()));
		user.setBirthPlace(Optional.ofNullable(entity.getBirthPlace()).orElse(user.getBirthPlace()));
		user.setNationality(Optional.ofNullable(entity.getNationality()).orElse(user.getNationality()));
		user.setBirthDate(Optional.ofNullable(entity.getBirthDate()).orElse(user.getBirthDate()));

		//controlProfileService.update(entity.getControlProfiles(), user);

		return buildEntityToJson(super.save(user));
	}

	public void delete(Long id) {
		User user = getOne(id);
		user.setActive(Boolean.FALSE);
		super.save(user);
	}

	@Override
	public void getProperties() {
		propertiesToReturn = new User().getProperties().toArray(new String[0]);
	}

}
