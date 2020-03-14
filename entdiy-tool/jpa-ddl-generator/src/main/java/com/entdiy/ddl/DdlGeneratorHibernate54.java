package com.entdiy.ddl;

import com.entdiy.tool.EntityClassScaner;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.tool.hbm2ddl.SchemaExport;
import org.hibernate.tool.schema.TargetType;
import org.springframework.util.CollectionUtils;

import javax.persistence.Table;
import java.util.Collection;
import java.util.EnumSet;

@Slf4j
public class DdlGeneratorHibernate54 {

    @SneakyThrows
    public static String generateDdl(SchemaExport.Action action, String... basePackages) {
        final StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
        registryBuilder.applySetting("hibernate.dialect", "org.hibernate.dialect.MySQL57Dialect");
        registryBuilder.applySetting("hibernate.physical_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        registryBuilder.applySetting("hibernate.implicit_naming_strategy", "org.springframework.boot.orm.jpa.hibernate.SpringImplicitNamingStrategy");

        registryBuilder.applySetting("hibernate.multiTenancy", "DISCRIMINATOR");

        final StandardServiceRegistry standardRegistry = registryBuilder.build();

        final MetadataSources metadataSources = new MetadataSources(standardRegistry);
        Collection<Class<?>> entityClasses = EntityClassScaner.scan(Table.class, basePackages);
        if (CollectionUtils.isEmpty(entityClasses)) {
            log.info("Found NO class annotation with: " + Table.class);
            return null;
        }
        for (final Class<?> entityClass : entityClasses) {
            metadataSources.addAnnotatedClass(entityClass);
        }

        final Metadata metadata = metadataSources.buildMetadata();
        final SchemaExport export = new SchemaExport();
        export.setDelimiter(";");
        export.setManageNamespaces(true);
        export.setFormat(true);

        export.execute(EnumSet.of(TargetType.STDOUT), action, metadata);

        return null;
    }
}
