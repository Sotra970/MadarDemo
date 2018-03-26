package com.madar.madardemo.Model;

import android.util.Log;

import com.google.gson.annotations.SerializedName;
import com.madar.madardemo.Util.TimeUtils;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by Ahmed on 10/6/2017.
 */

public class OrderItem  implements Serializable{

    String Title ;
    String Time_From ;
    String Time_To ;

    @SerializedName("ID")
    String ID;

    @SerializedName("Deliver_Place")
    String Deliver_Place;
    @SerializedName("Recipient_Place")
    String Recipient_Place;

    @SerializedName("From_City_Name")
    String From_City_Name;

    @SerializedName("From_City_No")
    String From_City_No;

    @SerializedName("From_District")
    String From_District;

    @SerializedName("To_City_Name")
    String To_City_Name;

    @SerializedName("To_City_No")
    String To_City_No;

    String Place_Detail;

    @SerializedName("To_District")
    String To_District;

    @SerializedName("Customer_Name")
    String Customer_Name;
    @SerializedName("Customer_ID")
    String Customer_ID;

    @SerializedName("Shop_Mobile_Num")
    String Sender_Num;// sender
    String Mobile_Num;// reciver
    String Receiver_Name;

    @SerializedName("Date")
    String Date;

    String Size;
    String Ship_Price;
    String Good_Price ;
    String Payment_Method_ID;
    String Payment_Method_Name;
    String Ship_Type;

    @SerializedName("Case_ID")
    String Case_ID;

    @SerializedName("Case_Name")
    String Case_Name;

    private String Case_Description;

    @SerializedName("Case_Img")
    private String Case_Img;

    private String Contact_Before_Arrive;
    private String Pack_Good_State;
    private String Packages_Numbers;

    @SerializedName("Type")
    String Type;

    ArrayList<PicModel> Pics;

    private String parsedDate;

    private static final String typeConsts[] = new String[]{
            "0", // receiver
            "1" // sender
    };
    @SerializedName("Note")
    private String note;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getDeliver_Place() {
        return Deliver_Place;
    }


    public void setDeliver_Place(String deliver_Place) {
        Deliver_Place = deliver_Place;
    }

    public String getFrom_City_Name() {
        return From_City_Name;
    }

    public void setFrom_City_Name(String from_City_Name) {
        From_City_Name = from_City_Name;
    }

    public String getFrom_City_No() {
        return From_City_No;
    }

    public void setFrom_City_No(String from_City_No) {
        From_City_No = from_City_No;
    }

    public String getFrom_District() {
        return From_District;
    }

    public void setFrom_District(String from_District) {
        From_District = from_District;
    }

    public String getTo_City_Name() {
        return To_City_Name;
    }

    public void setTo_City_Name(String to_City_Name) {
        To_City_Name = to_City_Name;
    }

    public String getTo_City_No() {
        return To_City_No;
    }

    public void setTo_City_No(String to_City_No) {
        To_City_No = to_City_No;
    }

    public String getPlace_Detail() {
        return Place_Detail;
    }

    public void setPlace_Detail(String place_Detail) {
        Place_Detail = place_Detail;
    }

    public String getTo_District() {
        return To_District;
    }

    public void setTo_District(String to_District) {
        To_District = to_District;
    }

    public String getCustomer_Name() {
        return Customer_Name;
    }

    public void setCustomer_Name(String customer_Name) {
        Customer_Name = customer_Name;
    }

    public String getMobile_Num() {
        return Mobile_Num;
    }

    public void setMobile_Num(String mobile_Num) {
        Mobile_Num = mobile_Num;
    }

    public String getReceiver_Name() {
        return Receiver_Name;
    }

    public void setReceiver_Name(String receiver_Name) {
        Receiver_Name = receiver_Name;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        this.Date = date;
    }

    public String getSize() {
        return Size;
    }

    public void setSize(String size) {
        Size = size;
    }

    public String getShip_Price() {
        return Ship_Price;
    }

    public void setShip_Price(String ship_Price) {
        Ship_Price = ship_Price;
    }

    public String getGood_Price() {
        return Good_Price;
    }

    public void setGood_Price(String good_Price) {
        Good_Price = good_Price;
    }

    public String getPayment_Method_ID() {
        return Payment_Method_ID;
    }

    public void setPayment_Method_ID(String payment_Method_ID) {
        Payment_Method_ID = payment_Method_ID;
    }

    public String getPayment_Method_Name() {
        return Payment_Method_Name;
    }

    public void setPayment_Method_Name(String payment_Method_Name) {
        Payment_Method_Name = payment_Method_Name;
    }

    public String getShip_Type() {
        return Ship_Type;
    }

    public void setShip_Type(String ship_Type) {
        Ship_Type = ship_Type;
    }

    public String getCase_ID() {
        return Case_ID;
    }

    public void setCase_ID(String case_ID) {
        Case_ID = case_ID;
    }

    public String getCase_Name() {
        return Case_Name;
    }

