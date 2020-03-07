package com.martix.logicanegocio.Empleado;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.io.Serializable;
import java.util.Date;

public class Empleado implements Serializable {
    private String Codigo;
    private String Nombre;
    private String Departamento;
    private String Cargo;
    private float Salario;
    private Date FechaIngreso;

    public Empleado(){
        super();
    }

    public Empleado(String codigo, String nombre, String departamento, String cargo, float salario, Date fechaIngreso) {
        Codigo = codigo;
        Nombre = nombre;
        Departamento = departamento;
        Cargo = cargo;
        Salario = salario;
        FechaIngreso = fechaIngreso;
    }

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }

    public String getDepartamento() {
        return Departamento;
    }

    public void setDepartamento(String departamento) {
        Departamento = departamento;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String cargo) {
        Cargo = cargo;
    }

    public float getSalario() {
        return Salario;
    }

    public void setSalario(float salario) {
        Salario = salario;
    }

    public Date getFechaIngreso() {
        return FechaIngreso;
    }

    public void setFechaIngreso(Date fechaIngreso) {
        FechaIngreso = fechaIngreso;
    }

    public boolean Add(Context context)
    {
        boolean flag = false;
        SQLiteHelper myHelper = null;
        SQLiteDatabase myDatabase =null;

        try{
            myHelper = new SQLiteHelper(context.getFilesDir().getCanonicalPath(), context);
            myDatabase = myHelper.getWritableDatabase();

            ContentValues values =  new ContentValues();
            values.put("Codigo", Codigo);
            values.put("Nombre", Nombre);
            values.put("Departamento", Departamento);
            values.put("Cargo", Cargo);
            values.put("Salario", Salario);
            values.put("FechaIngreso", FechaIngreso.getTime());

            flag = myDatabase.insert("Empleado", null, values)!=-1;

        }catch (Exception e)
        {
            e.printStackTrace();
        }finally {
            if(myHelper!=null)
                myHelper.close();
            if(myDatabase!=null)
                myDatabase.close();
        }


        return  flag;
    }

    public static Empleado Search(Context context, String key)
    {
        Empleado empleado = null;
        SQLiteHelper myHelper = null;
        SQLiteDatabase myDatabase = null;

        try {
            myHelper = new SQLiteHelper(context.getFilesDir().getCanonicalPath(), context);
            myDatabase = myHelper.getReadableDatabase();

            Cursor cursor = myDatabase.query(true, "Empleado", null, "Codigo=?", new String[]{key}, null, null, null, null);

            if(cursor.getColumnCount()>0)
            {
                cursor.moveToFirst();
                while (!cursor.isAfterLast())
                {
                    empleado = new Empleado();
                    empleado.setCodigo(cursor.getString(cursor.getColumnIndex("Codigo")));
                    empleado.setCargo(cursor.getString(cursor.getColumnIndex("Codigo")));
                    empleado.setNombre(cursor.getString(cursor.getColumnIndex("Nombre")));
                    empleado.setDepartamento(cursor.getString(cursor.getColumnIndex("Departamento")));
                    empleado.setSalario(cursor.getFloat(cursor.getColumnIndex("Salario")));
                    empleado.setFechaIngreso(new Date(cursor.getLong(cursor.getColumnIndex("FechaIngreso"))));
                    cursor.moveToNext();
                }
            }

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(myHelper!=null)
                myHelper.close();
            if(myDatabase!=null)
                myDatabase.close();
        }


        return empleado;
    }

    public boolean Modify(Context context, String key){
        boolean flag = false;
        SQLiteHelper myHelper = null;
        SQLiteDatabase myDatabase = null;
        try{
            myHelper = new SQLiteHelper(context.getFilesDir().getCanonicalPath(), context);
            myDatabase = myHelper.getWritableDatabase();
            ContentValues values =  new ContentValues();
            values.put("Nombre", Nombre);
            values.put("Departamento", Departamento);
            values.put("Cargo", Cargo);
            values.put("Salario", Salario);
            values.put("FechaIngreso", FechaIngreso.getTime());

            flag = myDatabase.update("Empleado", values, "Codigo=?", new String[]{key})!=-1;

        }catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(myHelper!=null)
                myHelper.close();
            if(myDatabase!=null)
                myDatabase.close();
        }

        return  flag;
    }

    public static boolean Delete(Context context, String key){
        boolean flag = false;
        SQLiteHelper myHelper = null;
        SQLiteDatabase myDatabase = null;

        try{
            myHelper = new SQLiteHelper(context.getFilesDir().getCanonicalPath(), context);
            myDatabase = myHelper.getWritableDatabase();

            flag = myDatabase.delete("Empleado", "Codigo=?", new String[]{key})!=-1;

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if(myHelper!=null)
                myHelper.close();
            if(myDatabase!=null)
                myDatabase.close();
        }

        return  flag;
    }
}
