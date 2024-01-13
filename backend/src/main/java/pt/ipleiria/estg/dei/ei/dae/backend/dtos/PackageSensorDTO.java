package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.io.Serializable;
import java.util.List;

public class PackageSensorDTO implements Serializable {

    private long id;

    private List<SensorDTO> sensorDTOs;

    private PackageDTO packageDTO;

    private UnitProductDTO unitProductDTO;

    private String value;


    public PackageSensorDTO(Long id, List<SensorDTO> sensorDTOs) {
        this.id = id;
        this.sensorDTOs = sensorDTOs;
    }

    public PackageSensorDTO(Long id, String value, List<SensorDTO> sensorDTOs) {
        this.id = id;
        this.value =  value;
        this.sensorDTOs = sensorDTOs;
    }


    // getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public PackageDTO getPackageDTO() {
        return packageDTO;
    }

    public void setPackageDTO(PackageDTO packageDTO) {
        this.packageDTO = packageDTO;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public UnitProductDTO getUnitProductDTO() {
        return unitProductDTO;
    }

    public void setUnitProductDTO(UnitProductDTO unitProductDTO) {
        this.unitProductDTO = unitProductDTO;
    }

    public List<SensorDTO> getSensorDTOs() {
        return sensorDTOs;
    }

    public void setSensorDTOs(List<SensorDTO> sensorDTOs) {
        this.sensorDTOs = sensorDTOs;
    }




}
