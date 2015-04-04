
package com.myznikov.weather.dots;

import java.util.List;

public class NoCityFoundDto {
   	private String cod;
   	private String message;

 	public String getCod(){
		return this.cod;
	}
	public void setCod(String cod){
		this.cod = cod;
	}
 	public String getMessage(){
		return this.message;
	}
	public void setMessage(String message){
		this.message = message;
	}
}
