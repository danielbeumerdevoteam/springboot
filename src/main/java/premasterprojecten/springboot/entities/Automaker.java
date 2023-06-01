package premasterprojecten.springboot.entities;
import jakarta.persistence.*;
import lombok.Getter;
import java.util.List;

@Entity
@Table(name = "automakers")
@Getter
public class Automaker {
@Id
@GeneratedValue(strategy = GenerationType.IDENTITY)
@Column(name = "automaker_Id")
    private int automakerId;
@Column(name = "automakers")
    private String automakers;
    @OneToMany
    @JoinColumn(name= "automaker_Id")
    private List<Vehicles> vehicles;
}
