package pt.ipleiria.estg.dei.ei.dae.backend.ejbs;

import jakarta.annotation.PostConstruct;
import jakarta.ejb.EJB;
import jakarta.ejb.Singleton;
import jakarta.ejb.Startup;

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


    @PostConstruct
    public void populateDB() {
        System.out.println("Hello Java EE!");

        try{
            endConsumerBean.create("Cliente1", "123", "Cliente1", "Cliente1@gmail.com", "Cliente");
            endConsumerBean.create("Cliente2", "123", "Cliente2", "Cliente2@gmail.com", "Cliente");
            endConsumerBean.create("Cliente3", "123", "Cliente3", "Cliente3@gmail.com", "Cliente");
        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        try{
            manufacturerBean.create("Fabricante1", "123", "Fabricante1", "Fabricante1@gmail.com", "Cliente");
            manufacturerBean.create("Fabricante2", "123", "Fabricante2", "Fabricante2@gmail.com", "Cliente");
            manufacturerBean.create("Fabricante3", "123", "Fabricante3", "Fabricante3@gmail.com", "Cliente");
        }catch (Exception e){
            logger.severe(e.getMessage());
        }

        try{
            logisticsOperatorBean.create("Operador1", "123", "Operador1", "Operador1@gmail.com", "Cliente");
            logisticsOperatorBean.create("Operador2", "123", "Operador2", "Operador2@gmail.com", "Cliente");
            logisticsOperatorBean.create("Operador3", "123", "Operador3", "Operador3@gmail.com", "Cliente");
        }catch (Exception e){
            logger.severe(e.getMessage());
        }


    }
}