package com.yzspp.sewage.Bump.frag;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.NestedScrollView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    private ImageView ivBigLandScapeView;

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
            ivBigLandScapeView.setVisibility(View.VISIBLE);
        } else if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {//竖屏
            //需要执行的操作，可以不写
//            MyToast.success(getCurrentActivity(), "竖屏");
            elDataView.setVisibility(View.VISIBLE);
            ivBigLandScapeView.setVisibility(View.GONE);
        }
    }

    @Override
    protected void initView(View view) {
        elDataView = view.findViewById(R.id.elDataView);
        ivBigLandScapeView = view.findViewById(R.id.ivBigLandScapeView);
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
