    package com.example.granjapp;

    import android.provider.BaseColumns;

    public final class UsuariosContract {
        private UsuariosContract() {}

        public static class UsuarioEntry implements BaseColumns {
            public static final String TABLE_NAME_USUARIOS = "usuarios";
            public static final String COLUMN_NOMBRE = "nombre";
            public static final String COLUMN_APELLIDO = "apellido";
            public static final String COLUMN_CORREO = "correo";
            public static final String COLUMN_CONTRASENA = "contrasena";
        }

        public static class CampesinoEntry implements BaseColumns {
            public static final String TABLE_NAME_CAMPESINOS = "campesinos";
            public static final String COLUMN_NOMBRE = "nombre";
            public static final String COLUMN_APELLIDO = "apellido";
            public static final String COLUMN_CORREO = "correo";
            public static final String COLUMN_CONTRASENA = "contrasena";
            public static final String COLUMN_NOMBRE_GRANJA = "nombre_granja";


        }



        public static class SocioEntry implements BaseColumns {
            public static final String TABLE_NAME_SOCIOS = "socios";
            public static final String COLUMN_NOMBRE = "nombre";
            public static final String COLUMN_APELLIDO = "apellido";
            public static final String COLUMN_CORREO = "correo";
            public static final String COLUMN_CONTRASENA = "contrasena";
            public static final String COLUMN_NOMBRE_ORGANIZACION = "nombre_organizacion";
        }

        public static class RegistrarPuntoVentaEntry implements BaseColumns {
            public static final String TABLE_NAME_DIRECCIONES = "direcciones";
            public static final String COLUMN_DIRECCION = "direccion";
            public static final String COLUMN_ID_USUARIO = "id_usuario";
            public static final String COLUMN_HORA_ENTRADA = "hora_entrada";
            public static final String COLUMN_HORA_SALIDA = "hora_salida";
            public static final String COLUMN_DIA = "dia";
            public static final String COLUMN_ESTADO =  "Estado";
            public static final String COLUMN_ID = "_id";;

            public static final String COLUM_LAITUD = "latitud";
            public static final String COLUM_LONGITUD = "longitud";
        }

        public static class SobreMiEntry implements BaseColumns {
            public static final String TABLE_NAME_SOBREMI = "sobremi";
            public static final String COLUMN_ID_Granejero = "id_usuario";
            public static final String COLUMN_DESCRIPCION = "descripcion";
        }
    }
