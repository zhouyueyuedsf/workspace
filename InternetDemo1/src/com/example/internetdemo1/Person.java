package com.example.internetdemo1;

public class Person {
	private int id;
	private String name;
	private Short age;
	public Person(){
		
	}
	public Person(int id,String name,Short age){
		this.id = id;
		this.age = age;
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Short getAge() {
		return age;
	}
	public void setAge(Short age) {
		this.age = age;
	}
	
}
