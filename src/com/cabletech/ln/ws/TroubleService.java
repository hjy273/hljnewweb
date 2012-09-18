package com.cabletech.ln.ws;

import java.rmi.RemoteException;

public interface TroubleService {
	public void sendTroubleInfo(ClientRequest cres) throws RemoteException;
}
