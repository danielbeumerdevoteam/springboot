package premasterprojecten.springboot.repository;
import org.springframework.data.jpa.repository.JpaRepository;
import premasterprojecten.springboot.entities.Vehicles;
import java.util.List;


public interface VehiclesRepository extends JpaRepository <Vehicles,Long> {
    List<Vehicles> findByModel(String name);
    List<Vehicles> findByAutomakerId(int autoId);
}
