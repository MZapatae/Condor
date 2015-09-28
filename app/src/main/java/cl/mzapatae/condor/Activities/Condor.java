package cl.mzapatae.condor.Activities;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.mikepenz.community_material_typeface_library.CommunityMaterial;
import com.mikepenz.iconics.IconicsDrawable;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.DividerDrawerItem;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.ProfileDrawerItem;
import com.mikepenz.materialdrawer.model.SecondaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IProfile;
import com.mikepenz.materialdrawer.util.DrawerImageLoader;
import com.squareup.picasso.Picasso;

import cl.mzapatae.condor.R;

public class Condor extends AppCompatActivity {
    public static Toolbar toolbar;
    String twitterName = "Miguel A. Zapata";
    String twitterUser = "@Miguelost";
    private Drawer menuDrawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_condor);
        toolbar = (Toolbar) findViewById(R.id.condor_toolbar);
        setSupportActionBar(toolbar);

        PrimaryDrawerItem timeline = new PrimaryDrawerItem()
                .withName(R.string.app_menu_timeline)
                .withIcon(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_twitter).color(Color.GRAY).sizeDp(24));

        SecondaryDrawerItem settings = new SecondaryDrawerItem()
                .withName(R.string.app_menu_settings)
                .withIcon(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_settings).color(Color.GRAY).sizeDp(24));

        SecondaryDrawerItem logout = new SecondaryDrawerItem()
                .withName(R.string.app_menu_logout)
                .withIcon(new IconicsDrawable(this).icon(CommunityMaterial.Icon.cmd_logout).color(Color.GRAY).sizeDp(24));


        ProfileDrawerItem userProfile = new ProfileDrawerItem()
                .withName(twitterName)
                .withEmail(twitterUser)
                .withIcon(R.drawable.default_profile);


        AccountHeader headerMenu = new AccountHeaderBuilder()
                .withActivity(this)
                .withHeaderBackground(R.color.app_main_primary_dark)
                .addProfiles(userProfile)
                .withCurrentProfileHiddenInList(true)
                .withSelectionListEnabled(false)
                .withOnAccountHeaderSelectionViewClickListener(new AccountHeader.OnAccountHeaderSelectionViewClickListener() {
                    @Override
                    public boolean onClick(View view, IProfile iProfile) {
                        //Intent launchRegisterScreen = new Intent(MainApp.this, EditProfile.class);
                        //MainApp.this.startActivity(launchRegisterScreen);
                        return false;
                    }
                })
                .build();

        DrawerImageLoader.init(new DrawerImageLoader.IDrawerImageLoader() {
            @Override
            public void set(ImageView imageView, Uri uri, Drawable placeholder) {
                Picasso.with(imageView.getContext()).load(uri).placeholder(placeholder).into(imageView);
            }

            @Override
            public void cancel(ImageView imageView) {
                Picasso.with(imageView.getContext()).cancelRequest(imageView);
            }

            @Override
            public Drawable placeholder(Context ctx) {
                return null;
            }

            @Override
            public Drawable placeholder(Context context, String s) {
                return null;
            }
        });

        menuDrawer = new DrawerBuilder()
                .withActivity(this)
                .withToolbar(toolbar)
                .withActionBarDrawerToggle(true)
                .withAccountHeader(headerMenu)
                .addDrawerItems(
                        timeline,
                        new DividerDrawerItem(),
                        settings,
                        logout
                )
                .withOnDrawerItemClickListener(new Drawer.OnDrawerItemClickListener() {
                    @Override
                    public boolean onItemClick(View view, int position, IDrawerItem drawerItem) {
                        // do something with the clicked item :D
                        return false;
                    }
                })
                .build();
        menuDrawer.setSelection(1);
    }

    @Override
    public void onBackPressed() {
        if (menuDrawer != null && menuDrawer.isDrawerOpen()) {
            menuDrawer.closeDrawer();
        } else {
            super.onBackPressed();
        }
    }
}
