package com.example.seguranca.modal.audit;

import com.example.seguranca.revision.AuditListener;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.RevisionEntity;
import org.hibernate.envers.RevisionNumber;
import org.hibernate.envers.RevisionTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;

@Entity
@RevisionEntity(AuditListener.class)
@Table(name = "tb_info_aud", schema = "audit")
public class AuditInfoEntity implements Serializable {

    @Id
    @RevisionNumber
    @SequenceGenerator(name="info_id_seq", sequenceName="audit.info_id_seq", allocationSize=1)
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator = "info_id_seq")
    @Getter @Setter
    @Column(name="id")
    private Long id;

    @Getter @Setter
    @RevisionTimestamp
    private long timestamp;

    @Getter @Setter
    @Column(name = "id_user")
    private Long idUser;

    @Getter @Setter
    @Column(name = "name")
    private String name;

    @Getter @Setter
    @Column(name = "date_operation")
    private LocalDateTime dateOperation;

}
