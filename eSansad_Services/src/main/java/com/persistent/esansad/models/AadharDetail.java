package com.persistent.esansad.models;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "AadharDetail", propOrder = { "uid", "name", "gender", "yob",
		"co", "house", "street", "lm", "loc", "vtc", "dist", "state", "pc" })
public class AadharDetail {

	private String uid;
	private String name;
	private String gender;
	private String yob;
	private String co;
	private String house;
	private String street;
	private String lm;
	private String loc;
	private String vtc;
	private String dist;
	private String state;
	private String pc;
	private String po;

	public String getPo() {
		return po;
	}

	public void setPo(String po) {
		this.po = po;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getYob() {
		return yob;
	}

	public void setYob(String yob) {
		this.yob = yob;
	}

	public String getCo() {
		return co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getHouse() {
		return house;
	}

	public void setHouse(String house) {
		this.house = house;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getLm() {
		return lm;
	}

	public void setLm(String lm) {
		this.lm = lm;
	}

	public String getLoc() {
		return loc;
	}

	public void setLoc(String loc) {
		this.loc = loc;
	}

	public String getVtc() {
		return vtc;
	}

	public void setVtc(String vtc) {
		this.vtc = vtc;
	}

	public String getDist() {
		return dist;
	}

	public void setDist(String dist) {
		this.dist = dist;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getPc() {
		return pc;
	}

	public void setPc(String pc) {
		this.pc = pc;
	}

	@Override
	public String toString() {
		return "AadharDetail [uid=" + uid + ", name=" + name + ", gender="
				+ gender + ", yob=" + yob + ", co=" + co + ", house=" + house
				+ ", street=" + street + ", lm=" + lm + ", loc=" + loc
				+ ", vtc=" + vtc + ", dist=" + dist + ", state=" + state
				+ ", pc=" + pc + "]";
	}

}
