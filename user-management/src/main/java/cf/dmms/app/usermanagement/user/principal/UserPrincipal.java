package cf.dmms.app.usermanagement.user.principal;

import cf.dmms.app.usermanagement.user.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

import static java.util.Arrays.asList;


//TODO: investigate if there is any better way to of custom UserDetails object (maybe combine it with entity)
public class UserPrincipal implements UserDetails {

    private Long id;
    private String login;
    private String email;
    private String displayName;
    private Collection<? extends GrantedAuthority> authorities;

    @JsonIgnore
    private String password;

    private UserPrincipal(Long id, String login, String email, String displayName, Collection<? extends GrantedAuthority> authorities, String password) {
        this.id = id;
        this.login = login;
        this.email = email;
        this.displayName = displayName;
        this.authorities = authorities;
        this.password = password;
    }

    public static UserPrincipal of(User user) {
        return new UserPrincipal(
                user.getId(),
                user.getLogin(),
                user.getEmail(),
                user.getDisplayName(),
                asList(new SimpleGrantedAuthority(user.getRole().name())),
                user.getPassword()
        );
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Override
    public String toString() {
        return "UserPrincipal{" +
                "id=" + id +
                ", login='" + login + '\'' +
                ", email='" + email + '\'' +
                ", displayName='" + displayName + '\'' +
                ", authorities=" + authorities +
                ", password='[PROTECTED]" +
                '}';
    }
}
