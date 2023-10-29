package com.stc.application.model.domain;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.List;

@Entity
@Data
@Builder(toBuilder = true)
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class PermissionGroup {

    @Id
    @GenericGenerator(name = "sequence_permission_group_id", strategy = "uuid2")
    @GeneratedValue(generator = "sequence_permission_group_id")
    String id;


    @Column(name = "group_name", nullable = false)
    private String groupName;

    @OneToMany(mappedBy = "permissionGroup", fetch = FetchType.LAZY)
    private List<Permission> permissionCollection;

    @OneToMany(mappedBy = "permissionGroup", fetch = FetchType.LAZY)
    private List<Item> itemCollection;


}


