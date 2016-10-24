package xiaoyuz.com.readingassistant.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.squareup.otto.Subscribe;

import xiaoyuz.com.readingassistant.EventDispatcher;
import xiaoyuz.com.readingassistant.R;
import xiaoyuz.com.readingassistant.base.BaseActivity;
import xiaoyuz.com.readingassistant.base.BaseFragment;
import xiaoyuz.com.readingassistant.base.LazyInstance;
import xiaoyuz.com.readingassistant.contract.NoteListContract;
import xiaoyuz.com.readingassistant.cropimage.Crop;
import xiaoyuz.com.readingassistant.db.repository.NoteRepository;
import xiaoyuz.com.readingassistant.db.repository.local.NoteLocalDataSource;
import xiaoyuz.com.readingassistant.event.FloatWindowClickEvent;
import xiaoyuz.com.readingassistant.fragment.DefaultFragment;
import xiaoyuz.com.readingassistant.fragment.NoteListFragment;
import xiaoyuz.com.readingassistant.presenter.NoteListPresenter;
import xiaoyuz.com.readingassistant.service.AssistantService;
import xiaoyuz.com.readingassistant.utils.App;
import xiaoyuz.com.readingassistant.utils.Constants;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final int PERMISSION_CHECK_RESULT_CODE = 1;
    private Intent mAssistantIntent;
    private boolean mAssistantActivited;

    private FragmentManager mFragmentManager;
    private LazyInstance<DefaultFragment> mLazyDefaultFragment;
    private LazyInstance<NoteListFragment> mLazyNoteListFragment;

    private NavigationView mNavigationView;

    private EventHandler mEventHandler;

    private NoteListContract.Presenter mNoteListPresenter;

    private class EventHandler {

        @Subscribe
        public void onFloatWindowClick(FloatWindowClickEvent event) {
            selectNavItem(1);
        }
    }

    @Override
    protected void initVariables() {
        mAssistantIntent = new Intent(MainActivity.this, AssistantService.class);
        mFragmentManager = getSupportFragmentManager();
        mLazyDefaultFragment =
                new LazyInstance<>(new LazyInstance.InstanceCreator<DefaultFragment>() {
                    @Override
                    public DefaultFragment createInstance() {
                        return new DefaultFragment();
                    }
                });
        mLazyNoteListFragment =
                new LazyInstance<>(new LazyInstance.InstanceCreator<NoteListFragment>() {
                    @Override
                    public NoteListFragment createInstance() {
                        return new NoteListFragment();
                    }
                });
        mEventHandler = new EventHandler();
        EventDispatcher.register(mEventHandler);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                if (mAssistantActivited) {
                    closeAssistant();
                } else {
                    openAssistant();
                }
                mAssistantActivited = !mAssistantActivited;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);
        // select first item at beginning.
        selectNavItem(0);
    }

    @Override
    protected void loadData() {

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            moveTaskToBack(false);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        closeAssistant();
        EventDispatcher.unregister(mEventHandler);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        switch (id) {
            case R.id.quit:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_gallery:
                if (mNoteListPresenter == null) {
                    mNoteListPresenter = new NoteListPresenter(mLazyNoteListFragment.get(),
                            NoteRepository.getInstance(NoteLocalDataSource.getInstance()));
                }
                replaceFragment(mLazyNoteListFragment.get());
                break;
            default:
                replaceFragment(mLazyDefaultFragment.get());
                break;
        }

//        if (id == R.id.nav_camera) {
//            // Handle the camera action
//            replaceFragment(new NoteListFragment());
//            Toast.makeText(this, String.valueOf(mFragmentManager.getBackStackEntryCount()),
//                    Toast.LENGTH_SHORT).show();
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void openAssistant(){
        if (Build.VERSION.SDK_INT >= 23) {
            if(!Settings.canDrawOverlays(this)) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION);
                startActivityForResult(intent, PERMISSION_CHECK_RESULT_CODE);
                return;
            } else {
                startService(mAssistantIntent);
            }
        }
    }

    private void closeAssistant() {
        stopService(mAssistantIntent);
    }

    private void selectNavItem(int pos) {
        onNavigationItemSelected(mNavigationView.getMenu().getItem(pos).setChecked(true));
    }

    public void replaceFragment(BaseFragment fragment) {
        mFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment).commitAllowingStateLoss();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (resultCode) {
            case PERMISSION_CHECK_RESULT_CODE:
                startService(mAssistantIntent);
                break;
            case RESULT_OK:
                if (requestCode == Crop.REQUEST_CROP) {
                    Bitmap bitmap = App.getACache().getAsBitmap(Constants.ACACHE_CROP_IMAGE_KEY);
                    Log.d("aaaaa", "adfasdf");
                }
                break;
            default:
                break;
        }
    }
}
