package com.example.vikasjilla.materialtest;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.menu.MenuBuilder;
import android.support.v7.widget.Toolbar;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ImageSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private ViewPager mPager;
    private SlidingTabLayout mTabLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        NavDrawerFrag frag = (NavDrawerFrag) getSupportFragmentManager().findFragmentById(R.id.fragment_nav_drawer_frag);
        DrawerLayout drawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        frag.setUp(R.id.fragment_nav_drawer_frag,toolbar,drawerLayout);
         mPager = (ViewPager) findViewById(R.id.myPager);
        mTabLayout = (SlidingTabLayout) findViewById(R.id.myTabLayout);
        mTabLayout.setCustomTabView(R.layout.tab_item_layout,R.id.textTabItem);
        mTabLayout.setDistributeEvenly(true);
        mTabLayout.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.colorPrimaryDark);
            }
        });
        mPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        mTabLayout.setViewPager(mPager);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.next) {
            startActivity(new Intent(this,ActivityNextBack.class));
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
    public static class MyFragment extends Fragment{
        public static MyFragment createOne(int pos){
            MyFragment frag = new MyFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("Pos",pos);
            frag.setArguments(bundle);
            return frag;
        }
        int position;
        @Override
        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup group,@Nullable Bundle savedInstanceState){
            View v = inflater.inflate(R.layout.my_frag,group,false);
            TextView tv = v.findViewById(R.id.myFragTV);
            savedInstanceState = getArguments();
            if(savedInstanceState != null)
            tv.setText(savedInstanceState.getInt("Pos")+" frag");
            return v;
        }

    }

    class MyPagerAdapter extends FragmentPagerAdapter{
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return MyFragment.createOne(position);
        }

        @Override
        public CharSequence getPageTitle(int position){
            String text;
            int image;
            switch (position){
                case 0:text = "fragment1";
                    image = R.drawable.ic_menu_camera;
                    break;
                case 1:text = "fragment2";
                    image = R.drawable.ic_menu_gallery;
                    break;
                default:text = "fragmet3";
                    image = R.drawable.ic_menu_send;
                    break;
            }
            Drawable drawable = getResources().getDrawable(image);
            ImageSpan span = new ImageSpan(drawable);
            drawable.setBounds(0,0,48,48);
            SpannableString result = new SpannableString(" ");//should have atleast one character for it to work
            result.setSpan(span,0,result.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            return result;

        }

        @Override
        public int getCount() {
            return 3;
        }
    }
}
