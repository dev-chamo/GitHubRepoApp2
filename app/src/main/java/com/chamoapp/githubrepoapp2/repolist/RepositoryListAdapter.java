package com.chamoapp.githubrepoapp2.repolist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.chamoapp.githubrepoapp2.R;
import com.chamoapp.githubrepoapp2.data.GitHubService;
import com.chamoapp.githubrepoapp2.databinding.RepoItemBinding;

import java.util.List;


/**
 * RecyclerView에서 Repository 목록을 표시하기 위한 Adapter 클래스
 * 이 클래스에 의해 RecyclerView 아이템의 View를 생성하고 View에 데이터를 넣는다
 */
public class RepositoryListAdapter extends RecyclerView.Adapter<RepositoryListAdapter.RepoViewHolder> {
    private RepositoryListViewContract mView;
    private Context mContext;
    private List<GitHubService.RepositoryItem> mItems;

    public RepositoryListAdapter(Context context, RepositoryListViewContract view) {
        this.mContext = context;
        this.mView = view;
    }

    public void setItemsAndRefresh(List<GitHubService.RepositoryItem> items) {
        this.mItems = items;
        notifyDataSetChanged();
    }

    public GitHubService.RepositoryItem getItemAt(int position) {
        return mItems.get(position);
    }

    @Override
    public RepoViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RepoItemBinding binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.repo_item, parent, false);
        binding.setViewModel(new RepositoryItemViewModel(mView));
        return new RepoViewHolder(binding.getRoot(), binding.getViewModel());
    }

    @Override
    public void onBindViewHolder(final RepoViewHolder holder, final int position) {
        final GitHubService.RepositoryItem item = getItemAt(position);
        holder.loadItem(item);

    }

    @Override
    public int getItemCount() {
        if (mItems == null) {
            return 0;
        }
        return mItems.size();
    }

    static class RepoViewHolder extends RecyclerView.ViewHolder {
        private final RepositoryItemViewModel viewModel;

        public RepoViewHolder(View itemView, RepositoryItemViewModel viewModel) {
            super(itemView);
            this.viewModel = viewModel;
        }

        public void loadItem(GitHubService.RepositoryItem item) {
            viewModel.loadItem(item);
        }
    }
}
