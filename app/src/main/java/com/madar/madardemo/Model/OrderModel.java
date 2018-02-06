package com.madar.madardemo.Model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by sotra on 9/29/2017.
 */

public class OrderModel implements Serializable {

    String Request="AddOrder" ;



    // lang lat
    String Deleviry_Place ;
    //address
    String District ;
    String City_ID ;
    String To_City ;
    double Deleviry_LAT  ;
    double Deleviry_LUNG  ;


    // lang lat
    double Recipient_LAT  ;// page1
    double Recipient_LUNG  ;// page 1
    String Recipient_Place ;// page1
    String Receiving_City_ID ;// page1
    //address
    String Receiving_District ;// page1
    int From_City ;// page1


    String More_Place_Details ;// page 2
    int Contact_Before_Arrival ;// page 2
    int Capture_The_Package ;// page

    String  Date ;
    String Time_From ;
    String Time_To ;// page 3



    String Title;//page 4
    int  ShippingSize =0; // page 4
    int Car_Type_ID  =0;// page 4
    int Pack_Num =1;// page 4
    ArrayList<String>  Imgs  = new ArrayList<>();


    String Phone ;// 5
    String Recipient_Name ;







    int Payment_Method_ID=1 ;// 6
    int Ship_Type_ID=1 ;//6
    int Goods_Values ;// page 6 // price




    int Order_State_ID ;//
    String Store_ID ;//
    private String reciverMapCity;


    public String getRequest() {
        return Request;
    }

    public void setRequest(String request) {
        Request = request;
    }

    public String getDeleviry_Place() {
        return Deleviry_Place;
    }

    public void setDeleviry_Place(String deleviry_Place) {
        Deleviry_Place = deleviry_Place;
    }

    public String getDistrict() {
        return District;
    }

    public void setDistrict(String district) {
        District = district;
    }

    public String getCity_ID() {
        return City_ID;
    }

    public void setCity_ID(String city_ID) {
        City_ID = city_ID;
    }

    public String getTo_City() {
        return To_City;
    }

    public void setTo_City(String to_City) {
        To_City = to_City;
    }

    public double getDeleviry_LAT() {
        return Deleviry_LAT;
    }

    public void setDeleviry_LAT(double deleviry_LAT) {
        Deleviry_LAT = deleviry_LAT;
    }

    public double getDeleviry_LUNG() {
        return Deleviry_LUNG;
    }

    public void setDeleviry_LUNG(double deleviry_LUNG) {
        Deleviry_LUNG = deleviry_LUNG;
    }

    public double getRecipient_LAT() {
        return Recipient_LAT;
    }

    public void setRecipient_LAT(double recipient_LAT) {
        Recipient_LAT = recipient_LAT;
    }

    public double getRecipient_LUNG() {
        return Recipient_LUNG;
    }

    public void setRecipient_LUNG(double recipient_LUNG) {
        Recipient_LUNG = recipient_LUNG;
    }

    public String getRecipient_Place() {
        return Recipient_Place;
    }

    public void setRecipient_Place(String recipient_Place) {
        Recipient_Place = recipient_Place;
    }

    public String getReceiving_City_ID() {
        return Receiving_City_ID;
    }

    public void setReceiving_City_ID(String receiving_City_ID) {
        Receiving_City_ID = receiving_City_ID;
    }

    public String getReceiving_District() {
        return Receiving_District;
    }

    public void setReceiving_District(String receiving_District) {
        Receiving_District = receiving_District;
    }

    public int getFrom_City() {
        return From_City;
    }

    public void setFrom_City(int from_City) {
        From_City = from_City;
    }

    public String getMore_Place_Details() {
        return More_Place_Details;
    }

    public void setMore_Place_Details(String more_Place_Details) {
        More_Place_Details = more_Place_Details;
    }

    public int getContact_Before_Arrival() {
        return Contact_Before_Arrival;
    }

    public void setContact_Before_Arrival(int contact_Before_Arrival) {
        Contact_Before_Arrival = contact_Before_Arrival;
    }

    public int getCapture_The_Package() {
        return Capture_The_Package;
    }

    public void setCapture_The_Package(int capture_The_Package) {
        Capture_The_Package = capture_The_Package;
    }

    public String getDate() {
        return Date;
    }

    public void setDate(String date) {
        Date = date;
    }

    public String getTime_From() {
        return Time_From;
    }

    public void setTime_From(String time_From) {
        Time_From = time_From;
    }

    public String getTime_To() {
        return Time_To;
    }

    public void setTime_To(String time_To) {
        Time_To = time_To;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        this.Title = title;
    }

    public int getShippingSize() {
        return ShippingSize;
    }

    public void setShippingSize(int shippingSize) {
        ShippingSize = shippingSize;
    }

    public int getCar_Type_ID() {
        return Car_Type_ID;
    }

    public void setCar_Type_ID(int car_Type_ID) {
        Car_Type_ID = car_Type_ID;
    }

    public int getPack_Num() {
        return Pack_Num;
    }

    public void setPack_Num(int pack_Num) {
        Pack_Num = pack_Num;
    }

    public int getGoods_Values() {
        return Goods_Values;
    }

    public void setGoods_Values(int goods_Values) {
        Goods_Values = goods_Values;
    }

    public String getStore_ID() {
        return Store_ID;
    }

    public void setStore_ID(String store_ID) {
        Store_ID = store_ID;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public int getPayment_Method_ID() {
        return Payment_Method_ID;
    }

    public void setPayment_Method_ID(int payment_Method_ID) {
        Payment_Method_ID = payment_Method_ID;
    }

    public int getShip_Type_ID() {
        return Ship_Type_ID;
    }

    public void setShip_Type_ID(int ship_Type_ID) {
        Ship_Type_ID = ship_Type_ID;
    }

    public int getOrder_State_ID() {
        return Order_State_ID;
    }

    public void setOrder_State_ID(int order_State_ID) {
        Order_State_ID = order_State_ID;
    }

    public ArrayList<String> getImgs() {
        return Imgs;
    }

    public void setImgs(ArrayList<String> imgs) {
        Imgs = imgs;
    }

    public String getRecipient_Name() {
        return Recipient_Name;
    }

    public void setRecipient_Name(String recipient_Name) {
        Recipient_Name = recipient_Name;
    }

    public void setReciverMapCity(String reciverMapCity) {
        this.reciverMapCity = reciverMapCity;
    }

    public String getReciverMapCity() {
        return reciverMapCity;
    }
}
