package br.com.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import br.com.jpautil.JPAUtil;

public class DaoGeneric<E> {
	
	public void salvar(E entidade) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		manager.persist(entidade);
		
		transaction.commit();
		manager.close();
	}
	
	public E merge(E entidade) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		E retorno = manager.merge(entidade);
		
		transaction.commit();
		manager.close();
		
		return retorno;
	}
	
	public void deletar(E entidade) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		manager.remove(entidade);
		
		transaction.commit();
		manager.close();
	}
	
	public void deletarPorId(E entidade) {
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		Object id = JPAUtil.getPrimaryKey(entidade);
		manager.createQuery("delete from " + entidade.getClass().getSimpleName() + " where id = " + id).executeUpdate();
		
		transaction.commit();
		manager.close();
	}
	
	public List<E> getListEntity(E entidade){
		EntityManager manager = JPAUtil.getEntityManager();
		EntityTransaction transaction = manager.getTransaction();
		transaction.begin();
		
		List<E> retorno = manager.createQuery("from " + entidade.getClass().getSimpleName()).getResultList();
		
		transaction.commit();
		manager.close();
		return retorno;
	}

}
