package com.madar.madardemo.Service;


import com.madar.madardemo.Model.AddOrderResponseModel;
import com.madar.madardemo.Model.BankAccountInfo;
import com.madar.madardemo.Model.FavLocationModel;
import com.madar.madardemo.Model.OrderModel;
import com.madar.madardemo.Model.ProfileItem;
import com.madar.madardemo.Model.ResponseItem;
import com.madar.madardemo.Model.ServerResponse.AddBankResponse;
import com.madar.madardemo.Model.ServerResponse.AddressDetailsResponse;
import com.madar.madardemo.Model.ServerResponse.AvDatesResponse;
import com.madar.madardemo.Model.ServerResponse.BankListResponse;
import com.madar.madardemo.Model.ServerResponse.CityListResponse;
import com.madar.madardemo.Model.ServerResponse.ConfirmationResponse;
import com.madar.madardemo.Model.ServerResponse.CountryListResponse;
import com.madar.madardemo.Model.ServerResponse.DaySummaryResponse;
import com.madar.madardemo.Model.ServerResponse.DeleteNotificationResponse;
import com.madar.madardemo.Model.ServerResponse.FavLocationResponse;
import com.madar.madardemo.Model.ServerResponse.ForgetResponse;
import com.madar.madardemo.Model.ServerResponse.GetNotificationResponse;
import com.madar.madardemo.Model.ServerResponse.GetOrderCasesResponse;
import com.madar.madardemo.Model.ServerResponse.MapLinkFetchResponse;
import com.madar.madardemo.Model.ServerResponse.PriceResponse;
import com.madar.madardemo.Model.ServerResponse.ReceiptItemResponse;
import com.madar.madardemo.Model.ServerResponse.TransactionInfoResponse;
import com.madar.madardemo.Model.ServerResponse.UpdateProfileResponse;
import com.madar.madardemo.Model.ServerResponse.UserLoginResponse;
import com.madar.madardemo.Model.ServerResponse.UserOrdersResponse;
import com.madar.madardemo.Model.ServerResponse.UserRegResponse;
import com.madar.madardemo.Model.ServerResponse.WireTransferSummaryResponse;
import com.madar.madardemo.Model.ServerResponse.ShowBankListResponse;
import com.madar.madardemo.Model.isFoundModel;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by Ahmed on 8/29/2017.
 */

public interface ApiInterface {

