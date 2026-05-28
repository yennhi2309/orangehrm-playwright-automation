//package DAO;
//
//import utils.DatabaseUtils;
//
//import java.sql.ResultSet;
//
//public class UserDAO {
//    public static SystemUser getUserByUsername(String username, String tableName) {
//        String query = "SELECT username, user_role, employee_name, status FROM " + tableName +
//                " WHERE username = '" + username + "'";
//
//        try {
//            ResultSet rs = DatabaseUtils.executeQuery(query);
//            if (rs.next()) {
//                SystemUser user = new SystemUser(
//                        rs.getString("username"),
//                        rs.getString("user_role"),
//                        rs.getString("employee_name"),
//                        rs.getString("status")
//                );
//                rs.close();
//                return user;
//            }
//        } catch (Exception e) {
//            throw new RuntimeException("Failed to find user: " + e.getMessage());
//        }
//
//        return null;
//    }
//}
