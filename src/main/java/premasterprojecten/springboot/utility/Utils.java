package premasterprojecten.springboot.utility;

import lombok.RequiredArgsConstructor;
import premasterprojecten.springboot.exception.ResourceNotFoundException;
import org.springframework.stereotype.Component;
import premasterprojecten.springboot.entities.Vehicles;
import premasterprojecten.springboot.repository.VehiclesRepository;

@Component
@RequiredArgsConstructor
public class Utils {
    private final VehiclesRepository vehiclesRepository;

    public Vehicles findVehicleOrThrowNotFound(Long id) {
        return vehiclesRepository
                .findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Vehicle Not Found"));
    }
}