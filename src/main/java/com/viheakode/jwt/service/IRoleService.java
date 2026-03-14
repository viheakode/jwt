package com.viheakode.jwt.service;

import com.viheakode.jwt.dto.request.RoleRequest;
import com.viheakode.jwt.dto.response.RoleDto;

import java.util.List;

public interface IRoleService {
    RoleDto addRole(RoleRequest roleRequest);
    List<RoleDto> getRoles();
    RoleDto getRole(Long roleId);
    RoleDto updateRole(Long roleId, RoleRequest roleRequest);
    RoleDto deleteRole(Long roleId);
}
