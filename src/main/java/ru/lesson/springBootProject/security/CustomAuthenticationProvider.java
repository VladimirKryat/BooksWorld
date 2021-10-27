package ru.lesson.springBootProject.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import ru.lesson.springBootProject.services.UserService;


//AuthenticationProvider в DaoAuth нужен для разрешения UserDetails по имени и паролю пользователя
// кастомизируем провайдер для того чтобы обрабатывать, результаты проверок.
//так мы сможем заменить 'Bad credentials' на нужный message
@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Autowired
    private UserService userService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        //authentication до проверки содержит имя и пароль (principal and credentials соответственно) (их туда положил Username..Filter)
        //если все пройдет успешно UserDetails нужно положить в principal
        String username = authentication.getName();
        String password = authentication.getCredentials().toString();
        try {
            UserDetails principal = userService.loadUserByUsername(username);
            if (!passwordEncoder.matches(password,principal.getPassword())){
                throw new BadCredentialsException("Bad password");
            }
            return new UsernamePasswordAuthenticationToken(principal,password,principal.getAuthorities());
        }catch (UsernameNotFoundException usernameNotFoundException){
            throw new BadCredentialsException("Bad username");
        }
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
