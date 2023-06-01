package premasterprojecten.springboot.entities;
import jakarta.persistence.*;
import lombok.Getter;

import java.util.List;

@Entity
@Table(name = "types")
@Getter
public class Type {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "type_Id")
    private int typeId;
    @Column(name = "type")
    private String type;

    @OneToMany
    @JoinColumn(name= "type_Id")
    private List<Vehicles> vehicles;

}