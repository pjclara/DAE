package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.OrderItem;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.Orderr;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;

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
    private PackageBean packageBean;

    @EJB
    private OrderBean orderBean;


    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        try {
            endConsumerBean.create("endConsumer1", "endConsumer1", "endConsumer1", "endConsumer1@teste.pt", "endConsumer");
            endConsumerBean.create("endConsumer2", "endConsumer2", "endConsumer2", "endConsumer2@teste.pt", "endConsumer");
        }catch (Exception e){
            logger.warning(e.getMessage());
        }

        try {
            manufacturerBean.create("manufacturer1", "manufacturer1", "manufacturer1", "manufacturer1@teste.pt", "manufacturer");
            manufacturerBean.create("manufacturer2", "manufacturer2", "manufacturer2", "manufacturer2@teste.pt", "manufacturer");
        }catch (Exception e){
            logger.warning(e.getMessage());
        }

        try {
            logisticsOperatorBean.create("logisticsOperator1", "logisticsOperator1", "logisticsOperator1", "logisticsOperator1@teste.pt", "logisticsOperator");
        }catch (Exception e){
            logger.warning(e.getMessage());
        }

        try{
            packageBean.create(  PackagingType.PRIMARY, "Plástico");
            packageBean.create(  PackagingType.PRIMARY, "Vidro");
            packageBean.create(  PackagingType.PRIMARY, "Papel");
            packageBean.create(  PackagingType.SECONDARY, "Cartão");
            packageBean.create(  PackagingType.SECONDARY, "Plástico");
            packageBean.create(  PackagingType.THIRD, "Cartão");
            packageBean.create(  PackagingType.THIRD, "Plástico");
            packageBean.create(  PackagingType.TRANSPORT, "Palete");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        Long productId1 = null;
        try {
            productId1 = productBean.create("Produto1", 10, "https://www.madeinmarket.eu/cdn/shop/products/original_bottle_single.png?v=1668450078", "manufacturer1", 1L);
            productBean.create("Produto2", 20, "https://contents.mediadecathlon.com/p2200778/k$f44d57c690a98ab7d64b47d2cae1ce98/sq/mochila-de-caminhada-16l-nh-escape-500.jpg?format=auto&f=800x0", "manufacturer2", 2L);
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
        try {
            sensorBean.create("Produto", "Temperatura", "20", "ºC", "10", "30", 123456789L);
            sensorBean.create("Produto", "Humidade", "50", "%", "30", "70", 123456789L);
            sensorBean.create("Produto", "Pressão", "1000", "Pa", "900", "1100", 123456789L);
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
        try {


            System.out.println("final orderBean");
        }catch (Exception e){
            logger.warning(e.getMessage());

        }


        // ADD SENSORS TO A PACKAGE
        try{
            packageBean.addSensorToPackage( 1L, 1L);
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}