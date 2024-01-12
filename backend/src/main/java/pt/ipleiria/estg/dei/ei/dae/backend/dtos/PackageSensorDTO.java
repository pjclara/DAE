package pt.ipleiria.estg.dei.ei.dae.backend.dtos;

import jakarta.ejb.Stateless;

import java.io.Serializable;

public class PackageSensorDTO implements Serializable {

    private long id;

    private SensorDTO sensorDTO;

    private PackageDTO packageDTO;

    private UnitProductDTO unitProductDTO;

    private String value;

    public PackageSensorDTO() {
    }

    public PackageSensorDTO(Long id, SensorDTO sensorDTO, PackageDTO packageDTO, String value) {
        this.id = id;
        this.sensorDTO = sensorDTO;
        this.value = value;
        this.packageDTO = packageDTO;
    }

    public PackageSensorDTO(long id, SensorDTO sensorDTO, PackageDTO packageDTO,
                            UnitProductDTO unitProductDTO, String value) {
        this.id = id;
        this.sensorDTO = sensorDTO;
        this.packageDTO = packageDTO;
        this.unitProductDTO = unitProductDTO;
        this.value = value;
    }



    // getters and setters
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }

    public SensorDTO getSensorDTO() {
        return sensorDTO;
    }

    public void setSensorDTO(SensorDTO sensorDTO) {
        this.sensorDTO = sensorDTO;
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



}
