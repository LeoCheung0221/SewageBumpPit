package com.yzspp.sewage.Bump.frag;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.yzspp.sewage.Bump.BumpDetailActivity;
import com.yzspp.sewage.R;
import com.yzspp.sewage.utils.SSIntentTool;
import com.yzspp.sewage.base.BaseFragment;
import com.yzspp.sewage.bean.BranchNode;
import com.yzspp.sewage.bean.BranchViewBinder;
import com.yzspp.sewage.bean.LeafNode;
import com.yzspp.sewage.bean.LeafViewBinder;
import com.yzspp.sewage.bean.RootNode;
import com.yzspp.sewage.bean.RootViewBinder;
import com.yzspp.sewage.widget.tree.TreeViewAdapter;
import com.yzspp.sewage.widget.tree.bean.LayoutItem;
import com.yzspp.sewage.widget.tree.bean.TreeNode;
import com.yzspp.sewage.widget.tree.bean.TreeViewBinder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by 鼠夏目 on 2019/3/5.
 *
 * @See
 * @Description
 */
public class DirectoryFragment extends BaseFragment {

    private RecyclerView rv;
    private List<TreeNode> list = new ArrayList<>();
    private TreeViewAdapter adapter;

    private ProgressDialog progressDialog;

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
        rv = view.findViewById(R.id.rv_bump_position);

        list.clear();
        initTree();
        adapter.notifyData(list);
    }

    private void initTree() {
        TreeNode<RootNode> rootNode = new TreeNode<>(new RootNode("扬中市"));
        TreeNode<BranchNode> branchNode1 = new TreeNode<>(new BranchNode("八桥镇"));
        TreeNode<BranchNode> branchNode2 = new TreeNode<>(new BranchNode("XXX镇"));

        rootNode.addChild(branchNode1);
        rootNode.addChild(branchNode2);
        list.add(rootNode);
        initAdapter();
    }

    private void initAdapter() {
        adapter = new TreeViewAdapter(list, Arrays.asList(new RootViewBinder(), new BranchViewBinder(), new LeafViewBinder())) {
            @Override
            public void toggleClick(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                if (isOpen) {
                    addNewNode(treeNode);
                } else {
                    adapter.lastToggleClickToggle();
                }
            }

            @Override
            public void toggled(TreeViewBinder.ViewHolder viewHolder, View view, boolean isOpen, TreeNode treeNode) {
                viewHolder.findViewById(R.id.ivNode).setRotation(isOpen ? 90 : 0);
            }

            @Override
            public void checked(TreeViewBinder.ViewHolder viewHolder, View view, boolean checked, TreeNode treeNode) {

            }

            @Override
            public void itemClick(TreeViewBinder.ViewHolder viewHolder, View view, TreeNode treeNode) {
                String name = null;
                LayoutItem item = treeNode.getValue();
                if (item instanceof RootNode) {
                    name = ((RootNode) item).getName();
                } else if (item instanceof BranchNode) {
                    name = ((BranchNode) item).getName();
                } else if (item instanceof LeafNode) {
                    name = ((LeafNode) item).getName();
                    SSIntentTool.start(getContext(), BumpDetailActivity.class);
                }
//                Toast.makeText(BumpListActivity.this, name, Toast.LENGTH_SHORT).show();
            }
        };
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        rv.setAdapter(adapter);
    }

    private void addNewNode(final TreeNode treeNode) {
        autoProgress(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                String name = null;
                LayoutItem item = treeNode.getValue();
                if (item instanceof RootNode) {
                    name = ((RootNode) item).getName();
                } else if (item instanceof BranchNode) {
                    name = ((BranchNode) item).getName();

                    TreeNode<LeafNode> leafNode1 = new TreeNode<>(new LeafNode("泵坑1"));
                    TreeNode<LeafNode> leafNode2 = new TreeNode<>(new LeafNode("泵坑2"));
                    List<TreeNode> list = treeNode.getChildNodes();
                    boolean hasLeaf = false;
                    for (TreeNode child : list) {
                        if (child.getValue() instanceof LeafNode) {
                            hasLeaf = true;
                            break;
                        }
                    }
                    treeNode.addChild(leafNode1);
                    treeNode.addChild(leafNode2);
                } else if (item instanceof LeafNode) {
                    name = ((LeafNode) item).getName();
                }

                autoProgress(false);
                adapter.lastToggleClickToggle();
            }
        }, 500);
    }

    private void autoProgress(boolean show) {
        if (progressDialog == null) {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("加载中...");
        }
        if (show) {
            progressDialog.show();
        } else {
            progressDialog.dismiss();
        }
    }

    /**
     * 初始化跟
     */
    private List<TreeNode> initRoot() {
        List<TreeNode> rootList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TreeNode<RootNode> node = new TreeNode<>(new RootNode("根" + i));
            if (i % 2 == 0) {
                node.setChildNodes(initBranchs(node.getValue().getName()));
            } else {
                node.setChildNodes(initLeaves(node.getValue().getName()));
            }
            rootList.add(node);
        }
        return rootList;
    }

    int count = 5;

    /**
     * 初始化枝
     */
    private List<TreeNode> initBranchs(String name) {
        List<TreeNode> branchList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TreeNode<BranchNode> node = new TreeNode<>(new BranchNode(name + "枝" + i));
            if (i % 2 == 0) {
                node.setChildNodes(initLeaves(node.getValue().getName()));
            } else {
                if (count > 0) {
                    count--;
                    node.setChildNodes(initBranchs(node.getValue().getName()));
                } else {
                    node.setChildNodes(initLeaves(node.getValue().getName()));
                }
            }
            branchList.add(node);
        }
        return branchList;
    }

    /**
     * 初始化叶
     */
    private List<TreeNode> initLeaves(String name) {
        List<TreeNode> leafList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            TreeNode<LeafNode> node = new TreeNode<>(new LeafNode(name + "叶" + i));
            leafList.add(node);
        }
        return leafList;
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
