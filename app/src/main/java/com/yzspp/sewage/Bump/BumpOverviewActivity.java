package com.yzspp.sewage.Bump;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import com.yzspp.sewage.Bump.frag.BumpDetailFragment;
import com.yzspp.sewage.Bump.frag.DirectoryFragment;
import com.yzspp.sewage.Bump.frag.MeFragment;
import com.yzspp.sewage.Bump.frag.OverViewFragment;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.widget.boommenu.BoomButtons.BoomButton;
import com.yzspp.sewage.widget.boommenu.BoomButtons.ButtonPlaceEnum;
import com.yzspp.sewage.widget.boommenu.BoomMenuButton;
import com.yzspp.sewage.widget.boommenu.BuilderManager;
import com.yzspp.sewage.widget.boommenu.ButtonEnum;
import com.yzspp.sewage.widget.boommenu.OnBoomListener;
import com.yzspp.sewage.widget.boommenu.Piece.PiecePlaceEnum;


public class BumpOverviewActivity extends BaseActivity implements OnBoomListener {

    private BoomMenuButton bmb;

    final FragmentManager manager = getSupportFragmentManager();

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bump_overview;
    }

    @Override
    protected void initView() {
        initBoomMenu();
        initFragment();
    }

    private void initBoomMenu() {
        bmb = findViewById(R.id.bmb);
        assert bmb != null;
        bmb.setButtonEnum(ButtonEnum.TextOutsideCircle);
        bmb.setPiecePlaceEnum(PiecePlaceEnum.DOT_4_1);
        bmb.setButtonPlaceEnum(ButtonPlaceEnum.SC_4_1);
        for (int i = 0; i < bmb.getPiecePlaceEnum().pieceNumber(); i++)
            bmb.addBuilder(BuilderManager.getTextOutsideCircleButtonBuilder());

        bmb.setOnBoomListener(this);
    }

    private void initFragment() {
        switchActTitle(0);

        FragmentTransaction transaction = manager.beginTransaction();
        OverViewFragment overViewFragment = OverViewFragment.newInstance();
        transaction.replace(R.id.flContainer, overViewFragment).commit();
    }

    @Override
    protected void setListener() {
//        fb_overview.setNaviClickListener(this);
//        fb_directory.setNaviClickListener(this);
//        fb_detail.setNaviClickListener(this);
//        fb_me.setNaviClickListener(this);
    }

    @Override
    protected void initData() {

    }

    @Override
    protected void setData() {

    }

    @Override
    protected void getBundleExtras(Bundle bundle) {
    }

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    private void switchActTitle(int pageIndex) {
        String title;
        switch (pageIndex) {
            case 0:
                title = "扬中市截污泵坑集中监视";
                break;
            case 1:
                title = "扬中市截污泵坑集中监视";
                break;
            case 2:
                title = "XXXXXXX泵站";
                break;
            case 3:
                title = "我的";
                break;
            default:
                title = "总览";
                break;
        }
        initToolbar(title, true);
    }

    @Override
    public void onClicked(int index, BoomButton boomButton) {
        FragmentTransaction transaction = manager.beginTransaction();
        switch (index){
            case 0:
                switchActTitle(0);
                OverViewFragment overViewFragment = OverViewFragment.newInstance();
                transaction.replace(R.id.flContainer, overViewFragment).commit();
                break;
            case 1:
                switchActTitle(1);
                DirectoryFragment directoryFragment = DirectoryFragment.newInstance();
                transaction.replace(R.id.flContainer, directoryFragment).commit();
                break;
            case 2:
                switchActTitle(2);
                BumpDetailFragment bumpDetailFragment = BumpDetailFragment.newInstance();
                transaction.replace(R.id.flContainer, bumpDetailFragment).commit();
                break;
            case 3:
                switchActTitle(3);
                MeFragment meFragment = MeFragment.newInstance();
                transaction.replace(R.id.flContainer, meFragment).commit();
                break;
        }
    }

    @Override
    public void onBackgroundClick() {

    }

    @Override
    public void onBoomWillHide() {

    }

    @Override
    public void onBoomDidHide() {

    }

    @Override
    public void onBoomWillShow() {

    }

    @Override
    public void onBoomDidShow() {

    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
            //需要执行的操作，可以不写

        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            //需要执行的操作，可以不写
        }
    }
}
