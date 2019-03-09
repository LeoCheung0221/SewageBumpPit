package com.yzspp.sewage.bean;

import com.yzspp.sewage.R;
import com.yzspp.sewage.view.tree.bean.LayoutItem;

/**
 * LeafNode
 */

public class LeafNode implements LayoutItem {
    private String name;

    public LeafNode(String name) {
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
        return R.layout.item_tree_leaf;
    }

    @Override
    public int getToggleId() {
        return 0;
    }

    @Override
    public int getCheckedId() {
        return R.id.ivCheck;
    }

    @Override
    public int getClickId() {
        return R.id.llParent;
    }
}
