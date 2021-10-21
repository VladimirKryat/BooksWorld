package ru.lesson.springBootProject.security.details;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import ru.lesson.springBootProject.models.State;
import ru.lesson.springBootProject.models.User;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserDetailsImpl implements UserDetails {
    private User user;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public UserDetailsImpl(User user){
        this.user=user;
    }
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        //нельзя передать просто список нашх ролей, так как они не имплементируют GrantedAuthority
        return user.getRoles().stream().map(x->new SimpleGrantedAuthority(x.name())).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return user.getState()!=State.DELETED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return user.getState()!=State.BANNED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.getState()==State.ACTIVE;
    }
}
