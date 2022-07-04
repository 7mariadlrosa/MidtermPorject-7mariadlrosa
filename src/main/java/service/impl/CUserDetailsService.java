package service.impl;

import model.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import security.CUserDetails;

import java.util.Optional;

@Service
public class CUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = userRepository.findByUsername(username);
        if(!user.isPresent()) {
            throw new UsernameNotFoundException("NOT EXIST");
        }

        CUserDetails CUserDetails = new CUserDetails(user.get());

        return CUserDetails;
    }
}
