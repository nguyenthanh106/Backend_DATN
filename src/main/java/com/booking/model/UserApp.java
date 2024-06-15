package com.booking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.NaturalId;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;


@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "`user`", uniqueConstraints = {
        @UniqueConstraint(columnNames = {
                "username"
        }),
        @UniqueConstraint(columnNames = {
                "email"
        })
})
public class UserApp implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private Integer id;
    @NotBlank
    @Column(name = "UserName", unique = true, nullable = false)
    private String username;
    @JsonIgnore
    @NotBlank
    @Column(name = "Password", nullable = false)
    @Size(min = 6, max = 100)
    private String password;
    @NotBlank
    @Column(name = "FullName")
    private String fullname;
    @NaturalId
    @Email
    @Column(name = "Email", unique = true, nullable = false)
    private String email;
    @Size(min = 10, max = 13)
    @Column(name = "PhoneNumber")
    private String phone;
    @Column(name = "Address",nullable = true)
    private String address;
    @Column(name = "Gender")
    private String gender;
    @Lob
    @Column(name = "ImageId")
    private Integer imageId;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "userRole",
    joinColumns = @JoinColumn(name = "userId"), inverseJoinColumns = @JoinColumn(name = "roleId"))
    Set<Role> roles = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        roles.stream().forEach(auth -> {
            SimpleGrantedAuthority authority = new SimpleGrantedAuthority(String.valueOf(auth.getRoleName()));
            list.add(authority);
        });
        return list;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
