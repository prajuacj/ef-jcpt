package com.ef.jcpt.trade.dao.model;

import java.math.BigDecimal;
import java.util.Date;

public class ProductPrice {
    private Integer id;

    private Integer productId;

    private String productNationCode;

    private String userNationCode;

    private BigDecimal price;

    private BigDecimal discountPrice;

    private String priceStatus;

    private String remark;

    private Date createTime;

    private Date updateTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductNationCode() {
        return productNationCode;
    }

    public void setProductNationCode(String productNationCode) {
        this.productNationCode = productNationCode == null ? null : productNationCode.trim();
    }

    public String getUserNationCode() {
        return userNationCode;
    }

    public void setUserNationCode(String userNationCode) {
        this.userNationCode = userNationCode == null ? null : userNationCode.trim();
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public BigDecimal getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(BigDecimal discountPrice) {
        this.discountPrice = discountPrice;
    }

    public String getPriceStatus() {
        return priceStatus;
    }

    public void setPriceStatus(String priceStatus) {
        this.priceStatus = priceStatus == null ? null : priceStatus.trim();
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}