    @FormUrlEncoded
    @POST("Api.php")
    Call<UserLoginResponse> login(
            @Field("Request") String req,
            @Field("UserName") String userName,
            @Field("Password") String password
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<UserLoginResponse> sociallogin(
            @Field("Request") String req,
            @Field("FB_UID") String FB_UID,
            @Field("Twitter_UID") String Twitter_UID,
            @Field("Google_UID") String Google_UID
    );

    @POST("/Api/Api.php")
    Call<UserRegResponse> createAccount(@Body ProfileItem profileItem) ;

    @GET("Api.php?Request=Countries_Parent")
    Call<CountryListResponse> getCountries();


     @GET("Api.php?Request=Countries")
    Call<CityListResponse> getAvailableCities();

    @GET("Api.php?Request=Countries")
    Call<CityListResponse> getAvailableCities(
            @Query("ID") String CountryID
    );

    @GET("Api.php?Request=GetAvailableDates")
    Call<AvDatesResponse> getAvailableDates();




    @GET("Api.php?Request=UAddress")
    Call<FavLocationResponse> getUserFavsLocation(
            @Query("Secure") String Secure

    );

    @POST("Api.php?")
    Call<FavLocationResponse> AddUserFavsLocation(
            @Body FavLocationModel favLocationModel

            );



    @GET("Api.php?Request=LinkFetch")
    Call<MapLinkFetchResponse> mapLinkFetch(
            @Query("Address") String link

    );

    @GET("Api.php?Request=AddressDetail")
    Call<AddressDetailsResponse> getAddressDetails(
            @Query("LatLong") String LatLong

    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<ConfirmationResponse> check_code(
            @Field("Request") String req,
            @Field("ValidCode") String ValidCode,
            @Field("Secure") String Secure
    );
   @FormUrlEncoded
    @POST("Api.php")
    Call<ConfirmationResponse> resend_code(
           @Field("Request") String req,
           @Field("Secure") String Secure
   );

    @FormUrlEncoded
    @POST("Api.php")
    Call<ConfirmationResponse> resend_code_phone(
            @Field("Request") String req,
            @Field("Secure") String Secure,
            @Field("Phone") String Phone,
            @Field("Type") String Type
    );


    @POST("Api.php")
    Call<AddOrderResponseModel> add_order(
            @Body OrderModel orderModel
            );


    @GET("Api.php?Request=getUserOrder")
    Call<UserOrdersResponse> getOrderByID(
            @Query("Order_id") String Order_id,
            @Query("Secure") String Secure

    );


    //////////////////////////////
    @POST("/Api/Api.php?AddUBank")
    Call<ResponseItem> saveClientBankAccount(@Body BankAccountInfo bankAccountInfo);

    @GET("Api.php?Request=Banks")
    Call<BankListResponse> getAvailableBanks(
            @Query("Country") String  Country
    );

    @POST("/Api/Api.php?ResendVerif")
    Call<ResponseItem> resendVerificationCode(@Field("Secure") String secret);

    @GET("/Api/Api.php?getComp")
    Call<ResponseItem> getCompanyInfo(@Field("Secure") String secret);

    @FormUrlEncoded
    @POST("Api.php")
    Call<ReceiptItemResponse> getInvoices(
            @Field("Request") String request,
            @Field("Secure") String secure
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<WireTransferSummaryResponse> getWireTransfers(
            @Field("Request") String req,
            @Field("Secure") String secure
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<TransactionInfoResponse> getTransactionInfo(
            @Field("Request") String request,
            @Field("Secure") String secure,
            @Field("Transaction_No") String transactionId
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<UserOrdersResponse> getUserOrderInfo(
            @Field("Request") String request,
            @Field("Secure") String secure
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<DaySummaryResponse> getDaySummary(
            @Field("Request") String request,
            @Field("Secure") String secure,
            @Field("Date") String date
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<AddBankResponse> saveBankAccount(
            @Field("Request") String request,
            @Field("Secure") String secure,
            @Field("AccountHolderName") String accountHolderName,
            @Field("BankID") String bankId,
            @Field("Account_Number") String accountNumber
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<GetOrderCasesResponse> getOrderCases(
            @Field("Request") String req
    );

    @FormUrlEncoded
    @POST("Api.php")
    Call<GetNotificationResponse> getNotifications(
            @Field("Request") String req,
            @Field("Secret") String secure
    );


    @GET("/Api/Api.php?Request=GetPrice")
    Call<PriceResponse> getPrice(
            @Query("From") int From,
            @Query("To") String To,
            @Query("Size") int Size,
            @Query("Car_Type") int Car_Type,
            @Query("Secret") String Secret
    );

    @POST("Api.php")
    Call<UpdateProfileResponse> updateProfile(@Body ProfileItem profileItem);


    @FormUrlEncoded
    @POST("Api.php")
    Call<DeleteNotificationResponse> deleteNotification(
            @Field("Request") String request,
            @Field("Secret") String secret,
            @Field("ID") String id
    );

    @GET("Api.php?Request=ForgetPass")
    Call<ForgetResponse> forgetPass(
            @Query("Email") String secret
    );



    @GET("Api.php?Request=RemoveUserAddress")
    Call<ConfirmationResponse> deleteFav(
            @Query("Secure") String secret,
            @Query("ID") String id
    );

    @GET("Api.php?Request=DeleteUBank")
    Call<ConfirmationResponse> deleteBank(
            @Query("Secure") String secret,
            @Query("ID") String id
    );


    @GET("Api.php?Request=RemoveUserAddress")
    Call<ConfirmationResponse> RemoveRequest(
            @Query("Secret") String secret,
            @Query("Order_ID") String id
    );

    @GET("Api.php?Request=UBank")
    Call<ShowBankListResponse> showBanks(
            @Query("Secure") String secret
    );


    @GET("Api.php?Request=ISFound")
    Call<isFoundModel> iSFound(
            @Query("Secure") String secret
    );




}
