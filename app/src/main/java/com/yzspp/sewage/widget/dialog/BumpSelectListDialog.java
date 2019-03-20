package com.yzspp.sewage.widget.dialog;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LayoutAnimationController;
import android.view.animation.TranslateAnimation;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;


import com.yzspp.sewage.R;
import com.yzspp.sewage.widget.dialog.entity.DialogMenuItem;
import com.yzspp.sewage.widget.dialog.listener.OnBtnClickL;
import com.yzspp.sewage.widget.dialog.widget.base.BaseDialog;

import java.util.ArrayList;
import java.util.List;


public class BumpSelectListDialog extends BaseDialog<BumpSelectListDialog> {

    private ListView mLv;
    private Button mTvBtnLeft;
    private Button mTvBtnRight;

    /**
     * left btn click listener(左按钮接口)
     */
    protected OnBtnClickL mOnBtnLeftClickL;
    /**
     * right btn click listener(右按钮接口)
     */
    protected OnBtnClickL mOnBtnRightClickL;

    /**
     * adapter(自定义适配器)
     */
    private BaseAdapter mAdapter;
    /**
     * operation items(操作items)
     */
    private ArrayList<DialogMenuItem> mContents = new ArrayList<>();
    private LayoutAnimationController mLac;

    public BumpSelectListDialog(Context context, List<String> items) {
        super(context);
        mContents = new ArrayList<>();
        for (String item : items) {
            DialogMenuItem customBaseItem = new DialogMenuItem(item, 0);
            mContents.add(customBaseItem);
        }
        init();
    }

    public BumpSelectListDialog(Context context, BaseAdapter adapter) {
        super(context);
        mAdapter = adapter;
        init();
    }

    private void init() {
        widthScale(0.85f);

        /** LayoutAnimation */
        TranslateAnimation animation = new TranslateAnimation(Animation.RELATIVE_TO_SELF, 2f, Animation.RELATIVE_TO_SELF,
                0f, Animation.RELATIVE_TO_SELF, 0f, Animation.RELATIVE_TO_SELF, 0);
        animation.setInterpolator(new DecelerateInterpolator());
        animation.setDuration(500);

        mLac = new LayoutAnimationController(animation, 0.12f);
        mLac.setInterpolator(new DecelerateInterpolator());
    }

    @Override
    public View onCreateView() {
        View inflate = View.inflate(mContext, R.layout.dialog_bump_list, null);
        mLv = inflate.findViewById(R.id.listview);
        mTvBtnLeft = inflate.findViewById(R.id.btn_neg);
        mTvBtnRight = inflate.findViewById(R.id.btn_pos);
        return inflate;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void setUiBeforShow() {
        /** listview */
        if (mAdapter == null) {
            mAdapter = new ListDialogAdapter();
        }
        mLv.setAdapter(mAdapter);
        mLv.setLayoutAnimation(mLac);

        setListViewHeight(mLv); //把上面的设置方法加到这里

        mTvBtnLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnLeftClickL != null) {
                    mOnBtnLeftClickL.onBtnClick();
                } else {
                    dismiss();
                }
            }
        });

        mTvBtnRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mOnBtnRightClickL != null) {
                    mOnBtnRightClickL.onBtnClick();
                } else {
                    dismiss();
                }
            }
        });
    }

    public void setOnBtnClickL(OnBtnClickL... onBtnClickLs) {
        if (onBtnClickLs.length < 1 || onBtnClickLs.length > 2) {
            throw new IllegalStateException(" range of param onBtnClickLs length is [1,3]!");
        }

        if (onBtnClickLs.length == 1) {
            mOnBtnLeftClickL = onBtnClickLs[0];
        } else if (onBtnClickLs.length == 2) {
            mOnBtnLeftClickL = onBtnClickLs[0];
            mOnBtnRightClickL = onBtnClickLs[1];
        }
    }

    private void setListViewHeight(ListView listView) {
        ListAdapter listAdapter = listView.getAdapter(); //得到ListView 添加的适配器
        if (listAdapter == null) {
            return;
        }
        View itemView = listAdapter.getView(0, null, listView); //获取其中的一项 //进行这一项的测量，为什么加这一步
        itemView.measure(0, 0);
        int itemHeight = itemView.getMeasuredHeight(); //一项的高度
        int itemCount = listAdapter.getCount();//得到总的项数
        LinearLayout.LayoutParams layoutParams = null; //进行布局参数的设置
        if (itemCount <= 5) {
            if (itemCount <= 3) {
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * 3);
            } else {
                layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * itemCount);
            }
        } else if (itemCount > 5) {
            layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, itemHeight * 5);
        }
        listView.setLayoutParams(layoutParams);
    }

    class ListDialogAdapter extends BaseAdapter {
        @Override
        public int getCount() {
            return mContents.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            final DialogMenuItem item = mContents.get(position);

            @SuppressLint("ViewHolder")
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item_bump_selected_layout, null);
            TextView fruitName = view.findViewById(R.id.tvInvalidPrdTitle);
            fruitName.setText(item.mOperName);

            fruitName.setOnClickListener(v -> {
                if (onSelectedItemListener!=null)
                    onSelectedItemListener.click(item.mOperName);
            });
            return view;
        }
    }

    public interface OnSelectedItemListener{
        void click(String name);
    }

    private OnSelectedItemListener onSelectedItemListener;


    public void setOnSelectedItemListener(OnSelectedItemListener onSelectedItemListener) {
        this.onSelectedItemListener = onSelectedItemListener;
    }
}
