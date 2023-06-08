package ru.otus.socialnetwork.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.otus.socialnetwork.storage.repository.UserRepository;
import ru.otus.socialnetwork.utils.JwtTokenUtil;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtTokenUtil jwtTokenUtil;

    private final UserRepository userRepository;

    public String login(String id, String password) throws AuthenticationException {
        Authentication authentication;
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(id, password);
        authentication = authenticationManager.authenticate(token);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        return jwtTokenUtil.generateToken(userRepository.getById(id));
    }
}
