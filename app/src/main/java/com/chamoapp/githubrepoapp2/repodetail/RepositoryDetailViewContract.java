package com.chamoapp.githubrepoapp2.repodetail;

/**
 * 상세 화면의 View가 가진 Contract을 정의해 두는 인터페이스
 * ViewModel이 직접 Activity를 참조하지 않도록 인터페이스로 명확히 나눈다
 */
public interface RepositoryDetailViewContract {
    String getFullRepositoryName();

    void startBrowser(String url);

    void showError(String message);
}
