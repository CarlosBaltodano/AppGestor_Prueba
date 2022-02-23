package com.example.appgestor.bd;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION=1;
    private static final String DATABASE_NOMBRE="AppGestor";
    public static final String TABLE_USERS="usuarios";
    public static final String TABLE_PDV="pdv";
    public static final String TABLE_PRODUCTOS="productos";



    public DbHelper(@Nullable Context context) {
        super(context,DATABASE_NOMBRE, null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_USERS+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "nombre TEXT NOT NULL," +
                "correo TEXT NOT NULL," +
                "usuario TEXT NOT NULL," +
                "password TEXT NOT NULL,"+
                "image BLOB)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_PDV+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                "codigo TEXT NOT NULL," +
                "nombre TEXT NOT NULL," +
                "direccion TEXT NOT NULL,"+
                "image BLOB)");

        db.execSQL("CREATE TABLE IF NOT EXISTS "+ TABLE_PRODUCTOS+"("+
                "id INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "id_pdv INTEGER,"+
                "nombre TEXT," +
                "costo DECIMAL(8,2)," +
                "mayor DECIMAL(8,2),"+
                "stock INTEGER(4) ,"+
                "FOREIGN KEY(id_pdv) REFERENCES pdv(id));");

        db.execSQL("INSERT INTO "+TABLE_USERS+"(nombre,correo,usuario,password) VALUES('Carlos Baltodano','carlos@gmail.com','beto','123456');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409183','YOSLI AMALI SEGUILAR','REAL S/N Urb: ESQ.10 NOVIEMBRE');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409184','METRO ALFONSO UGARTE','AV.ALFONSO UGARTE 740');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409185','TOTTUS ZORRITOS','AV.COLONIAL 1520');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409186','MEGAPLAZA LIMA NORTE','AV.INDEPENDENCIA 3150');");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Crisolx12 Bot',45.23,45.23,100);");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Crisolx12 Bot',45.23,45.23,100);");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Crisolx12 Bot',45.23,45.23,100);");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Crisolx12 Bot',45.23,45.23,100);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("ALTER TABLE "+TABLE_PDV+" ADD image BLOB;");

        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409183','YOSLI AMALI SEGUILAR','REAL S/N Urb: ESQ.10 NOVIEMBRE');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409184','METRO ALFONSO UGARTE','AV.ALFONSO UGARTE 740');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409185','TOTTUS ZORRITOS','AV.COLONIAL 1520');");
        db.execSQL("INSERT INTO "+TABLE_PDV+"(codigo,nombre,direccion) VALUES('409186','MEGAPLAZA LIMA NORTE','AV.INDEPENDENCIA 3150');");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(1,'Aceite Crisolx12 Bot',45.23,45.23,100);");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(2,'Agua Crisolx12 Bot',45.23,45.23,100);");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(3,'Leche Crisolx12 Bot',45.23,45.23,100);");

        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Cilx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Idealx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Metrox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Primorx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Bellx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Wongx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Cocinerox12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Bellsx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa SolSurx12 Bot',45.23,45.23,100);");
        db.execSQL("INSERT INTO "+TABLE_PRODUCTOS+"(id_pdv,nombre,costo,mayor,stock) VALUES(4,'Gaseosa Crisolx12 Bot',45.23,45.23,100);");
            onCreate(db);
    }


}
