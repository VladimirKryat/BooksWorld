package ru.lesson.springBootProject.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import ru.lesson.springBootProject.models.Role;
import ru.lesson.springBootProject.services.UserServiceImpl;

import javax.sql.DataSource;

//аннотация EnableGlobalMethodSecurity позволяет включить проверку ролей
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private UserServiceImpl userDetailsService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private AuthenticationProvider authenticationProvider;
    @Autowired
    private DataSource dataSource;

    //добавили раздачу статиков всем
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                    .antMatchers("/","/signup","/static/**","/activate/*","/img/**").permitAll()
                    .antMatchers("/user").hasAuthority(Role.ADMIN.name())
                    .antMatchers("/manager/**").hasAuthority(Role.MANAGER.name())
                    .anyRequest().authenticated()
                    .and()
                .formLogin()
                    .loginPage("/login")
                    .permitAll()
                    .defaultSuccessUrl("/comment")
                    .and()
                .rememberMe()
                    .rememberMeParameter("remember-me")
                    .tokenRepository(tokenRepository())
                    .tokenValiditySeconds(86400)
                    .userDetailsService(userDetailsService)
                    .and()
                .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/")
                    .permitAll()
                    .invalidateHttpSession(true)
                    .deleteCookies("JSESSIONID");

    }
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        определяем DaoAuthentication
        /*auth.userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder);*/
        auth.authenticationProvider(authenticationProvider);
    }
    @Bean
    public PersistentTokenRepository tokenRepository(){
        //SpringBoot repository для взаимодействия с таблицей токенов в БД
        //SpringSecurity сама сохраняет данные в таблицу persistent_logins
        JdbcTokenRepositoryImpl tokenRepository=new JdbcTokenRepositoryImpl();
        tokenRepository.setDataSource(dataSource);
        return tokenRepository;
    }
}
