package org.hotel.packages.dal.po;

public class CabinetBO {
    private Integer id;

    private String cabinetId;

    private String size;

    private String cabinetStatus;

    private String number;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCabinetId() {
        return cabinetId;
    }

    public void setCabinetId(String cabinetId) {
        this.cabinetId = cabinetId == null ? null : cabinetId.trim();
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCabinetStatus() {
        return cabinetStatus;
    }

    public void setCabinetStatus(String cabinetStatus) {
        this.cabinetStatus = cabinetStatus;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
}