package com.javalive.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javalive.entity.Department;
import com.javalive.entity.Employee;

/**
 * @author javalive.com 
 *         Earlier we have annotated the Employee and Department
 *         entities and List<Employee> property with the @Cache annotation. To
 *         know whether the @Cache annotation is working or not, create an
 *         EntityCollectionCacheExample class and write the following code in
 *         it.
 */
public class EntityCollectionCacheExample {

	public static void main(String[] args) {
		Session session = null;
		Transaction transaction = null;
		try {

			// Get Department from DATABASE
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			Department department = session.get(Department.class, 11L);
			System.out.println("Department :" + department.getName());
			List<Employee> employees = department.getEmployees();
			for (Employee employee : employees) {
				System.out.println("\tEmployee Name : " + employee.getName());
			}
			transaction.commit();
			session.close();

			// Get Department from Cache
			session = HibernateUtil.getSessionFactory().openSession();
			transaction = session.getTransaction();
			transaction.begin();
			Department department2 = session.get(Department.class, 11L);
			System.out.println("Department :" + department2.getName());
			List<Employee> employees2 = department2.getEmployees();
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
