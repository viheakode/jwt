package com.viheakode.jwt.mapper;

import com.viheakode.jwt.dto.response.RoleDto;
import com.viheakode.jwt.model.Role;

public class RoleMapper {
    public static RoleDto toDto(Role role){
        RoleDto roleDto = new RoleDto();
        roleDto.setRoleId(role.getRoleId());
        roleDto.setUuid(role.getUuid());
        roleDto.setRoleName(role.getRoleName());
        roleDto.setDescription(role.getDescription());
        roleDto.setStatus(role.getStatus());
        roleDto.setPublisher(role.getPublisher());
        roleDto.setPublishedDate(role.getPublishedDate());
        roleDto.setModifiedDate(role.getModifiedDate());
        return roleDto;
    }
}
