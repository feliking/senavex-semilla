/**
 *   ____
 *  / ___|  ___ _ __   __ ___   _______  __
 *  \___ \ / _ \ '_ \ / _` \ \ / / _ \ \/ /
 *   ___) |  __/ | | | (_| |\ V /  __/>  <
 *  |____/ \___|_| |_|\__,_| \_/ \___/_/\_\
 *
 *  Copyright © 2020
 *  http://www.senavex.gob.bo/licenses/LICENSE-1.0
 */
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bo.gob.senavex.vortex2.base;

import bo.gob.senavex.vortex2.AuditorListener;
import static bo.gob.senavex.vortex2.base.SmokeTest.em;
import java.util.List;
import java.util.Map;
import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.FlushModeType;
import javax.persistence.LockModeType;
import javax.persistence.Query;
import javax.persistence.StoredProcedureQuery;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.CriteriaUpdate;
import javax.persistence.metamodel.Metamodel;

/**
 * @author Willyams Yujra
 */
public class EntityManagerWrap implements EntityManager {
   private final EntityManager warp;
   private final AuditorListener al = new AuditorListener();

   public EntityManagerWrap(EntityManager warp) {
      this.warp = warp;
   }

   @Override
   public void persist(Object o) {
      al.prePersist(o);
      warp.persist(o);
   }

   @Override
   public <T> T merge(T t) {
      al.preUpdate(t);
      return warp.merge(t);
   }

   @Override
   public void remove(Object o) {
      warp.remove(o);
   }

   @Override
   public <T> T find(Class<T> type, Object o) {
      return warp.find(type, o);
   }

   @Override
   public <T> T find(Class<T> type, Object o, Map<String, Object> map) {
      return warp.find(type, o, map);
   }

   @Override
   public <T> T find(Class<T> type, Object o, LockModeType lmt) {
      return warp.find(type, o, lmt);
   }

   @Override
   public <T> T find(Class<T> type, Object o, LockModeType lmt, Map<String, Object> map) {
      return warp.find(type, o, lmt, map);
   }

   @Override
   public <T> T getReference(Class<T> type, Object o) {
      return warp.getReference(type, o);
   }

   @Override
   public void flush() {
      warp.flush();
   }

   @Override
   public void setFlushMode(FlushModeType fmt) {
      warp.setFlushMode(fmt);
   }

   @Override
   public FlushModeType getFlushMode() {
      return warp.getFlushMode();
   }

   @Override
   public void lock(Object o, LockModeType lmt) {
      warp.lock(o, lmt);
   }

   @Override
   public void lock(Object o, LockModeType lmt, Map<String, Object> map) {
      warp.lock(o, lmt, map);
   }

   @Override
   public void refresh(Object o) {
      warp.refresh(o);
   }

   @Override
   public void refresh(Object o, Map<String, Object> map) {
      warp.refresh(o, map);
   }

   @Override
   public void refresh(Object o, LockModeType lmt) {
      warp.refresh(o, lmt);
   }

   @Override
   public void refresh(Object o, LockModeType lmt, Map<String, Object> map) {
      warp.refresh(o, lmt, map);
   }

   @Override
   public void clear() {
      warp.clear();
   }

   @Override
   public void detach(Object o) {
      warp.detach(o);
   }

   @Override
   public boolean contains(Object o) {
      return warp.contains(o);
   }

   @Override
   public LockModeType getLockMode(Object o) {
      return warp.getLockMode(o);
   }

   @Override
   public void setProperty(String string, Object o) {
      warp.setProperty(string, o);
   }

   @Override
   public Map<String, Object> getProperties() {
      return warp.getProperties();
   }

   @Override
   public Query createQuery(String string) {
      return warp.createQuery(string);
   }

   @Override
   public <T> TypedQuery<T> createQuery(CriteriaQuery<T> cq) {
      return warp.createQuery(cq);
   }

   @Override
   public Query createQuery(CriteriaUpdate cu) {
      return warp.createQuery(cu);
   }

   @Override
   public Query createQuery(CriteriaDelete cd) {
      return warp.createQuery(cd);
   }

   @Override
   public <T> TypedQuery<T> createQuery(String string, Class<T> type) {
      return warp.createQuery(string, type);
   }

   @Override
   public Query createNamedQuery(String string) {
      return warp.createNamedQuery(string);
   }

   @Override
   public <T> TypedQuery<T> createNamedQuery(String string, Class<T> type) {
      return warp.createNamedQuery(string, type);
   }

   @Override
   public Query createNativeQuery(String string) {
      return warp.createNativeQuery(string);
   }

   @Override
   public Query createNativeQuery(String string, Class type) {
      return warp.createNativeQuery(string, type);
   }

   @Override
   public Query createNativeQuery(String string, String string1) {
      return warp.createNativeQuery(string, string1);
   }

   @Override
   public StoredProcedureQuery createNamedStoredProcedureQuery(String string) {
      return warp.createNamedStoredProcedureQuery(string);
   }

   @Override
   public StoredProcedureQuery createStoredProcedureQuery(String string) {
      return warp.createStoredProcedureQuery(string);
   }

   @Override
   public StoredProcedureQuery createStoredProcedureQuery(String string, Class... types) {
      return warp.createStoredProcedureQuery(string, types);
   }

   @Override
   public StoredProcedureQuery createStoredProcedureQuery(String string, String... strings) {
      return warp.createStoredProcedureQuery(string, strings);
   }

   @Override
   public void joinTransaction() {
      warp.joinTransaction();
   }

   @Override
   public boolean isJoinedToTransaction() {
      return warp.isJoinedToTransaction();
   }

   @Override
   public <T> T unwrap(Class<T> type) {
      return warp.unwrap(type);
   }

   @Override
   public Object getDelegate() {
      return warp.getDelegate();
   }

   @Override
   public void close() {
      warp.close();
   }

   @Override
   public boolean isOpen() {
      return warp.isOpen();
   }

   @Override
   public EntityTransaction getTransaction() {
      return warp.getTransaction();
   }

   @Override
   public EntityManagerFactory getEntityManagerFactory() {
      return warp.getEntityManagerFactory();
   }

   @Override
   public CriteriaBuilder getCriteriaBuilder() {
      return warp.getCriteriaBuilder();
   }

   @Override
   public Metamodel getMetamodel() {
      return warp.getMetamodel();
   }

   @Override
   public <T> EntityGraph<T> createEntityGraph(Class<T> type) {
      return warp.createEntityGraph(type);
   }

   @Override
   public EntityGraph<?> createEntityGraph(String string) {
      return warp.createEntityGraph(string);
   }

   @Override
   public EntityGraph<?> getEntityGraph(String string) {
      return warp.getEntityGraph(string);
   }

   @Override
   public <T> List<EntityGraph<? super T>> getEntityGraphs(Class<T> type) {
      return warp.getEntityGraphs(type);
   }
}
