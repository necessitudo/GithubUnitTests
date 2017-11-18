package ru.arturvasilov.githubmvp.screen.walkthrough;

import android.support.annotation.NonNull;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import ru.arturvasilov.githubmvp.screen.auth.AuthPresenterTest;
import ru.arturvasilov.githubmvp.test.TestGithubRepository;
import ru.arturvasilov.githubmvp.test.TestKeyValueStorage;
import ru.arturvasilov.rxloader.RxSchedulers;
import ru.gdgkazan.githubmvp.R;
import ru.gdgkazan.githubmvp.api.ApiFactory;
import ru.gdgkazan.githubmvp.content.Authorization;
import ru.gdgkazan.githubmvp.content.Benefit;
import ru.gdgkazan.githubmvp.content.Repository;
import ru.gdgkazan.githubmvp.repository.KeyValueStorage;
import ru.gdgkazan.githubmvp.repository.RepositoryProvider;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter;
import ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughView;
import ru.gdgkazan.githubmvp.utils.AuthorizationUtils;
import rx.Observable;

import static junit.framework.Assert.assertNotNull;

/**
 * @author Artur Vasilov
 */
@RunWith(JUnit4.class)
public class WalkthroughPresenterTest {

    private WalkthroughView mWalkthroughView;
    private  WalkthroughPresenter presenter;

    @Before
    public void setUp(){
        mWalkthroughView = Mockito.mock(WalkthroughView.class);
        presenter = new WalkthroughPresenter(mWalkthroughView);

    }

    @Test
    public void testCreated(){

        assertNotNull(presenter);

    }

    @Test
    public void testInitPassed(){

        KeyValueStorage storage = new TokenKeyValueStorage(true);
        RepositoryProvider.setKeyValueStorage(storage);

        presenter.init();
        Mockito.verify(mWalkthroughView).startAuth();

    }


    @Test
    public void testInitNotPassed(){

        KeyValueStorage storage = new TokenKeyValueStorage(false);
        RepositoryProvider.setKeyValueStorage(storage);

        presenter.init();

        Mockito.verify(mWalkthroughView).showBenefits(presenter.getBenefits());
        Mockito.verify(mWalkthroughView).showActionButtonText(R.string.next_uppercase);

    }


    @Test
    public void onActionButtonClickLastBenefit(){

        KeyValueStorage storage = new TokenKeyValueStorage(true);
        RepositoryProvider.setKeyValueStorage(storage);

        presenter.onActionButtonClick();
        presenter.onActionButtonClick();
        presenter.onActionButtonClick();

        Mockito.verify(mWalkthroughView).startAuth();

    }

    @Test
    public void onActionButtonClickNoLastBenefit(){

        KeyValueStorage storage = new TokenKeyValueStorage(true);
        RepositoryProvider.setKeyValueStorage(storage);

        presenter.onActionButtonClick();

        Mockito.verify(mWalkthroughView).scrollToNextBenefit();

    }

    @Test
    public void onPageChangedTrue(){

        presenter.onPageChanged(1, true);
        Assert.assertEquals(1, presenter.getCurrentItem());

    }

    @Test
    public void onPageChangedFalse(){

        presenter.onPageChanged(1, false);
        Assert.assertEquals(0, presenter.getCurrentItem());

    }



    private class TokenKeyValueStorage extends TestKeyValueStorage {

        private  boolean mResult;

        public  TokenKeyValueStorage(boolean result){
            mResult =  result;

        }

        @Override
        public void saveWalkthroughPassed() {
            super.saveWalkthroughPassed();
        }

        @Override
        public boolean isWalkthroughPassed() {
            return mResult;
        }


    }





    /**
     * TODO : task
     *
     * Create tests for {@link ru.gdgkazan.githubmvp.screen.walkthrough.WalkthroughPresenter}
     *
     * Your test cases must have at least 6 small tests and 1 large test (for some interaction scenario for this screen)
     */

}
