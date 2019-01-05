package test.bak.model;

import java.util.Date;

public class SnatchTreasureRecord {
    private Integer id;

    private Integer mid;

    private Integer awardid;

    private Integer treasureno;

    private Integer status;

    private Integer range;

    private String dateno;

    private Date ceatetime;

    private Date modifytime;

    private String ext1;

    private String ext2;

    private String ext3;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getMid() {
        return mid;
    }

    public void setMid(Integer mid) {
        this.mid = mid;
    }

    public Integer getAwardid() {
        return awardid;
    }

    public void setAwardid(Integer awardid) {
        this.awardid = awardid;
    }

    public Integer getTreasureno() {
        return treasureno;
    }

    public void setTreasureno(Integer treasureno) {
        this.treasureno = treasureno;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getRange() {
        return range;
    }

    public void setRange(Integer range) {
        this.range = range;
    }

    public String getDateno() {
        return dateno;
    }

    public void setDateno(String dateno) {
        this.dateno = dateno == null ? null : dateno.trim();
    }

    public Date getCeatetime() {
        return ceatetime;
    }

    public void setCeatetime(Date ceatetime) {
        this.ceatetime = ceatetime;
    }

    public Date getModifytime() {
        return modifytime;
    }

    public void setModifytime(Date modifytime) {
        this.modifytime = modifytime;
    }

    public String getExt1() {
        return ext1;
    }

    public void setExt1(String ext1) {
        this.ext1 = ext1 == null ? null : ext1.trim();
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2 == null ? null : ext2.trim();
    }

    public String getExt3() {
        return ext3;
    }

    public void setExt3(String ext3) {
        this.ext3 = ext3 == null ? null : ext3.trim();
    }
}