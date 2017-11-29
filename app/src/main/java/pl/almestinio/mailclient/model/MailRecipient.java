package pl.almestinio.mailclient.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.io.Serializable;
import java.util.Observable;

/**
 * Created by mesti193 on 29.11.2017.
 */

@DatabaseTable(tableName = "MailRecipient")
public class MailRecipient extends Observable implements Serializable{

    private static final String COL_ID = "id";
    private static final String COL_RECIPIENT = "recipient";

    @DatabaseField(generatedId = true, columnName = COL_ID)
    private int id;
    @DatabaseField(columnName = COL_RECIPIENT)
    private String recipient;


    public MailRecipient(){

    }
    public MailRecipient(String recipient){
        this.recipient = recipient;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    @Override
    public String toString() {
        return recipient;
    }
}
