package com.config;

import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataBase {
	
	private static EntityManagerFactory factory;
	private static EntityManager entityManager;
	private static EntityManager em = Persistence.createEntityManagerFactory("tarefas").createEntityManager();
	
	
	public static EntityManager getEm() {
		return em;
	}
	public static EntityManager getCon() {
		if(factory == null) {
			
			factory = Persistence.createEntityManagerFactory("tarefas");
		}
		entityManager = factory.createEntityManager();
		return entityManager;
	}
	public static void closeCon() {
		if(entityManager != null && entityManager.isOpen()) {
			entityManager.close();
		}
	}
	public static void dbSave(Object entity) throws Exception {
		try {
			getEm().getTransaction().begin();
			getEm().persist(entity);
			getEm().getTransaction().commit();
		} catch (Exception e) {
        	getEm().getTransaction().rollback();
            throw new Exception("operação não realizada \n" + "Erro exception number 4 - TransactionRequiredException");
        }
	}

	public static void dbUpdate(Object entity) throws Exception {
		try {
			getEm().getTransaction().begin();
			getEm().merge(entity);
			getEm().getTransaction().commit();
		} catch (Exception e) {
        	getEm().getTransaction().rollback();
            throw new Exception("operação não realizada \n" + "Erro exception number 4 - TransactionRequiredException");
        }
	}
	public static void dbDelete (Object entity) throws Exception {
		try {
			getEm().getTransaction().begin();
			getEm().remove(entity);
			getEm().getTransaction().commit();
		} catch (Exception e) {
        	getEm().getTransaction().rollback();
            throw new Exception("operação não realizada ");
        }
	}
}
