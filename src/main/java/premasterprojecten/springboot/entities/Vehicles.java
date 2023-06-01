package premasterprojecten.springboot.entities;
import jakarta.persistence.*;
import lombok.Getter;

@Entity
@Table(name = "vehicles")
@Getter
public class Vehicles {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "vehicle_Id")
    private int vehicleId;

    @ManyToOne
    @JoinColumn(name="automaker_Id", insertable = false,updatable = false)
    private Automaker automaker;

    @ManyToOne
    @JoinColumn(name="type_Id", insertable = false,updatable = false)
    private Type type;

    @Column(name = "automaker_Id")
    private int automakerId;

    @Column(name = "model")
    private String model;

    @Column(name = "year")
    private String year;

    @Column(name = "color")
    private String color;

    @Column(name = "type_Id")
    private int typeId;



}