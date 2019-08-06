package main.java.app;

import java.sql.ResultSet;
import java.util.ArrayList;

public interface ServiceInterface {

    public <T> ArrayList<T> get(ResultSet resultSet);


}
