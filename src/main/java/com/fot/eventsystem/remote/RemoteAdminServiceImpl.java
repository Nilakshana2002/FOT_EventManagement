package com.fot.eventsystem.remote;

import com.fot.eventsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import jakarta.annotation.PostConstruct;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

/**
 * RMI REQUIREMENT: Remote Service Implementation
 */
@Component
public class RemoteAdminServiceImpl extends UnicastRemoteObject implements RemoteAdminService {

    @Autowired
    private UserRepository userRepository;

    public RemoteAdminServiceImpl() throws RemoteException {
        super();
    }

    @Override
    public String getSystemStatus() throws RemoteException {
        return "ONLINE - FOT Event Management System is running normally.";
    }

    @Override
    public int getTotalUsers() throws RemoteException {
        return (int) userRepository.count();
    }

    @PostConstruct
    public void registerRMI() {
        try {
            // Create RMI Registry on Port 1099
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(1099);
            } catch (RemoteException e) {
                registry = LocateRegistry.getRegistry(1099);
            }

            // Bind the service to the registry
            registry.rebind("AdminService", this);
            
            System.out.println("=========================================");
            System.out.println("RMI: RemoteAdminService bound to Registry.");
            System.out.println("RMI: Registry active on Port 1099.");
            System.out.println("=========================================");
        } catch (RemoteException e) {
            System.err.println("RMI Registration failed: " + e.getMessage());
        }
    }
}
