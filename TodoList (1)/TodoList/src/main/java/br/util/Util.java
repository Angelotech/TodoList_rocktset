package br.util;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

/*esta e responsavel por pegar todos os valores nulos e setar seus valores */
public class Util {

    public static void copyNonNullProperties(Object source, Object target){
        BeanUtils.copyProperties(source, target, getNullPropretyNames(source));
    }

    /* este metodo pegar todas as propriedades nulas */
    public static String[] getNullPropretyNames(Object source){
        final BeanWrapper src = new BeanWrapperImpl(source);

        /*obtendo propriedades do objeto */
        PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();

        for(PropertyDescriptor pd: pds){
            Object srcValue = src.getPropertyValue(pd.getName());
            if(srcValue == null){
                emptyNames.add(pd.getName());

            }

        }

        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);

    }
    
}
