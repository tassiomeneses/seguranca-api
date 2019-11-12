package com.example.seguranca.util;


import java.util.List;
import java.util.MissingResourceException;
import java.util.ResourceBundle;

public class MessageUtil {

    public static ResourceBundle getResourceBundle() {
        return ResourceBundle.getBundle("messages", new UTF8Control());
    }

    public static String getMessgeBuilder(List<String> msgs) {
        StringBuilder errMsgs = new StringBuilder();
        ResourceBundle bundle = getResourceBundle();

        msgs.forEach(m -> {
            try {
                errMsgs.append(bundle.getString(m));
            } catch (MissingResourceException e) {
                errMsgs.append(m);
            } finally {
                errMsgs.append("\n");
            }
        });

        return errMsgs.toString();
    }
}
