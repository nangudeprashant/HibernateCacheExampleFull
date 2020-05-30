package com.javalive.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javalive.entity.Employee;

/**
 * @author javalive.com 
 * 		   Hibernate Query Cache to avoid amount of traffic between
 *         your application and database. The query cache is responsible for
 *         caching the results of queries. Let us have a look how Hibernate uses
 *         the query cache to retrieve objects. Note that the query cache does
 *         not cache the state of the actual entities in the result set; it
 *         caches only identifier values and results of value type. So the query
 *         cache should always be used in conjunction with the second-level
 *         cache. 
 *         The combination of the query and the values provided as
 *         parameters to that query is used as a key, and the value is the list
 *         of identifiers for that query. Note that this becomes more complex
 *         from an internal perspective as you begin to consider that a query
 *         can have an effect of altering associations to objects returned that
 *         query; not to mention the fact that a query may not return whole
 *         objects, but may in fact only return scalar values (when you have
 *         supplied a select clause for instance). That being said, this is a
 *         sound and reliable way to think of the query cache conceptually.
 */
public class QueryCacheExample {

	public static void main(String[] args) {
		Session session = null;
		Transaction transaction = null;
		try {

			// Get employee list from DATABASE
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			@SuppressWarnings("unchecked")
			List<Employee> employees = session.createQuery("from Employee").setCacheable(true)//*****IMP
					.setCacheRegion("employee.cache").list();
			for (Employee employee : employees) {
				System.out.println("\tEmployee Name : " + employee.getName());
			}
			transaction.commit();
			session.close();

			/// Get employee list from Cached result
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			@SuppressWarnings("unchecked")
			List<Employee> employees2 = session.createQuery("from Employee").setCacheable(true)//*****IMP
					.setCacheRegion("employee.cache").list();
			for (Employee employee : employees2) {
				System.out.println("\tEmployee Name : " + employee.getName());
			}
			transaction.commit();
			session.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
		HibernateUtil.shutdown();
	}
}
