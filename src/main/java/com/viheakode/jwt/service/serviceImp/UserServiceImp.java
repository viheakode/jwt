package com.viheakode.jwt.service.serviceImp;

import com.viheakode.jwt.dto.request.ResetPasswordRequest;
import com.viheakode.jwt.dto.request.UserModifyRequest;
import com.viheakode.jwt.dto.request.UserRequest;
import com.viheakode.jwt.dto.response.UserDto;
import com.viheakode.jwt.exception.ResourceDuplicateException;
import com.viheakode.jwt.exception.ResourceNotFoundException;
import com.viheakode.jwt.mapper.UserMapper;
import com.viheakode.jwt.model.Role;
import com.viheakode.jwt.model.User;
import com.viheakode.jwt.model.UserRole;
import com.viheakode.jwt.repository.RoleRepository;
import com.viheakode.jwt.repository.UserRepository;
import com.viheakode.jwt.repository.UserRoleRepository;
import com.viheakode.jwt.service.IUserService;
import com.viheakode.jwt.util.SecurityUtil;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class UserServiceImp implements IUserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleRepository userRoleRepository;

    public UserServiceImp(
            UserRepository userRepository,
            RoleRepository roleRepository,
            PasswordEncoder passwordEncoder,
            UserRoleRepository userRoleRepository
    ) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDto addUser(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername())){
            throw new ResourceDuplicateException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())){
            throw new ResourceDuplicateException("Email already exists");
        }

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setPublisher(SecurityUtil.getPublisher());
        userRepository.save(user);

        Role role = roleRepository.findByRoleName("ROLE_USER");
        if (role == null){
            throw new ResourceNotFoundException("Role not found");
        }

        UserRole userRole = new UserRole();
        userRole.setUserId(user.getUserId());
        userRole.setRoleId(role.getRoleId());
        userRoleRepository.save(userRole);
        return UserMapper.toDto(user);
    }

    @Override
    public List<UserDto> getUsers() {
        List<User> userList = userRepository.findAll();
        return userList.stream().map(UserMapper::toDto).toList();
    }

    @Override
    public UserDto getUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto updateUser(Long userId, UserRequest userRequest) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (userRepository.existsByUsername(userRequest.getUsername())){
            throw new ResourceDuplicateException("Username already exists");
        }

        if (userRepository.existsByEmail(userRequest.getEmail())){
            throw new ResourceDuplicateException("Email already exists");
        }

        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setModifiedDate(new Date());
        userRepository.save(user);
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        userRepository.deleteById(userId);
        return UserMapper.toDto(user);
    }

    @Override
    public UserDto modifyUser(Long userId, UserModifyRequest userModifyRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        for(String roleName : userModifyRequest.getRoles()){
            Role role = roleRepository.findByRoleName(roleName);

            if (role == null) {
                throw new ResourceNotFoundException("Role not found");
            }

            if (userRoleRepository.existsByUserIdAndRoleId(userId, role.getRoleId())){
                throw new ResourceDuplicateException("Role already exists");
            }
            UserRole userRole = new UserRole();
            userRole.setUserId(userId);
            userRole.setRoleId(role.getRoleId());
            userRoleRepository.save(userRole);
        }
        return UserMapper.toDto(user);
    }

    @Override
    public void resetPassword(Long userId, ResetPasswordRequest resetPasswordRequest) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        user.setPassword(passwordEncoder.encode(resetPasswordRequest.getPassword()));
        user.setModifiedDate(new Date());
        userRepository.save(user);
    }
}
