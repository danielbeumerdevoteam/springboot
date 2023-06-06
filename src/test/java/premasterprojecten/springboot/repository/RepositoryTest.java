package premasterprojecten.springboot.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import premasterprojecten.springboot.entities.Automaker;
import premasterprojecten.springboot.entities.Type;
import premasterprojecten.springboot.entities.Vehicles;

import java.util.Arrays;
import java.util.List;

public class RepositoryTest {
    @Mock
    private AutomakerRepository automakerRepository;
    @Mock TypesRepository typesRepository;
    @Mock VehiclesRepository vehiclesRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Automaker automaker = new Automaker();
        Type type = new Type();
        Vehicles vehicle = new Vehicles();

        BDDMockito.when(automakerRepository.findByAutomakers(ArgumentMatchers.anyString()))
                .thenReturn(Arrays.asList(automaker));
        BDDMockito.when(automakerRepository.findByAutomakerId(ArgumentMatchers.anyInt()))
                .thenReturn(Arrays.asList(automaker));
        BDDMockito.when(typesRepository.findByTypeId(ArgumentMatchers.anyInt()))
                .thenReturn(Arrays.asList(type));
        BDDMockito.when(vehiclesRepository.findByModel(ArgumentMatchers.anyString()))
                .thenReturn(Arrays.asList(vehicle));
        BDDMockito.when(vehiclesRepository.findByAutomakerId(ArgumentMatchers.anyInt()))
                .thenReturn(Arrays.asList(vehicle));
    }
    @Test
    @DisplayName("findByAutomakers returns a list of automakers when successful")
    public void findByAutomakers_ReturnsListOfAutomakers_WhenSuccessful() {
        List<Automaker> expectedAutomakers = Arrays.asList(new Automaker());
        List<Automaker> actualAutomakers = automakerRepository.findByAutomakers(Mockito.anyString());

        Assertions.assertEquals(expectedAutomakers.get(0).getAutomakers(), actualAutomakers.get(0).getAutomakers());
    }

    @Test
    @DisplayName("findByAutomakerId returns a list of automakers when successful")
    public void findByAutomakerId_ReturnsListOfAutomakers_WhenSuccessful() {
        List<Automaker> expectedAutomakers = Arrays.asList(new Automaker());
        List<Automaker> actualAutomakers = automakerRepository.findByAutomakerId(Mockito.anyInt());

        Assertions.assertEquals(expectedAutomakers.get(0).getAutomakers(), actualAutomakers.get(0).getAutomakers());
    }

    @Test
    @DisplayName("findByTypeId returns a list of types when successful")
    public void findByTypeId_ReturnsListOfTypes_WhenSuccessful() {
        List<Type> expectedTypes = Arrays.asList(new Type());
        List<Type> actualTypes = typesRepository.findByTypeId(Mockito.anyInt());

        Assertions.assertEquals(expectedTypes.get(0).getType(), actualTypes.get(0).getType());
    }

    @Test
    @DisplayName("findByModel returns a list of vehicles when successful")
    public void findByModel_ReturnsListOfVehicles_WhenSuccessful() {
        List<Vehicles> expectedVehicles = Arrays.asList(new Vehicles());
        List<Vehicles> actualVehicles = vehiclesRepository.findByModel(Mockito.anyString());

        Assertions.assertEquals(expectedVehicles.get(0).getModel(), actualVehicles.get(0).getModel());
    }

    @Test
    @DisplayName("findByAutomakerId returns a list of vehicles when successful")
    public void findByAutomakerId_ReturnsListOfVehicles_WhenSuccessful() {
        List<Vehicles> expectedVehicles = Arrays.asList(new Vehicles());
        List<Vehicles> actualVehicles = vehiclesRepository.findByAutomakerId(Mockito.anyInt());

        Assertions.assertEquals(expectedVehicles.get(0).getModel(), actualVehicles.get(0).getModel());
    }
}
