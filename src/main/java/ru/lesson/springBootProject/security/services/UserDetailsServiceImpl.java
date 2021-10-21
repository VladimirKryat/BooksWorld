package ru.lesson.springBootProject.security.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.lesson.springBootProject.repositories.UserRepository;
import ru.lesson.springBootProject.security.details.UserDetailsImpl;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        return new UserDetailsImpl(userRepository.findByUsername(s).orElseThrow(()->new IllegalArgumentException("User not found by UserDetailsServiceImpl")));
    }
}
