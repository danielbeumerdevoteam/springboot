package premasterprojecten.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import premasterprojecten.springboot.entities.Automaker;
import java.util.List;
@Repository
public interface AutomakerRepository extends JpaRepository <Automaker,Long> {
    List<Automaker> findByAutomakers(String name);
    List<Automaker>findByAutomakerId(int automakerId);
}
