package com.yzspp.sewage.bean;

import com.yzspp.sewage.R;
import com.yzspp.sewage.view.tree.bean.LayoutItem;

/**
 * RootNode
 */

public class RootNode implements LayoutItem {
    private String name;

    public RootNode(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int getLayoutId() {
        return R.layout.item_tree_root;
    }

    @Override
    public int getToggleId() {
        return R.id.llParent;
    }

    @Override
    public int getCheckedId() {
        return R.id.ivCheck;
    }

    @Override
    public int getClickId() {
        return R.id.tvName;
    }
}
