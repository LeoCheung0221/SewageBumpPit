package com.yzspp.sewage.Bump.frag;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.xiao.nicevideoplayer.NiceVideoPlayer;
import com.xiao.nicevideoplayer.TxVideoPlayerController;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseFragment;

/**
 * Created by 鼠夏目 on 2019/3/5.
 *
 * @See
 * @Description
 */
public class BumpDetailFragment extends BaseFragment {

    private NestedScrollView elDataView;
    private NiceVideoPlayer mNiceVideoPlayer;
    private NiceVideoPlayer mBigNiceVideoPlayer;

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    public static BumpDetailFragment newInstance() {
        BumpDetailFragment fragment = new BumpDetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bump_detail, container, false);
        return view;
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

    @Override
    protected void initView(View view) {
        elDataView = view.findViewById(R.id.elDataView);
        mBigNiceVideoPlayer = view.findViewById(R.id.nice_video_player_big);
        mNiceVideoPlayer = view.findViewById(R.id.nice_video_player);

        initPlayer();
    }

    private void initPlayer() {
        mNiceVideoPlayer.setPlayerType(NiceVideoPlayer.TYPE_IJK); // IjkPlayer or MediaPlayer
        String videoUrl = "http://tanzi27niu.cdsb.mobi/wps/wp-content/uploads/2017/05/2017-05-17_17-33-30.mp4";
        mNiceVideoPlayer.setUp(videoUrl, null);
        TxVideoPlayerController controller = new TxVideoPlayerController(getContext());
        controller.setTitle("远程泵站实时境况");
        controller.setLenght(98000);
        Glide.with(getContext())
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
        TxVideoPlayerController controller = new TxVideoPlayerController(getContext());
        controller.setTitle("远程泵站实时境况");
        controller.setLenght(98000);
        Glide.with(getContext())
                .load("https://github.com/LeoCheung0221/SewageBumpPit/test_bump_info.png")
                .placeholder(R.mipmap.test_bump_info)
                .crossFade()
                .into(controller.imageView());
        mBigNiceVideoPlayer.setController(controller);
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
}
