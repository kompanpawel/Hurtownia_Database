package hurtownia.model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import hurtownia.dbUtil.dbConnection;

public class LoginModel {
    Connection con;

    public LoginModel() {
        try {
            this.con = dbConnection.getConnection();
        } catch (SQLException ex ) {
           ex.printStackTrace();
        }
        if(this.con == null) {
            System.exit(1);
        }
    }

    public boolean isDbConnected () {
        return this.con != null;
    }

   public boolean isLogin(String user, String pass) throws SQLException {

       PreparedStatement ps = null;
       ResultSet rs = null;

       String sql = "SELECT * FROM mydb.login WHERE login = ? and password = ?";

       try {
           ps = this.con.prepareStatement(sql);
           ps.setString(1, user);
           ps.setString(2, pass);

           rs = ps.executeQuery();

           if (rs.next())
               return true;
           return false;
       } catch (SQLException ex) {
           ex.printStackTrace();
           return false;
       }

       finally {
           ps.close();
           rs.close();
       }

   }
}
