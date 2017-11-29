package pl.almestinio.mailclient.database;

import com.j256.ormlite.stmt.DeleteBuilder;

import java.sql.SQLException;
import java.util.List;

import pl.almestinio.mailclient.model.MailRecipient;

/**
 * Created by mesti193 on 29.11.2017.
 */

public class DatabaseRecipient {

    public static List<MailRecipient> getRecipient() {
        List<MailRecipient> mailRecipientList = null;
        try {
            mailRecipientList = DatabaseHelper.instance.getRecipients().queryForAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return mailRecipientList;
    }

    public static void addOrUpdateRecipient(MailRecipient mailRecipient) {
        try {
            DatabaseHelper.instance.getRecipients().createOrUpdate(mailRecipient);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void deleteAllRecipients() {
        try {
            DeleteBuilder<MailRecipient, Integer> deleteBuilder = DatabaseHelper.instance.getRecipients().deleteBuilder();
            deleteBuilder.delete();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
