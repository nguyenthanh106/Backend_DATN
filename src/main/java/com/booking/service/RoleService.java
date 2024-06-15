package com.booking.service;

import com.booking.contants.ErrorMessage;
import com.booking.enums.RoleName;
import com.booking.exception.AppException;
import com.booking.model.Role;
import com.booking.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class  RoleService {
    private final RoleRepository roleRepository;

    public Optional<Role> findByRoleName(RoleName roleName) {
        return roleRepository.findByRoleName(roleName);
    }

    public Optional<Role> create(RoleName roleName) {
        Role newRole = new Role( roleName);
        return Optional.of(roleRepository.save(newRole));
    }

    public Role findById(Integer roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new AppException(ErrorMessage.ROLE_NOT_FOUND));
        return role;
    }
}
