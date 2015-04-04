
package com.myznikov.weather.dots;

import java.util.List;

public class WeatherResponseDto{
   	private String base;
   	private Clouds clouds;
   	private Number cod;
   	private Coord coord;
   	private String dt;
   	private Number id;
   	private Main main;
   	private String name;
   	private Sys sys;
   	private List<Weather> weather;
   	private Wind wind;

 	public String getBase(){
		return this.base;
	}
	public void setBase(String base){
		this.base = base;
	}
 	public Clouds getClouds(){
		return this.clouds;
	}
	public void setClouds(Clouds clouds){
		this.clouds = clouds;
	}
 	public Number getCod(){
		return this.cod;
	}
	public void setCod(Number cod){
		this.cod = cod;
	}
 	public Coord getCoord(){
		return this.coord;
	}
	public void setCoord(Coord coord){
		this.coord = coord;
	}
 	public String getDt(){
		return this.dt;
	}
	public void setDt(String dt){
		this.dt = dt;
	}
 	public Number getId(){
		return this.id;
	}
	public void setId(Number id){
		this.id = id;
	}
 	public Main getMain(){
		return this.main;
	}
	public void setMain(Main main){
		this.main = main;
	}
 	public String getName(){
		return this.name;
	}
	public void setName(String name){
		this.name = name;
	}
 	public Sys getSys(){
		return this.sys;
	}
	public void setSys(Sys sys){
		this.sys = sys;
	}
 	public List<Weather> getWeather(){
		return this.weather;
	}
	public void setWeather(List weather){
		this.weather = weather;
	}
 	public Wind getWind(){
		return this.wind;
	}
	public void setWind(Wind wind){
		this.wind = wind;
	}
}
