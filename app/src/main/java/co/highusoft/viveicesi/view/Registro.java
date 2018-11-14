package co.highusoft.viveicesi.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Constantes;
import co.highusoft.viveicesi.model.Usuario;

public class Registro extends AppCompatActivity {

    FirebaseDatabase db;
    FirebaseAuth auth;

    private EditText et_usr;
    private EditText et_email;
    private EditText et_name;
    private EditText et_pwd;
    private EditText et_con_pwd;

    private Button btn_registrar;

    private RadioGroup rg_tipoUsuario;
    private CheckBox cb_terminos;

    private Spinner sp_tipo_area;

    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(getApplicationContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    //startActivity(new Intent(Login.this, singin_activity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Succed to connect", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Registro.this, Home.class);
                    startActivity(i);
                    finish();
                }
            }
        };

        DatabaseReference dbr = db.getReference().child("estudiantes");

        sp_tipo_area = findViewById(R.id.sp_tipo_area);


        String[] mensaje = {"Seleccionar tipo usuario"};
        final List<String> plantsList = new ArrayList<>(Arrays.asList(mensaje));

        // Initializing an ArrayAdapter
        final ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                this, R.layout.spinner_item, plantsList);

        spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sp_tipo_area.setAdapter(spinnerArrayAdapter);


        loadComponents();

    }

    private void loadComponents() {
        et_con_pwd = findViewById(R.id.et_con_pwd);
        et_email = findViewById(R.id.et_email);
        et_name = findViewById(R.id.et_name);
        et_pwd = findViewById(R.id.et_pwd);
        et_usr = findViewById(R.id.et_usr);

        rg_tipoUsuario = findViewById(R.id.rg_tipo_usuario);
        rg_tipoUsuario.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (checkedId == R.id.rb_estudiante) {
                    final List<String> plantsList = new ArrayList<>(Arrays.asList(Constantes.ESTUDIANTES));

                    // Initializing an ArrayAdapter
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            Registro.this, R.layout.spinner_item, plantsList);

                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    sp_tipo_area.setAdapter(spinnerArrayAdapter);
                } else {
                    final List<String> plantsList = new ArrayList<>(Arrays.asList(Constantes.TRABAJADORES));

                    // Initializing an ArrayAdapter
                    ArrayAdapter<String> spinnerArrayAdapter = new ArrayAdapter<String>(
                            Registro.this, R.layout.spinner_item, plantsList);

                    spinnerArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                    sp_tipo_area.setAdapter(spinnerArrayAdapter);
                }
            }
        });
        int selected = rg_tipoUsuario.getCheckedRadioButtonId();

        btn_registrar = findViewById(R.id.btn_registrar);
        btn_registrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                boolean aceptado = cb_terminos.isChecked();

                if(rg_tipoUsuario.getCheckedRadioButtonId()==-1){
                    Toast.makeText(Registro.this, "Debe seleccionar ser Estudiante/Trabajador", Toast.LENGTH_SHORT).show();
                    return;
                }

                 if(aceptado) {
                 RadioButton rb=findViewById(rg_tipoUsuario.getCheckedRadioButtonId());
                 Usuario user = new Usuario("", et_usr.getText().toString(), et_email.getText().toString(), et_name.getText().toString(),sp_tipo_area.getSelectedItem().toString(),rb.getText().toString());
                 registrarUsuario(user);
                 }else{
                     Toast.makeText(Registro.this, "Debe aceptar los términos y condiciones", Toast.LENGTH_SHORT).show();
                 }

            }
        });

        cb_terminos = findViewById(R.id.cb_terminos);


    }

    private void registrarUsuario(final Usuario user) {
        if (et_pwd.getText().toString().length() <= 5) {
            Toast.makeText(this, "La contraseña debe ser de mínimo 6 o más caracteres", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!et_pwd.getText().toString().equals(et_con_pwd.getText().toString())) {
            Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show();
            return;
        }

        auth.createUserWithEmailAndPassword(user.getCorreo(), et_pwd.getText().toString()).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Toast.makeText(Registro.this, "Registro exitoso", Toast.LENGTH_SHORT).show();
                    user.setUid(auth.getCurrentUser().getUid());
                    Log.i("USUARIO", "onComplete: " + user.getNombre());
                    DatabaseReference dbr = db.getReference().child("Usuarios").child(user.getUid());
                    dbr.setValue(user);

                    Intent i = new Intent(Registro.this, Home.class);
                    startActivity(i);
                    finish();
                    //Aquí va para el perfil


                } else {
                    Toast.makeText(Registro.this, "Registro fallido: " + task.getException(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
