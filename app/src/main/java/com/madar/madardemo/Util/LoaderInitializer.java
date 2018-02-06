package com.madar.madardemo.Util;

import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;


/**
 * Created by Ahmed on 9/17/2016.
 */
public class LoaderInitializer {

    public static <T> void initLoader(final int loaderId, final Bundle args,
                                      final LoaderManager loaderManager,
                                      final LoaderManager.LoaderCallbacks callbacks)
    {
        try {
            if(loaderManager != null){
                final Loader<T> loader = loaderManager.getLoader(loaderId);

                if (loader != null) {
                    loaderManager.restartLoader(loaderId, args, callbacks).forceLoad();
                }

                else {
                    loaderManager.initLoader(loaderId, args, callbacks).forceLoad();
                }
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
