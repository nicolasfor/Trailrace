package com.cam.trailrace.actividades;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Handler;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cam.trailrace.R;
import com.cam.trailrace.lib.FireBaseHelper;
import com.cam.trailrace.modelo.Piloto;
import com.cam.trailrace.modelo.PilotoItem;
import com.cam.trailrace.modelo.Pista;
import com.cam.trailrace.modelo.PistaItem;
import com.cam.trailrace.navegacion.DataCommunication;
import com.cam.trailrace.navegacion.PilotFragment;
import com.cam.trailrace.navegacion.PilotListFragment;
import com.cam.trailrace.navegacion.TeamFragment;
import com.cam.trailrace.navegacion.TrackListFragment;
import com.cam.trailrace.other.CircleTransform;

import java.util.ArrayList;

public class NavigationMainActivity extends AppCompatActivity implements DataCommunication{


    private NavigationView navigationView;
    private DrawerLayout drawer;
    private View navHeader;
    private ImageView imgNavHeaderBg, imgProfile;
    private Toolbar toolbar;
    private FloatingActionButton fab;
    private ArrayList<Pista> pistas;
    private ArrayList<PistaItem> pistasItem;
    private ArrayList<Piloto> pilotos;
    private PilotoItem pilotoAEditar;
    private ArrayList<PilotoItem> pilotosItem;
    private int posicion;
    private String currentID;
    private FireBaseHelper helper;

    // urls to load navigation header background image
    // and profile image
    private static final String urlNavHeaderBg = "http://api.androidhive.info/images/nav-menu-header-bg.jpg";
    // index to identify current nav menu item
    public static int navItemIndex = 0;

    // tags used to attach the fragments
    private static final String TAG_PILOTS = "Pilotos";
    private static final String TAG_TEAM = "Team";
    private static final String TAG_START = "Start";
    public static String CURRENT_TAG = TAG_PILOTS;

    // toolbar titles respected to selected nav menu item
    private String[] activityTitles;

