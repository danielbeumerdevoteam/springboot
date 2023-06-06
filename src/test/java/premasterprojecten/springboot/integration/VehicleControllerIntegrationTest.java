package premasterprojecten.springboot.integration;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import premasterprojecten.springboot.entities.Vehicles;
import premasterprojecten.springboot.service.VehiclesService;

import java.io.File;
import java.util.Arrays;
import java.util.List;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
     class VehicleControllerIntegrationTest {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @MockBean
    private VehiclesService vehiclesServiceMock;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Mockito.when(vehiclesServiceMock.findModelByAutomaker(Mockito.anyString()))
                .thenReturn(Arrays.asList("Model1", "Model2"));
        Mockito.when(vehiclesServiceMock.findAutomakerByModel(Mockito.anyString()))
                .thenReturn("Automaker");
        Mockito.when(vehiclesServiceMock.save(Mockito.any(Vehicles.class)))
                .thenAnswer(invocation -> invocation.getArgument(0));
        Mockito.doAnswer(invocation -> {
            Vehicles vehicle = invocation.getArgument(0);
            return null;
        }).when(vehiclesServiceMock).update(Mockito.any(Vehicles.class));
        Mockito.doNothing().when(vehiclesServiceMock).delete(Mockito.anyLong());

        File reportFile = new File("/report.txt");
        Mockito.when(vehiclesServiceMock.generateVehicleReport())
                .thenReturn(reportFile);
    }

    @Test
    @DisplayName("findByName returns a list of models when successful")
    public void findByName_ReturnListOfModels_WhenSuccessful() {
        ResponseEntity<List<String>> response = testRestTemplate.exchange(
                "/vehicles/findautomaker?name=Automaker", HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {});
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).containsExactly( "Model1", "Model2");
    }

    @Test
    @DisplayName("searchModelName returns the automaker when successful")
    public void searchModelName_ReturnsAutomaker_WhenSuccessful() {
        ResponseEntity<String> response = testRestTemplate.exchange(
                "/vehicles/findmodel?name=Model1", HttpMethod.GET, null, new ParameterizedTypeReference<String>() {}
        );
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).isEqualTo("Automaker");
    }

    @Test
    @DisplayName("addVehicle saves the vehicle when successful")
    public void addVehicle_SavesVehicle_WhenSuccessful() {
        Vehicles vehicleToBeAdded = new Vehicles();

        ResponseEntity<Vehicles> response = testRestTemplate.postForEntity("/vehicles", vehicleToBeAdded, Vehicles.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        // Assertions.assertThat(response.getBody()).isEqualTo(vehicleToBeAdded);
    }

    @Test
    @DisplayName("update updates the vehicle when successful")
    public void update_UpdatesVehicle_WhenSuccessful() {
        Vehicles vehicleToUpdate = new Vehicles();

        ResponseEntity<String> response = testRestTemplate.exchange("/vehicles", HttpMethod.PUT, new HttpEntity<>(vehicleToUpdate), String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("Vehicle ID " + vehicleToUpdate.getVehicleId() + " updated");
    }

    @Test
    @DisplayName("delete removes the vehicle when successful")
    public void delete_RemovesVehicle_WhenSuccessful() {
        ResponseEntity<String> response = testRestTemplate.exchange("/vehicles/" + 1L, HttpMethod.DELETE, null, String.class);
        Assertions.assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        Assertions.assertThat(response.getBody()).contains("Vehicle ID " + 1L + " deleted");
    }
}

