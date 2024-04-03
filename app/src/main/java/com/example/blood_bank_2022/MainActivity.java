package com.example.blood_bank_2022;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.blood_bank_2022.Adapter.UserAdapter;
import com.example.blood_bank_2022.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    private DrawerLayout drawerLayout;
    private Toolbar Toolbar;
    private NavigationView nav_view;
    private CircleImageView nav_profile_image;
    private TextView nav_fullname , nav_email , nav_bloodgroup, nav_type ,phoneNumber , message;

    private DatabaseReference userRef;

    private RecyclerView recyclerView;
    private ProgressBar progressbar ;

    private List<User> userList ;
private UserAdapter userAdapter ;
    private Button emailNow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        Toolbar = findViewById(R.id.Toolbar);

        getSupportActionBar().setTitle("Blood Donation App");


        drawerLayout  = findViewById(R.id.drawerLayout);
        nav_view=findViewById(R.id.nav_view);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(MainActivity.this,  drawerLayout,
                Toolbar, R. string.nevigation_drawer_open,R.string.nevigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();


        nav_view.setNavigationItemSelectedListener(this);
        progressbar = findViewById(R.id.progressbar);

        recyclerView =findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager =new LinearLayoutManager(this);
        layoutManager.setReverseLayout(true);
        layoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(layoutManager);

        userList = new ArrayList<>();
        userAdapter =new UserAdapter(MainActivity.this,userList);

        recyclerView.setAdapter(userAdapter);
DatabaseReference ref = FirebaseDatabase.getInstance().getReference()
        .child("users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
ref.addValueEventListener(new ValueEventListener() {
    @Override
    public void onDataChange(@NonNull DataSnapshot snapshot) {
        String type =snapshot.child("type").getValue().toString();
        if (type.equals("donor")){
            readRecipients();
        }else {
            readDonors();
        }

    }

    @Override
    public void onCancelled(@NonNull DatabaseError error) {

    }
});





        nav_profile_image = nav_view.getHeaderView(0).findViewById(R.id.nav_user_image);
        nav_fullname = nav_view.getHeaderView(0).findViewById(R.id.nav_user_fullname);
        nav_email = nav_view.getHeaderView(0).findViewById(R.id.nav_user_email);
        nav_bloodgroup = nav_view.getHeaderView(0).findViewById(R.id.nav_user_bloodgroup);
        nav_type = nav_view.getHeaderView(0).findViewById(R.id.nav_user_type);

        userRef = FirebaseDatabase.getInstance().getReference().child("users").child(
                FirebaseAuth.getInstance().getCurrentUser().getUid()

        );
        userRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    String name = snapshot.child("name").getValue().toString();
                    nav_fullname.setText(name);

                    String email = snapshot.child("email").getValue().toString();
                    nav_email.setText(email);

                    String bloodGroup = snapshot.child("bloodGroup").getValue().toString();
                    nav_bloodgroup.setText(bloodGroup);

                    String type = snapshot.child("type").getValue().toString();
                    nav_type.setText(type);


                    if(snapshot.hasChild("profillepictureur1")) {

                        String imageUrl = snapshot.child("profillepictureur1").getValue().toString();
                        Glide.with(getApplicationContext()).load(imageUrl).into(nav_profile_image);
                    }else{
                        nav_profile_image.setImageResource(R.drawable.profile);
                    }

                    Menu nav_menu = nav_view.getMenu();

                    if (type.equals("donor")){
                        nav_menu.findItem(R.id.sentEmail).setTitle("Received Emails");
                        nav_menu.findItem(R.id.notifications).setVisible(true);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readDonors() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query =reference.orderByChild("type").equalTo("donor");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot  dataSnapshot : snapshot .getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
                userAdapter.notifyDataSetChanged();
                progressbar.setVisibility(View.GONE);

                if (userList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No recipients", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }

    private void readRecipients() {
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference().child("users");
        Query query =reference.orderByChild("type").equalTo("recipient");
        query.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for (DataSnapshot  dataSnapshot : snapshot .getChildren()){
                    User user = dataSnapshot.getValue(User.class);
                    userList.add(user);
                }
                userAdapter.notifyDataSetChanged();
                progressbar.setVisibility(View.GONE);

                if (userList.isEmpty()){
                    Toast.makeText(MainActivity.this, "No recipients", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {


            case R.id.aplus:
                Intent intent3 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent3.putExtra("group", "A+");
                startActivity(intent3);
                break;

            case R.id.aminus:
                Intent intent4 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent4.putExtra("group", "A-");
                startActivity(intent4);
                break;

            case R.id.bplus:
                Intent intent5 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent5.putExtra("group", "B+");
                startActivity(intent5);
                break;

            case R.id.bminus:
                Intent intent6 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent6.putExtra("group", "B-");
                startActivity(intent6);
                break;

            case R.id.abplus:
                Intent intent7 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent7.putExtra("group", "AB+");
                startActivity(intent7);
                break;

            case R.id.abminus:
                Intent intent8 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent8.putExtra("group", "AB-");
                startActivity(intent8);
                break;
            case R.id.oplus:
                Intent intent9 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent9.putExtra("group", "O+");
                startActivity(intent9);
                break;

            case R.id.ominus:
                Intent intent10 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent10.putExtra("group", "O-");
                startActivity(intent10);
                break;

            case R.id.compatible:
                Intent intent11 = new Intent(MainActivity.this, CategorySelectedActivity.class);
                intent11.putExtra("group", "Compatible with  ");
                startActivity(intent11);
                break;

            case R.id.sentEmail:
                Intent intent12 = new Intent(MainActivity.this, SentEmailActivty.class);
                startActivity(intent12);
                break;

            case R.id.notifications:
                Intent intent13 = new Intent(MainActivity.this, NotificationsActivity.class);
                startActivity(intent13);
                break;

            case R.id.profile:
                Intent intent = new Intent(MainActivity.this, ProfileActivity.class);
                startActivity(intent);
                break;

            case R.id.logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent2 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent2);
                break;

            case R.id.Omdurman:
                    Intent intent16 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.app.goo.gl/XABfREakAAbJmpdo9"));
                    startActivity(intent16);

                break;
            case R.id.Sudan:
                Intent intent17 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.app.goo.gl/Dki3jqCRgwTBfQqF9"));
                startActivity(intent17);

                break;

            case R.id.Center:
                Intent intent18 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.app.goo.gl/37PUwDjAzyXU8zxL8"));
                startActivity(intent18);

                break;
            case R.id.kardom:
                Intent intent15 = new Intent(Intent.ACTION_VIEW,Uri.parse("https://maps.app.goo.gl/F6N3p1t3tv7mEC5m8"));
                startActivity(intent15);

                break;
            case R.id.share:
                try {
                    Intent i = new Intent(Intent.ACTION_SENDTO);
                    i.setType("text/plain");
                    i.putExtra(Intent.EXTRA_SUBJECT, "Diploma Guruvar");
                    i.putExtra(Intent.EXTRA_TEXT, "http://blood_bank_2022.google.com/store/apps/details?id=" + getApplicationContext().getPackageName());
                    startActivity(Intent.createChooser(i, "Share With"));


                } catch (Exception e) {
                    Toast.makeText(this, "Unable to share this app.", Toast.LENGTH_SHORT).show();
                }

                break;

        }

        return true;
    }





}


