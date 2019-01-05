package com.qs.entity;

public class Player1 {
    private Integer id;

    private Integer dayTime;

    private Integer playing;

    private Byte gp;

    private String type;

    private Integer lookon;

    private Byte gametype;

    public Player1(){

    }

    public Player1(Player player){
        this.dayTime = player.getDayTime();
        this.playing = player.getPlaying();
        this.gp = player.getGp();
        this.type = player.getType();
        this.lookon = player.getLookon();
        this.gametype = player.getGametype();
    }


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getDayTime() {
        return dayTime;
    }

    public void setDayTime(Integer dayTime) {
        this.dayTime = dayTime;
    }

    public Integer getPlaying() {
        return playing;
    }

    public void setPlaying(Integer playing) {
        this.playing = playing;
    }

    public Byte getGp() {
        return gp;
    }

    public void setGp(Byte gp) {
        this.gp = gp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type == null ? null : type.trim();
    }

    public Integer getLookon() {
        return lookon;
    }

    public void setLookon(Integer lookon) {
        this.lookon = lookon;
    }

    public Byte getGametype() {
        return gametype;
    }

    public void setGametype(Byte gametype) {
        this.gametype = gametype;
    }
}