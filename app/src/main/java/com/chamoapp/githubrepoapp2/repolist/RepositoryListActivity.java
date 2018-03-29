package com.chamoapp.githubrepoapp2.repolist;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.chamoapp.githubrepoapp2.GlobalApplication;
import com.chamoapp.githubrepoapp2.R;
import com.chamoapp.githubrepoapp2.databinding.ActivityRepositoryListBinding;
import com.chamoapp.githubrepoapp2.data.GitHubService;
import com.chamoapp.githubrepoapp2.repodetail.RepositoryDetailActivity;

import butterknife.BindView;
import butterknife.ButterKnife;


/**
 * Repository 목록을 표시하는 액티비티
 * MVVM의 뷰 역할을 한다
 */
public class RepositoryListActivity extends AppCompatActivity implements RepositoryListViewContract {

    private Context mContext;

    @BindView(R.id.coordinator_layout)
    View mRootView;

    @BindView(R.id.toolbar)
    Toolbar mToolbar;

    @BindView(R.id.recycler_repos)
    RecyclerView mRecyclerView;

    private RepositoryListAdapter mRepositoryListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        ActivityRepositoryListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_repository_list);
        GitHubService gitHubService = ((GlobalApplication) getApplication()).getGitHubService();
        RepositoryListViewModel repositoryListViewModel = new RepositoryListViewModel(this, gitHubService);
        binding.setViewModel(repositoryListViewModel);

        ButterKnife.bind(this);

        init();
    }

    private void init() {
        setSupportActionBar(mToolbar);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mRepositoryListAdapter = new RepositoryListAdapter(mContext, this);
        mRecyclerView.setAdapter(mRepositoryListAdapter);
    }

    @Override
    public void showRepositories(GitHubService.Repositories repositories) {
        mRepositoryListAdapter.setItemsAndRefresh(repositories.items);
    }

    @Override
    public void showError() {
        Snackbar.make(mRootView, "읽을 수 없습니다", Snackbar.LENGTH_LONG)
                .setAction("재시도", null).show();
    }

    @Override
    public void startRepositoryDetailActivity(String fullRepositoryName) {
        RepositoryDetailActivity.start(mContext, fullRepositoryName);
    }
}
