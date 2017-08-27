package vos;

import java.text.SimpleDateFormat;

import java.sql.Date;

public class pruebas 
{
	

	
	 java.sql.Date x = java.sql.Date.valueOf( "2016-03-10" );
	
	public Date getFecha()
	{
		
		return x; 
	}
	
	public final static void main(String args[])
	{
		pruebas x = new pruebas();
		System.out.println(x.getFecha());
		Date y = x.getFecha();
		System.out.println(y.getDay() +"mes "+  y.getMonth() + "año"+  y.getYear());
		java.util.Date fechaActual = new java.util.Date(); 
		System.out.println(( fechaActual.getTime() - y.getTime())* 0.0000000115);
		
		System.out.println();
	}
}
