package com.yzspp.sewage.Bump.frag;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseFragment;

/**
 * Created by 鼠夏目 on 2019/3/5.
 *
 * @See
 * @Description
 */
public class DirectoryFragment extends BaseFragment {

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    public static DirectoryFragment newInstance() {
        DirectoryFragment fragment = new DirectoryFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_directory, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {

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
