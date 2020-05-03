package com.example.intern_food;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.os.Bundle;

import com.shrikanthravi.customnavigationdrawer2.data.MenuItem;
import com.shrikanthravi.customnavigationdrawer2.widget.SNavigationDrawer;

import java.util.ArrayList;
import java.util.List;

public class My_test extends AppCompatActivity {
    SNavigationDrawer sNavigationDrawer;
    Class fragmentClass;
    public static Fragment fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_test);

        sNavigationDrawer = findViewById(R.id.navigationDrawer);
        List<MenuItem> menuItems = new ArrayList<>();

        menuItems.add(new MenuItem("Monday",R.drawable.monday));
        menuItems.add(new MenuItem("Tuesday",R.drawable.tuesday));
        menuItems.add(new MenuItem("Wednesday",R.drawable.wednesday));
        menuItems.add(new MenuItem("Thursday",R.drawable.thursday));
        menuItems.add(new MenuItem("Friday",R.drawable.friday));
        menuItems.add(new MenuItem("Saturday",R.drawable.saturday));
        menuItems.add(new MenuItem("Sunday",R.drawable.sunday));

        sNavigationDrawer.setMenuItemList(menuItems);

        fragmentClass =  MenuFragment.class;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();
        }

        sNavigationDrawer.setOnMenuItemClickListener(new SNavigationDrawer.OnMenuItemClickListener() {
            @Override
            public void onMenuItemClicked(int position) {
                System.out.println("Position "+position);

                switch (position){
                    case 0:{
                        fragmentClass = MenuFragment.class;
                        break;
                    }
                    case 1:{
                        fragmentClass = MenuFragment.class;
                        break;
                    }
                    case 2:{
                        fragmentClass = MenuFragment.class;
                        break;
                    }
                    case 3:{
                        fragmentClass = MenuFragment.class;
                        break;
                    }

                }

                //Listener for drawer events such as opening and closing.
                sNavigationDrawer.setDrawerListener(new SNavigationDrawer.DrawerListener() {

                    @Override
                    public void onDrawerOpened() {

                    }

                    @Override
                    public void onDrawerOpening(){

                    }

                    @Override
                    public void onDrawerClosing(){
                        System.out.println("Drawer closed");

                        try {
                            fragment = (Fragment) fragmentClass.newInstance();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        if (fragment != null) {
                            FragmentManager fragmentManager = getSupportFragmentManager();
                            fragmentManager.beginTransaction().setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out).replace(R.id.frameLayout, fragment).commit();

                        }
                    }

                    @Override
                    public void onDrawerClosed() {

                    }

                    @Override
                    public void onDrawerStateChanged(int newState) {
                        System.out.println("State "+newState);
                    }
                });
            }
        });
    }
}
