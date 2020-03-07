package com.martix.empleado;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.Toast;

import com.martix.logicanegocio.Empleado.Empleado;

import java.util.Calendar;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

    EditText Codigo, Nombre, Departamento, Cargo, Salario;
    Button btnAdd, btnSearch, btnModify, btnDelete;

    private Date myFecha;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Codigo = findViewById(R.id.Codigo);
        Nombre = findViewById(R.id.Nombre);
        Departamento = findViewById(R.id.Departamento);
        Cargo = findViewById(R.id.Cargo);
        Salario = findViewById(R.id.Salario);
        btnAdd = findViewById(R.id.btnAdd);
        btnSearch = findViewById(R.id.btnSearch);
        btnModify = findViewById(R.id.btnModify);
        btnDelete = findViewById(R.id.btnDelete);

        final CalendarView calendarView = findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int year, int month, int dayOfMonth) {
                Calendar c = Calendar.getInstance();
                c.set(Calendar.YEAR, year);
                c.set(Calendar.MONTH, month);
                c.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                myFecha = c.getTime();
            }
        });

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado empleado = new Empleado();
                empleado.setCodigo(Codigo.getText().toString());
                empleado.setNombre(Nombre.getText().toString());
                empleado.setDepartamento(Departamento.getText().toString());
                empleado.setCargo(Cargo.getText().toString());
                empleado.setSalario(Float.parseFloat(Salario.getText().toString()));
                empleado.setFechaIngreso(myFecha);

                if(empleado.Add(MainActivity.this))
                {
                    LimpiarCampos();
                    calendarView.setDate(new Date().getTime());
                    myFecha = new Date();
                    Toast.makeText(MainActivity.this, "Se guardo Correctamente", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No fue posible guardar el dato", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado empleado = Empleado.Search(MainActivity.this, Codigo.getText().toString());
                if(empleado!=null)
                {
                    Codigo.setText(empleado.getCodigo());
                    Nombre.setText(empleado.getNombre());
                    Departamento.setText(empleado.getDepartamento());
                    Cargo.setText(empleado.getCargo());
                    Salario.setText(String.valueOf(empleado.getSalario()));
                    calendarView.setDate(empleado.getFechaIngreso().getTime());
                    myFecha = empleado.getFechaIngreso();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No fue posible recuperar el dato", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnModify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Empleado empleado = new Empleado();
                empleado.setNombre(Nombre.getText().toString());
                empleado.setDepartamento(Departamento.getText().toString());
                empleado.setCargo(Cargo.getText().toString());
                empleado.setSalario(Float.parseFloat(Salario.getText().toString()));
                empleado.setFechaIngreso(myFecha);

                if(empleado.Modify(MainActivity.this, Codigo.getText().toString()))
                {
                    LimpiarCampos();
                    calendarView.setDate(new Date().getTime());
                    myFecha = new Date();
                    Toast.makeText(MainActivity.this, "Se modifico el dato", Toast.LENGTH_LONG).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No fue posible la modificación", Toast.LENGTH_LONG).show();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Empleado.Delete(MainActivity.this, Codigo.getText().toString()))
                {
                    Toast.makeText(MainActivity.this, "Se elimino el dato", Toast.LENGTH_LONG).show();
                    LimpiarCampos();
                    myFecha = new Date();
                    calendarView.setDate(new Date().getTime());
                }
                else
                {
                    Toast.makeText(MainActivity.this, "No fue posible eliminar el dato", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    void LimpiarCampos()
    {
        Codigo.setText("");
        Nombre.setText("");
        Departamento.setText("");
        Cargo.setText("");
        Salario.setText("");
    }
}
