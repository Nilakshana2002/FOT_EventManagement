package com.fot.eventsystem.utill;
import com.fot.eventsystem.remote.RemoteAdminService;
import java.rmi.registry.*;
public class RmiClient {
    public static void main(String[] args) throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        RemoteAdminService service = (RemoteAdminService) registry.lookup("AdminService");
        System.out.println("RMI CALL SUCCESS: " + service.getSystemStatus());
        System.out.println("LIVE USER COUNT VIA RMI: " + service.getTotalUsers());
    }
}
