package com.example.GARA_API.services.appUser;

import com.example.GARA_API.model.User;
import com.example.GARA_API.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AppUserDetailsService implements UserDetailsService {
    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepo.findByUserNameIgnoreCase(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new AppUserDetails(user);
    }
}
