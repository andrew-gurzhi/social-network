package ru.otus.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.otus.socialnetwork.model.Role;
import ru.otus.socialnetwork.storage.model.UserEntity;
import ru.otus.socialnetwork.storage.repository.UserRepository;

import java.util.Collections;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class UserDetailServiceCustom implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserEntity> optionalUser = userRepository.findById(username);
        if (optionalUser.isEmpty()) {
            throw new UsernameNotFoundException("User with id " + username + " not found");
        }
        UserEntity user = optionalUser.get();
        return new org.springframework.security.core.userdetails.User(user.getId(), user.getPassword(),
                Collections.singleton(Role.USER));
    }
}

