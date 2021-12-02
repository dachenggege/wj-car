package org.springblade.car.wx.dto;

import lombok.Data;

import java.util.List;

@Data
public class VinRepDetail {
    private String name;
    private String brand;
    private String typename;
    private String logo;
    private String manufacturer;
    private String yeartype;
    private String environmentalstandards;
    private String comfuelconsumption;
    private String engine;
    private String fueltype;
    private String gearbox;
    private String drivemode;
    private String fronttiresize;
    private String reartiresize;
    private String displacement;
    private String fuelgrade;
    private String price;
    private String chassis;
    private String frontbraketype;
    private String rearbraketype;
    private String parkingbraketype;
    private String maxpower;
    private String sizetype;
    private String gearnum;
    private String geartype;
    private String seatnum;
    private String bodystructure;
    private String maxhorsepower;
    private String vin;
    private String iscorrect;
    private String listdate;
    private String len;
    private String width;
    private String height;
    private String wheelbase;
    private String weight;
    private String bodytype;

    private VinRepMachineoil  machineoil;
    private List<VinRepCar> carlist;
}
