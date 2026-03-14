package com.viheakode.jwt.service.serviceImp;

import com.viheakode.jwt.dto.request.LoginRequest;
import com.viheakode.jwt.dto.request.RegisterRequest;
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
import com.viheakode.jwt.util.SecurityUtil;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthServiceImp {
    private final AuthenticationManager authenticationManager;
    private final CustomUserDetailsService customUserDetailsService;
    private final MoyJwtService moyJwtService;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserRoleRepository userRoleRepository;

    public AuthServiceImp(
            AuthenticationManager authenticationManager,
            CustomUserDetailsService customUserDetailsService,
            MoyJwtService moyJwtService,
            PasswordEncoder passwordEncoder,
            UserRepository userRepository,
            RoleRepository roleRepository, UserRoleRepository userRoleRepository
    ) {
        this.authenticationManager = authenticationManager;
        this.customUserDetailsService = customUserDetailsService;
        this.moyJwtService = moyJwtService;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;

        this.userRoleRepository = userRoleRepository;
    }

    public String authenticate(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getUsername(),
                loginRequest.getPassword())
        );
        UserDetails userDetails = customUserDetailsService.loadUserByUsername(loginRequest.getUsername());
        System.out.println(userDetails.getAuthorities());
        return moyJwtService.generateToken(userDetails);
    }

    public UserDto register(RegisterRequest registerRequest){

        if (userRepository.existsByUsername(registerRequest.getUsername())){
            throw new ResourceDuplicateException("Username already exists");
        }

        if (userRepository.existsByEmail(registerRequest.getEmail())){
            throw new ResourceDuplicateException("Email already exists");
        }

        User user = new User();
        user.setUuid(UUID.randomUUID().toString());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
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

}
