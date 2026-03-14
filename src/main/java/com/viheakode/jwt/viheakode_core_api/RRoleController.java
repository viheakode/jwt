package com.viheakode.jwt.viheakode_core_api;

import com.viheakode.jwt.dto.request.RoleRequest;
import com.viheakode.jwt.dto.response.RoleDto;
import com.viheakode.jwt.service.serviceImp.RoleServiceImp;
import com.viheakode.jwt.util.ApiResponseStructure;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/roles")
public class RRoleController {

    private final RoleServiceImp roleServiceImp;

    public RRoleController(RoleServiceImp roleServiceImp) {
        this.roleServiceImp = roleServiceImp;
    }

    @PostMapping
    public ResponseEntity<Object> create(@Valid @RequestBody RoleRequest roleRequest){
        RoleDto roleDto = roleServiceImp.addRole(roleRequest);
        return ApiResponseStructure.singleResp("Created", roleDto, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<Object> getAll(){
        List<RoleDto> roleDtoList = roleServiceImp.getRoles();
        return ApiResponseStructure.singleResp("Ok", roleDtoList, HttpStatus.OK);
    }

    @GetMapping("/{roleId}")
    public ResponseEntity<Object> get(@PathVariable Long roleId){
        RoleDto roleDto = roleServiceImp.getRole(roleId);
        return ApiResponseStructure.singleResp("Ok", roleDto, HttpStatus.OK);
    }

    @PutMapping("/{roleId}")
    public ResponseEntity<Object> update(@PathVariable Long roleId, @Valid @RequestBody RoleRequest roleRequest){
        RoleDto roleDto = roleServiceImp.updateRole(roleId, roleRequest);
        return ApiResponseStructure.singleResp("updated", roleDto, HttpStatus.CREATED);
    }

    @DeleteMapping("/{roleId}")
    public ResponseEntity<Object> delete(@PathVariable Long roleId){
        RoleDto roleDto = roleServiceImp.deleteRole(roleId);
        return ApiResponseStructure.singleResp("Deleted", roleDto, HttpStatus.OK);
    }


}
