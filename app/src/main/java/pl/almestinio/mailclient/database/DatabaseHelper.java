package pl.almestinio.mailclient.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import pl.almestinio.mailclient.model.MailRecipient;

/**
 * Created by mesti193 on 29.11.2017.
 */

public class DatabaseHelper extends OrmLiteSqliteOpenHelper {

    private static final String DATABASE_NAME = "DatabaseMailClient.db";
    private static final int DATABASE_VERSION = 1;
    private Dao<MailRecipient, Integer> recipients = null;

    static DatabaseHelper instance;

    public static synchronized DatabaseHelper getInstance(Context context) {

        if (instance == null) instance = new DatabaseHelper(context);

        return instance;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database, ConnectionSource connectionSource) {
        try {
            TableUtils.createTable(connectionSource, MailRecipient.class);
//            TableUtils.dropTable(connectionSource, MailRecipient.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, ConnectionSource connectionSource, int oldVersion, int newVersion) {
        try {
            TableUtils.createTableIfNotExists(connectionSource, MailRecipient.class);
//            TableUtils.dropTable(connectionSource, MailRecipient.class, false);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<String> allSql = new ArrayList<>();
        for (String sql : allSql) {
            db.execSQL(sql);
        }
    }

    public Dao<MailRecipient, Integer> getRecipients() {
        if (recipients == null) {
            try {
                recipients = getDao(MailRecipient.class);
            } catch (java.sql.SQLException e) {
                e.printStackTrace();
            }
        }
        return recipients;
    }

}
