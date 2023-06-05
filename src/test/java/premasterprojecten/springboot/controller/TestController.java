package premasterprojecten.springboot.controller;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import premasterprojecten.springboot.entities.Vehicles;
import premasterprojecten.springboot.service.VehiclesService;
import java.io.File;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import org.assertj.core.api.Assertions;

class TestController {
    @InjectMocks
    private VehicleController vehicleController;

    @Mock
    private VehiclesService vehiclesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Vehicles vehicle = new Vehicles();
        File reportFile = Mockito.mock(File.class);

        BDDMockito.when(vehiclesService.findModelByAutomaker(ArgumentMatchers.anyString()))
                .thenReturn(Arrays.asList("model1", "model2", "model3"));
        BDDMockito.when(vehiclesService.findAutomakerByModel(ArgumentMatchers.anyString()))
                .thenReturn("automakerName");
        BDDMockito.when(vehiclesService.save(ArgumentMatchers.any(Vehicles.class)))
                .thenReturn(vehicle);
        BDDMockito.doNothing().when(vehiclesService).update(ArgumentMatchers.any(Vehicles.class));
        BDDMockito.doNothing().when(vehiclesService).delete(123L);
        BDDMockito.when(vehiclesService.generateVehicleReport())
                .thenReturn(reportFile);
        BDDMockito.when(reportFile.exists()).thenReturn(true);
    }

    @Test
    @DisplayName("findByName returns a list of model names when successful")
    public void findByName_ReturnListOfModelNames_WhenSuccessful() {
        String automakerName = "automakerName";
        List<String> expectedModelNames = Arrays.asList("model1", "model2", "model3");

        ResponseEntity<List<String>> response = vehicleController.findByName(automakerName);
        List<String> actualModelNames = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedModelNames, actualModelNames);
        verify(vehiclesService).findModelByAutomaker(automakerName);
    }

    @Test
    @DisplayName("searchModelName returns a string of an automaker name when successful")
    public void searchModelName_ReturnStringOfAutomaker_WhenSuccessful() {
        ResponseEntity<String> response = vehicleController.searchModelName( "modelName");
        String actualAutomakerName = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("automakerName", actualAutomakerName);
        verify(vehiclesService).findAutomakerByModel("modelName");
    }

    @Test
    @DisplayName("addVehicle saves the vehicle and returns it when successful")
    public void addVehicle_SavesVehicleAndReturnsIt_WhenSuccessful() {
        Vehicles vehicle = new Vehicles();

        ResponseEntity<Vehicles> response = vehicleController.addVehicle(vehicle);
        Vehicles savedVehicle = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(vehicle, savedVehicle);
        verify(vehiclesService).save(vehicle);
    }

    @Test
    @DisplayName("update updates the vehicle and returns a success message when successful")
    public void update_UpdatesVehicleAndReturnsSuccessMessage_WhenSuccessful() {
        Vehicles vehicle = new Vehicles();

        ResponseEntity<String> response = vehicleController.update(vehicle);
        String message = response.getBody();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle ID " + vehicle.getVehicleId() + " updated", message);
        verify(vehiclesService).update(vehicle);
    }

    @Test
    @DisplayName("delete deletes the vehicle and returns a success message when successful")
    public void delete_DeletesVehicleAndReturnsSuccessMessage_WhenSuccessful() {
        Long vehicleId = 123L;
        ResponseEntity<String> response = vehicleController.delete(vehicleId);
        String message = response.getBody();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Vehicle ID " + vehicleId + " deleted", message);
        verify(vehiclesService).delete(vehicleId);
    }

    @Test
    @DisplayName("generatereport returns a File resource when successful")
    public void generateReport_ReturnsFileResource_WhenSuccessful() {
      ResponseEntity<FileSystemResource> response = vehicleController.generatereport();
      FileSystemResource fileResource = response.getBody();

      assertEquals(HttpStatus.OK, response.getStatusCode());
      Assertions.assertThat(fileResource.isFile()).isTrue();
      verify(vehiclesService).generateVehicleReport();
  }
}
