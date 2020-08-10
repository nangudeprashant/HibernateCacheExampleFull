package com.javalive.hibernate;

import java.util.HashMap;
import java.util.Map;

import org.ehcache.jsr107.EhcacheCachingProvider;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cache.jcache.JCacheRegionFactory;
import org.hibernate.cfg.Environment;

import com.javalive.entity.Department;
import com.javalive.entity.Employee;

/**
 * @author javalive.com
 */
public class HibernateUtil {

   private static StandardServiceRegistry registry;
   private static SessionFactory sessionFactory;

   public static SessionFactory getSessionFactory() {
      if (sessionFactory == null) {
         try {
            StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();

            Map<String, Object> settings = new HashMap<>();
            settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
            settings.put(Environment.URL,"jdbc:mysql://localhost:3306/test1?useSSL=false");
            settings.put(Environment.USER, "root");
            settings.put(Environment.PASS, "root");
            settings.put(Environment.GENERATE_STATISTICS, true);
            settings.put(Environment.SHOW_SQL, true);
            //settings.put(Environment.HBM2DDL_AUTO, "update");

            // Enable second level cache (default value is true)
            settings.put(Environment.USE_SECOND_LEVEL_CACHE, true);

            // Enable Query cache
            settings.put(Environment.USE_QUERY_CACHE, true);

            // Specify cache region factory class
            settings.put(Environment.CACHE_REGION_FACTORY, JCacheRegionFactory.class);

            // Specify cache provider
            settings.put("hibernate.javax.cache.provider", EhcacheCachingProvider.class);

            registryBuilder.applySettings(settings);
            registry = registryBuilder.build();
            MetadataSources sources = new MetadataSources(registry)
                  .addAnnotatedClass(Department.class).addAnnotatedClass(Employee.class);
            Metadata metadata = sources.getMetadataBuilder().build();
            sessionFactory = metadata.getSessionFactoryBuilder().build();
         } catch (Exception e) {
            if (registry != null) {
               StandardServiceRegistryBuilder.destroy(registry);
            }
            e.printStackTrace();
         }
      }
      return sessionFactory;
   }

   public static void shutdown() {
      if (registry != null) {
         StandardServiceRegistryBuilder.destroy(registry);
      }
   }
}
