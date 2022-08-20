package br.ufc.mandacaru5.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.ufc.mandacaru5.model.Login;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
 
    Login findByEmail(String email);
}

