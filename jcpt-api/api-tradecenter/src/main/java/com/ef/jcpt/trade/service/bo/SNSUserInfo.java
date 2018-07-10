package com.ef.jcpt.trade.service.bo;

import java.io.Serializable;
import java.util.List;

public class SNSUserInfo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6168896215001616889L;
		//用户标示
		private String openId;
		//用户昵称
		private String nickname;
		//性别 (1男性，2女性 ，0未知)
		private Integer sex;
		//国家
		private String country;
		//省份
		private String province;
		//城市
		private String city;
		//用户头像链接
		private String headImgUrl;
		//用户特权信息
		private List<String> privilegeList;
		//如果 用户绑定用户将公众号绑定到微信开放平台帐号后，才会出现该字段
		private String unionid;
		public String getOpenId() {
			return openId;
		}
		public void setOpenId(String openId) {
			this.openId = openId;
		}
		public String getNickname() {
			return nickname;
		}
		public void setNickname(String nickname) {
			this.nickname = nickname;
		}
		public Integer getSex() {
			return sex;
		}
		public void setSex(Integer sex) {
			this.sex = sex;
		}
		public String getCountry() {
			return country;
		}
		public void setCountry(String country) {
			this.country = country;
		}
		public String getProvince() {
			return province;
		}
		public void setProvince(String province) {
			this.province = province;
		}
		public String getCity() {
			return city;
		}
		public void setCity(String city) {
			this.city = city;
		}
		public String getHeadImgUrl() {
			return headImgUrl;
		}
		public void setHeadImgUrl(String headImgUrl) {
			this.headImgUrl = headImgUrl;
		}
		public List<String> getPrivilegeList() {
			return privilegeList;
		}
		public void setPrivilegeList(List<String> privilegeList) {
			this.privilegeList = privilegeList;
		}
		public String getUnionid() {
			return unionid;
		}
		public void setUnionid(String unionid) {
			this.unionid = unionid;
		}
}
