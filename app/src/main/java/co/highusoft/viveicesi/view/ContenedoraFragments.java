package co.highusoft.viveicesi.view;

import android.net.Uri;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import co.highusoft.viveicesi.FragCalendario;
import co.highusoft.viveicesi.FragItems;
import co.highusoft.viveicesi.FragPerfil;
import co.highusoft.viveicesi.R;

public class ContenedoraFragments extends AppCompatActivity implements FragPerfil.OnFragmentInteractionListener, FragItems.OnFragmentInteractionListener, FragmentoInfo.OnFragmentInteractionListener, FragCalendario.OnFragmentInteractionListener {
    FragCalendario fragCalendario;
    FragmentoInfo fragmentoInfo;
    FragPerfil fragPerfil;
    FragItems fragItems;
    String fragmentPedido;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_contenedora_fragments);
setContentView(R.layout.content_menu_bienestar);
        fragCalendario = new FragCalendario();
        fragmentoInfo = new FragmentoInfo();
        fragPerfil = new FragPerfil();
        fragItems= new FragItems();


       // getSupportFragmentManager().beginTransaction().add(R.id.contenedorFragments, fragCalendario).commit();


        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        Bundle bundle = new Bundle();
        bundle = getIntent().getExtras();
String fragmentSolicitado= bundle.getString("fragment");


        switch (fragmentSolicitado) {
            case "calendario":
                fragmentTransaction.replace(R.id.contenedorFragments, fragCalendario);
                break;
            case "informacion":
                fragmentTransaction.replace(R.id.contenedorFragments, fragmentoInfo);
                break;
            case "items":
                fragmentTransaction.replace(R.id.contenedorFragments, fragItems);
                break;
            case "perfil":
                fragmentTransaction.replace(R.id.contenedorFragments, fragPerfil);
                break;
        }

        fragmentTransaction.commit();


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
