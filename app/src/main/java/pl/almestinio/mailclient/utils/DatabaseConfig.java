package pl.almestinio.mailclient.utils;

import com.j256.ormlite.android.apptools.OrmLiteConfigUtil;

import pl.almestinio.mailclient.model.MailRecipient;

/**
 * Created by mesti193 on 29.11.2017.
 */


public class DatabaseConfig extends OrmLiteConfigUtil {
    private static final Class<?>[] classes = new Class[] {
            MailRecipient.class
    };

    public static void main(String[] args) throws Exception {
        writeConfigFile("ormlite_config.txt", classes);
    }
}
