package com.movinghead333.kingsize.utilities;

import android.content.Context;

import com.movinghead333.kingsize.AppExecutors;
import com.movinghead333.kingsize.data.KingSizeRepository;
import com.movinghead333.kingsize.data.database.KingSizeLocalDatabase;
import com.movinghead333.kingsize.data.network.KingSizeNetworkDataSource;
import com.movinghead333.kingsize.ui.mycards.showmycards.ShowMyCardsViewModelFactory;

public class InjectorUtils {

    public static KingSizeRepository provideRepository(Context context){
        // get database
        KingSizeLocalDatabase database = KingSizeLocalDatabase.getDatabase(context.getApplicationContext());

        //get available threads
        AppExecutors executors = AppExecutors.getInstance();

        // get network data source
        KingSizeNetworkDataSource networkDataSource = KingSizeNetworkDataSource.getsInstance(context, executors);

        return KingSizeRepository.getsInstance(database.cardDao(), networkDataSource, executors);
    }

    public static ShowMyCardsViewModelFactory provideShowMyCardsViewModelFactory(Context context){
        KingSizeRepository repository = provideRepository(context.getApplicationContext());
        return new ShowMyCardsViewModelFactory(repository);
    }
}
