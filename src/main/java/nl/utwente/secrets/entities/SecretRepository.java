package nl.utwente.secrets.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@Transactional
public interface SecretRepository extends JpaRepository<Secret, Long> {

    public List<Secret> findAllByAuthor(User user);

}
