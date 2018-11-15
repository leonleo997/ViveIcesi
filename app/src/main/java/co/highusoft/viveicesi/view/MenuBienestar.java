package co.highusoft.viveicesi.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import co.highusoft.viveicesi.CalificacionActividades;
import co.highusoft.viveicesi.FragActividad;
import co.highusoft.viveicesi.FragCalendario;
import co.highusoft.viveicesi.FragItems;
import co.highusoft.viveicesi.FragMostrarEvento;
import co.highusoft.viveicesi.FragPerfil;
import co.highusoft.viveicesi.R;

public class MenuBienestar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragPerfil.OnFragmentInteractionListener, FragItems.OnFragmentInteractionListener, FragmentoInfo.OnFragmentInteractionListener,
        FragCalendario.OnFragmentInteractionListener, AgregarEvento.OnFragmentInteractionListener, FragMostrarEvento.OnFragmentInteractionListener, FragCambiarContrasenia.OnFragmentInteractionListener,FragActividad.OnFragmentInteractionListener, CalificacionActividades.OnFragmentInteractionListener{


    private FragCalendario fragCalendario;
    private FragmentoInfo fragmentoInfo;
    private FragPerfil fragPerfil;
    private FragItems fragItems;
    private FragCambiarContrasenia fragCambiarContrasenia;
    private AgregarEvento fragAgregarEvento;
    private FragActividad fragActividad ;
    private CalificacionActividades calificacionActividades;

    private FragMostrarEvento fragMostrarEventos ;


    FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    String FRAGMENT_ITEMS = "items";
    String FRAGMENT_INFORMACION = "informacion";
    String FRAGMENT_CALENDARIO = "calendario";
    String FRAGMENT_PERFIL = "perfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth=FirebaseAuth.getInstance();
        //
        fragCalendario = new FragCalendario();
        fragmentoInfo = new FragmentoInfo();
        fragPerfil = new FragPerfil();
        fragItems = new FragItems();
        fragAgregarEvento= new AgregarEvento();
        fragActividad = new FragActividad();
        fragMostrarEventos= new FragMostrarEvento();
        fragCambiarContrasenia= new FragCambiarContrasenia();
        fragActividad = new FragActividad();
        calificacionActividades= new CalificacionActividades();

        //

        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if (firebaseAuth.getCurrentUser() == null) {
                    Toast.makeText(getApplicationContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MenuBienestar.this, Login.class);
                    startActivity(i);
                    finish();
                    //startActivity(new Intent(Login.this, singin_activity.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Succed to connect", Toast.LENGTH_SHORT).show();
                }
            }
        };


        setContentView(R.layout.activity_menu_bienestar);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragments, fragMostrarEventos).commit();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_bienestar, menu);
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(authStateListener);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_deporte) {
//
            fragmentTransaction.replace(R.id.contenedorFragments, fragItems).commit();
//finish();

        } else if (id == R.id.nav_cultura) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragItems).commit();
        } else if (id == R.id.nav_calendario) {

            fragmentTransaction.replace(R.id.contenedorFragments, fragCalendario).commit();

            //fragmentTransaction.replace(R.id.contenedorFragments, fragActividad).commit();
        } else if (id == R.id.nav_sesion) {
            auth.signOut();
            Intent i = new Intent(MenuBienestar.this, Login.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_salud) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragItems).commit();

        } else if (id == R.id.nav_psu) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragItems).commit();


        } else if (id == R.id.nav_perfil) {
           // fragmentTransaction.replace(R.id.contenedorFragments,fragAgregarEvento).commit();
            fragmentTransaction.replace(R.id.contenedorFragments, fragPerfil).commit();
        } else if (id == R.id.nav_informacion) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragmentoInfo).commit();
        } else if (id == R.id.nav_contrasenha) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragCambiarContrasenia).commit();
        }else if (id == R.id.add_event) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragAgregarEvento).commit();
        }else if (id == R.id.actividad_222) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragActividad).commit();
        }else if (id == R.id.cuestionario) {
            fragmentTransaction.replace(R.id.contenedorFragments, calificacionActividades).commit();
        }



        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
