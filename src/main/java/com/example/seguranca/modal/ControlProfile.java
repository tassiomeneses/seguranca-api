package com.example.seguranca.modal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@AuditTable(value = "tb_control_profile_aud", schema = "audit")
@Table(name = "tb_control_profile", schema = "security")
@NoArgsConstructor
public class ControlProfile extends AbstractModel<Long> {

    @Id
    @SequenceGenerator(name="control_profile_id_seq", sequenceName="security.control_profile_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="control_profile_id_seq")
    @Column(name="id")
    private Long id;

    @NotNull
    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "id_application", referencedColumnName = "id")
    private Application application;

    @NotNull
    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "id_user", referencedColumnName = "id")
    private User user;

    @NotNull
    @ManyToOne
    @Getter @Setter
    @JoinColumn(name = "id_profile", referencedColumnName = "id")
    private Profile profile;

    public ControlProfile(@NotNull Application application, @NotNull User user, @NotNull Profile profile) {
        this.application = application;
        this.user = user;
        this.profile = profile;
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
            "application.id",
            "application.name",
            "profile.id",
            "profile.name"
        };
        return new ArrayList<>(Arrays.asList(properties));
    }
}
