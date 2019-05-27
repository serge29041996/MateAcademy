package com.homework19.utils;

import com.homework13.model.User;
import com.homework17.model.CodeConfirmation;
import com.homework17.model.Good;
import com.homework20.model.Basket;
import com.homework20.model.BasketGood;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
  private static SessionFactory sessionFactory;
  private static final Logger LOGGER = Logger.getLogger(HibernateSessionFactoryUtil.class);

  private HibernateSessionFactoryUtil() {}

  /**
   * Get session factory.
   * @return session factory
   */
  public static SessionFactory getSessionFactory() {
    if (sessionFactory == null) {
      try {
        Configuration configuration = new Configuration().configure();
        configuration.addAnnotatedClass(User.class);
        configuration.addAnnotatedClass(Good.class);
        configuration.addAnnotatedClass(Basket.class);
        configuration.addAnnotatedClass(BasketGood.class);
        configuration.addAnnotatedClass(CodeConfirmation.class);
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder()
            .applySettings(configuration.getProperties());
        sessionFactory = configuration.buildSessionFactory(builder.build());
      } catch (Exception e) {
        LOGGER.error("Unable initialize session factory", e);
      }
    }
    return sessionFactory;
  }
}