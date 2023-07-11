package server;

import common.collections.Coordinates;
import common.collections.Country;
import common.collections.Location;
import common.collections.Person;
import common.utility.CustomConsole;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayDeque;
import java.util.Deque;

import static common.utility.DateUtils.asLocalDate;

public class DBhandler {

    private static String url="jdbc:postgresql://localhost:5432/postgres";
    private static String user="postgres";
    private static String password="1234";
    private static Connection connection;
        static{
            try {
                connection = DriverManager.getConnection(url, user, password);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }}


        private Statement statement = connection.createStatement();

    public DBhandler() throws SQLException {
    }

    public int checkUser(String username, String password) {
        int num_result=-1;
        try {
            String query = "select * from users where username=\'"+username+"\'";

            var res = statement.executeQuery(query);
            if (res.next()) {
                String pass = res.getString("password");
                if (!pass.equals(password)) {
                    num_result = -1;
                } else {res.getInt("id");

                num_result=1;}
            }
            return num_result;
        } catch (SQLException e) {
            return -1;
        }
    }

        public int setUser(String username, String password) {
            try {
                String query = "insert into users (username, password) values ('"+username+"','"+password+"') returning id";
                var res = statement.executeQuery(query);
                if(res.next()){
                res.getInt("id");}
            } catch (SQLException e) {
                System.out.println("Ошибка в получении данных из таблицы");
                return -1;
            }
           return 1;
        }
        public int getID(String name){
            int user_id =0;
            try{
            var query = "select * from users where username='"+name+"'";


            var res1 = statement.executeQuery(query);
            if(res1.next()){
                user_id = res1.getInt("id");}}
            catch(SQLException e){
        System.out.println("Ошибка");
    }
    return user_id;}


        public int addElement(Person person, int user_id) throws SQLException{
            try {
                var query = coordinatesInsertQuery(person.getCoordinates());

                var res = statement.executeQuery(query);
                var coordinates_id=0;
               Location location;
                if (res.next()) {
                    coordinates_id = res.getInt("id");
                }
                 location = person.getLocation();
               int location_id=0;
                var query1 = locationInsertQuery(person.getLocation());
                var res1 = statement.executeQuery(query1);
                if (res1.next()) {
                    location_id = res1.getInt("id");
                }
//                var nationality = person.getNationality();
               var query2 = countryInsertQuery(person.getNationality());
               var res2 = statement.executeQuery(query2);
                    if (res2.next()) {
                var nationality_id = res2.getInt("id");
                query = "insert into collection_elements (name,coordinates_id, creationdate, " +
                        "height, birthday, passportid, nationality_id, location_id, user_id) values ('"+person.getName()+"',"+coordinates_id+",'"+person.getCreationDate()+"'," +
                        person.getHeight()+",'"+person.getBirthday()+"','"+person.getPassportID()+"',"+ nationality_id+","+ location_id+","+user_id+") returning id";
                var res3 = statement.executeQuery(query);
                if(res3.next()){
                var ans = res3.getInt("id");

                return ans;}}
            } catch (SQLException e) {
                System.out.println("Ошибка в получении данных из таблицы");
            }
            return 0;
        }

