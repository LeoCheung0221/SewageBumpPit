package com.yzspp.sewage.bean;

import com.yzspp.sewage.R;
import com.yzspp.sewage.view.tree.bean.LayoutItem;

/**
 * BranchNode
 */

public class BranchNode implements LayoutItem {
    private String name;

    public BranchNode(String name) {
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
        return R.layout.item_tree_branch;
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
