package com.persistent.esansad;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.persistent.esansad.constants.Constants;
import com.persistent.esansad.fragments.DashboardFragment;
import com.persistent.esansad.fragments.EsansadTab;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import br.liveo.interfaces.NavigationLiveoListener;
import br.liveo.navigationliveo.NavigationLiveo;

public class MainActivity extends NavigationLiveo implements NavigationLiveoListener {

    private static final int DEFAULT_POSITION = 0;

    @Override
    public void onUserInformation() {
        //User information here
        this.mUserName.setText("Pranav Tendolkar");
        this.mUserEmail.setText("pranavtalking@gmail.com");
        this.mUserPhoto.setImageResource(R.drawable.pranav);
        this.mUserBackground.setImageResource(R.drawable.ic_user_background);
    }

    @Override
    public void onInt(Bundle bundle) {


        Intent i = getIntent();
        if (i.getStringExtra("name") != null)
            this.mUserName.setText(i.getStringExtra("name"));
        if (i.getStringExtra("email") != null)
            this.mUserEmail.setText(i.getStringExtra("email"));
        if (i.getStringExtra("profileImage") != null)
            new LoadProfileImage(this.mUserPhoto).execute(i.getStringExtra("profileImage"));


        // set listener {required}
        this.setNavigationListener(this);
        this.setDefaultStartPositionNavigation(DEFAULT_POSITION);

        // name of the list items
        List<String> mListNameItem = new ArrayList<>();
        mListNameItem.add(0, getResources().getString(R.string.title_section1));
        mListNameItem.add(1, getResources().getString(R.string.title_section2));
        mListNameItem.add(2, getResources().getString(R.string.title_section3));
        mListNameItem.add(3, getResources().getString(R.string.title_section4));
        mListNameItem.add(4, getResources().getString(R.string.title_section5));

        // icons list items
        List<Integer> mListIconItem = new ArrayList<>();
        mListIconItem.add(0, R.drawable.ic_drawer);
        mListIconItem.add(1, R.drawable.ic_drawer);
        mListIconItem.add(2, R.drawable.ic_drawer);
        mListIconItem.add(3, R.drawable.ic_drawer);
        mListIconItem.add(4, R.drawable.ic_drawer);


        //{optional} - Among the names there is some subheader, you must indicate it here
        List<Integer> mListHeaderItem = new ArrayList<>();
        mListHeaderItem.add(5);

        //{optional} - Among the names there is any item counter, you must indicate it (position) and the value here
        SparseIntArray mSparseCounterItem = new SparseIntArray(); //indicate all items that have a counter
        //  mSparseCounterItem.put(1, 123);   // appears next to section 1

        //this.setFooterInformationDrawer("Logout",R.drawable.ic_drawer);

        this.setNavigationAdapter(mListNameItem, mListIconItem, mListHeaderItem, mSparseCounterItem);
    }


    private Boolean exit = false;

    @Override
    public void onBackPressed() {
        if (exit) {
            finish(); // finish activity
        } else {
            Toast.makeText(this, "Press Back again to Exit.",
                    Toast.LENGTH_SHORT).show();
            exit = true;
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    exit = false;
                }
            }, 3 * 1000);

        }

    }

    @Override
    public void onItemClickNavigation(int position, int layoutContainerId) {


        Fragment fragment = null;

        switch (position) {
            case 0:
                fragment = new DashboardFragment();
                break;
            case 1:
                fragment = EsansadTab.newInstance(Constants.IS_PROPOSAL);
                break;

            case 2:
                fragment = PeopleCardsView.newInstance(Constants.IS_PROJECT);
                break;

            case 3:
                Intent i = new Intent(getBaseContext(), AadharActivity.class);
                MainActivity.this.startActivity(i);
                break;
            case 4:
                Intent i2 = new Intent(getBaseContext(), GooglePlusSignInActivity.class);
                MainActivity.this.startActivity(i2);
                finish();
                break;


        }
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.container, fragment)
                    .commit();
        }
    }

    @Override
    public void onPrepareOptionsMenuNavigation(Menu menu, int i, boolean b) {

    }

    @Override
    public void onClickFooterItemNavigation(View view) {

    }

    @Override
    public void onClickUserPhotoNavigation(View view) {

    }

    /**
     * Background Async task to load user profile picture from url
     */
    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }
}

