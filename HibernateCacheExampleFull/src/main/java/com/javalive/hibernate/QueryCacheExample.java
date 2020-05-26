package com.javalive.hibernate;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.Transaction;

import com.javalive.entity.Employee;

/**
 * @author javalive.com
 */
public class QueryCacheExample {

   public static void main(String[] args) {
      Session session = null;
      Transaction transaction = null;
      try {

         //Get employee list from DATABASE
         session = HibernateUtil.getSessionFactory().openSession();
         transaction = session.getTransaction();
         transaction.begin();
         @SuppressWarnings("unchecked")
         List<Employee> employees=session.createQuery("from Employee")
               .setCacheable(true)
               .setCacheRegion("employee.cache")
               .list();
         for (Employee employee : employees) {
            System.out.println("\tEmployee Name : "+employee.getName());
         }
         transaction.commit();
         session.close();
         
         ///Get employee list from Cached result
         session = HibernateUtil.getSessionFactory().openSession();
         transaction = session.getTransaction();
         transaction.begin();
         @SuppressWarnings("unchecked")
         List<Employee> employees2=session.createQuery("from Employee")
               .setCacheable(true)
               .setCacheRegion("employee.cache")
               .list();
         for (Employee employee : employees2) {
            System.out.println("\tEmployee Name : "+employee.getName());
         }
         transaction.commit();
         session.close();
         
      } catch (Exception e) {
         e.printStackTrace();
      } 
      HibernateUtil.shutdown();
   }
}
