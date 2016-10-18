package com.example.rex.xmcg.Dao;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Created by Rex on 2016/10/18.
 */

@Entity
public class UserG {
    @Id
    private Long id;
    private String name;
    private String moblie;
    private String identity;
    private String patNumber;
    private String idType;
    private String idNumber;
    private String isFirst;
    private String memo;
    public String getMemo() {
        return this.memo;
    }
    public void setMemo(String memo) {
        this.memo = memo;
    }
    public String getIsFirst() {
        return this.isFirst;
    }
    public void setIsFirst(String isFirst) {
        this.isFirst = isFirst;
    }
    public String getIdNumber() {
        return this.idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }
    public String getIdType() {
        return this.idType;
    }
    public void setIdType(String idType) {
        this.idType = idType;
    }
    public String getPatNumber() {
        return this.patNumber;
    }
    public void setPatNumber(String patNumber) {
        this.patNumber = patNumber;
    }
    public String getMoblie() {
        return this.moblie;
    }
    public void setMoblie(String moblie) {
        this.moblie = moblie;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getIdentity() {
        return this.identity;
    }
    public void setIdentity(String identity) {
        this.identity = identity;
    }
    @Generated(hash = 1385289686)
    public UserG(Long id, String name, String moblie, String identity,
            String patNumber, String idType, String idNumber, String isFirst,
            String memo) {
        this.id = id;
        this.name = name;
        this.moblie = moblie;
        this.identity = identity;
        this.patNumber = patNumber;
        this.idType = idType;
        this.idNumber = idNumber;
        this.isFirst = isFirst;
        this.memo = memo;
    }
    @Generated(hash = 41474780)
    public UserG() {
    }


}