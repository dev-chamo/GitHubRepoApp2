package com.chamoapp.githubrepoapp2.repolist;

import android.databinding.ObservableField;
import android.view.View;

import com.chamoapp.githubrepoapp2.data.GitHubService;

/**
 * ViewModel 클래스
 */
public class RepositoryItemViewModel {
    public ObservableField<String> mRepoName = new ObservableField<>();
    public ObservableField<String> mRepoDetail = new ObservableField<>();
    public ObservableField<String> mRepoStar = new ObservableField<>();
    public ObservableField<String> mRepoImageUrl = new ObservableField<>();

    RepositoryListViewContract mView;
    private String mFullName;

    public RepositoryItemViewModel(RepositoryListViewContract view) {
        this.mView = view;
    }

    public void loadItem(GitHubService.RepositoryItem item) {
        mFullName = item.fullName;
        mRepoDetail.set(item.description);
        mRepoName.set(item.name);
        mRepoStar.set(item.stargazersCount);
        mRepoImageUrl.set(item.owner.avatarUrl);
    }

    public void onItemClick(View itemView) {
        mView.startRepositoryDetailActivity(mFullName);
    }
}
