package com.chamoapp.githubrepoapp2.repolist;

import android.databinding.ObservableInt;
import android.text.format.DateFormat;
import android.view.View;

import com.chamoapp.githubrepoapp2.data.GitHubService;

import java.util.Calendar;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.schedulers.Schedulers;

/**
 * MVVM의 ViewModel 역할을 하는 클래스
 */
public class RepositoryListViewModel {
    public ObservableInt mLoadingViewVisibility = new ObservableInt(View.VISIBLE);
    private RepositoryListViewContract mRepositoryListView;
    private GitHubService mGitHubService;

    public RepositoryListViewModel(RepositoryListViewContract repositoryListView, GitHubService gitHubService) {
        this.mRepositoryListView = repositoryListView;
        this.mGitHubService = gitHubService;

        onReady();
    }


    private void onReady() {
        loadRepositories("java");
    }

    /**
     * 지난 일주일간 만들어진 라이브러리를 인기순으로 가져온다
     */
    private void loadRepositories(String language) {
        mLoadingViewVisibility.set(View.VISIBLE);

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -7);
        String text = DateFormat.format("yyyy-MM-dd", calendar).toString();

        Observable<GitHubService.Repositories> observable = mGitHubService.listRepos(
                "language:" + language + " " + "created:>" + text);

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<GitHubService.Repositories>() {
                    @Override
                    public void onNext(GitHubService.Repositories repositories) {
                        mLoadingViewVisibility.set(View.GONE);
                        mRepositoryListView.showRepositories(repositories);
                    }

                    @Override
                    public void onError(Throwable e) {
                        mRepositoryListView.showError();
                    }

                    @Override
                    public void onComplete() {
                    }
                });
    }
}
