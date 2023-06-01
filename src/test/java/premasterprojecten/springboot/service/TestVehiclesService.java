package premasterprojecten.springboot.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import premasterprojecten.springboot.entities.Vehicles;

import java.io.File;
import java.util.Arrays;
import java.util.List;

@ExtendWith(SpringExtension.class)
class TestVehiclesService {
    @InjectMocks
    private TestVehiclesService testVehiclesService;
    @Mock
    private VehiclesService vehiclesService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        BDDMockito.when(vehiclesService.findAutomakerByModel(ArgumentMatchers.any()))
                .thenReturn("automakerName");
        BDDMockito.when(vehiclesService.save(ArgumentMatchers.any()))
                .thenReturn(Mockito.mock(Vehicles.class));
        Mockito.doNothing().when(vehiclesService).update(ArgumentMatchers.any());
        Mockito.doNothing().when(vehiclesService).delete(ArgumentMatchers.any());
        BDDMockito.when(vehiclesService.findModelByAutomaker(ArgumentMatchers.any()))
                .thenReturn(Arrays.asList("modelName1", "modelName2"));
        BDDMockito.when(vehiclesService.generateVehicleReport())
                .thenReturn(Mockito.mock(File.class));
        BDDMockito.when(vehiclesService.getAutomakerIdByName(ArgumentMatchers.any()))
                .thenReturn(1);
    }

    @Test
    @DisplayName("findAutomakerByModel returns a string of an automaker name when successful")
    public void findAutomakerByModel_ReturnStringOfAutomaker_WhenSuccessful() {
        String expectedAutomakerName = "automakerName";
        String model = "modelName";
        String actualAutomakerName = vehiclesService.findAutomakerByModel(model);

        Assertions.assertEquals(expectedAutomakerName, actualAutomakerName);
    }

    @Test
    @DisplayName("save returns a Vehicles object when successful")
    public void save_ReturnsVehiclesObject_WhenSuccessful() {
        Assertions.assertDoesNotThrow(() -> vehiclesService.save(Mockito.any()));
    }

    @Test
    @DisplayName("update returns null when successful")
    public void update_ReturnsNull_WhenSuccessful() {
        Assertions.assertDoesNotThrow(() -> vehiclesService.update(Mockito.any()));
    }

    @Test
    @DisplayName("delete returns null when successful")
    public void delete_ReturnsNull_WhenSuccessful() {
        Assertions.assertDoesNotThrow(() -> vehiclesService.delete(Mockito.any()));
    }

    @Test
    @DisplayName("findModelByAutomaker returns a list of model names when successful")
    public void findModelByAutomaker_ReturnListOfModels_WhenSuccessful() {
        List<String> expectedModelNames = Arrays.asList("modelName1", "modelName2");
        String automaker = "automakerName";
        List<String> actualModelNames = vehiclesService.findModelByAutomaker(automaker);

        Assertions.assertEquals(expectedModelNames, actualModelNames);
    }

    @Test
    @DisplayName("generateVehicleReport returns a File object when successful")
    public void generateVehicleReport_ReturnsMockFileObject_WhenSuccessful() {
        File actualFile = vehiclesService.generateVehicleReport();

        Assertions.assertNotNull(actualFile);
    }

    @Test
    @DisplayName("getAutomakerIdByName returns an integer automaker ID when successful")
    public void getAutomakerIdByName_ReturnsIntegerAutomakerID_WhenSuccessful() {
        int expectedAutomakerId = 1;
        String automakerName = "automakerName";
        int actualAutomakerId = vehiclesService.getAutomakerIdByName(automakerName);

        Assertions.assertEquals(expectedAutomakerId, actualAutomakerId);
    }
}
