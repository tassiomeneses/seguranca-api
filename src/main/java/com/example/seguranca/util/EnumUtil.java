package com.example.seguranca.util;

public class EnumUtil {

    public static <T extends Enum<T>> T getEnumFromString(Class<T> enumClass, String value) {
        if (enumClass == null) {
            throw new IllegalArgumentException("O valor de enumClass não pode ser nulo.");
        }

        for(Enum<?> enumValue : enumClass.getEnumConstants()) {
            if (enumValue.toString().equalsIgnoreCase(value)) {
                return (T) enumValue;
            }

            if(enumValue.name().equals(value)) {
                return (T) enumValue;
            }
        }

        StringBuilder errorMessage = new StringBuilder();
        boolean bFirstTime = true;
        for(Enum<?> enumValue : enumClass.getEnumConstants()) {
            errorMessage.append(bFirstTime ? "": ",").append(enumValue);
            bFirstTime = false;
        }

        throw new IllegalArgumentException(value + " não é um valor válido para o enum " +
                enumClass.getName() + ". Valores válidos são: " + errorMessage);

    }

}