        public boolean removeElement(int id, int user_id) {
            try {
                var query = "select user_id from collection_elements where id="+id;
                var res = statement.executeQuery(query);
                if(res.next()){
                if (res.getInt("user_id") != user_id)
                  return false;
                    query = "delete from collection_elements where id="+id;
                    executeUpdate(query);
                    return true;
               }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
return true;
        }
    public boolean removeGreaterElement(int id, int height) {
        try {
            var query = "delete from collection_elements where user_id="+id+" and height>"+height;
                executeUpdate(query);
                return true;
            }
         catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

        public boolean clearCollection(int user_id) {
            try {
                var query = "delete from collection_elements where user_id=user_id";
                statement.executeUpdate(query);
                return true;
            } catch (SQLException e) {
                System.out.println("Ошибка в получении данных из таблицы");
               return false;
            }
        }
        public boolean updateElement(Person person, int user_id, int person_id) throws SQLException{
            try {
                var query = "select user_id from collection_elements where id="+person_id;
                var res = statement.executeQuery(query);
                if(res.next()){
                if (res.getInt("user_id") != user_id){
                    CustomConsole.printError("Вы не можете обновлять не свой элемент коллекции");

                    return false;}}
                var query1 = coordinatesInsertQuery(person.getCoordinates());
                var res1 = statement.executeQuery(query1);
                    if(res1.next()){
                var coordinates_id = res1.getInt("id");
                var location = person.getLocation();
                int location_id=0;
                if (location != null) {
                    var query2 = locationInsertQuery(location);
                    var res2 = statement.executeQuery(query2);
                    if(res2.next()){
                    location_id = res2.getInt("id");

                var nationality = person.getNationality();
               var query3 = countryInsertQuery(nationality);
                var res3 = statement.executeQuery(query3);
                if(res3.next()){
                var nationality_id = res3.getInt("id");
                    query = "update coordinates set " +
                            "x="+person.getCoordinates().getX()+", " +
                            "y="+person.getCoordinates().getY() +
                            " where id="+coordinates_id+";"+"\n";
                    if(location!=null){
                    query += "update locations set " +
                            "x="+person.getLocation().getX()+", " +
                            "y="+person.getLocation().getY()+", " +
                            "z="+person.getLocation().getZ()+", " +
                            "name= '"+person.getLocation().getName()+"'" +
                            " where id="+location_id+";"+"\n";
                    statement.executeUpdate(query);
                    } else {
                        query += "delete from locations where id="+location_id;
                    }
//                    if (nationality_id == 0){
//                        query += "update country set " +
//                                "name=${from.getName()} " +
//                                "where id=${res.getLong(nationality_id)};";
//                }
//                    else {
//                        query+= "delete from country where id=${res.getLong(nationality_id)};";
//                    }
                    statement.executeUpdate(query);
                    query = "update collection_elements set name= '"+person.getName()+"', " +
                            "coordinates_id="+ coordinates_id+
                            ", creationDate='"+person.getCreationDate()+"', " +
                            "height="+person.getHeight()+", " +
                            "birthday='"+person.getBirthday()+"', " +
                            "passportID="+person.getPassportID()+", " +
                            "nationality_id="+nationality_id+", "+
                            "location_id="+location_id+" , user_id="+user_id+
                            " where id="+person_id;
                    statement.executeUpdate(query);
                    return true;
                }}}}
            } catch (SQLException e) {
                System.out.println("Ошибка в получении данных из таблицы");
               return false;
            }
            return true;
        }
        private void executeUpdate(String s)throws SQLException
        { statement.executeUpdate(s); }
        private ResultSet executeQuery(String s, boolean withNext) throws SQLException {
            var res = statement.executeQuery(s);
            if (withNext) res.next();
            return res;
        }
        private String coordinatesInsertQuery(Coordinates coordinates){
    return "insert into coordinates (x, y) values ("+coordinates.getX() +", "+coordinates.getY()+") returning id";}
        private String locationInsertQuery(Location location) throws SQLException {
    return "insert into locations (x, y, z, name) values ("+location.getX()+", "+location.getY()+", "+location.getZ()+",'"+location.getName()+"') returning id";}
private String countryInsertQuery(int person){
            return "select id from country where id="+person;
}
    private Coordinates downloadCurrentCoordinates() throws SQLException {
        String query = "select * from collection_elements";
        var res = connection.createStatement().executeQuery(query);
        if(res.next()){
        var coordinates_id = res.getInt("coordinates_id");
        String query1 = "select * from coordinates where id ="+ coordinates_id+"";
        ResultSet res1;

res1 = connection.createStatement().executeQuery(query1);
if(res1.next()){

            return new Coordinates(
res1.getDouble("x"),
                res1.getInt("y")
            );}}
        return null;
    }
    private Location downloadCurrentLocation() throws SQLException {
        String query = "select * from collection_elements";
        var res = connection.createStatement().executeQuery(query);
        if(res.next()){
        var location_id = res.getString("location_id");
        String query1 = "select * from locations where id ="+ location_id+"";
        ResultSet res1;
        res1 = connection.createStatement().executeQuery(query1);
        if(res1.next()){
        return new Location(
                res1.getLong("x"),
                res1.getInt("y"),
                res1.getFloat("z"),
                res1.getString("name")
        );}}
        return null;
    }

        public Deque<Person> downloadCurrentCollection() throws SQLException {
            String query = "select * from collection_elements";
            var res = connection.createStatement().executeQuery(query);
            var ans = new ArrayDeque<Person>();
            ans.clear();
            while (res.next()) {
                var user_id = res.getLong("user_id");
                var location_id = res.getInt("location_id");
                var res1 = connection.createStatement().executeQuery(query);
                Coordinates coordinates;
                Country nationality;
                Location location;
if(res1.next()){
                ans.add(new Person(
                 res.getInt("id"),
                    res.getString("name"),
                      downloadCurrentCoordinates(),
                        LocalDate.now(),
                    res.getInt("height"),
                       asLocalDate(res.getDate("creationdate")),
                    res.getString("passportid"),
                         res.getInt("nationality_id"),
                    downloadCurrentLocation(),
                  res1.getInt("user_id")));
                }}

            return ans;}
        }


