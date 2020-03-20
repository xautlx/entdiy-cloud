package com.entdiy.tool;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.util.ClassUtils;
import org.springframework.util.SystemPropertyUtils;

import java.lang.annotation.Annotation;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Slf4j
public class EntityClassScaner {

    protected static final String DEFAULT_RESOURCE_PATTERN = "**/*.class";

    @SneakyThrows
    public static Collection<Class<?>> scan(Class annotationClass, String... basePackages) {
        Set<Class<?>> classes = new HashSet();
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metadataReaderFactory = new CachingMetadataReaderFactory(resolver);

        for (String basePackage : basePackages) {
            log.info("Processing annotation {} for package: {}", annotationClass, basePackage);
            String packageSearchPath = ResourcePatternResolver.CLASSPATH_ALL_URL_PREFIX +
                    ClassUtils.convertClassNameToResourcePath(SystemPropertyUtils.resolvePlaceholders(basePackage)) + "/" + DEFAULT_RESOURCE_PATTERN;
            Resource[] resources = resolver.getResources(packageSearchPath);

            for (Resource resource : resources) {
                MetadataReader metadataReader = metadataReaderFactory.getMetadataReader(resource);
                String className = metadataReader.getClassMetadata().getClassName();
                Class<?> clazz = Class.forName(className);
                //System.out.println("Process class: " + clazz);
                Annotation annotation = clazz.getAnnotation(annotationClass);
                if (annotation != null) {
                    log.info(" - Adding class: {}", clazz);
                    classes.add(clazz);
                }
            }
        }

        return classes;
    }
}
