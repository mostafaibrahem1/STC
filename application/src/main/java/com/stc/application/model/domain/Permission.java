package com.stc.application.model.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;


@Entity
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Permission {


    @Id
    @GenericGenerator(name = "sequence_permission_id", strategy = "uuid2")
    @GeneratedValue(generator = "sequence_permission_id")
    String id;

    @Column(nullable = false)
    private String userEmail;


    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PermissionType type;

    @JoinColumn(name = "permission_group_id")
    @ManyToOne(fetch = FetchType.LAZY)
    private PermissionGroup permissionGroup;


    public enum PermissionType {
        EDIT, VIEW
    }

}
