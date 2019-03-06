package com.config;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class DataBase {
	
	private static EntityManagerFactory factory;
	private static EntityManager entityManager;
	
	
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
}
