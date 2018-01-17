package com.example.vikasjilla.materialtest;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NavDrawerFrag.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public class NavDrawerFrag extends Fragment implements RecycleAdapt.ClickListener{

    public static final String PREF_FILE_NAME = "PrefStore";
    public static final String PREF_USERLEARNED_DRAWER_KEY = "UserLearnedDrawer";

    private Toolbar mToolBar;
    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mtoggleListener;
    private RecyclerView mRecyclerView;

    private RecycleAdapt mAdapter;

    private boolean misUserReadDrawer;
    private boolean misComingFromSavedState;
    private OnFragmentInteractionListener mListener;

    public View mcontainerView;

    public NavDrawerFrag() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        misUserReadDrawer = Boolean.valueOf(readFromPrefs(getActivity(), PREF_USERLEARNED_DRAWER_KEY,"false"));
        if(savedInstanceState != null){
            misComingFromSavedState = true;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_nav_drawer, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);


    }
    @Override
    public void onResume(){
        super.onResume();
        mAdapter = new RecycleAdapt(getActivity());
        mAdapter.setListener(this);
        mRecyclerView.setAdapter(mAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
    }
    @Override
    public void onDetach() {
        super.onDetach();
    }

    public void setUp(int fragmentID, final Toolbar toolbar, DrawerLayout drawerLayout) {
        mcontainerView = getActivity().findViewById(fragmentID);
        mToolBar = toolbar;
        mDrawerLayout = drawerLayout;
        mtoggleListener = new ActionBarDrawerToggle(getActivity(),drawerLayout,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                if(!misUserReadDrawer) {
                    misUserReadDrawer = true;
                    saveToSharedPreference(getActivity(), PREF_USERLEARNED_DRAWER_KEY,misUserReadDrawer+"");
                }
                getActivity().invalidateOptionsMenu();//to refresh toolbar
            }

            @Override
            public void onDrawerClosed(View drawerView)
            {
                super.onDrawerClosed(drawerView);
                getActivity().invalidateOptionsMenu();//to refresh toolbar
            }
            @Override
            public void onDrawerSlide(View drawerView,float slideOffset){//slideoffset-->0 to 1
                float alpha = (float)( 1-slideOffset < 0.4?0.4:1-slideOffset);
                toolbar.setAlpha(alpha);
            }
        };
        if(!misUserReadDrawer || !misComingFromSavedState){
            mDrawerLayout.openDrawer(mcontainerView);
        }
        mDrawerLayout.addDrawerListener(mtoggleListener);
        mDrawerLayout.post(new Runnable() {
            @Override
            public void run() {
                mtoggleListener.syncState();
            }
        });
    }

    void saveToSharedPreference(Context context,String preferenceKey,String preferenceValue){
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(preferenceKey,preferenceValue);
        editor.apply();//not reliable -- used for returning fastly from method
    }

    String readFromPrefs(Context context,String prefKey,String defaultValue){
        SharedPreferences preferences = context.getSharedPreferences(PREF_FILE_NAME,Context.MODE_PRIVATE);
        return preferences.getString(prefKey,defaultValue);
    }

    @Override
    public void itemClicked(View v, int position) {
        startActivity(new Intent(getActivity(),ActivityNextBack.class));
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
