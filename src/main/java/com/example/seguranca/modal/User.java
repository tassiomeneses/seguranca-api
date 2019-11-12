package com.example.seguranca.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;
import org.hibernate.envers.NotAudited;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Audited
@AuditTable(value = "tb_user_aud", schema = "audit")
@Table(name = "tb_user", schema = "security")
public class User extends AbstractModel<Long> {

    @Id
    @SequenceGenerator(name="user_id_seq", sequenceName="security.user_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="user_id_seq")
    @Column(name="id")
    private Long id;

    @NotNull(message = "user.name.required")
    @Getter @Setter
    @Column(name = "name")
    private String name;

    @Getter @Setter
    @Column(name = "login", unique = true)
    private String login;

    @CPF(message = "user.cpf.invalid")
    @NotNull(message = "user.cpf.required")
    @Getter @Setter
    @Column(name = "cpf", unique = true)
    private String cpf;

    @Getter @Setter
    @Column(name = "password")
    private String password;

    @Getter @Setter
    @Column(name = "last_access")
    private LocalDateTime lastAccess;

    @NotNull
    @Getter @Setter
    @Column(name = "active")
    private Boolean active;

    @NotNull(message = "user.email.required")
    @Getter @Setter
    @Column(name = "email", unique = true)
    private String email;

    @Getter @Setter
    @Column(name = "gender")
    private String gender;

    @Getter @Setter
    @Column(name = "birth_place")
    private String birthPlace;

    @Getter @Setter
    @Column(name = "nationality")
    private String nationality;

    @Getter @Setter
    @Column(name = "birth_date")
    private LocalDate birthDate;

    @NotAudited
    @Getter @Setter
    @OneToMany(mappedBy = "user")
    private List<ControlProfile> controlProfileList;

    @Transient
    @Getter @Setter
    private List<ControlProfile> controlProfiles;

    public User() {
    }

    public User(
        @NotNull String name, String login, @NotNull String cpf,
        String password, @NotNull String email, String gender, String birthPlace, String nationality,
        LocalDate birthDate, LocalDateTime lastAccess, @NotNull Boolean active

    ) {
        this.name = name;
        this.login = login;
        this.cpf = cpf;
        this.password = password;
        this.email = email;
        this.lastAccess = lastAccess;
        this.active = active;
        this.gender = gender;
        this.birthPlace = birthPlace;
        this.nationality = nationality;
        this.birthDate = birthDate;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @JsonIgnore
    public List<String> getProperties() {
        String[] properties = {
            "id",
            "name",
            "login",
            "cpf",
            "active",
            "email",
            "gender",
            "birthPlace",
            "nationality",
            "birthDate"
        };

          List<String> propertiesFiltered = new ArrayList<>(Arrays.asList(properties));
        return propertiesFiltered;
    }
}
