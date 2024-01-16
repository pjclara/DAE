package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.*;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyConstraintViolationException;
import pt.ipleiria.estg.dei.ei.dae.backend.exceptions.MyEntityNotFoundException;

import java.util.logging.Logger;

@Startup
@Singleton
public class ConfigBean {
    private static final Logger logger = Logger.getLogger("ejbs.ConfigBean");
    @EJB
    private AdministratorBean administratorBean;
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

    @EJB
    private OrderItemBean orderItemBean;

    @EJB
    private UnitProductBean unitProductBean;



    @PostConstruct
    public void populateDB() throws MyEntityNotFoundException {
            System.out.println("Hello Java EE!");

            try {
                administratorBean.create("administrator1", "administrator1", "Administrator1", "Administrator1@teste.pt", "administrator");
                System.out.println("Administrator created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

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
            productPackageBean.create(PackagingType.PRIMARY, "Vidro");
            productPackageBean.create(PackagingType.SECONDARY, "Cartão");
        }catch (MyConstraintViolationException e) {
            logger.warning(e.getMessage());
        }

            try {
                productBean.create("product1", 1, "image1", "manufacturer1",1);
                productBean.create("product2", 2, "image2", "manufacturer2", 0);

                System.out.println("Product created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }



            try {
                sensorBean.create("sensor1", "sensor1", "sensor1", "sensor1", "sensor1");
                sensorBean.create("sensor2", "sensor2", "sensor2", "sensor2", "sensor2");

                System.out.println("Sensor created");
            } catch (Exception e) {
                logger.warning(e.getMessage());
            }

            try{
                packageSensorBean.create(1, 1);
                System.out.println("PackageSensor created");

                // add a unit product to the package

            }catch(Exception e){
                logger.warning(e.getMessage());
            }

            try {
                String data  = "{\n" +
                        "  \"status\": \"WAITING_PAYMENT\",\n" +
                        "  \"orderItems\": [\n" +
                        "    {\n" +
                        "      \"quantity\": 1,\n" +
                        "      \"productId\": \"1\"\n" +
                        "    }\n" +
                        "  ]\n" +
                        "}";

                long order = orderBean.create("endConsumer1", data );

                System.out.println("Order created");

                //orderBean.addProductToOrder(order, 1, 1);
            }catch (Exception e){
                logger.warning(e.getMessage());
            }

            try{
                packageSensorBean.create(1, 1);
                System.out.println("PackageSensor created");

                unitProductBean.update(1,1);

                System.out.println("Unitproduct update");

            }catch(Exception e){
                logger.warning(e.getMessage());
            }

            try {
                orderItemBean.create(1, 1, 1);
                System.out.println("OrderItem created");
            }catch (Exception e){
                logger.warning(e.getMessage());
            }

            try{
                packageSensorBean.addSensorToPackage(1,1);
                packageSensorBean.addSensorToPackage(1,2);
                System.out.println("SensorValue add");
            }catch (Exception e){
                logger.warning(e.getMessage());
            }


        }
    }