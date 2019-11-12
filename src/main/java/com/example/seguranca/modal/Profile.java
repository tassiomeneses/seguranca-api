package com.example.seguranca.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Entity
@Audited
@AuditTable(value = "tb_profile_aud", schema = "audit")
@Table(name = "tb_profile", schema = "security")
public class Profile extends AbstractModel<Long>  {

    @Id
    @SequenceGenerator(name="profile_id_seq", sequenceName="security.profile_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="profile_id_seq")
    @Column(name="id")
    private Long id;

    @NotNull
    @Getter @Setter
    @Column(name = "name", unique = true)
    private String name;

    @NotNull
    @Getter @Setter
    @Column(name = "key", unique = true)
    private String key;

    public Profile() {
    }

    public Profile(@NotNull String name) {
        this.name = name;
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
            "functionalityList.id",
            "functionalityList.name",
        };

        return new ArrayList<>(Arrays.asList(properties));
    }
}
