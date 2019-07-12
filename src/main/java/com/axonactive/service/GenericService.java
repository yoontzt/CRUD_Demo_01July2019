package com.axonactive.service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public abstract class GenericService<E> {
	
	@PersistenceContext(name="demo")
	protected EntityManager em;

	public void save(E entity) {
			em.persist(entity);
	}
	
	public void update(E entity) {
		em.merge(entity);
	}
	
	public void remove(E entity) {
		em.remove(entity);
	}
}
