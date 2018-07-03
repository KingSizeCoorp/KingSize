package com.movinghead333.kingsize.utilities;

import android.app.Application;
import android.content.Context;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.KingSizeLocalDatabase;
import com.movinghead333.kingsize.data.network.KingSizeNetworkDataSource;
import com.movinghead333.kingsize.ui.game.choosedeckactivity.ChooseDeckViewModelFactory;
import com.movinghead333.kingsize.ui.game.gamescreenactivity.GameScreenViewModelFactory;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsViewModelFactory;
import com.movinghead333.kingsize.ui.mydecks.changercardactivity.ChangeCardViewModelFactory;
import com.movinghead333.kingsize.ui.mydecks.showcardindeckactivity.ShowCardInDeckViewModelFactory;
import com.movinghead333.kingsize.ui.mydecks.showdecks.ShowMyDecksViewModelFactory;
import com.movinghead333.kingsize.ui.mydecks.showsingledeckactivity.ShowSingleDeckViewModelFactory;

public class InjectorUtils {

    public static KingSizeRepository provideRepository(Context context){
        // get database
        KingSizeLocalDatabase database = KingSizeLocalDatabase.getDatabase(context.getApplicationContext());

        //get available threads
        AppExecutors executors = AppExecutors.getInstance();

        // get network data source
        KingSizeNetworkDataSource networkDataSource = KingSizeNetworkDataSource.getsInstance(context, executors);

        return KingSizeRepository.getsInstance(database.cardDao(), database.cardDeckDao(),
                database.cardInCardDeckRelationDao(), networkDataSource, executors);
    }

    public static ShowMyCardsViewModelFactory provideShowMyCardsViewModelFactory(Context context){
        KingSizeRepository repository = provideRepository(context.getApplicationContext());
        return new ShowMyCardsViewModelFactory(repository);
    }

    public static ShowMyDecksViewModelFactory provideShowMyDecksViewModelFactory(Context context){
        KingSizeRepository repository = provideRepository(context);
        return new ShowMyDecksViewModelFactory(repository);
    }

    public static ShowSingleDeckViewModelFactory provideShowSingleDeckViewModelFactory(Context context, long id){
        KingSizeRepository repository = provideRepository(context);
        return new ShowSingleDeckViewModelFactory(repository, id);
    }

    public static ShowCardInDeckViewModelFactory provideShowCardInDeckViewModelFactory(Context context){
        KingSizeRepository repository = provideRepository(context);
        return new ShowCardInDeckViewModelFactory(repository);
    }

    public static ChangeCardViewModelFactory provideChangeCardViewModelFactory(Context context,
                                                                               Application application,
                                                                               long deckId){
        KingSizeRepository repository = provideRepository(context);
        return new ChangeCardViewModelFactory(repository, application, deckId);
    }

    public static ChooseDeckViewModelFactory provideChooseDeckViewModelFactory(Context context){
        KingSizeRepository repository = provideRepository(context);
        return new ChooseDeckViewModelFactory(repository);
    }

    public  static GameScreenViewModelFactory provideGameScreenViewModelFactory(Context context,
        Application application){
        KingSizeRepository repository = provideRepository(context);
        return new GameScreenViewModelFactory(repository, application);
    }
}
