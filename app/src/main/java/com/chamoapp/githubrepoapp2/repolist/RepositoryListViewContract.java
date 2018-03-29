package com.chamoapp.githubrepoapp2.repolist;


import com.chamoapp.githubrepoapp2.data.GitHubService;

/**
 * 리포지터리 목록 화면이 가진 Contract를 정의해두는 인터페이스
 * ViewModel이 직접 Activity를 참조하지 않도록 인터페이스로 명확히 나눈다.
 */
public interface RepositoryListViewContract {
  void showRepositories(GitHubService.Repositories repositories);

  void showError();

  void startRepositoryDetailActivity(String fullRepositoryName);
}


