package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO {
    private Long id;
    private PackagingType packagingType;
    private String packagingMaterial;
    private List<SensorDTO> sensors;

    public PackageDTO() {
        this.sensors = new ArrayList<>();
    }

    public PackageDTO(Long id, PackagingType packagingType, String packagingMaterial, List<SensorDTO> sensors) {
        this.id = id;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensors = sensors;
    }

    public PackageDTO(Long id, List<Sensor> sensors) {
        this.id = id;
        this.sensors = SensorDTO.from(sensors);
    }

    public long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public PackagingType getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(PackagingType packagingType) {
        this.packagingType = packagingType;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public List<SensorDTO> getSensors() {
        return sensors;
    }

    public void setSensors(List<SensorDTO> sensorData) {
        this.sensors = sensorData;
    }


    public static PackageDTO from(Package package_) {
        return new PackageDTO(
                package_.getId(),
                package_.getPackagingType(),
                package_.getPackagingMaterial(),
                SensorDTO.from(package_.getSensors())
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
