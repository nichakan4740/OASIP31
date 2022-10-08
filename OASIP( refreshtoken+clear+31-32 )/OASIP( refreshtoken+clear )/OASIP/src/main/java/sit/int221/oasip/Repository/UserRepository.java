package sit.int221.oasip.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import sit.int221.oasip.Entity.User;

import javax.transaction.Transactional;
import java.util.List;


public interface UserRepository extends JpaRepository<User, Integer> {
    public User findByEmail(String email);
    public boolean existsByEmail(String email);
}
