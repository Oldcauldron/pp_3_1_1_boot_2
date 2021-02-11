package jm.homework.pp_3_1_1_boot_2.service;

import jm.homework.pp_3_1_1_boot_2.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
public class SecurityServiceImpl implements SecurityService {

    private AuthenticationManager authenticationManager;
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    private UserDetailsService userDetailsService;
    private UserService userService;

    @Autowired
    public SecurityServiceImpl(AuthenticationManager authenticationManager,
                               AuthenticationManagerBuilder authenticationManagerBuilder,
                               @Qualifier("userDetailsServiceImpl")
                               UserDetailsService userDetailsService,
                               UserService userService) {
        this.authenticationManager = authenticationManager;
        this.authenticationManagerBuilder = authenticationManagerBuilder;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @Override
    public void autoLogin(User user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getUsername());
        Authentication authentication = new UsernamePasswordAuthenticationToken(userDetails, userDetails.getPassword(), userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);

    }

    @Override
    public User getAuthUserName() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        if (userService.isExistingUserByName(username)) {
            return (User) userDetailsService.loadUserByUsername(username);
        }
        return new User();
    }
}
