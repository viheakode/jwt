package com.viheakode.jwt.service.serviceImp;

import com.viheakode.jwt.dto.request.RoleRequest;
import com.viheakode.jwt.dto.response.RoleDto;
import com.viheakode.jwt.exception.ResourceDuplicateException;
import com.viheakode.jwt.exception.ResourceNotFoundException;
import com.viheakode.jwt.mapper.RoleMapper;
import com.viheakode.jwt.model.Role;
import com.viheakode.jwt.repository.RoleRepository;
import com.viheakode.jwt.service.IRoleService;
import com.viheakode.jwt.util.SecurityUtil;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class RoleServiceImp implements IRoleService {

    private final RoleRepository roleRepository;

    public RoleServiceImp(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public RoleDto addRole(RoleRequest roleRequest) {

        if (roleRepository.existsByRoleName(roleRequest.getRoleName())){
            throw new ResourceDuplicateException("Role already exists");
        }

        Role role = new Role();
        role.setUuid(UUID.randomUUID().toString());
        role.setRoleName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getDescription());
        role.setPublisher(SecurityUtil.getPublisher());
        roleRepository.save(role);
        return RoleMapper.toDto(role);
    }

    @Override
    public List<RoleDto> getRoles() {
        List<Role> roleList = roleRepository.findAll();
        return roleList.stream().map(RoleMapper::toDto).toList();
    }

    @Override
    public RoleDto getRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        return RoleMapper.toDto(role);
    }

    @Override
    public RoleDto updateRole(Long roleId, RoleRequest roleRequest) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));

        if (roleRepository.existsByRoleName(roleRequest.getRoleName())){
            throw new ResourceDuplicateException("Role already exists");
        }
        role.setRoleName(roleRequest.getRoleName());
        role.setDescription(roleRequest.getDescription());
        role.setModifiedDate(new Date());
        roleRepository.save(role);
        return RoleMapper.toDto(role);

    }

    @Override
    public RoleDto deleteRole(Long roleId) {
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found"));
        roleRepository.deleteById(roleId);
        return RoleMapper.toDto(role);
    }
}
