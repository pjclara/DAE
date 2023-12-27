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

        try{
            packageBean.create( 1L, PackagingType.SECONDARY, "Plastico");
            packageBean.create( 2L, PackagingType.PRIMARY, "Vidro");
        } catch (Exception e) {
            logger.severe(e.getMessage());
        }

        try {
            productBean.create("Produto1", 10, "imagem1", "manufacturer1", 1L);
            productBean.create("Produto2", 20, "imagem2", "manufacturer2", 2L);
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


    }
}