    public void setCase_Name(String case_Name) {
        Case_Name = case_Name;
    }

    public String getCase_Description() {
        return Case_Description;
    }

    public void setCase_Description(String case_Description) {
        Case_Description = case_Description;
    }

    public String getCase_Img() {
        return Case_Img;
    }

    public void setCase_Img(String case_Img) {
        Case_Img = case_Img;
    }

    public String getContact_Before_Arrive() {
        return Contact_Before_Arrive;
    }

    public void setContact_Before_Arrive(String contact_Before_Arrive) {
        Contact_Before_Arrive = contact_Before_Arrive;
    }

    public String getPack_Good_State() {
        return Pack_Good_State;
    }

    public void setPack_Good_State(String pack_Good_State) {
        Pack_Good_State = pack_Good_State;
    }

    public String getPackages_Numbers() {
        return Packages_Numbers;
    }

    public void setPackages_Numbers(String packages_Numbers) {
        Packages_Numbers = packages_Numbers;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public ArrayList<PicModel> getPics() {
        return Pics;
    }

    public void setPics(ArrayList<PicModel> pics) {
        Pics = pics;
    }

    public String getParsedDate() {
        return parsedDate;
    }

    public boolean isReceiving(){
        return Type != null && Type.equals(typeConsts[0]);
    }

    public boolean isDelivering(){
        return !isReceiving();
    }

    public void initDate(){
        Log.e("PackageDetails" , "ORDER MODEL " + "initDate : " +  Date+"") ;

        if(Date != null && !Date.isEmpty()){

            parsedDate =
                    TimeUtils.getDayOfMonth(
                            Date,
                            TimeUtils.FORMAT_DATE_WITH_TIME,
                            TimeUtils.LANGUAGE_EN)
                            + " " +
                    TimeUtils.getMonthOfYear(
                            Date,
                            TimeUtils.FORMAT_DATE_WITH_TIME,
                            TimeUtils.LANGUAGE_EN,
                            TimeUtils.LENGTH_SHORT
                    );
        }
    }

    public String getTitle() {
        return Title;
    }

    public String getTime_From() {
        return Time_From;
    }

    public String getTime_To() {
        return Time_To;
    }

    public static String[] getTypeConsts() {
        return typeConsts;
    }

    public String getRecipient_Place() {
        return Recipient_Place;
    }

    public void setRecipient_Place(String recipient_Place) {
        Recipient_Place = recipient_Place;
    }

    public String getSender_Num() {
        return Sender_Num;
    }

    public void setSender_Num(String sender_Num) {
        Sender_Num = sender_Num;
    }

    public String getCustomer_ID() {
        return Customer_ID;
    }

    public void setCustomer_ID(String customer_ID) {
        Customer_ID = customer_ID;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    @Override
    public String toString() {
        return "OrderItem{" +
                "Title='" + Title + '\'' +
                ", Time_From='" + Time_From + '\'' +
                ", Time_To='" + Time_To + '\'' +
                ", ID='" + ID + '\'' +
                ", Deliver_Place='" + Deliver_Place + '\'' +
                ", Recipient_Place='" + Recipient_Place + '\'' +
                ", From_City_Name='" + From_City_Name + '\'' +
                ", From_City_No='" + From_City_No + '\'' +
                ", From_District='" + From_District + '\'' +
                ", To_City_Name='" + To_City_Name + '\'' +
                ", To_City_No='" + To_City_No + '\'' +
                ", Place_Detail='" + Place_Detail + '\'' +
                ", To_District='" + To_District + '\'' +
                ", Customer_Name='" + Customer_Name + '\'' +
                ", Customer_ID='" + Customer_ID + '\'' +
                ", Sender_Num='" + Sender_Num + '\'' +
                ", Mobile_Num='" + Mobile_Num + '\'' +
                ", Receiver_Name='" + Receiver_Name + '\'' +
                ", Date='" + Date + '\'' +
                ", Size='" + Size + '\'' +
                ", Ship_Price='" + Ship_Price + '\'' +
                ", Good_Price='" + Good_Price + '\'' +
                ", Payment_Method_ID='" + Payment_Method_ID + '\'' +
                ", Payment_Method_Name='" + Payment_Method_Name + '\'' +
                ", Ship_Type='" + Ship_Type + '\'' +
                ", Case_ID='" + Case_ID + '\'' +
                ", Case_Name='" + Case_Name + '\'' +
                ", Case_Description='" + Case_Description + '\'' +
                ", Case_Img='" + Case_Img + '\'' +
                ", Contact_Before_Arrive='" + Contact_Before_Arrive + '\'' +
                ", Pack_Good_State='" + Pack_Good_State + '\'' +
                ", Packages_Numbers='" + Packages_Numbers + '\'' +
                ", Type='" + Type + '\'' +
                ", Pics=" + Pics +
                ", parsedDate='" + parsedDate + '\'' +
                ", note='" + note + '\'' +
                '}';
    }
}
