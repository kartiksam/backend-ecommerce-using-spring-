package repository;
import model.User;
//const a=reequire
//
import org.springframework.data.jpa.repository.JpaRepository;
//two thuings need tpo menyion foer whiuch class and in them id its type
public interface UserRepository extends JpaRepository<User,Long>
{













}
