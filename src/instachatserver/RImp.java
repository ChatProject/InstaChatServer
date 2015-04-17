/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package instachatserver;

import instachatrmi.CallBack;
import instachatrmi.InstaChatInterface;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Saurin
 */
public class RImp extends UnicastRemoteObject implements InstaChatInterface {

    MySqlCon db;
    ResultSet result;
    Vector<CallBack> user; 
    Vector<String> name;
    
    HashMap<String, Object> map = new HashMap<>();

    public RImp() throws RemoteException {
        user = new Vector<CallBack>();
        name = new Vector<String>();
    }

    @Override
    public boolean login(String user, String pass) throws RemoteException {
        try{
            db = new MySqlCon();
            Connection con = db.getConnection();
            
            String query = "SELECT user_name FROM accounts WHERE user_name = '" +
                    user + "' and password = '" + pass + "'";
            
            Statement stmt = con.createStatement();
            result = stmt.executeQuery (query);
            if(result.next()){
                return true;
            }
        }catch(Exception e){
            
        }finally{
            try {
                result.close();
            } catch (SQLException ex) {
                Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }//END of login

    @Override
    public boolean add(String user,String friend) throws RemoteException {
        db = new MySqlCon();
        Connection con = db.getConnection();
        
        //String user = "Saurin";
        
        String query = "insert into friends (user, friend)" +
                    "values (?, ?)";
        
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2, friend);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }//END of add

    @Override
    public boolean register(String user, String pass) throws RemoteException {
        try {
            db = new MySqlCon();
            Connection con = db.getConnection();
            
            String query = "SELECT user_name FROM accounts WHERE user_name = '" + user + "'";
            
            Statement statement = null;
            try {
                statement = con.createStatement();
            } catch (SQLException ex) {
                Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            ResultSet resultSet = statement.executeQuery(query);
            
            if (!resultSet.isBeforeFirst() ) {
                String queryString = "insert into accounts (user_name, password)" +
                        "values (?, ?)";
                
                //Createa a Prepared Statement
                PreparedStatement preparedStatement = con.prepareStatement(queryString);
                
                preparedStatement.setString(1,user);
                preparedStatement.setString(2, pass);
                preparedStatement.execute();
            }
            
            
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return false;
    }// END of register

    @Override
    public void connect(CallBack client, String name) throws RemoteException {
        System.out.println("Connect statement client : " + client);
        System.out.println("Connect statement client name: " + name);
//        if (!this.user.contains(client)){
//            this.user.addElement(client);
//            this.name.add(name);
//        }
        
        map.put(name, client);
    }

    @Override
    public String sendChat(String to, String from, String message) throws RemoteException {
        System.out.println("Send Chat to: " + to + " from: " + from + " Message: " + message);
//        int i = 0;
//        while(i < name.size()){
//            if(name.get(i).matches(to)){
//                System.out.println("USer to: " + to );
//                user.get(i).sendMsg(to, from, message);
//            }
//            i++;
//        }
        boolean val = map.isEmpty();
        if(val == true){
            System.out.println(" hash map is Empty: " + val);
        }else{
            for(Map.Entry<String, Object> entry: map.entrySet()){
                System.out.println("Inside of for loop");
                String key = entry.getKey();
                if(key.equals(to)){
                Object value = entry.getValue();

                    ((CallBack) value).sendMsg(to, from, message);
                }
            }
        }
        return "Message";
    }

    @Override
    public Vector<String> friends(String user) throws RemoteException {
        Vector<String> friendsList = new Vector<String>();
        db = new MySqlCon();
        Connection con = db.getConnection();

        String query = "SELECT friend FROM friends WHERE user = '" + user + "'";

        Statement statement = null;
        try {
            statement = con.createStatement();
        } catch (SQLException ex) {
            Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            ResultSet resultSet = statement.executeQuery(query);
            while(resultSet.next()){
                String name = resultSet.getString(1);
                friendsList.add(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
        }

        return friendsList;
    }

    @Override
    public boolean delete(String user, String enemy) throws RemoteException {
        db = new MySqlCon();
        Connection con = db.getConnection();
        
        String query = "DELETE from friends WHERE user = ? and friend = ?";
        
        try {
            PreparedStatement preparedStatement = con.prepareStatement(query);
            preparedStatement.setString(1,user);
            preparedStatement.setString(2, enemy);
            preparedStatement.execute();

        } catch (SQLException ex) {
            Logger.getLogger(RImp.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return true;
    }

}// END of RImp
