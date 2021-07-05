package ru.levelp.hospital.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import ru.levelp.hospital.daoimpl.DoctorDao;
import ru.levelp.hospital.model.Doctor;

@Component
public class DetailsService implements UserDetailsService {

    @Autowired
    private DoctorDao doctors;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Doctor found = doctors.findByLogin(username);

        if (found == null) {
            throw new UsernameNotFoundException("User " + username + " not found");
        }
        String[] roles;
        if (found.isAdmin()) {
            roles = new String[] {"USER", "ADMIN"};
        }
        else {
            roles = new String[] {"USER"};
        }
        return User.withUsername(username)
                .password(found.getPassword())
                .roles(roles)
                .build();
    }
}
