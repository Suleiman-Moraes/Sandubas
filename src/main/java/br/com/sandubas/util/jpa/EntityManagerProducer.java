package br.com.sandubas.util.jpa;

import java.io.IOException;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * @author thiago v1.0.1
 * @version v1.0.1 05/01/2017
 * @since v1.0.0
 */
@ApplicationScoped
public class EntityManagerProducer {
   
   private EntityManagerFactory factory;
   
   public EntityManagerProducer() throws IOException {
      factory = Persistence.createEntityManagerFactory("ProjetoPU");
   }
   
   @Produces
   @RequestScoped
   public EntityManager createEntityManager() {
      return factory.createEntityManager();
   }
   
   public void closeEntityManager(@Disposes EntityManager manager) {
      if (manager.isOpen()) {
         manager.close();
      }
   }
   
}
