package com.yzspp.sewage.Bump.frag;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.yzspp.sewage.Mine.EditPersonalInfoActivity;
import com.yzspp.sewage.Mine.SettingsActivity;
import com.yzspp.sewage.R;
import com.yzspp.sewage.base.BaseFragment;

import de.hdodenhof.circleimageview.CircleImageView;
import frame.tool.MyPrefence;

/**
 * Created by 鼠夏目 on 2019/3/5.
 *
 * @See
 * @Description
 */
public class MeFragment extends BaseFragment {

    private CircleImageView civhead;
    private TextView tvusername;
    private RelativeLayout rluserinfo;
    private static final int REQUEST_EDIT_INFO = 607;
    private MyPrefence mPrefence;

    @Override
    protected String[] getNeedPermissions() {
        return new String[0];
    }

    public static MeFragment newInstance() {
        MeFragment fragment = new MeFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    protected View initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_me, container, false);
        return view;
    }

    @Override
    protected void initView(View view) {
        this.rluserinfo = view.findViewById(R.id.rl_user_info);
        this.tvusername = view.findViewById(R.id.tv_username);
        this.civhead = view.findViewById(R.id.civ_head);
    }

    @Override
    protected void setListener() {
        rluserinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(new Intent(getContext(), EditPersonalInfoActivity.class),
                        REQUEST_EDIT_INFO);
            }
        });
    }

    @Override
    protected void initData() {
        mPrefence = MyPrefence.getInstance(getContext());
    }

    @Override
    protected void setData() {
        showUserInfo();
    }

    /**
     * 显示用户信息
     */
    private void showUserInfo() {
        String path = mPrefence.getString(getString(R.string.avater_path), null);
        if (path != null) {
            Glide.with(this)
                    .load(path)
                    .into(civhead);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_EDIT_INFO) {
            if (resultCode == Activity.RESULT_OK) {
                showUserInfo();
            }
        }
    }

    @Override
    protected void getBundleExtras(Bundle bundle) {

    }
}
