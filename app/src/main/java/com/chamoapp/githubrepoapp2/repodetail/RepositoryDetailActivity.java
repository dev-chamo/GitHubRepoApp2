package com.chamoapp.githubrepoapp2.repodetail;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.chamoapp.githubrepoapp2.GlobalApplication;
import com.chamoapp.githubrepoapp2.R;
import com.chamoapp.githubrepoapp2.data.GitHubService;
import com.chamoapp.githubrepoapp2.databinding.ActivityRepositoryDetailBinding;

/**
 * 상세 화면을 표시하는 액티비티
 */
public class RepositoryDetailActivity extends AppCompatActivity implements RepositoryDetailViewContract {

    private static final String EXTRA_FULL_REPOSITORY_NAME = "EXTRA_FULL_REPOSITORY_NAME";
    private String mFullRepoName;

    public static void start(Context context, String fullRepositoryName) {
        final Intent intent = new Intent(context, RepositoryDetailActivity.class);
        intent.putExtra(EXTRA_FULL_REPOSITORY_NAME, fullRepositoryName);
        context.startActivity(intent);
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repository_detail);

        ActivityRepositoryDetailBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_repository_detail);
        GitHubService gitHubService = ((GlobalApplication) getApplication()).getGitHubService();
        RepositoryDetailViewModel repositoryDetailViewModel = new RepositoryDetailViewModel(this, gitHubService);
        binding.setViewModel(repositoryDetailViewModel);

        Intent intent = getIntent();
        mFullRepoName = intent.getStringExtra(EXTRA_FULL_REPOSITORY_NAME);
        repositoryDetailViewModel.loadRepositories();
    }

    @Override
    public String getFullRepositoryName() {
        return mFullRepoName;
    }

    @Override
    public void startBrowser(String url) {
        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
    }

    @Override
    public void showError(String message) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_LONG)
                .show();
    }
}
