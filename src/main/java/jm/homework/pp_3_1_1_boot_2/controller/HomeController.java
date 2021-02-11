package jm.homework.pp_3_1_1_boot_2.controller;

import jm.homework.pp_3_1_1_boot_2.model.Role;
import jm.homework.pp_3_1_1_boot_2.model.User;
import jm.homework.pp_3_1_1_boot_2.service.RoleService;
import jm.homework.pp_3_1_1_boot_2.service.SecurityService;
import jm.homework.pp_3_1_1_boot_2.service.UserService;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

@Controller
public class HomeController {

    private final UserDetailsService userDetailsService;
    private final UserService userService;
    private final RoleService roleService;
    private final SecurityService securityService;

    @Value("${other.value}")
    String value;

    public HomeController(@Qualifier("userDetailsServiceImpl")
                          UserDetailsService userDetailsService,
                          UserService userService,
                          RoleService roleService,
                          SecurityService securityService) {
        this.userDetailsService = userDetailsService;
        this.userService = userService;
        this.roleService = roleService;
        this.securityService = securityService;
    }

    @GetMapping(value = "/")
    public String getHomePage(Model model) {

        User user = securityService.getAuthUserName();
        String username = "";
        Set<String> roles = new HashSet<>();
        if (user.getId() != null) {
            username = user.getUsername();
            roles = user.getRoles().stream().map(Role::getRole).collect(Collectors.toSet());
        }

        model.addAttribute("username", username);
        model.addAttribute("roles", roles);
        model.addAttribute("value", value);
        model.addAttribute("user", user);
        return "index";
    }

    @GetMapping(value = "/logincustom")
    public String getLoginPage(@RequestParam(value = "error", required = false) String error,
                               @RequestParam(value = "logout", required = false) String logout,
                               Model model) {
        model.addAttribute("error", error != null);
        model.addAttribute("logout", logout != null);
        return "logincust";
    }

    @GetMapping("/registration")
    public String getRegisterForm(@ModelAttribute("user") User user, Model model) {
        model.addAttribute("roles", roleService.getAllRoles());
        return "regForm";
    }

    @PostMapping("/registration")
    public String regUser(@ModelAttribute("user") @Valid User user,
                          BindingResult bindingResult,
                          Model model) {
        String errorExist;
        model.addAttribute("roles", roleService.getAllRoles());
        if (bindingResult.hasErrors()) {
            return "regForm";
        }
        if (userService.isExistingUserByName(user.getUsername())) {
            errorExist = "this name is already exist";
            model.addAttribute("errorExist", errorExist);
            return "regForm";
        }
        userService.addUser(user);
        return "redirect:/admin";
    }


    @GetMapping(value = "/some")
    public String getSomePage() {
        return "some";
    }

}

/*
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        Set<String> roles = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority).collect(Collectors.toSet());

        String username;
        Object principal = authentication.getPrincipal();
        if (principal instanceof UserDetails) {
            username = ((UserDetails)principal).getUsername();
        } else {
            username = principal.toString();
        }

        User user = (User) userDetailsService.loadUserByUsername(username);


 // SOME METHODS TO AUTH FILTER
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        assert(authentication.isAuthenticated);

        Set<String> roles = SuccessUserHandler.getRoles();

        @RequestMapping("/foo")
        public String foo(@AuthenticationPrincipal User user) {
             do stuff with user
        }

*/
