package premasterprojecten.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import premasterprojecten.springboot.entities.Type;
import premasterprojecten.springboot.entities.Vehicles;
import java.util.List;

public interface TypesRepository extends JpaRepository <Type,Long> {
    List<Type> findByTypeId(int typeId);
}