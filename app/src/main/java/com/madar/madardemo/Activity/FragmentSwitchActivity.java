package com.madar.madardemo.Activity;

import android.Manifest;
import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.madar.madardemo.AppManger.Config;
import com.madar.madardemo.Fragment.AddOrder.FavLocationsFragment;
import com.madar.madardemo.Fragment.ConnectionFailedFragment;
import com.madar.madardemo.Fragment.ProfileFragment;
import com.madar.madardemo.Interface.NoConn;
import com.madar.madardemo.Model.ServerResponse.ImgsResponse;
import com.madar.madardemo.Model.ServerResponse.ProfileImgModel;
import com.madar.madardemo.Model.ServerResponse.ProfileImgsResponse;
import com.madar.madardemo.R;
import com.madar.madardemo.Service.Injector;
import com.madar.madardemo.Service.ProgressRequestBody;
import com.yalantis.ucrop.UCrop;
import com.yalantis.ucrop.UCropActivity;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import droidninja.filepicker.FilePickerBuilder;
import droidninja.filepicker.FilePickerConst;
import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.madar.madardemo.AppManger.Config.REQUEST_IMAGE_CAPTURE;


/**
 * Created by Ahmed on 9/16/2017.
 */

public abstract class FragmentSwitchActivity extends LocalizedActivity {

    private static final int ENTER_ANIM = R.anim.fade_in;
    private static final int LEAVE_ANIM = R.anim.fade_out;
    private ArrayList<String> photoPaths = new ArrayList<>();
    private ArrayList<File> photos_result_uri_array  = new ArrayList<>();
    private ArrayList<ProfileImgModel> profile_upload_img_result  = new ArrayList<>();
    private ArrayList<String> upload_img_result_ids  = new ArrayList<>();



    public void showFragment(Fragment fragment , boolean back){
        if(fragment != null){

            if (back){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(ENTER_ANIM , LEAVE_ANIM , ENTER_ANIM , LEAVE_ANIM)
                        .replace(R.id.main_activity_fragment_place_holder, fragment)
                        .addToBackStack(null)
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(ENTER_ANIM , LEAVE_ANIM , ENTER_ANIM , LEAVE_ANIM)
                        .replace(R.id.main_activity_fragment_place_holder, fragment)
                        .commit();
            }
        }
    }


    public void showFragment(Fragment fragment , boolean back , int anim_enter  , int exit_anim  ){
        if(fragment != null){

            if (back){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(anim_enter , exit_anim)
                        .replace(R.id.main_activity_fragment_place_holder, fragment)
                        .addToBackStack(null)
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(ENTER_ANIM , LEAVE_ANIM)
                        .replace(R.id.main_activity_fragment_place_holder, fragment)
                        .commit();
            }
        }
    }


    public void showFragment(Fragment fragment , String tag  ){
        if(fragment != null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .setCustomAnimations(ENTER_ANIM , LEAVE_ANIM)
                    .replace(R.id.main_activity_fragment_place_holder, fragment)
                    .addToBackStack(tag)
                    .commit();
        }
    }

    public void showFragmentNoAnim(Fragment fragment, String tag, boolean back){
        if(fragment != null){
            if (back){
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(0 , 0)
                        .replace(R.id.main_activity_fragment_place_holder, fragment)
                        .addToBackStack(tag)
                        .commit();
            }else {
                getSupportFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(0 , 0)
                        .replace(R.id.main_activity_fragment_place_holder, fragment)
                        .commit();
            }
        }
    }



    public void showNoConn(NoConn noConn) {
        try{
            showLoading(false);
        }catch (Exception e){

        }
        ConnectionFailedFragment connectionFailedFragment = ConnectionFailedFragment.getInstance() ;
        connectionFailedFragment.setNoConn(noConn);
        showFragment(connectionFailedFragment , "no_conn_frag");

    }


    View progrssView ;
    View container ;


    public void initLoading(View progrssView, View container){
        this.progrssView = progrssView ;
        this.container = container ;
    }

    public void showLoading(final boolean show) {
        try {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            container.setVisibility(show ? View.GONE : View.VISIBLE);
            container.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    container.setVisibility(show ? View.GONE : View.VISIBLE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    container.setVisibility(show ? View.GONE : View.VISIBLE);

                }
            });

            progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
            progrssView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter()
            {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
                }

