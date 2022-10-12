package com.example.besmart;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentTransaction;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.besmart.Classes.FirebaseAuth;
import com.example.besmart.Fragments.Notes_Fragment;
import com.example.besmart.Fragments.Profile_Fragment;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class DashboardActivity extends AppCompatActivity {
BottomNavigationView nav;
FragmentContainerView container;
FirebaseAuth Auth;
FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        Auth=new FirebaseAuth(this);
        nav = findViewById(R.id.bottom_navigation);
        container = findViewById(R.id.container);
        ft=getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.container,new Notes_Fragment()).commit();

        nav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.page_4:
                        AlertDialog.Builder b = new AlertDialog.Builder(DashboardActivity.this);
                        b.setMessage("Confirm LogOut");
                        b.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Auth.EndSession();
                                Intent ii=new Intent(DashboardActivity.this,SigninActivity.class);
                                DashboardActivity.this.startActivity(ii);
                                DashboardActivity.this.finish();
                                Toast.makeText(DashboardActivity.this,"Logout Successfully", Toast.LENGTH_SHORT).show();
                            }
                        });
                        b.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                nav.setSelectedItemId(R.id.page_1);
                            }
                        });
                            AlertDialog alrt = b.create();
                            alrt.show();
                        break;
//                    case R.id.page_3:
//
//                        ft= getSupportFragmentManager().beginTransaction();
//                        ft.replace(R.id.container,new Profile_Fragment()).commit();
//                        break;
                    case R.id.page_1:
//                    case R.id.page_2:
                        ft=getSupportFragmentManager().beginTransaction();
                        ft.replace(R.id.container,new Notes_Fragment()).commit();
                        break;
                }
                return true;
            }
        });
    }
}