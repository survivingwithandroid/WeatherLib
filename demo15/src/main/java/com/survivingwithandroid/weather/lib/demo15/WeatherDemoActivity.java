/**
 * ${copyright}.
 */
package com.survivingwithandroid.weather.lib.demo15;


import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;

import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.survivingwithandroid.weather.lib.demo15.*;
import com.survivingwithandroid.weather.lib.demo15.fragment.ChartFragment;
import com.survivingwithandroid.weather.lib.demo15.fragment.HistoricalWeatherFragment;
import com.survivingwithandroid.weather.lib.demo15.fragment.HttpClientFragment;
import com.survivingwithandroid.weather.lib.demo15.fragment.RadarFragment;
import com.survivingwithandroid.weather.lib.demo15.fragment.WebcamFragment;

public class WeatherDemoActivity extends Activity {


    private CharSequence mTitle;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView itemsList;

    private DrawerLayout mDrawerLayout;
    private String[] options;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        options = getResources().getStringArray(R.array.demo_list);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        itemsList = (ListView) mDrawerLayout.findViewById(R.id.item_list);

        itemsList.setAdapter(new ArrayAdapter<String>(this, R.layout.drawer_list_item, options));

        mTitle = getTitle();
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.navigation_drawer_open,  /* "open drawer" description for accessibility */
                R.string.navigation_drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(mTitle);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        itemsList.setOnItemClickListener(new DrawerItemClickListener());
    }


    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        Fragment frag = null;
        switch(position) {
            case 0:
                frag = new HttpClientFragment();
                break;
            case 1:
                frag = new ChartFragment();
                break;
            case 2: {
                frag = new RadarFragment();
                Bundle b = new Bundle();
                b.putString("image_type", "radar");
                frag.setArguments(b);
                break;
            }
            case 3: {
                frag = new RadarFragment();
                Bundle b = new Bundle();
                b.putString("image_type", "satellite");
                frag.setArguments(b);
                break;
           }
            case 4: {
                frag = new HistoricalWeatherFragment();
                break;
            }
            case 5: {
                    frag = new WebcamFragment();
                    break;
            }
        }
        FragmentManager fm = getFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.content_frame, frag);
        ft.commit();
        itemsList.setItemChecked(position, true);
        mTitle = options[position];
        getActionBar().setTitle(mTitle);
        mDrawerLayout.closeDrawers();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

       return super.onOptionsItemSelected(item);
    }
}




