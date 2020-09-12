package com.example.to_lettracker;

//API Model Class
public class APIModel {
    String id, phone_number, location, longitude, latitude, flat_description, flat_size, total_rent, photos, post_time, is_active;
//    DateFormat post_time;

    public APIModel(String id, String phone_number, String location, String flat_description, String flat_size, String total_rent, String post_time, String is_active) {
        this.id = id;
        this.phone_number = phone_number;
        this.location = location;
        this.longitude = longitude;
        this.latitude = latitude;
        this.flat_description = flat_description;
        this.flat_size = flat_size;
        this.total_rent = total_rent;
        this.post_time = post_time;
        this.is_active = is_active;
    }

/*    public APIModel(String photos, String id, String location) {
        this.photos = photos;
        this.id = id;
        this.location = location;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getFlat_description() {
        return flat_description;
    }

    public void setFlat_description(String flat_description) {
        this.flat_description = flat_description;
    }

    public String getFlat_size() {
        return flat_size;
    }

    public void setFlat_size(String flat_size) {
        this.flat_size = flat_size;
    }

    public String getTotal_rent() {
        return total_rent;
    }

    public void setTotal_rent(String total_rent) {
        this.total_rent = total_rent;
    }

    public String getPost_time() {
        String formatDate = formatPostTime(post_time);
        return formatDate;
    }

    public void setPost_time(String post_time) {
        this.post_time = post_time;
    }

    public String getIs_active() {
        String status = "";

        if (is_active == "true") {
            status = "Available ☑";
        } else {
            status = "Not Available ❌";
        }

        return status;
    }

    public void setIs_active(String is_active) {
        this.is_active = is_active;
    }

    private String formatPostTime(String post_time) {
        String formatMonths = "";
        String typeOfHours = "";
        String formatDate = "";

        String formatYear = post_time.substring(0,4);
        String formatDay = post_time.substring(5,7);
        String formatMonth = post_time.substring(8,10);
        String formatTime = post_time.substring(11,19);
        String formatHours = post_time.substring(11,13);
        int hours = Integer.parseInt(formatHours);

        if (hours>=0 && hours<12) {
            typeOfHours = "AM";
        } else {
            typeOfHours = "PM";
        }

        if(formatMonth.equals("01")) {
            formatMonths = "January";
        } else if (formatMonth.equals("02")) {
            formatMonths = "February";
        } else if (formatMonth.equals("03")) {
            formatMonths = "March";
        } else if (formatMonth.equals("04")) {
            formatMonths = "April";
        } else if (formatMonth.equals("05")) {
            formatMonths = "May";
        } else if (formatMonth.equals("06")) {
            formatMonths = "June";
        } else if (formatMonth.equals("07")) {
            formatMonths = "July";
        } else if (formatMonth.equals("08")) {
            formatMonths = "August";
        } else if (formatMonth.equals("09")) {
            formatMonths = "September";
        } else if (formatMonth.equals("10")) {
            formatMonths = "October";
        } else if (formatMonth.equals("11")) {
            formatMonths = "November";
        } else if (formatMonth.equals("12")) {
            formatMonths = "December";
        }
        else {
            formatMonths = formatMonth;
        }

        return formatDate = formatDay + " " + formatMonths + ", " + formatYear + "  |  " + formatTime +" " + typeOfHours;
    }
}
