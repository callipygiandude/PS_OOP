package psallotment;

import java.util.*;

public class PSList
{

    private static PSList instance;
    private static ArrayList<PSStation> listOfStations; 	//admin will access this list
    private static ArrayList<Student> listOfStudents;
    private static int stationCount;
    private static HashMap<String, PSStation> stationByName;
    private static HashMap<String, List<PSStation>> stationByLoc;

    private PSList()
    {
        listOfStations = new ArrayList<PSStation>();
        listOfStudents = new ArrayList<Student>();
        stationByName = new HashMap<String, PSStation>();
        stationByLoc = new HashMap<String, List<PSStation>>();
    }

    //synchronized method to control simultaneous access
    synchronized public static PSList getInstance()
    {
        if (instance == null)
        {
            // if instance is null, initialize
            instance = new PSList();
        }
        return instance;
    }

    public boolean addStation(PSStation station)
    {
        if(!stationByName.containsKey(station.getName())){
            listOfStations.add(station);
            stationByName.put(station.getName() , station);

            if(stationByLoc.containsKey(station.getLocation()))
                stationByLoc.get(station.getLocation()).add(station);
            else
            {
                ArrayList<PSStation> newList = new ArrayList<PSStation>();
                newList.add(station);
                stationByLoc.put(station.getLocation(), newList); 
            }
            stationCount--;
            return true;
        }
        else
            return false;
    }

    public boolean removeStation(PSStation station)
    {
        if(stationByName.containsKey(station.getName()))
        {
            listOfStations.remove(station);
            stationByName.remove(station.getName());
            stationByLoc.get(station.getLocation()).remove(station);
            stationCount--;
            return true;
        }
        else
            return false;
    }
    
    
    public PSStation searchByName(String name)
    {
        return stationByName.get(name);
    }

    
    public List<PSStation> searchByLoc(String loc)
    {
        return stationByLoc.get(loc);
    }
    
    public int getStationCount()
    {
        return stationCount;
    }
    
    
    public List<PSStation> getStationList()
    {
    	return listOfStations;
    }
    
    public List<Student> getStudentList()
    {
    	return listOfStudents;
    }
}