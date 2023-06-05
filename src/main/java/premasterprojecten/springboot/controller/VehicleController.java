package premasterprojecten.springboot.controller;
//import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import premasterprojecten.springboot.service.VehiclesService;
import premasterprojecten.springboot.entities.Vehicles;

import java.io.File;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("vehicles")
@Slf4j
@RequiredArgsConstructor
public class VehicleController {
    private final VehiclesService vehiclesService;

    @GetMapping(path = "/findautomaker")
    public ResponseEntity<List<String>> findByName(@RequestParam(value = "name") String name) {
        return ResponseEntity.ok(vehiclesService.findModelByAutomaker(name));
    }

    @GetMapping(path = "/findmodel")
    public ResponseEntity <String> searchModelName(@RequestParam("name") String name) {
        return ResponseEntity.ok(vehiclesService.findAutomakerByModel(name));
    }
    @PostMapping
    public ResponseEntity<Vehicles> addVehicle(@RequestBody /*@Valid*/ Vehicles vehicles) {
        vehiclesService.save(vehicles);
        return ResponseEntity.ok(vehicles);
    }
    @PutMapping
    public ResponseEntity<String> update(@RequestBody Vehicles vehicles) {
        vehiclesService.update(vehicles);
        return ResponseEntity.ok("Vehicle ID "+ vehicles.getVehicleId() +" updated");
    }
    @DeleteMapping(path = "/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        vehiclesService.delete(id);
        return ResponseEntity.ok("Vehicle ID "+ id +" deleted");
    }
    @GetMapping("/generatereport")
    public ResponseEntity<FileSystemResource> generatereport() {
        File reportFile = vehiclesService.generateVehicleReport();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.TEXT_PLAIN);
            headers.setContentDispositionFormData("attachment", "report.txt");

            FileSystemResource fileResource = new FileSystemResource(reportFile);
            return ResponseEntity.status(HttpStatus.OK)
                    .headers(headers)
                    .body(fileResource);
    }
}
