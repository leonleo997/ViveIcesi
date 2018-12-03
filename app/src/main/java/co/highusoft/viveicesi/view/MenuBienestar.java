package co.highusoft.viveicesi.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import co.highusoft.viveicesi.FragRegistro;
import co.highusoft.viveicesi.model.Constantes;
import co.highusoft.viveicesi.view.fragments.CalificacionActividades;
import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Usuario;
import co.highusoft.viveicesi.view.fragments.AgregarEvento;
import co.highusoft.viveicesi.view.fragments.FragActividad;
import co.highusoft.viveicesi.view.fragments.FragCalendario;
import co.highusoft.viveicesi.view.fragments.FragCrearActividad;
import co.highusoft.viveicesi.view.fragments.FragCultura;
import co.highusoft.viveicesi.view.fragments.FragDeportes;
import co.highusoft.viveicesi.view.fragments.FragEditarPerfil;
import co.highusoft.viveicesi.view.fragments.FragEscaner;
import co.highusoft.viveicesi.view.fragments.FragEvento;
import co.highusoft.viveicesi.view.fragments.FragMostrarEvento;

import co.highusoft.viveicesi.view.fragments.FragCambiarContrasenia;
import co.highusoft.viveicesi.view.fragments.FragPSU;
import co.highusoft.viveicesi.view.fragments.FragPerfil;
import co.highusoft.viveicesi.view.fragments.FragReportesActividad;
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
        FragCrearActividad.OnFragmentInteractionListener, FragEditarPerfil.OnFragmentInteractionListener,
        FragEscaner.OnFragmentInteractionListener, FragEvento.OnFragmentInteractionListener,
        FragRegistro.OnFragmentInteractionListener, FragReportesActividad.OnFragmentInteractionListener {

    private FragDeportes fragDeportes;
    private FragSalud fragSalud;
    private FragCultura fragCultura;
    private FragPSU fragPSU;

    private FragEscaner fragEscaner;
    private FragReportesActividad fragReportesActividad;
    private FragRegistro fragRegistro;
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
    private ImageView img_usuario;

    private Menu menu;

    FirebaseAuth auth;
    FirebaseDatabase db;
    FirebaseStorage storage;

    String FRAGMENT_ITEMS = "items";
    String FRAGMENT_INFORMACION = "informacion";
    String FRAGMENT_CALENDARIO = "calendario";
    String FRAGMENT_PERFIL = "perfil";

    private FirebaseAuth.AuthStateListener authStateListener;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_bienestar);

        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        storage = FirebaseStorage.getInstance();

        boolean isAuth = validarAutenticacion();
        if (!isAuth)
            return;
        guardarRol();

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
        fragEscaner = new FragEscaner();
        fragRegistro = new FragRegistro();
        fragReportesActividad = new FragReportesActividad();

        fragPSU = new FragPSU();
        fragCultura = new FragCultura();
        fragSalud = new FragSalud();
        fragDeportes = new FragDeportes();

        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerLayout =
                navigationView.inflateHeaderView(R.layout.nav_header_menu_bienestar);
        img_usuario = headerLayout.findViewById(R.id.img_usuario);


        DatabaseReference myRef = db.getReference("Usuarios");
        Log.e(">>>", auth.getCurrentUser().getEmail());
        myRef.orderByChild("correo").equalTo(auth.getCurrentUser().getEmail()).addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Usuario user = dataSnapshot.getValue(Usuario.class);
                StorageReference storageReference = storage.getReference().child("fotos").child(user.getFoto());
                storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        Glide.with(getApplicationContext()).load(uri)
                                .apply(RequestOptions.circleCropTransform())
                                .into(img_usuario);
                    }
                });
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //


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
                if (Constantes.isAdmin == false) {
                    fb_agregar_actividad.setVisibility(View.GONE);
                } else {
                    fb_agregar_actividad.setVisibility(View.VISIBLE);
                }
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.contenedorFragments, fragMostrarEventos).commit();
        fb_home.setVisibility(View.GONE);

    }

    private void guardarRol() {
        db.getReference().child("Usuarios").orderByChild("correo").equalTo(auth.getCurrentUser().getEmail())
                .addChildEventListener(new ChildEventListener() {
                    @Override
                    public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                        Usuario user = dataSnapshot.getValue(Usuario.class);
                        Constantes.isAdmin = user.getAdmin();
                        if (Constantes.isAdmin == false) {
                            fb_agregar_actividad.setVisibility(View.GONE);
                            NavigationView navigationView = findViewById(R.id.nav_view);
                            navigationView.getMenu().getItem(6).getSubMenu()
                                    .getItem(0)
                                    .setVisible(false);
                            navigationView.getMenu().getItem(6).getSubMenu()
                                    .getItem(1)
                                    .setVisible(false);
                            navigationView.getMenu().getItem(6).getSubMenu()
                                    .getItem(2)
                                    .setVisible(false);

                        } else {
                            fb_agregar_actividad.setVisibility(View.VISIBLE);
                        }

                    }

                    @Override
                    public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    }

                    @Override
                    public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
    }

    private boolean validarAutenticacion() {

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this, "Failed to connect", Toast.LENGTH_SHORT).show();
            finish();
            Intent i = new Intent(MenuBienestar.this, Login.class);
            startActivity(i);
            return false;
        }
        Log.e("vaa", "validarAutenticacion: " + auth.getCurrentUser().getEmail());
        return true;
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
        this.menu = menu;
        return true;
    }

    @Override
    protected void onStart() {
        super.onStart();
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
        } else if (id == R.id.add_activity) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragCrearActividad).commit();
        } else if (id == R.id.escaner) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragEscaner).commit();
        } else if (id == R.id.add_admin) {
            fragmentTransaction.replace(R.id.contenedorFragments, fragRegistro).commit();
        } else if (id==R.id.ver_reportes){
            fragmentTransaction.replace(R.id.contenedorFragments, fragReportesActividad).commit();
        }


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
