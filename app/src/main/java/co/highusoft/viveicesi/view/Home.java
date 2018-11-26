package co.highusoft.viveicesi.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import co.highusoft.viveicesi.R;
//import co.highusoft.viveicesi.adapters.Adaptador;
import co.highusoft.viveicesi.model.Comentario;


public class Home extends AppCompatActivity {

    FirebaseAuth auth;
    FirebaseDatabase db;

    //private Adaptador adaptador;

    private EditText et_comentario;
    private Button btn_comentar;
    private ListView view_comentarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db=FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();

        //borrar
        Button borrar = findViewById(R.id.btn_logout);
        borrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                Intent i = new Intent(Home.this, Login.class);
                startActivity(i);
                finish();
            }
        });

        //borrar

        if (auth.getCurrentUser() == null) {
            Toast.makeText(this.getApplicationContext(), "Failed to connect", Toast.LENGTH_SHORT).show();
            Intent i = new Intent(Home.this, Login.class);
            startActivity(i);
            finish();
            return;
        }
        et_comentario=findViewById(R.id.et_comentario);

        btn_comentar=findViewById(R.id.btn_comentar);

        view_comentarios=findViewById(R.id.view_comentarios);
//        adaptador=new Adaptador(this);
//        view_comentarios.setAdapter(adaptador);

        btn_comentar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String comentario=et_comentario.getText().toString();
                if(comentario.isEmpty())
                    return;
                DatabaseReference reference=db.getReference().child("Comentarios").push();
                String id_comment= reference.getKey();

                Comentario cm = new Comentario();
                cm.setId(id_comment);
                cm.setContenido(comentario);

                reference.setValue(cm);

            }
        });

        DatabaseReference comentarios_ref = db.getReference().child("Comentarios");
        comentarios_ref.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                Comentario comentario=dataSnapshot.getValue(Comentario.class);
//                adaptador.addComment(comentario);
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
}
