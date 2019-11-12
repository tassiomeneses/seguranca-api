package com.example.seguranca.util;

import org.springframework.beans.BeanWrapperImpl;

import javax.persistence.Entity;
import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

public class EntityUtil {

    public static Object createNewInstanceWithProperties(Object source, String[] properties)
            throws InstantiationException, IllegalAccessException {
        String propertiesClass[] = Arrays.stream(properties).map(t -> t.split("\\.")[0]).toArray(String[]::new);

        BeanWrapperImpl bwSource = new BeanWrapperImpl(source);
        Object target = source.getClass().newInstance();
        BeanWrapperImpl bwTarget = new BeanWrapperImpl(target);
        PropertyDescriptor[] propertyDescriptors = bwSource.getPropertyDescriptors();

        Arrays.sort(propertiesClass, String::compareTo);
        for (PropertyDescriptor propertyDescriptor : propertyDescriptors) {
            String vlrAtributo = propertyDescriptor.getName();
            if (Arrays.binarySearch(propertiesClass, vlrAtributo, String::compareTo) >= 0 &&
                    bwSource.isWritableProperty(propertyDescriptor.getName())) {
                String subPropertiesClass[] =
                        Stream.of(properties)
                                .filter(p -> p.matches(vlrAtributo + "\\..*"))
                                .map(p -> p.substring(p.indexOf(".") + 1))
                                .toArray(String[]::new);
                if (Collection.class.isInstance(bwSource.getPropertyValue(propertyDescriptor.getName()))) {
                    bwTarget.setPropertyValue(propertyDescriptor.getName(), new ArrayList());
                    for (Object obj : (Collection)bwSource.getPropertyValue(propertyDescriptor.getName())) {
                        Object retorno = createNewInstanceWithProperties(obj, subPropertiesClass);
                        ((Collection)bwTarget.getPropertyValue(propertyDescriptor.getName())).add(retorno);
                    }
                } else if (propertyDescriptor.getPropertyType().isAnnotationPresent(Entity.class)) {
                    if (bwSource.getPropertyValue(vlrAtributo) != null) {
                        Object retorno = createNewInstanceWithProperties(bwSource.getPropertyValue(vlrAtributo), subPropertiesClass);
                        bwTarget.setPropertyValue(propertyDescriptor.getName(), retorno);
                    }
                } else {
                    bwTarget.setPropertyValue(propertyDescriptor.getName(), bwSource.getPropertyValue(propertyDescriptor.getName()));
                }
            }
        }

        return target;
    }

}
