package com.movinghead333.kingsize.utilities;

import android.content.Context;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.KingSizeLocalDatabase;
import com.movinghead333.kingsize.data.network.KingSizeNetworkDataSource;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsViewModelFactory;
import com.movinghead333.kingsize.ui.mydecks.showdecks.ShowMyDecksViewModelFactory;

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
        return  new ShowMyDecksViewModelFactory(repository);
    }
}
