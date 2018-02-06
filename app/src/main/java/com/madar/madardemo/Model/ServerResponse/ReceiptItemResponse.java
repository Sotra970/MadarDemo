package com.madar.madardemo.Model.ServerResponse;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Model.ReceiptItem;
import com.madar.madardemo.Model.ResponseItem;
import com.madar.madardemo.Util.TimeUtils;

import java.util.ArrayList;

/**
2 * Created by Ahmed on 10/3/2017.
 */

public class ReceiptItemResponse {

    @SerializedName("status")
    private ResponseItem status;

    @SerializedName("Data")
    private ReceiptData receiptData;

    public ReceiptItemResponse(ResponseItem status, ReceiptData receiptData) {
        this.status = status;
        this.receiptData = receiptData;
    }

    public ResponseItem getStatus() {
        return status;
    }

    public ReceiptData getReceiptData() {
        return receiptData;
    }

    public class ReceiptData {

        @SerializedName("Invoices")
        private ArrayList<ReceiptItem> receiptListItems;


        @SerializedName("Not_Paid")
        private NotPaidData notPaidData;

        @SerializedName("Last_2_Trans")
        private ArrayList<LastTransactionsData> lastTransactionsData;


        public ArrayList<ReceiptItem> getReceiptListItems() {
            return receiptListItems;
        }

        public NotPaidData getNotPaidData() {
            return notPaidData;
        }

        public ArrayList<LastTransactionsData> getLastTransactionsData() {
            return lastTransactionsData;
        }

        public class NotPaidData{
            @SerializedName("Total_Ship_Price")
            private String totalShipPrice;

            @SerializedName("Total_Products_Price")
            private String totalProductsPrice;

            @SerializedName("Total_balance")
            private String totalBalance;

            private NotPaidData(String totalShipPrice, String totalProductsPrice, String totalBalance) {
                this.totalShipPrice = totalShipPrice;
                this.totalProductsPrice = totalProductsPrice;
                this.totalBalance = totalBalance;
            }

            public String getTotalShipPrice() {
                return totalShipPrice;
            }

            public String getTotalProductsPrice() {
                return totalProductsPrice;
            }

            public String getTotalBalance() {
                return totalBalance;
            }
        }

        public class LastTransactionsData {
            String Amount, Date , showDate, showAmount;

            public String getShowAmount() {
                return showAmount;
            }

            public void setShowDate(){
                boolean ok = false;
                if(Date != null && !Date.isEmpty()){
                    String day = TimeUtils.getDayOfMonth(Date, TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN);
                    String month = TimeUtils.getMonthOfYear(Date, TimeUtils.FORMAT_DATE_WITH_TIME, TimeUtils.LANGUAGE_EN, TimeUtils.LENGTH_SHORT);

                    try {
                        this.showDate = day + " " + month;
                        ok = true;
                    }
                    catch (Exception ignored){}
                }

                if(!ok) this.showDate = "";
            }

            public String getShowDate() {
                return showDate;
            }

            public void setShowAmount(){
                boolean ok = false;

                if(Amount != null && !Amount.isEmpty()){
                    int dotIndex = -1;
                    try {
                        dotIndex = Amount.indexOf(".");
                    }
                    catch (Exception e){}

                    if(dotIndex != -1){
                        try {
                            showAmount = Amount.substring(0, dotIndex);
                            ok = true;
                        }
                        catch (Exception e){}
                    }
                }

                if(!ok) showAmount = Amount;
            }
        }
    }


}