    // flag to load home fragment when user presses back key
    private boolean shouldLoadHomeFragOnBackPress = true;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation_main);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Log.e("ESTADO:","Navigation Main Activity");
        mHandler = new Handler();
        helper = FireBaseHelper.getInstance();
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        fab = (FloatingActionButton) findViewById(R.id.fab);

        // Navigation view header
        navHeader = navigationView.getHeaderView(0);
        imgNavHeaderBg = (ImageView) navHeader.findViewById(R.id.img_header_bg);
        imgProfile = (ImageView) navHeader.findViewById(R.id.img_profile);

        // load toolbar titles from string resources
        activityTitles = getResources().getStringArray(R.array.nav_item_activity_titles);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // load nav menu header data
        loadNavHeader();

        // initializing navigation menu
        setUpNavigationView();

        if (savedInstanceState == null) {
            navItemIndex = 0;
            CURRENT_TAG = TAG_PILOTS;
            loadHomeFragment();
        }
    }

    /***
     * Load navigation menu header information
     * like background image, profile image
     * name, website, notifications action view (dot)
     */
    private void loadNavHeader() {

        // loading header background image
        Glide.with(this).load(urlNavHeaderBg)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imgNavHeaderBg);

        // Loading profile image

    }

    /***
     * Returns respected fragment that user
     * selected from navigation menu
     */
    private void loadHomeFragment() {
        // selecting appropriate nav menu item
        selectNavMenu();

        // set toolbar title
        setToolbarTitle();

        // if user select the current navigation menu again, don't do anything
        // just close the navigation drawer
        if (getSupportFragmentManager().findFragmentByTag(CURRENT_TAG) != null) {
            drawer.closeDrawers();

            // show or hide the fab button
            toggleFab();
            return;
        }

        // Sometimes, when fragment has huge data, screen seems hanging
        // when switching between navigation menus
        // So using runnable, the fragment is loaded with cross fade effect
        // This effect can be seen in GMail app
        Runnable mPendingRunnable = new Runnable() {
            @Override
            public void run() {
                // update the main content by replacing fragments
                Fragment fragment = getHomeFragment();
                fragment.setRetainInstance(true);
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,
                        android.R.anim.fade_out);
                fragmentTransaction.replace(R.id.frame, fragment, CURRENT_TAG);
                fragmentTransaction.commit();
            }
        };

        // If mPendingRunnable is not null, then add to the message queue
        if (mPendingRunnable != null) {
            mHandler.post(mPendingRunnable);
        }

        // show or hide the fab button
        toggleFab();

        //Closing drawer on item click
        drawer.closeDrawers();

        // refresh toolbar menu
        invalidateOptionsMenu();
    }

    private Fragment getHomeFragment() {
        switch (navItemIndex) {
            case 0:
                // home
                PilotListFragment pilotoListFragment = new PilotListFragment();
                return pilotoListFragment;
            case 1:
                // photos
                TeamFragment teamFragment = new TeamFragment();
                return teamFragment;
            case 2:
                // movies fragment
                TrackListFragment trackListFragment = new TrackListFragment();
                return trackListFragment;
            default:
                return new PilotListFragment();
        }
    }

    private void setToolbarTitle() {
        getSupportActionBar().setTitle(activityTitles[navItemIndex]);
    }

    private void selectNavMenu() {
        navigationView.getMenu().getItem(navItemIndex).setChecked(true);
    }

    private void setUpNavigationView() {
        //Setting Navigation View Item Selected Listener to handle the item click of the navigation menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {

            // This method will trigger on item Click of navigation menu
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                //Check to see which item was being clicked and perform appropriate action
                switch (menuItem.getItemId()) {
                    //Replacing the main content with ContentFragment Which is our Inbox View;
                    case R.id.nav_pilots:
                        navItemIndex = 0;
                        CURRENT_TAG = TAG_PILOTS;
                        break;
                    case R.id.nav_team:
                        navItemIndex = 1;
                        CURRENT_TAG = TAG_TEAM;
                        break;
                    case R.id.nav_movies:
                        navItemIndex = 2;
                        CURRENT_TAG = TAG_START;
                        break;
                    case R.id.teamOther:
                        startActivity(new Intent(NavigationMainActivity.this, TeamActivity.class));
                        drawer.closeDrawers();
                        return true;
                    default:
                        navItemIndex = 0;
                }

                //Checking if the item is in checked state or not, if not make it in checked state
                if (menuItem.isChecked()) {
                    menuItem.setChecked(false);
                } else {
                    menuItem.setChecked(true);
                }
                menuItem.setChecked(true);

                loadHomeFragment();

                return true;
            }
        });


        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawer.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessary or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawers();
            return;
        }

        // This code loads home fragment when back key is pressed
        // when user is in other fragment than home
        if (shouldLoadHomeFragOnBackPress) {
            // checking if user is on other navigation menu
            // rather than home
            if (navItemIndex != 0) {
                navItemIndex = 0;
                CURRENT_TAG = TAG_PILOTS;
                loadHomeFragment();
                return;
            }
        }

        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.main, menu);
        // when fragment is notifications, load the menu created for notifications
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_logout) {
            helper.getAuthentication().signOut();
            Intent it = new Intent(NavigationMainActivity.this, MainActivity.class);
            startActivity(it);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    // show or hide the fab
    private void toggleFab() {
        if (navItemIndex == 2)
            fab.show();
        else
            fab.hide();
    }

    public String darCurrentUser(){
        return currentID;
    }
    public ArrayList<Piloto> darPilotos(){
        return pilotos;
    }
    public void agregarPiloto(ArrayList<Piloto> nuevoPilotos){
        this.pilotos=nuevoPilotos;
    }

    public int darPosicion(){
        return posicion;
    }
    public void cambiarPosicion(int posicion){
        this.posicion=posicion;
    }

    public ArrayList<Pista> darPistas(){
        return pistas;
    }
    public void agregarPista(ArrayList<Pista> nuevaPistas){
        this.pistas=nuevaPistas;
    }

    public ArrayList<PistaItem> darPistasItem(){
        return pistasItem;
    }
    public void agregarPistaItem(ArrayList<PistaItem> nuevaPistas){
        this.pistasItem=nuevaPistas;
    }

    public PilotoItem darPilotoAEditar(){
        return pilotoAEditar;
    }

    public void setPilotoAEditar(PilotoItem piloto){
        this.pilotoAEditar=piloto;
    }

    public ArrayList<PilotoItem> darPilotosItem(){
        return pilotosItem;
    }
    public void agregarPilotoItem(ArrayList<PilotoItem> nuevosPilotosItem){
        pilotosItem=nuevosPilotosItem;
    }

}
