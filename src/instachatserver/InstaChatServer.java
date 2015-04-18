/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instachatserver;

import instachatrmi.InstaChatInterface;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

/**
 *
 * @author Saurin
 */
public class InstaChatServer {

    public InstaChatServer() {
        try {
            InstaChatInterface obj = new RImp();
            //InstaChatInterface stub = (InstaChatInterface) UnicastRemoteObject.exportObject(obj, 0);
//            System.setProperty("java.security.policy","file:C:\\Users\\Saurin\\Documents\\NetBeansProjects\\InstaChatServer\\Server.policy");
//            System.setSecurityManager(new RMISecurityManager());
            System.setProperty("java.rmi.server.hostname", "71.1.116.167");

            LocateRegistry.createRegistry(1099);
            Registry registry = LocateRegistry.getRegistry(1099);
            registry.rebind("Login", obj);
            System.out.println("Server: Ready...");
        } catch (Exception e) {
            System.out.println("Server: Failed to register RMIExampleImpl: " + e);
        }
    }

    public static void main(String[] args) {
        InstaChatServer server = new InstaChatServer();
    }// END of main

}// END of InstaChatServer
