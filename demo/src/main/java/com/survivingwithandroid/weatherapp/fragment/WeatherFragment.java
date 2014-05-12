package com.survivingwithandroid.weatherapp.fragment;

import android.app.Fragment;

/**
 * ${copyright}.
 */
public abstract class WeatherFragment extends Fragment {

    protected WeatherEventListener getListener() {
        return ( (WeatherEventListener) getActivity());
    }

    public static interface WeatherEventListener {
        public void requestCompleted();
    }

    public abstract void refreshData();
}
