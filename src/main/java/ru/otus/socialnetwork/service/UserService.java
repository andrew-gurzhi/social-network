package ru.otus.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.socialnetwork.mapper.UserMapper;
import ru.otus.socialnetwork.model.UserDto;
import ru.otus.socialnetwork.storage.repository.UserRepository;

import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public String createUser(UserDto userDto) {
        var user = userMapper.map(userDto);
        user.setId(UUID.randomUUID().toString());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
        return user.getId();
    }

    @Transactional(readOnly = true)
    public UserDto getUserById(String id) {
        return userMapper.map(userRepository.findById(id).orElse(null));
    }
}
