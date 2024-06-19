package edu.icet.pos.util;

import edu.icet.pos.entity.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtil {
    private static SessionFactory session = createSession();

    private static SessionFactory createSession() {
        StandardServiceRegistry builder = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml")
                .build();

        Metadata metadata = new MetadataSources(builder)
                .addAnnotatedClass(EmployeeEntity.class)
                .addAnnotatedClass(UserEntity.class)
                .addAnnotatedClass(CategoryEntity.class)
                .addAnnotatedClass(SupplierEntity.class)
                .addAnnotatedClass(ProductEntity.class)
                .addAnnotatedClass(ProductImageEntity.class)
                .addAnnotatedClass(StockEntity.class)
                .addAnnotatedClass(OrderEntity.class)
                .addAnnotatedClass(OrderDetailEntity.class)
                .addAnnotatedClass(CustomerEntity.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();

        return metadata.getSessionFactoryBuilder().build();
    }

    public static Session getSession(){
        return session.openSession();
    }
}
