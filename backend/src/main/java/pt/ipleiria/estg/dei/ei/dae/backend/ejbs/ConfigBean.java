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

            /*

            try {
                productBean.create("product1", 2, "tete", "manufacturer1", 0);
                System.out.println("Product created");
            }catch (MyConstraintViolationException e){
                logger.warning(e.getMessage());
            }

            try {
                productPackageBean.create(PackagingType.PRIMARY, "Cartão");
                System.out.println("ProductPackage created");
            }catch (MyConstraintViolationException e){
                logger.warning(e.getMessage());
            }

            try {
                sensorBean.create("Product", "sensor1", "sensor1", "asda","asd");
                sensorBean.create("Orders", "sensor1", "sensor1", "asda","asd");

                System.out.println("Sensor created");
            }catch (MyConstraintViolationException e){
                logger.warning(e.getMessage());
            }

            packageSensorBean.create(1, 1);
            System.out.println("PackageSensor created");

            productBean.setPackaging(1L, 1L);
            System.out.println("ProductPackage created");

            String data = "{\"status\":\"pending\",\"orderItems\":[{\"productId\":1,\"quantity\":1}]}";

            try {
                orderBean.create("endConsumer1", data);
                System.out.println("Order created");

                orderItemBean.create(1L, 1L, 1);
                System.out.println("OrderItem created");

            }catch (MyConstraintViolationException e){
                logger.warning(e.getMessage());
            }

            productBean.setPackaging(1L, 1L);

*/
        }
    }