package com.Man10h.BusTicket.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Table(name = "user")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

    @Id
    private String id;

    private String username;
    private String email;

    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<BusEntity> busEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<TicketEntity> ticketEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<InvoiceEntity> invoiceEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<TripEntity> tripEntityList = new ArrayList<>();

    @OneToMany(mappedBy = "userEntity", cascade = {CascadeType.MERGE, CascadeType.PERSIST}, orphanRemoval = true)
    private List<ImageEntity> imageEntityList = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "roleId")
    private RoleEntity roleEntity;

    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleEntity.getName()));
        return authorities;
    }
}
