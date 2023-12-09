package pt.ipleiria.estg.dei.ei.dae.backend.dtos;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Package;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Sensor;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PackageDTO {

    private long packagingCode;
    private String packagingType;
    private String packagingMaterial;
    private List<Sensor> sensors; // passar para SensorDTO -- mudar depois no construtor
    // TODO: depois passas de <Sensor> para <SensorDTO>

    public PackageDTO(long packagingCode, String packagingType, String packagingMaterial) { //TODO: passar para SensorDTO
        this.packagingCode = packagingCode;
        this.packagingType = packagingType;
        this.packagingMaterial = packagingMaterial;
        this.sensors = new ArrayList<>();
    }

    public long getPackagingCode() {
        return packagingCode;
    }

    public void setPackagingCode(long packagingCode) {
        this.packagingCode = packagingCode;
    }

    public String getPackagingType() {
        return packagingType;
    }

    public void setPackagingType(String packagingType) {
        this.packagingType = packagingType;
    }

    public String getPackagingMaterial() {
        return packagingMaterial;
    }

    public void setPackagingMaterial(String packagingMaterial) {
        this.packagingMaterial = packagingMaterial;
    }

    public List<Sensor> getSensors() {
        return sensors;
    }

    public void setSensors(List<Sensor> sensorData) {
        this.sensors = sensorData;
    }


    public static PackageDTO from(Package package_) { // o nome esta como 'package_' pois dava conflito sem o '_'
        return new PackageDTO(
                package_.getPackageCode(),
                package_.getPackagingType(),
                package_.getPackagingMaterial()
                //package_.getSensors() // TODO: remove comment after fix
        );
    }

    public static List<PackageDTO> from(List<Package> packages) {
        return packages.stream().map(PackageDTO::from).collect(Collectors.toList());
    }
}