                @Override
                public void onAnimationCancel(Animator animation) {
                    progrssView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void clearStack(){
        try {

            for (int i=0 ; i<(getSupportFragmentManager().getBackStackEntryCount() ); i++){
                getSupportFragmentManager().popBackStack();
            }
        }catch (Exception e){
            Log.e("baseFragment" , "clearStack ex" +e.toString());
        }
    }


    public void clearStack(ClearStack clearStack){
        try {

            for (int i=0 ; i<(getSupportFragmentManager().getBackStackEntryCount() ); i++){
                getSupportFragmentManager().popBackStack();
            }
            clearStack.onClearFinish();
        }catch (Exception e){
            Log.e("baseFragment" , "clearStack ex" +e.toString());
            clearStack.onClearFinish();
        }
    }



    public  interface  ClearStack{
        void onClearFinish();
    }


    public Geocoder getGeocoder(){
       return new Geocoder(this,  new Locale("ar"));
    }



    public  void pick_image_permission() {
        // Here, thisActivity is the current activity
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.MY_PERMISSIONS_REQUEST_STORAGE);
        }else {
            pick_img();
        }
    }

    boolean from_profile = false ;
    public  void pick_profile_image_permission() {
        // Here, thisActivity is the current activity
        from_profile = true ;
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, Config.MY_PERMISSIONS_REQUEST_STORAGE);
        }else {
            pick_profile_img();
        }
    }


    public  void capture_img_permission() {
        // Here, thisActivity is the current activity
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ) {
//            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, Config.MY_PERMISSIONS_REQUEST_CAMERA);
//        }else {
//            capture_img();
//        }

        capture_img();
    }




    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Config.MY_PERMISSIONS_REQUEST_STORAGE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    pick_img();
                } else {
                    Toast.makeText(this," Storage permission id needed  to get into your images ", Toast.LENGTH_SHORT).show();
                }
                return;
            }

            case Config.MY_PERMISSIONS_REQUEST_CAMERA: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    capture_img_permission();
                } else {
                    Toast.makeText(this," Camera permission id needed  to get into your images ", Toast.LENGTH_SHORT).show();
                }
                return;
            }

        }
    }





    private void pick_img(){
        photoPaths = new ArrayList<>() ;
        photos_result_uri_array = new ArrayList<>() ;
        FilePickerBuilder.getInstance().setMaxCount(5)
                .setSelectedFiles(photoPaths)
                .setActivityTheme(R.style.file_picker_theme)
                .pickPhoto(this);
    }

    private void pick_profile_img(){
        photoPaths = new ArrayList<>() ;
        photos_result_uri_array = new ArrayList<>() ;
        FilePickerBuilder.getInstance().setMaxCount(1)
                .setSelectedFiles(photoPaths)
                .setActivityTheme(R.style.file_picker_theme)
                .pickPhoto(this);
    }


    String photoURIPath = "" ;
    private void capture_img(){
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {


            try {
                File file = createImageFile() ;
                photoURIPath = file.getPath();
                Uri photoURI =
                        FileProvider.getUriForFile(
                                getApplicationContext(), getApplicationContext().getPackageName() + ".provider"
                        , file);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /// select from gallery section
        if (requestCode == FilePickerConst.REQUEST_CODE_PHOTO && resultCode == this.RESULT_OK && data!=null) {

            photoPaths = new ArrayList<>();
            photoPaths.addAll(data.getStringArrayListExtra(FilePickerConst.KEY_SELECTED_MEDIA));
            add_to_u_crop();


        }
        else if (requestCode == UCrop.REQUEST_CROP) {

            if (resultCode == Activity.RESULT_OK) {
                handleCropResult(data);
            }
            else if (resultCode == UCrop.RESULT_ERROR) {
                handleCropError(data);
            }
        }else  if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            photoPaths = new ArrayList<>();
            photoPaths.add(photoURIPath) ;
            add_to_u_crop();
        }

    }


    int current = -1 ;
    private void add_to_u_crop(){
        if (photoPaths.isEmpty())
         return;
        current++ ;

        if (current >= photoPaths.size()){
            // start upload
            current=-1;
            if (from_profile)
                upload_profile();
            else
                upload_Files();
            return;
        }
        startUcrop(photoPaths.get(current));

    }


    int current_body = 1   ;
    private void upload_Files() {

        current_body = 1 ;

        showProgressDialog(getString(R.string.loading));


            ProgressRequestBody.UploadCallbacks  UploadCallbacks = new ProgressRequestBody.UploadCallbacks() {
                @Override
                public void onProgressUpdate(int percentage) {
                    Log.e("OkHttp_upload_exp",percentage +"") ;
                    updateProgressDialogMessage(percentage+" %");

                }

                @Override
                public void onError() {
                    Log.e("OkHttp_upload_exp","error") ;

                }

                @Override
                public void onFinish() {
                    Log.e("OkHttp_upload_exp","finish") ;
                }
            };


            ArrayList< MultipartBody.Part> multipartBodies = new ArrayList<>();
        for (File child : photos_result_uri_array){
            ProgressRequestBody fileBody = new ProgressRequestBody(child, UploadCallbacks , current_body , photos_result_uri_array.size());
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("upload[]", child.getName(), fileBody);
            multipartBodies.add(body) ;
            current_body++ ;
        }
        Call<ImgsResponse> call = Injector.UploadApi().multipleUpload(
          multipartBodies
        );
            call.enqueue(new Callback<ImgsResponse>() {
                @Override
                public void onResponse(Call<ImgsResponse> call,
                                       Response<ImgsResponse> response) {
                    Log.e("OkHttp_upload_exp", "success");
                    if (response.body().getResponseItem().isSuccessful()){
                        upload_img_result_ids = response.body().getData();

                    }
                    hideProgressDialog();
                }

                @Override
                public void onFailure(Call<ImgsResponse> call, Throwable t) {
                    Log.e("OkHttp_upload_exp"," error"+ t.getMessage()+"  " + t.toString());
                    hideProgressDialog();
                    showNoConn(new NoConn() {
                        @Override
                        public void onRetry() {
                            upload_Files();
                        }
                    });


                }
            });
    }


    private void upload_profile() {

        current_body = 1 ;

        showProgressDialog(getString(R.string.loading));


        ProgressRequestBody.UploadCallbacks  UploadCallbacks = new ProgressRequestBody.UploadCallbacks() {
            @Override
            public void onProgressUpdate(int percentage) {
                Log.e("OkHttp_upload_exp",percentage +"") ;
                updateProgressDialogMessage(percentage+" %");

            }

            @Override
            public void onError() {
                Log.e("OkHttp_upload_exp","error") ;

            }

            @Override
            public void onFinish() {
                Log.e("OkHttp_upload_exp","finish") ;
            }
        };


        ArrayList< MultipartBody.Part> multipartBodies = new ArrayList<>();
        for (File child : photos_result_uri_array){
            if (from_profile){
                Log.e("Profile" , "onStartSend") ;
                update_profile_img_src(child);
            }
            ProgressRequestBody fileBody = new ProgressRequestBody(child, UploadCallbacks , current_body , photos_result_uri_array.size());
            MultipartBody.Part body =
                    MultipartBody.Part.createFormData("upload[]", child.getName(), fileBody);
            multipartBodies.add(body) ;
            current_body++ ;
        }
        Call<ProfileImgsResponse> call = Injector.UploadApi().UploadProfile(
                multipartBodies
        );
        call.enqueue(new Callback<ProfileImgsResponse>() {
            @Override
            public void onResponse(Call<ProfileImgsResponse> call,
                                   Response<ProfileImgsResponse> response) {
                Log.e("OkHttp_upload_exp", "success");
                from_profile = false ;
                if (response.body().getResponseItem().isSuccessful()){
                    profile_upload_img_result = response.body().getData();

                }
                hideProgressDialog();
            }

            @Override
            public void onFailure(Call<ProfileImgsResponse> call, Throwable t) {
                Log.e("OkHttp_upload_exp"," error"+ t.getMessage()+"  " + t.toString());
                hideProgressDialog();
                showNoConn(new NoConn() {
                    @Override
                    public void onRetry() {
                        upload_profile();
                    }
                });


            }
        });
    }

    protected void update_profile_img_src(File child) {
       try {
           Log.e("Profile" , "onSend") ;
           Uri photoURI = Uri.fromFile(child);
           Intent intent = new Intent(Config.UPDATE_USER_PROFILE_IMG) ;
           intent.putExtra("img" , photoURI) ;
           LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent) ;
       }catch (Exception e){
           e.printStackTrace();
       }
    }


    private  void startUcrop(final  String path){
        Log.e("path" , path) ;
        UCrop.Options options = new UCrop.Options();
        options.setCompressionQuality(90);
        options.setCompressionFormat(Bitmap.CompressFormat.JPEG);
        options.setAllowedGestures(UCropActivity.SCALE , UCropActivity.NONE , UCropActivity.ALL);
        options.setFreeStyleCropEnabled(false);
        options.setShowCropFrame(true);
        options.setStatusBarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark));
        options.setToolbarColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        options.setToolbarTitle(getString(R.string.edit_img));
        options.setToolbarWidgetColor(ContextCompat.getColor(getApplicationContext(), R.color.app_dark_indigo));
        options.withMaxResultSize(420, 420);


        UCrop.of(Uri.fromFile(new File(path)), Uri.fromFile(create_jpg_uri()))
                .withOptions(options)
                .useSourceImageAspectRatio()
                .withMaxResultSize(420,420)
                .start(this , UCrop.REQUEST_CROP);
    }

    String TAG = "profile_img_upload";
    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    private void handleCropError(@NonNull Intent result) {
        final Throwable cropError = UCrop.getError(result);
        if (cropError != null) {
            Log.e(TAG, "handleCropError: ", cropError);
            Toast.makeText(getApplicationContext(), cropError.getMessage(), Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(getApplicationContext(), R.string.toast_unexpected_error, Toast.LENGTH_SHORT).show();
        }
    }


    private void handleCropResult(@NonNull Intent result) {
        final Uri resultUri = UCrop.getOutput(result);
        if (resultUri != null) {
            photos_result_uri_array.add(new File(resultUri.getPath())) ;
            add_to_u_crop();
        } else {
            Toast.makeText(getApplicationContext(), R.string.toast_cannot_retrieve_cropped_image, Toast.LENGTH_SHORT).show();
        }
    }


    public File create_jpg_uri()  {
//        File outputDir = Environment.getExternalStorageDirectory(); // context being the Activity pointer
        File outputDir = this.getCacheDir(); // context being the Activity pointer
        File outputFile = null;
        try {
            outputFile = File.createTempFile(currentDateFormat(),".jpg" , outputDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return outputFile;
    }


    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );

        // Save a file: path for use with ACTION_VIEW intents
//        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    public static String currentDateFormat(){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd_HH_mm_ss", Locale.ENGLISH);
        String  currentTimeStamp = dateFormat.format(new Date());
        return currentTimeStamp;
    }


    public ArrayList<String> getUpload_img_result_ids() {
        return upload_img_result_ids == null ? new ArrayList<String>() : upload_img_result_ids;
    }

    public ArrayList<ProfileImgModel> getProfile_upload_img_result() {
        return profile_upload_img_result;
    }

    public ArrayList<File> getPhotos_result_uri_array() {
        return photos_result_uri_array;
    }

    private ProgressDialog progressDialog;
    protected void showProgressDialog(String message) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(message);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    protected void hideProgressDialog() {
        if (progressDialog != null) {
            progressDialog.dismiss();
        }
    }
    void updateProgressDialogMessage(String message){
        progressDialog.setMessage(message);
    }






    protected void showLongToast(View view, String stringId) {

        Snackbar.make(view, stringId, Snackbar.LENGTH_LONG).show();
    }

    protected void showLongToast(View view, int stringId) {

        Snackbar.make(view, stringId, Snackbar.LENGTH_LONG).show();

    }

    protected void showLongToast(View view, int stringId, int action, View.OnClickListener listener) {

        Snackbar snack = Snackbar.make(view, stringId, Snackbar.LENGTH_LONG)
                .setAction(action, listener)
                .setActionTextColor(getResources().getColor(R.color.colorPrimary));
        snack.show();

    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Log.e("fragment" , getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName() +"");
//        if (getSupportFragmentManager().getBackStackEntryAt(getSupportFragmentManager().getBackStackEntryCount()-1).getName().equals("no_conn_frag")){
//            Log.e("fragment" , "no_conn_fragment") ;
//        }

    }

    public void show_favs(){
        ///
        FavLocationsFragment favLocationsFragment = new FavLocationsFragment();
//        showFragment(favLocationsFragment  , true , R.anim.activity_slide_up , R.anim.activity_slide_down);
        getSupportFragmentManager()
                .beginTransaction()
                .setCustomAnimations(R.anim.activity_slide_up , R.anim.activity_slide_down , R.anim.activity_slide_up , R.anim.activity_slide_down)
                .replace(R.id.address_fav_content_frame , favLocationsFragment)
                .addToBackStack(null)
                .commit();

    }


}
