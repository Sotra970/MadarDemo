package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ResponseItem;

/**
 * Created by Ahmed on 8/31/2017.
 */

public class UserRegResponse {

    @SerializedName("verificationCode")
    private ResCode verificationCode;

    @SerializedName("status")
    private ResponseItem responseItem;

    public ResCode getVerificationCode() {
        return verificationCode;
    }

    public void setVerificationCode(ResCode verificationCode) {
        this.verificationCode = verificationCode;
    }

    public ResponseItem getResponseItem() {
        return responseItem;
    }

    public void setResponseItem(ResponseItem responseItem) {
        this.responseItem = responseItem;
    }

    class ResCode{
        String Code  ;

        public String getCode() {
            return Code;
        }

        public void setCode(String code) {
            Code = code;
        }
    }
}
