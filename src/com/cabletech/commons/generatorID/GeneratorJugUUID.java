package com.cabletech.commons.generatorID;

import org.safehaus.uuid.EthernetAddress;
import org.safehaus.uuid.UUID;
import org.safehaus.uuid.UUIDGenerator;

public class GeneratorJugUUID {
	private static UUIDGenerator generator = UUIDGenerator.getInstance();
	public static String getUUIDByRandomNumber(){
		//EthernetAddress da = generator.getDummyAddress();
		java.util.Random rd = generator.getRandomNumberGenerator(); 
		//System.out.println(rd.toString());
		UUID uuid = generator.generateRandomBasedUUID(rd);
		//System.out.println("ID :"+uuid.toString());
		return uuid.toString();
	}
	
	public static String getUUIDByTime(){
		EthernetAddress da = generator.getDummyAddress();
		UUID uuid = generator.generateTimeBasedUUID(da);
		//System.out.println("ID :"+uuid.toString());
		return uuid.toString();
	}
}
