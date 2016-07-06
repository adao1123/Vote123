package com.example.vote123;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.design.widget.NavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

public class NaviActivity extends AppCompatActivity {
    private static final String TAG = "NaviActivity";
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private DrawerLayout drawer;
    private NavigationView navigationView;
    private ActionBarDrawerToggle drawerToggle;
    private SplashFragment splashFragment;
    private SelectFragment selectFragment;
    private MyBallotFragment myBallotFragment;
    private RegisterFragment registerFragment;
    private PollingFragment pollingFragment;
    private ExploreFragment exploreFragment;
    private CalendarFragment calendarFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navi);
        initializeViews();
        initializeFragments();
    }

    /**
     * This method initializes all the views within the activity.
     */
    private void initializeViews(){
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout_id);
        drawerToggle = setupDrawerToggle();
        drawer.addDrawerListener(drawerToggle);
        navigationView = (NavigationView) findViewById(R.id.nvView_id);
        setUpDrawerContent(navigationView);
    }

    /**
     * This method initializes all the fragments used in this activity.
     */
    private void initializeFragments(){
        splashFragment = new SplashFragment();
        selectFragment = new SelectFragment();
        myBallotFragment = new MyBallotFragment();
        registerFragment = new RegisterFragment();
        pollingFragment = new PollingFragment();
        exploreFragment = new ExploreFragment();
        calendarFragment = new CalendarFragment();
    }

    /**
     * This method sets up the toggle for the navigation drawer.
     * @return
     */
    private ActionBarDrawerToggle setupDrawerToggle(){
        return new ActionBarDrawerToggle(this, drawer, null, R.string.drawer_open, R.string.drawer_close);
    }

    /**
     * This method sets up interface for the the navigation drawer.
     * @param navigationView
     */
    private void setUpDrawerContent(NavigationView navigationView){
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                selectDrawerItem(item);
                return true;
            }
        });
    }

    /**
     * This method will swap the fragment displayed in the fragment container in the Profile Activity
     * when the user clicks on an item in the navigation drawer.
     * @param menuItem
     */
    public void selectDrawerItem(MenuItem menuItem){
        setFragmentLogistics();
        fragmentTransaction.addToBackStack(null);
        switch (menuItem.getItemId()){
            case R.id.drawer_register_id:
                fragmentTransaction.replace(R.id.navi_container_id, registerFragment);
                break;
            case R.id.drawer_polling_id:
                fragmentTransaction.replace(R.id.navi_container_id, pollingFragment);
                break;
            case R.id.drawer_calendar_id:
                fragmentTransaction.replace(R.id.navi_container_id, calendarFragment);
                break;
            case R.id.drawer_explore_id:
                fragmentTransaction.replace(R.id.navi_container_id, exploreFragment);
                break;
            case R.id.drawer_my_ballot_id:
                fragmentTransaction.replace(R.id.navi_container_id, myBallotFragment);
                break;
            case R.id.drawer_select_id:
                fragmentTransaction.replace(R.id.navi_container_id, selectFragment);
                break;
        }
        fragmentTransaction.commit();
        menuItem.setChecked(true);
        //setTitle(menuItem.getTitle());
        drawer.closeDrawers();
    }

    /**
     * This method sets up the fragmentManager and fragmentTransaction.
     */
    private void setFragmentLogistics(){
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
    }

}