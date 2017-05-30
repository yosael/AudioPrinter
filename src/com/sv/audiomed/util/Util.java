package com.sv.audiomed.util;

import java.util.Date;

public class Util{
	
	
	public static  Double moneyDecimal(Double num) {
		return new Long(Math.round(num*100))/100.0;
	}
	
	public static Double porcentIvaActual()
	{
		return 0.13;
	}
	
	
	public static Date definirFechaInicio()
	{
		return new Date();
	}
	
	public static Date definirFechaFin()
	{
		return new Date();
	} 
	
	
}