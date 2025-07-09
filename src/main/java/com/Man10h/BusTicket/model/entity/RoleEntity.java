package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "role")
public class RoleEntity {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "roleEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<UserEntity> userEntities = new ArrayList<>();
}
