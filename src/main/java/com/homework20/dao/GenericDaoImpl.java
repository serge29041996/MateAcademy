package com.homework20.dao;

import com.homework19.utils.HibernateSessionFactoryUtil;
import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Optional;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

/**
 * Realization generic dao.
 */
public abstract class GenericDaoImpl<T> implements GenericDao<T> {
  private final Class<T> entityClass;

  public GenericDaoImpl() {
    this.entityClass = (Class<T>) ((ParameterizedType) this.getClass().getGenericSuperclass())
        .getActualTypeArguments()[0];
  }

  @Override
  public void save(T newObject) {
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Transaction transaction = session.beginTransaction();
      session.save(newObject);
      transaction.commit();
    }
  }

  @Override
  public Optional<T> get(long id) {
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      return Optional.ofNullable(session.get(this.entityClass, id));
    }
  }

  @Override
  public long count() {
    return getAll().size();
  }

  @Override
  public void deleteAll() {
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      List<T> entities = getAll();
      Transaction transaction = session.beginTransaction();
      for (T entity : entities) {
        session.delete(entity);
      }
      transaction.commit();
    }
  }

  @Override
  public List<T> getAll() {
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
      CriteriaQuery<T> criteriaQuery = criteriaBuilder.createQuery(this.entityClass);
      Root<T> root = criteriaQuery.from(this.entityClass);
      criteriaQuery.select(root);
      Query<T> query = session.createQuery(criteriaQuery);
      List<T> objects = query.getResultList();
      return objects;
    }
  }

  @Override
  public void delete(T objectForDeleting) {
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Transaction transaction = session.beginTransaction();
      session.delete(objectForDeleting);
      transaction.commit();
    }
  }

  @Override
  public void update(T newObject) {
    try (Session session = HibernateSessionFactoryUtil
        .getSessionFactory()
        .openSession()) {
      Transaction transaction = session.beginTransaction();
      session.update(newObject);
      transaction.commit();
    }
  }
}
