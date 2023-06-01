package premasterprojecten.springboot.service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import premasterprojecten.springboot.entities.Automaker;
import premasterprojecten.springboot.entities.Vehicles;
import premasterprojecten.springboot.exception.ResourceNotFoundException;
import premasterprojecten.springboot.repository.AutomakerRepository;
import premasterprojecten.springboot.repository.TypesRepository;
import premasterprojecten.springboot.repository.VehiclesRepository;
import premasterprojecten.springboot.utility.Utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
@Slf4j
public class VehiclesService {
    private final TypesRepository typesRepository;
    private final VehiclesRepository vehiclesRepository;
    private final Utils utils;
    private final AutomakerRepository automakerRepository;

    public String findAutomakerByModel(String modelName) {
        List<Vehicles> listVehiclesFound = vehiclesRepository.findByModel(modelName);
        if (listVehiclesFound.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found for model: " + modelName);
        }
        int automakerId = listVehiclesFound.get(0).getAutomakerId();
        List<Automaker> listAutomakersFound = automakerRepository.findByAutomakerId(automakerId);
        if (listAutomakersFound.isEmpty()) {
            throw new ResourceNotFoundException("No automaker found for vehicles with model: " + modelName);
        }
        return listAutomakersFound.get(0).getAutomakers();
    }

    @Transactional
    public Vehicles save(Vehicles vehicles) {
        return vehiclesRepository.save(vehicles);
    }

    public void update(Vehicles vehicles) {
        long vehicleId = vehicles.getVehicleId();
        vehiclesRepository.save(utils.findVehicleOrThrowNotFound(vehicleId));
    }

    public void delete(Long id) {
        vehiclesRepository.delete(utils.findVehicleOrThrowNotFound(id));
    }

    public List<String> findModelByAutomaker(String automakerName) {
        int autoId = getAutomakerIdByName(automakerName);
        List<Vehicles> listVehiclesFound = vehiclesRepository.findByAutomakerId(autoId);
        if (listVehiclesFound.isEmpty()) {
            throw new ResourceNotFoundException("No vehicles found for automaker: " + automakerName);
        }
        List<String> listModels = new ArrayList<>();
        for (Vehicles vehicles : listVehiclesFound) {
            listModels.add(vehicles.getModel());
        }
        return listModels;
    }

    public File generateVehicleReport() {
        List<Vehicles> listVehicles = vehiclesRepository.findAll();
        File reportFile = new File("report.txt");
        try (FileWriter writer = new FileWriter(reportFile)) {
            for (Vehicles vehicle : listVehicles) {
                writer.write("#-------------------------------------------------------------------#\n");
                writer.write("Automaker: " + vehicle.getAutomaker().getAutomakers()+ "\n");
                writer.write("Model: " + vehicle.getModel() + "\n");
                writer.write("Type: " + vehicle.getType().getType() + "\n");
                writer.write("Color: " + vehicle.getColor()+ "\n");
                writer.write("Year: " + vehicle.getYear() + "\n");
                writer.write("#-------------------------------------------------------------------#\n");
            }
        } catch (IOException exception) {
            log.error("An error occurred while writing to the file.", exception);
            return null;
        }
        return reportFile;
    }

    public int getAutomakerIdByName(String automakerName) {
        List<Automaker> automakers = automakerRepository.findByAutomakers(automakerName);
        if(automakers.isEmpty()) {
            return -1;
        }
        else{
            return automakers.get(0).getAutomakerId();
        }
    }
}


