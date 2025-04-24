package com.wallacy.projetoestagio.configuration;

import com.wallacy.projetoestagio.model.User;
import com.wallacy.projetoestagio.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;


@Configuration
public class AdminUserConfig implements CommandLineRunner {

    private UserRepository userRepository;
    private BCryptPasswordEncoder passwordEncoder;

    public AdminUserConfig(UserRepository userRepository, BCryptPasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        //var roleAdmin = roleRepository.findByNome(Role.Values.ADMIN.name());

        var userAdmin = userRepository.findByEmail("timwallacy@gmail.com");

        userAdmin.ifPresentOrElse(
                cliente -> {
                    System.out.println("admin ja existe");
                },
                () -> {
                    var user = new User();
                    user.setName("Admin");
                    user.setEmail("timwallacy@gmail.com");
                    user.setRegistrationMethod("OWN");
                    user.setPassword(passwordEncoder.encode("root@root"));
                    //user.setRoles(Set.of(roleAdmin));
                    userRepository.save(user);
                }
        );
    }
}
