package com.example.granjapp;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.List;

public class dbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "granjapp.db";
    private static final int DATABASE_VERSION = 1;

    public dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_USUARIOS_TABLE = "CREATE TABLE " +
                UsuariosContract.UsuarioEntry.TABLE_NAME_USUARIOS + " (" +
                UsuariosContract.UsuarioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.UsuarioEntry.COLUMN_NOMBRE + " TEXT NOT NULL, " +
                UsuariosContract.UsuarioEntry.COLUMN_APELLIDO + " TEXT NOT NULL, " +
                UsuariosContract.UsuarioEntry.COLUMN_CORREO + " TEXT NOT NULL, " +
                UsuariosContract.UsuarioEntry.COLUMN_CONTRASENA + " TEXT NOT NULL" +
                ");";

        final String SQL_CREATE_CAMPESINOS_TABLE = "CREATE TABLE " +
                UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS + " (" +
                UsuariosContract.CampesinoEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.CampesinoEntry.COLUMN_NOMBRE + " TEXT NOT NULL, " +
                UsuariosContract.CampesinoEntry.COLUMN_APELLIDO + " TEXT NOT NULL, " +
                UsuariosContract.CampesinoEntry.COLUMN_CORREO + " TEXT NOT NULL, " +
                UsuariosContract.CampesinoEntry.COLUMN_CONTRASENA + " TEXT NOT NULL, " +
                UsuariosContract.CampesinoEntry.COLUMN_NOMBRE_GRANJA + " TEXT NOT NULL" +

                ");";


        final String SQL_CREATE_SOCIOS_TABLE = "CREATE TABLE " +
                UsuariosContract.SocioEntry.TABLE_NAME_SOCIOS + " (" +
                UsuariosContract.SocioEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.SocioEntry.COLUMN_NOMBRE + " TEXT NOT NULL, " +
                UsuariosContract.SocioEntry.COLUMN_APELLIDO + " TEXT NOT NULL, " +
                UsuariosContract.SocioEntry.COLUMN_CORREO + " TEXT NOT NULL, " +
                UsuariosContract.SocioEntry.COLUMN_CONTRASENA + " TEXT NOT NULL, " +
                UsuariosContract.SocioEntry.COLUMN_NOMBRE_ORGANIZACION + " TEXT NOT NULL" +
                ");";

        final String RegistrarPuntoVenta_TABLE = "CREATE TABLE " +
                UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + " (" +
                UsuariosContract.RegistrarPuntoVentaEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION + " TEXT NOT NULL, " +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO + " INTEGER NOT NULL," +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA + " TEXT NOT NULL," +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA + " TEXT NOT NULL," +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA + " TEXT NOT NULL," +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO + " TEXT DEFAULT 'FALSE'," +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LAITUD + " REAL NOT NULL ," +
                UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LONGITUD + " REAL NOT NULL" +

                ");";

        db.execSQL(SQL_CREATE_USUARIOS_TABLE);
        db.execSQL(SQL_CREATE_CAMPESINOS_TABLE);
        db.execSQL(SQL_CREATE_SOCIOS_TABLE);
        db.execSQL(RegistrarPuntoVenta_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + UsuariosContract.UsuarioEntry.TABLE_NAME_USUARIOS);
        db.execSQL("DROP TABLE IF EXISTS " + UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS);
        db.execSQL("DROP TABLE IF EXISTS " + UsuariosContract.SocioEntry.TABLE_NAME_SOCIOS);
        db.execSQL("DROP TABLE IF EXISTS " + UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES);
        onCreate(db);
    }


    public long insertarUsuario(String nombre, String apellido, String correo, String contrasena) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsuariosContract.UsuarioEntry.COLUMN_NOMBRE, nombre);
        values.put(UsuariosContract.UsuarioEntry.COLUMN_APELLIDO, apellido);
        values.put(UsuariosContract.UsuarioEntry.COLUMN_CORREO, correo);
        values.put(UsuariosContract.UsuarioEntry.COLUMN_CONTRASENA, contrasena);
        return db.insert(UsuariosContract.UsuarioEntry.TABLE_NAME_USUARIOS, null, values);
    }

    public long insertarCampesino(String nombre, String apellido, String correo, String contrasena, String nombreGranja) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsuariosContract.CampesinoEntry.COLUMN_NOMBRE, nombre);
        values.put(UsuariosContract.CampesinoEntry.COLUMN_APELLIDO, apellido);
        values.put(UsuariosContract.CampesinoEntry.COLUMN_CORREO, correo);
        values.put(UsuariosContract.CampesinoEntry.COLUMN_CONTRASENA, contrasena);
        values.put(UsuariosContract.CampesinoEntry.COLUMN_NOMBRE_GRANJA, nombreGranja);
        return db.insert(UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS, null, values);
    }

    public long insertarSocio(String nombre, String apellido, String correo, String contrasena, String nombreOrganizacion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsuariosContract.SocioEntry.COLUMN_NOMBRE, nombre);
        values.put(UsuariosContract.SocioEntry.COLUMN_APELLIDO, apellido);
        values.put(UsuariosContract.SocioEntry.COLUMN_CORREO, correo);
        values.put(UsuariosContract.SocioEntry.COLUMN_CONTRASENA, contrasena);
        values.put(UsuariosContract.SocioEntry.COLUMN_NOMBRE_ORGANIZACION, nombreOrganizacion);
        return db.insert(UsuariosContract.SocioEntry.TABLE_NAME_SOCIOS, null, values);
    }

    public boolean validarCredencialesCampesino(String correo, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS,
                null,
                UsuariosContract.CampesinoEntry.COLUMN_CORREO + " = ? AND " +
                        UsuariosContract.CampesinoEntry.COLUMN_CONTRASENA + " = ?",
                new String[]{correo, contraseña},
                null,
                null,
                null
        );
        boolean credencialesValidas = cursor.getCount() > 0;
        cursor.close();
        return credencialesValidas;
    }

    public boolean validarCredencialesComprador(String correo, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UsuariosContract.UsuarioEntry.TABLE_NAME_USUARIOS,
                null,
                UsuariosContract.UsuarioEntry.COLUMN_CORREO + " = ? AND " +
                        UsuariosContract.UsuarioEntry.COLUMN_CONTRASENA + " = ?",
                new String[]{correo, contraseña},
                null,
                null,
                null
        );
        boolean credencialesValidas = cursor.getCount() > 0;
        cursor.close();
        return credencialesValidas;
    }

    public boolean validarCredencialesSocio(String correo, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UsuariosContract.SocioEntry.TABLE_NAME_SOCIOS,
                null,
                UsuariosContract.SocioEntry.COLUMN_CORREO + " = ? AND " +
                        UsuariosContract.SocioEntry.COLUMN_CONTRASENA + " = ?",
                new String[]{correo, contraseña},
                null,
                null,
                null
        );
        boolean credencialesValidas = cursor.getCount() > 0;
        cursor.close();
        return credencialesValidas;
    }

    public boolean existeCorreo(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UsuariosContract.UsuarioEntry.TABLE_NAME_USUARIOS,
                null,
                UsuariosContract.UsuarioEntry.COLUMN_CORREO + " = ?",
                new String[]{correo},
                null,
                null,
                null
        );
        boolean existeCorreo = cursor.getCount() > 0;
        cursor.close();
        return existeCorreo;
    }

    public long insertarEntrada(int idUsuario, String direccion, String horaEntrada, String horaSalida, String dia, String estado, double latitud, double longitud) {
        // Verificar los valores de entrada
        if (idUsuario < 0 || direccion.isEmpty() || horaEntrada.isEmpty() || horaSalida.isEmpty() || dia.isEmpty() || estado.isEmpty()) {
            Log.e("ERROR", "Valores de entrada no válidos");
            return -1;
        }

        SQLiteDatabase db = null;
        long resultado = -1;
        try {
            db = this.getWritableDatabase();
            ContentValues values = new ContentValues();
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO, idUsuario);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION, direccion);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA, horaEntrada);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA, horaSalida);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA, dia);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO, estado);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LAITUD, latitud);
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LONGITUD, longitud);


            // Insertar el registro y obtener el ID insertado
            resultado = db.insert(UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES, null, values);
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al insertar entrada: ", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return resultado; // Devuelve el ID del registro insertado
    }

    public int obtenerIdUsuarioPorCorreo(String correo) {
        SQLiteDatabase db = null;
        int idUsuario = -1;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + UsuariosContract.CampesinoEntry._ID +
                    " FROM " + UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS +
                    " WHERE " + UsuariosContract.CampesinoEntry.COLUMN_CORREO + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{correo});
            int idIndex = cursor.getColumnIndex(UsuariosContract.CampesinoEntry._ID);
            if (idIndex != -1 && cursor.moveToFirst()) {
                idUsuario = cursor.getInt(idIndex);
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al obtener ID del usuario: ", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return idUsuario;
    }


    public String obtenerEstadoPuntoVenta(int idUsuario) {
        SQLiteDatabase db = null;
        String estado = null;
        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO +
                    " FROM " + UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES +
                    " WHERE " + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO + " = ?" +
                    " ORDER BY " + UsuariosContract.RegistrarPuntoVentaEntry._ID + " DESC LIMIT 1";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});
            if (cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO);
                if (columnIndex != -1) {
                    estado = cursor.getString(columnIndex);
                }
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al obtener estado del punto de venta: ", e);
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return estado;
    }


    public boolean actualizarEntradaExistente(int idTabla, String fecha, String horaEntrada, String horaSalida, String direccion) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA, horaEntrada);
        values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA, horaSalida);
        values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA, fecha);
        values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION, direccion);
        int rowsAffected = db.update(
                UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES,
                values,
                UsuariosContract.RegistrarPuntoVentaEntry._ID + " = ?",
                new String[]{String.valueOf(idTabla)}
        );
        return rowsAffected > 0;
    }


    public boolean actualizarEstadoEntrada(int idUsuario, long idTabla, String nuevoEstado) {
        SQLiteDatabase db = null;
        int rowsAffected = 0;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO, nuevoEstado);

            db.beginTransaction();

            rowsAffected = db.update(
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES,
                    values,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO + " = ? AND " +
                            UsuariosContract.RegistrarPuntoVentaEntry._ID + " = ?",
                    new String[]{String.valueOf(idUsuario), String.valueOf(idTabla)}
            );

            db.setTransactionSuccessful(); // Confirmar la transacción si la actualización tiene éxito
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al actualizar estado de la entrada: " + e.getMessage(), e);
        } finally {
            if (db != null) {
                if (db.inTransaction()) {
                    db.endTransaction(); // Finalizar la transacción
                }
                db.close();
            }
        }
        return rowsAffected > 0;
    }




    public List<PuntoVenta> obtenerTodosLosPuntosVenta(int idUsuario, boolean ordenarPorIdAscendente) {
        List<PuntoVenta> puntosVenta = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String[] projection = {
                    UsuariosContract.RegistrarPuntoVentaEntry._ID,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO
            };

            String selection = UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO + " = ?";
            String[] selectionArgs = {String.valueOf(idUsuario)};

            String sortOrder;
            if (ordenarPorIdAscendente) {
                sortOrder = UsuariosContract.RegistrarPuntoVentaEntry._ID + " ASC";
            } else {
                sortOrder = UsuariosContract.RegistrarPuntoVentaEntry._ID + " DESC";
            }

            Cursor cursor = db.query(
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    sortOrder
            );

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int puntoVentaId = cursor.getInt(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry._ID));
                        String direccion = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION));
                        String horaEntrada = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA));
                        String horaSalida = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA));
                        String dia = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA));
                        String estado = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO));

                        PuntoVenta puntoVenta = new PuntoVenta(puntoVentaId, direccion, horaEntrada, horaSalida, dia, estado, "null", 0, 0);
                        puntosVenta.add(puntoVenta);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Error al obtener los puntos de venta: " + e.getMessage());
            // Manejar el error según sea necesario
        } finally {
            db.close();
        }

        return puntosVenta;
    }


    public long obtenerUltimoIdInsertado(int idUsuario) {
        SQLiteDatabase db = null;
        Cursor cursor = null;
        long lastInsertedId = -1;

        try {
            db = this.getReadableDatabase();
            String query = "SELECT MAX(" + UsuariosContract.RegistrarPuntoVentaEntry._ID + ") AS last_id FROM " +
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES +
                    " WHERE " + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO + " = ?";
            cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndex("last_id");
                if (columnIndex != -1) {
                    lastInsertedId = cursor.getLong(columnIndex);
                }
            }
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al obtener el último ID insertado para el usuario " + idUsuario + ": " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return lastInsertedId;
    }


    public long obtenerIdPuntoVentaActivo(int idUsuario) {
        ; // Crear una instancia de dbHelper
        SQLiteDatabase db = null;
        Cursor cursor = null;
        long idPuntoVentaActivo = -1;

        try {
            db = this.getReadableDatabase(); // Llamar al método getReadableDatabase() desde una instancia de dbHelper
            String query = "SELECT " + UsuariosContract.RegistrarPuntoVentaEntry._ID +
                    " FROM " + UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES +
                    " WHERE " + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO + " = ?" +
                    " AND " + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO + " = 'true'";
            cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

            if (cursor != null && cursor.moveToFirst()) {
                idPuntoVentaActivo = cursor.getLong(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry._ID));
            }
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al obtener el ID del punto de venta activo: " + e.getMessage());
        } finally {
            if (cursor != null) {
                cursor.close();
            }
            if (db != null) {
                db.close();
            }
        }
        return idPuntoVentaActivo;
    }


    public List<PuntoVenta> obtenerPuntosVentaActivos() {
        List<PuntoVenta> puntosVenta = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String[] projection = {
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry._ID,
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION,
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LAITUD, // Nueva columna de latitud
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LONGITUD, // Nueva columna de longitud
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA,
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA,
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA,
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO,
                    UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS + "." + UsuariosContract.CampesinoEntry.COLUMN_NOMBRE_GRANJA
            };

            String selection = UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO + " = ?";
            String[] selectionArgs = {"true"};

            String tableName = UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES +
                    " INNER JOIN " + UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS +
                    " ON " + UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO +
                    " = " + UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS + "." + UsuariosContract.CampesinoEntry._ID;

            Cursor cursor = db.query(
                    tableName,
                    projection,
                    selection,
                    selectionArgs,
                    null,
                    null,
                    null
            );

            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        int puntoVentaId = cursor.getInt(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry._ID));
                        String direccion = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIRECCION));
                        double latitud = cursor.getDouble(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LAITUD));
                        double longitud = cursor.getDouble(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUM_LONGITUD));
                        String horaEntrada = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_ENTRADA));
                        String horaSalida = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_HORA_SALIDA));
                        String dia = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_DIA));
                        String estado = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO));
                        String nombreGranja = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.CampesinoEntry.COLUMN_NOMBRE_GRANJA));

                        PuntoVenta puntoVenta = new PuntoVenta(puntoVentaId, direccion, horaEntrada, horaSalida, dia, estado, nombreGranja, latitud, longitud);
                        puntosVenta.add(puntoVenta);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Error al obtener los puntos de venta activos: " + e.getMessage());
            // Manejar el error según sea necesario
        } finally {
            db.close();
        }

        return puntosVenta;
    }





}









