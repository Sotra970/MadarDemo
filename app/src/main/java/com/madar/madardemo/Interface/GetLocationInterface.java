package com.madar.madardemo.Interface;

import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;

/**
 * Created by sotra on 10/8/2017.
 */

public interface GetLocationInterface {
    void onFetchLocationFinish(AddressDetailsResponse.AddressDetailsData addressDetailsData);

}
