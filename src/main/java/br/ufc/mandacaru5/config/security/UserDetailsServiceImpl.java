package br.ufc.mandacaru5.config.security;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.ufc.mandacaru5.model.Login;
import br.ufc.mandacaru5.repository.LoginRepository;

@Service
@Transactional
public class UserDetailsServiceImpl implements UserDetailsService {
 
    @Autowired
    LoginRepository loginRepository;
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Login login = loginRepository.findByEmail(email);
        return new User(login.getUsername(), login.getPassword(), true, true, true, true, login.getAuthorities());
    } 
}
