package com.bmob.lostfound.bean;

import cn.bmob.v3.BmobObject;

/** ÕÐÁì
  * @ClassName: Found
  * @Description: TODO
  * @author smile
  * @date 2014-5-21 ÏÂÎç2:16:08
  */
public class Found extends BmobObject {

	private String title;//±êÌâ
	private String describe;//ÃèÊö
	private String phone;//ÁªÏµÊÖ»ú
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;//必须设置this,就是这个对象实例的属性了
	}
	public String getDescribe() {
		return describe;
	}
	public void setDescribe(String describe) {
		this.describe = describe;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
}
