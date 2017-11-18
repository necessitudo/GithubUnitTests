package ru.arturvasilov.githubmvp.screen.repositories;

import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import ru.arturvasilov.githubmvp.test.MockLifecycleHandler;
import ru.arturvasilov.githubmvp.test.TestGithubRepository;
import ru.arturvasilov.rxloader.LifecycleHandler;
import ru.arturvasilov.rxloader.RxSchedulers;
import ru.gdgkazan.githubmvp.api.ApiFactory;
import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.auth.AuthPresenter;
import ru.gdgkazan.githubmvp.screen.auth.AuthView;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter;
import ru.gdgkazan.githubmvp.screen.repositories.RepositoriesView;
import ru.gdgkazan.githubmvp.utils.AuthorizationUtils;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class RepositoriesPresenterTest {

    /**
     * TODO : task
     *
     * Create tests for {@link ru.gdgkazan.githubmvp.screen.repositories.RepositoriesPresenter}
     *
     * Your test cases must have at least 3 tests
     */

    private RepositoriesView mView;
    private RepositoriesPresenter presenter;

    @Before
    public void setUp(){
        LifecycleHandler mLifecycleHandler = new MockLifecycleHandler();
        mView = Mockito.mock(RepositoriesView.class);
        presenter = new RepositoriesPresenter(mLifecycleHandler, mView);
    }

    @Test
    public void created(){

        assertNotNull(presenter);

    }

    @Test
    public void onItemClick(){

        Repository repository = Mockito.mock(Repository.class);
        presenter.onItemClick(repository);


        Mockito.verify(mView).showCommits(repository);

    }


   @Test
    public void initSuccess(){

       List<Repository> repositories = getTestRepositories();
        RepositoryProvider.setGithubRepository(new WalkTroughTestRepository(true, repositories));

        presenter.init();

        Mockito.verify(mView).showRepositories(repositories);

    }

    @Test
    public void initError(){

        RepositoryProvider.setGithubRepository(new WalkTroughTestRepository(false));

        presenter.init();

        Mockito.verify(mView).showError();

    }

    private  List<Repository> getTestRepositories(){

        Repository mRepository = new Repository();
        List<Repository> repositories = new ArrayList<>();
        repositories.add(mRepository);

        return repositories;

    }

    
    private class WalkTroughTestRepository extends TestGithubRepository {

        private boolean mResult;
        private List<Repository> mRepositories;

        public WalkTroughTestRepository(boolean result) {
            mResult = result;


        }

        public WalkTroughTestRepository(boolean result, List<Repository> repositories) {
            mResult       = result;
            mRepositories = repositories;
        }



        @NonNull
        @Override
        public Observable<List<Repository>> repositories() {

            if (mResult == true) {
                return Observable.just(mRepositories);
            }

            return Observable.error(new IOException());
        }

    }

}
