package br.com.guifroes1984.ecom.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.guifroes1984.ecom.entity.User;
import br.com.guifroes1984.ecom.enums.UserRole;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findFirstByEmail(String email);
	
	User findByRole(UserRole userRole);
	
}
