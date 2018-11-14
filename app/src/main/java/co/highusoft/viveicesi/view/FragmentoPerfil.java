package co.highusoft.viveicesi.view;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Usuario;


public class FragmentoPerfil extends FragmentActivity {

    private Button btn_contrasenha;
    private  Button btn_registrarAdmin;
    private  Button btn_datos;
    private Usuario user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_fragmento_perfil);

        btn_contrasenha=findViewById(R.id.btn_contrasenha);
        btn_datos=findViewById(R.id.btn_datos);
        btn_registrarAdmin=findViewById(R.id.btn_registrarAdmin);

        if(user.gettipoUsuario().equals("Administrador")){
            btn_registrarAdmin.setVisibility(View.VISIBLE);
        }

        btn_contrasenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FragmentoPerfil.this, PasswordLogin.class);
                startActivity(i);
                finish();

            }
        });

        btn_registrarAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(FragmentoPerfil.this, Registro.class);
                startActivity(i);
                finish();

            }
        });


        btn_datos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }


}
