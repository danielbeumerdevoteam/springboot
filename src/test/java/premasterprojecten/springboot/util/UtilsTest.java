package premasterprojecten.springboot.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import premasterprojecten.springboot.entities.Vehicles;
import premasterprojecten.springboot.utility.Utils;

@ExtendWith(SpringExtension.class)
class UtilsTest {
    @Mock
    private Utils utils;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        Vehicles vehicle = new Vehicles();
        BDDMockito.when(utils.findVehicleOrThrowNotFound(ArgumentMatchers.anyLong()))
                .thenReturn(vehicle);
    }
        @Test
        @DisplayName("findVehicleOrThrowNotfound returns a vehicle object when successful")
        public void findVehicleOrThrowNotfound_returns_vehicle_when_successful () {
            Vehicles vehicle = new Vehicles();
            Long vehicleId = 1L;
            Vehicles expectedVehicle = vehicle;

            vehicle = utils.findVehicleOrThrowNotFound(vehicleId);
            Assertions.assertNotNull(vehicle);
            Assertions.assertEquals(expectedVehicle.getClass(),vehicle.getClass());
        }
    }

