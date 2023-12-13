package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;
import pt.ipleiria.estg.dei.ei.dae.backend.entities.PackagingType;

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

        try {
            productBean.create(1L, "product1", 10, "manufacturer1");
            productBean.create(2L, "product2", 2, "manufacturer2");
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
        try {
            sensorBean.create(1L, "Produto", "Temperatura", "20", "ºC", "10", "30", 123456789L);
            sensorBean.create(2L, "Produto", "Humidade", "50", "%", "30", "70", 123456789L);
            sensorBean.create(3L, "Produto", "Pressão", "1000", "Pa", "900", "1100", 123456789L);
        }catch (Exception e){
            logger.warning(e.getMessage());
        }
        
        try {
            orderBean.create(1L, "status1", "endConsumer1", "logisticsOperator1");
            System.out.println("final orderBean");
        }catch (Exception e){
            logger.warning(e.getMessage());

        }

        try{
            packageBean.create( 111L, PackagingType.SECONDARY, "Plastico");
            packageBean.create( 222L, PackagingType.PRIMARY, "Vidro");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }
    }
}