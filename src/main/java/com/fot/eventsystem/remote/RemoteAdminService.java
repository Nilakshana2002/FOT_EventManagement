package com.fot.eventsystem.remote;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * RMI REQUIREMENT: Remote Interface
 */
public interface RemoteAdminService extends Remote {
    String getSystemStatus() throws RemoteException;
    int getTotalUsers() throws RemoteException;
}
