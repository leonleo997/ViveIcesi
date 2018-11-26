package co.highusoft.viveicesi.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
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
import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.view.fragments.AgregarEvento;
import co.highusoft.viveicesi.view.fragments.FragActividad;
import co.highusoft.viveicesi.view.fragments.FragCalendario;
import co.highusoft.viveicesi.view.fragments.FragCrearActividad;
import co.highusoft.viveicesi.view.fragments.FragCultura;
import co.highusoft.viveicesi.view.fragments.FragDeportes;
import co.highusoft.viveicesi.view.fragments.FragItems;
import co.highusoft.viveicesi.view.fragments.FragMostrarEvento;

import co.highusoft.viveicesi.view.fragments.FragCambiarContrasenia;
import co.highusoft.viveicesi.view.fragments.FragPSU;
import co.highusoft.viveicesi.view.fragments.FragPerfil;
import co.highusoft.viveicesi.view.fragments.FragSalud;
import co.highusoft.viveicesi.view.fragments.FragmentoInfo;

public class MenuBienestar extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragPerfil.OnFragmentInteractionListener, FragDeportes.OnFragmentInteractionListener,
        FragmentoInfo.OnFragmentInteractionListener, FragCalendario.OnFragmentInteractionListener,
        AgregarEvento.OnFragmentInteractionListener, FragMostrarEvento.OnFragmentInteractionListener,
        FragCambiarContrasenia.OnFragmentInteractionListener, FragSalud.OnFragmentInteractionListener,
        FragPSU.OnFragmentInteractionListener, FragCultura.OnFragmentInteractionListener,
        FragActividad.OnFragmentInteractionListener, CalificacionActividades.OnFragmentInteractionListener,
        FragCrearActividad.OnFragmentInteractionListener {

    private FragDeportes fragDeportes;
    private FragSalud fragSalud;
    private FragCultura fragCultura;
    private FragPSU fragPSU;


    private FragCalendario fragCalendario;
    private FragmentoInfo fragmentoInfo;
    private FragPerfil fragPerfil;
    private FragCambiarContrasenia fragCambiarContrasenia;
    private AgregarEvento fragAgregarEvento;
    private FragActividad fragActividad;
    private FragCrearActividad fragCrearActividad;
    private CalificacionActividades calificacionActividades;

    private FragMostrarEvento fragMostrarEventos;

    private FloatingActionButton fb_home;
    private FloatingActionButton fb_agregar_actividad;

    FirebaseAuth auth;
    private FirebaseAuth.AuthStateListener authStateListener;

    String FRAGMENT_ITEMS = "items";
    String FRAGMENT_INFORMACION = "informacion";
    String FRAGMENT_CALENDARIO = "calendario";
    String FRAGMENT_PERFIL = "perfil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        auth = FirebaseAuth.getInstance();
        //
        fragCalendario = new FragCalendario();
        fragmentoInfo = new FragmentoInfo();
        fragPerfil = new FragPerfil();
        fragAgregarEvento = new AgregarEvento();
        fragActividad = new FragActividad();
        fragMostrarEventos = new FragMostrarEvento();
        fragCambiarContrasenia = new FragCambiarContrasenia();
        fragActividad = new FragActividad();
        calificacionActividades = new CalificacionActividades();
        fragCrearActividad = new FragCrearActividad();

        fragPSU=new FragPSU();
        fragCultura=new FragCultura();
        fragSalud=new FragSalud();
        fragDeportes = new FragDeportes();

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        fb_agregar_actividad = findViewById(R.id.fab_anhadir);
        fb_agregar_actividad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragments, fragAgregarEvento).commit();
                fb_agregar_actividad.setVisibility(View.GONE);
                fb_home.setVisibility(View.VISIBLE);
            }
        });


        fb_home = (FloatingActionButton) findViewById(R.id.fab);
        fb_home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.contenedorFragments, fragMostrarEventos).commit();
                fb_home.setVisibility(View.GONE);
                fb_agregar_actividad.setVisibility(View.VISIBLE);


//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragments, fragMostrarEventos).commit();
        fb_home.setVisibility(View.GONE);

        fb_agregar_actividad.setVisibility(View.VISIBLE);


    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
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

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        fb_home.setVisibility(View.VISIBLE);
        fb_agregar_actividad.setVisibility(View.INVISIBLE);


        // Handle navigation view item clicks here.
        int id = item.getItemId();


        if (id == R.id.nav_deporte) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragDeportes).commit();
        } else if (id == R.id.nav_cultura) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragCultura).commit();
        } else if (id == R.id.nav_calendario) {

            fragmentTransaction.replace(R.id.contenedorFragments, fragCalendario).commit();
        } else if (id == R.id.nav_sesion) {
            auth.signOut();
            Intent i = new Intent(MenuBienestar.this, Login.class);
            startActivity(i);
            finish();
        } else if (id == R.id.nav_salud) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragSalud).commit();
        } else if (id == R.id.nav_psu) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragPSU).commit();
        } else if (id == R.id.nav_perfil) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragPerfil).commit();
        } else if (id == R.id.nav_informacion) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragmentoInfo).commit();
        } else if (id == R.id.nav_contrasenha) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragCambiarContrasenia).commit();
        } else if (id == R.id.add_event) {
//            fragmentTransaction.replace(R.id.contenedorFragments, fragAgregarEvento).commit();
            fragmentTransaction.replace(R.id.contenedorFragments, fragCrearActividad).commit();
//            Intent i = new Intent(MenuBienestar.this,borrador.class);
//            startActivity(i);
        } else if (id == R.id.actividad_222) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragActividad).commit();
        } else if (id == R.id.cuestionario) {
            fragmentTransaction.replace(R.id.contenedorFragments, calificacionActividades).commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
