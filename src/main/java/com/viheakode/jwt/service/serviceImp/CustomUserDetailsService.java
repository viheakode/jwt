package com.viheakode.jwt.service.serviceImp;

import com.viheakode.jwt.config.JwtUserDetails;
import com.viheakode.jwt.model.User;
import com.viheakode.jwt.repository.RoleRepository;
import com.viheakode.jwt.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public CustomUserDetailsService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("User not found");
        }
        List<String> roles = roleRepository.findRoleNameByUserId(user.getUserId());
        return new JwtUserDetails(user, roles);
    }
}
