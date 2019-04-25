package com.yzspp.sewage.Bump;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.widget.NestedScrollView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.NiceVideoPlayerManager;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseActivity;
import com.yzspp.sewage.widget.ExcelLayout;

import frame.tool.MyToast;

public class BumpDetailActivity extends BaseActivity {

    private NestedScrollView elDataView;
    private NiceVideoPlayer mNiceVideoPlayer;
    private NiceVideoPlayer mBigNiceVideoPlayer;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_bump_detail;
    }

    @Override
    protected void initView() {
        initToolbar("扬子河泵坑监测画面", true);

        elDataView = findViewById(R.id.elDataView);
        mBigNiceVideoPlayer = findViewById(R.id.nice_video_player_big);
        mNiceVideoPlayer = findViewById(R.id.nice_video_player);

        initPlayer();
    }

    private void initPlayer() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        mNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("远程泵站实时境况");
        controller.setLenght(98000);
        Glide.with(this)
                .load("https://github.com/LeoCheung0221/SewageBumpPit/test_bump_info.png")
                .placeholder(R.mipmap.test_bump_info)
                .crossFade()
                .into(controller.imageView());
        mNiceVideoPlayer.setController(controller);
    }

    private void initBigPlayer() {
        mBigNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        mBigNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(this);
        controller.setTitle("远程泵站实时境况");
        controller.setLenght(98000);
        Glide.with(this)
                .load("https://github.com/LeoCheung0221/SewageBumpPit/test_bump_info.png")
                .placeholder(R.mipmap.test_bump_info)
                .crossFade()
                .into(controller.imageView());
        mBigNiceVideoPlayer.setController(controller);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // Checks the orientation of the screen
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {//横屏
//            MyToast.success(getCurrentActivity(), "横屏");
            //需要执行的操作，可以不写
            elDataView.setVisibility(View.GONE);
            mBigNiceVideoPlayer.setVisibility(View.VISIBLE);

            initBigPlayer();
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            //需要执行的操作，可以不写
//            MyToast.success(getCurrentActivity(), "竖屏");
            elDataView.setVisibility(View.VISIBLE);
            mBigNiceVideoPlayer.setVisibility(View.GONE);

            initPlayer();
        }
    }

    public void enterTinyWindow(View view) {
        if (mNiceVideoPlayer.isIdle()) {
            Toast.makeText(this, "要点击播放后才能进入小窗口", Toast.LENGTH_SHORT).show();
        } else {
            mNiceVideoPlayer.enterTinyWindow();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        NiceVideoPlayerManager.instance().releaseNiceVideoPlayer();
    }

    @Override
    public void onBackPressed() {
        if (NiceVideoPlayerManager.instance().onBackPressd()) return;
        super.onBackPressed();
    }

    @Override
    protected void setListener() {

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
}
