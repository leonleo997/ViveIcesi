package co.highusoft.viveicesi.view;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import co.highusoft.viveicesi.R;
import co.highusoft.viveicesi.model.Usuario;
import co.highusoft.viveicesi.view.fragments.FragActividad;

public class borrador extends AppCompatActivity {

    private ImageView borrar;
    private FirebaseDatabase db;
    FirebaseAuth auth;
    FirebaseStorage storage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_borrador);

        borrar=findViewById(R.id.imv_borrar);

        auth=FirebaseAuth.getInstance();
        db=FirebaseDatabase.getInstance();
        storage= FirebaseStorage.getInstance();

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
                        Log.e(">>>", uri.toString());
                        Log.e(">>>", "ENTRAAAAAAAAA");
                        Glide.with(getApplicationContext()).load(uri)
                                .into(borrar);
                        Log.e(">>>", "Terminaaaaaaa");

                    }
                });
                Log.e(">>>", user.getArea());
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
