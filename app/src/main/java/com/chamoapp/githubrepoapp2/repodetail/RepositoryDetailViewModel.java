package com.chamoapp.githubrepoapp2.repodetail;

import android.databinding.ObservableField;
import android.view.View;

import com.chamoapp.githubrepoapp2.data.GitHubService;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

public class RepositoryDetailViewModel {
    private RepositoryDetailViewContract mRepositoryDetailView;
    private GitHubService mGitHubService;
    public ObservableField<String> mRepoFullName = new ObservableField<>();
    public ObservableField<String> mRepoDetail = new ObservableField<>();
    public ObservableField<String> mRepoStar = new ObservableField<>();
    public ObservableField<String> mRepoFork = new ObservableField<>();
    public ObservableField<String> mRepoImageUrl = new ObservableField<>();
    private GitHubService.RepositoryItem mRepositoryItem;

    public RepositoryDetailViewModel(RepositoryDetailViewContract detailView, GitHubService gitHubService) {
        this.mRepositoryDetailView = detailView;
        this.mGitHubService = gitHubService;
    }

    public void prepare() {
        loadRepositories();
    }

    /**
     * 하나의 리포지터리에 대한 정보를 가져온다
     * 기본적으로 API 액세스 방법에 대해서는 RepositoryListActivity#loadRepositories(String)와 같다
     */
    public void loadRepositories() {
        String fullRepoName = mRepositoryDetailView.getFullRepositoryName();
        final String[] repoData = fullRepoName.split("/");
        final String owner = repoData[0];
        final String repoName = repoData[1];
        mGitHubService.detailRepo(owner, repoName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GitHubService.RepositoryItem>() {
                    @Override
                    public void onError(Throwable e) {
                        mRepositoryDetailView.showError("읽을 수 없습니다.");
                    }

                    @Override
                    public void onNext(GitHubService.RepositoryItem repositoryItem) {
                        loadRepositoryItem(repositoryItem);
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }

    private void loadRepositoryItem(GitHubService.RepositoryItem repositoryItem) {
        this.mRepositoryItem = repositoryItem;
        mRepoFullName.set(repositoryItem.fullName);
        mRepoDetail.set(repositoryItem.description);
        mRepoStar.set(repositoryItem.stargazersCount);
        mRepoFork.set(repositoryItem.forksCount);
        mRepoImageUrl.set(repositoryItem.owner.avatarUrl);
    }


    public void onTitleClick(View v) {
        try {
            mRepositoryDetailView.startBrowser(mRepositoryItem.htmlUrl);
        } catch (Exception e) {
            mRepositoryDetailView.showError("링크를 열 수 없습니다.");
        }
    }
}
