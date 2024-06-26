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



    // Variable estática para mantener la única instancia de dbHelper
    private static dbHelper instance;

    // Constructor privado para prevenir la creación de instancias directamente
    private dbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Método estático para obtener la instancia de dbHelper
    public static synchronized dbHelper getInstance(Context context) {
        if (instance == null) {
            instance = new dbHelper(context.getApplicationContext());
        }
        return instance;
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

        final String SobreMi_TABLE = "CREATE TABLE " +
                UsuariosContract.SobreMiEntry.TABLE_NAME_SOBREMI + " (" +
                UsuariosContract.SobreMiEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.SobreMiEntry.COLUMN_ID_Granejero + " INTEGER NOT NULL," +
                UsuariosContract.SobreMiEntry.COLUMN_DESCRIPCION + " TEXT NOT NULL" +
                ");";

        final String subirProductos_TABLE = "CREATE TABLE " +
                UsuariosContract.subirProductos.TABLE_NAME_PRODUCTOS + " (" +
                UsuariosContract.subirProductos._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                UsuariosContract.subirProductos.COLUMN_ID_USUARIO + " INTEGER NOT NULL," +
                UsuariosContract.subirProductos.COLUMN_NOMBRE_PRODUCTO + " TEXT NOT NULL," +
                UsuariosContract.subirProductos.COLUMN_CANTIDAD + " INTEGER NOT NULL," +
                UsuariosContract.subirProductos.COLUMN_PRECIO + " REAL NOT NULL," +
                UsuariosContract.subirProductos.COLUMN_DESCRIPCION + " TEXT NOT NULL," +
                UsuariosContract.subirProductos.COLUMN_IMAGEN + " TEXT NOT NULL" +
                ");";

        db.execSQL(SQL_CREATE_USUARIOS_TABLE);
        db.execSQL(SQL_CREATE_CAMPESINOS_TABLE);
        db.execSQL(SQL_CREATE_SOCIOS_TABLE);
        db.execSQL(RegistrarPuntoVenta_TABLE);
        db.execSQL(SobreMi_TABLE);
        db.execSQL(subirProductos_TABLE);
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
        return validarCredenciales(UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS, correo, contraseña);
    }

    public boolean validarCredencialesComprador(String correo, String contraseña) {
        return validarCredenciales(UsuariosContract.UsuarioEntry.TABLE_NAME_USUARIOS, correo, contraseña);
    }

    public boolean validarCredencialesSocio(String correo, String contraseña) {
        return validarCredenciales(UsuariosContract.SocioEntry.TABLE_NAME_SOCIOS, correo, contraseña);
    }

    private boolean validarCredenciales(String tableName, String correo, String contraseña) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                tableName,
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

    public boolean existeCorreoGra(String correo) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(
                UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS,
                null,
                UsuariosContract.CampesinoEntry.COLUMN_CORREO + " = ?",
                new String[]{correo},
                null,
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
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ESTADO,
                    UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO
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
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO));

                        PuntoVenta puntoVenta = new PuntoVenta(puntoVentaId, direccion, horaEntrada, horaSalida, dia, estado, "null", 0, 0, id);
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
                    UsuariosContract.RegistrarPuntoVentaEntry.TABLE_NAME_DIRECCIONES + "." + UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO,
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
                        int id = cursor.getInt(cursor.getColumnIndexOrThrow(UsuariosContract.RegistrarPuntoVentaEntry.COLUMN_ID_USUARIO));

                        PuntoVenta puntoVenta = new PuntoVenta(puntoVentaId, direccion, horaEntrada, horaSalida, dia, estado, nombreGranja, latitud, longitud, id);
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


    public Campesino obtenerDatosCampesino(int idUsuario) {
        SQLiteDatabase db = null;
        Campesino campesino = null;

        try {
            db = this.getReadableDatabase();

            // Consulta principal para obtener los datos del campesino
            String queryCampesino = "SELECT " +
                    UsuariosContract.CampesinoEntry.COLUMN_NOMBRE + ", " +
                    UsuariosContract.CampesinoEntry.COLUMN_APELLIDO + ", " +
                    UsuariosContract.CampesinoEntry.COLUMN_CORREO + ", " +
                    UsuariosContract.CampesinoEntry.COLUMN_NOMBRE_GRANJA +
                    " FROM " + UsuariosContract.CampesinoEntry.TABLE_NAME_CAMPESINOS +
                    " WHERE " + UsuariosContract.CampesinoEntry._ID + " = ?";
            Cursor cursorCampesino = db.rawQuery(queryCampesino, new String[]{String.valueOf(idUsuario)});

            if (cursorCampesino != null && cursorCampesino.moveToFirst()) {
                String nombre = cursorCampesino.getString(cursorCampesino.getColumnIndexOrThrow(UsuariosContract.CampesinoEntry.COLUMN_NOMBRE));
                String apellido = cursorCampesino.getString(cursorCampesino.getColumnIndexOrThrow(UsuariosContract.CampesinoEntry.COLUMN_APELLIDO));
                String correo = cursorCampesino.getString(cursorCampesino.getColumnIndexOrThrow(UsuariosContract.CampesinoEntry.COLUMN_CORREO));
                String nombreGranja = cursorCampesino.getString(cursorCampesino.getColumnIndexOrThrow(UsuariosContract.CampesinoEntry.COLUMN_NOMBRE_GRANJA));

                // Segunda consulta para obtener la descripción "Sobre Mi"
                String querySobreMi = "SELECT " + UsuariosContract.SobreMiEntry.COLUMN_DESCRIPCION +
                        " FROM " + UsuariosContract.SobreMiEntry.TABLE_NAME_SOBREMI +
                        " WHERE " + UsuariosContract.SobreMiEntry.COLUMN_ID_Granejero + " = ?";
                Cursor cursorSobreMi = db.rawQuery(querySobreMi, new String[]{String.valueOf(idUsuario)});

                String descripcion = null;
                if (cursorSobreMi != null && cursorSobreMi.moveToFirst()) {
                    descripcion = cursorSobreMi.getString(cursorSobreMi.getColumnIndexOrThrow(UsuariosContract.SobreMiEntry.COLUMN_DESCRIPCION));
                    cursorSobreMi.close();
                }

                // Crear un objeto Campesino con los datos obtenidos
                campesino = new Campesino(nombre, apellido, correo, nombreGranja, descripcion);
            }
            if (cursorCampesino != null) {
                cursorCampesino.close();
            }
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al obtener datos del campesino: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return campesino;
    }



    public void guardarInformacionSobreMi(int idUsuario, String descripcion) {
        SQLiteDatabase db = null;

        try {
            db = this.getWritableDatabase();

            ContentValues values = new ContentValues();
            values.put(UsuariosContract.SobreMiEntry.COLUMN_DESCRIPCION, descripcion);

            int rowsAffected = db.update(
                    UsuariosContract.SobreMiEntry.TABLE_NAME_SOBREMI,
                    values,
                    UsuariosContract.SobreMiEntry.COLUMN_ID_Granejero + " = ?",
                    new String[]{String.valueOf(idUsuario)}
            );

            // Si no hay filas afectadas (no se encontró una entrada existente), inserta una nueva entrada
            if (rowsAffected == 0) {
                values.put(UsuariosContract.SobreMiEntry.COLUMN_ID_Granejero, idUsuario);
                db.insert(UsuariosContract.SobreMiEntry.TABLE_NAME_SOBREMI, null, values);
            }
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al guardar o actualizar información sobre mí: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
    }


    public String ObtenerInformacionSobreMi(int idUsuario) {
        SQLiteDatabase db = null;
        String descripcion = null;

        try {
            db = this.getReadableDatabase();
            String query = "SELECT " + UsuariosContract.SobreMiEntry.COLUMN_DESCRIPCION +
                    " FROM " + UsuariosContract.SobreMiEntry.TABLE_NAME_SOBREMI +
                    " WHERE " + UsuariosContract.SobreMiEntry.COLUMN_ID_Granejero + " = ?";
            Cursor cursor = db.rawQuery(query, new String[]{String.valueOf(idUsuario)});

            if (cursor != null && cursor.moveToFirst()) {
                descripcion = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.SobreMiEntry.COLUMN_DESCRIPCION));
            }
            cursor.close();
        } catch (SQLiteException e) {
            Log.e("ERROR", "Error al obtener información sobre mí: " + e.getMessage());
        } finally {
            if (db != null) {
                db.close();
            }
        }
        return descripcion;
    }

    public void agregarProducto(int idUsuario, String nombreProducto, double precio, int cantidad, String descripcion, String imagen) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(UsuariosContract.subirProductos.COLUMN_ID_USUARIO, idUsuario);
        values.put(UsuariosContract.subirProductos.COLUMN_NOMBRE_PRODUCTO, nombreProducto);
        values.put(UsuariosContract.subirProductos.COLUMN_CANTIDAD, cantidad);
        values.put(UsuariosContract.subirProductos.COLUMN_PRECIO, precio); // Aquí el precio es double
        values.put(UsuariosContract.subirProductos.COLUMN_DESCRIPCION, descripcion);
        values.put(UsuariosContract.subirProductos.COLUMN_IMAGEN, imagen);
        db.insert(UsuariosContract.subirProductos.TABLE_NAME_PRODUCTOS, null, values);
        db.close();
    }

    public List<String> obtenerImagenesProductos(int idUsuario) {
        List<String> rutasImagenes = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String[] projection = {UsuariosContract.subirProductos.COLUMN_IMAGEN};
            String selection = UsuariosContract.subirProductos.COLUMN_ID_USUARIO + " = ?";
            String[] selectionArgs = {String.valueOf(idUsuario)};

            Cursor cursor = db.query(
                    UsuariosContract.subirProductos.TABLE_NAME_PRODUCTOS,
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
                        String rutaImagen = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.subirProductos.COLUMN_IMAGEN));
                        rutasImagenes.add(rutaImagen);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Error al obtener las imágenes de los productos: " + e.getMessage());
            // Manejar el error según sea necesario
        } finally {
            db.close();
        }

        return rutasImagenes;
    }

    public List<String> ObtenerDescripciones(int idUsuario) {
        List<String> descripciones = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        try {
            String[] projection = {UsuariosContract.subirProductos.COLUMN_DESCRIPCION};
            String selection = UsuariosContract.subirProductos.COLUMN_ID_USUARIO + " = ?";
            String[] selectionArgs = {String.valueOf(idUsuario)};

            Cursor cursor = db.query(
                    UsuariosContract.subirProductos.TABLE_NAME_PRODUCTOS,
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
                        String descripcion = cursor.getString(cursor.getColumnIndexOrThrow(UsuariosContract.subirProductos.COLUMN_DESCRIPCION));
                        descripciones.add(descripcion);
                    } while (cursor.moveToNext());
                }
                cursor.close();
            }
        } catch (Exception e) {
            Log.e("ERROR", "Error al obtener las descripciones de los productos: " + e.getMessage());
            // Manejar el error según sea necesario
        } finally {
            db.close();
        }

        return descripciones;
    }







}









