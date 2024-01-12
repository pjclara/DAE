package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.ProductPackage;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    private EndConsumerBean endConsumerBean;
    @EJB
    private ManufacturerBean manufacturerBean;
    @EJB
    private LogisticsOperatorBean logisticsOperatorBean;
    @EJB
    private ProductBean productBean;
    @EJB
    private SensorBean sensorBean;

    @EJB
    private PackageSensorBean packageSensorBean;

    @EJB
    private ProductPackageBean productPackageBean;
    @EJB
    private OrderBean orderBean;


    @PostConstruct
    public void populateDB() {
            System.out.println("Hello Java EE!");

            try {
                endConsumerBean.create("endConsumer1", "endConsumer1", "endConsumer1", "endConsumer1@teste.pt", "endConsumer");
                endConsumerBean.create("endConsumer2", "endConsumer2", "endConsumer2", "endConsumer2@teste.pt", "endConsumer");
                System.out.println("EndConsumer created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            try {
                manufacturerBean.create("manufacturer1", "manufacturer1", "manufacturer1", "manufacturer1@teste.pt", "manufacturer");
                manufacturerBean.create("manufacturer2", "manufacturer2", "manufacturer2", "manufacturer2@teste.pt", "manufacturer");
                System.out.println("Manufacturer created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            try {
                logisticsOperatorBean.create("logisticsOperator1", "logisticsOperator1", "logisticsOperator1", "logisticsOperator1@teste.pt", "logisticsOperator");
                System.out.println("LogisticsOperator created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            try {
                productBean.create("product1", 2, "image1", "manufacturer1");
                System.out.println("Product created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            try {
                productPackageBean.create(PackagingType.PRIMARY, "Vidro");
                productPackageBean.create(PackagingType.SECONDARY, "Cartão");
            }catch (MyConstraintViolationException e) {
                logger.warning(e.getMessage());
            }

            try {
                sensorBean.create("sensor1", "sensor1", "sensor1", "celcius", "20", "30", 15555555L);
                System.out.println("Sensor created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            try{
                packageSensorBean.create(1, 1, 1, "20");

                // add a unit product to the package

            }catch(Exception e){
                logger.warning(e.getMessage());
            }





        }
    }