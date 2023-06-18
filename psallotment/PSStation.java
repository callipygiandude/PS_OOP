package psallotment;

import java.util.*;

public class PSStation
{
	private String name;
	private String location;
	private ArrayList<String> compulsorySubjects;
	private ArrayList<branch> branchCriterion;
	private int vacancy;
	private float minCGPA;
	
	
	//constructors
	PSStation(String name, String loc, int vac, float min, ArrayList<branch> crit, ArrayList<String> subj)
	{
		this.name = name;
		this.location = loc;
		this.vacancy = vac;
		this.minCGPA = min;
		this.branchCriterion = new ArrayList<>(crit);
		this.compulsorySubjects = new ArrayList<>(subj);
		
		PSList.getInstance().addStation(this);
		
	}
	
	PSStation(String name, String loc, int vac, float min, ArrayList<branch> crit)
	{
		this.name = name;
		this.location = loc;
		this.vacancy = vac;
		this.minCGPA = min;
		this.branchCriterion = new ArrayList<>(crit);
		this.compulsorySubjects = new ArrayList<>();
		
		PSList.getInstance().addStation(this);
		
	}
	
	PSStation(String name, String loc, int vac, ArrayList<branch> crit)
	{
		this.name = name;
		this.location = loc;
		this.vacancy = vac;
		this.minCGPA = 0.0f;
		this.branchCriterion = new ArrayList<>(crit);
		this.compulsorySubjects = new ArrayList<>();
		
		PSList.getInstance().addStation(this);
		
	}
	
	
	//setters and getters
	public String getName() {
		return name;
	}

	public void setName(String name) {
		PSList plist = PSList.getInstance();
		plist.removeStation(this);
		this.name = name;
		plist.addStation(this);
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		PSList plist = PSList.getInstance();
		plist.removeStation(this);
		this.location = location;
		plist.addStation(this);
	}

	public ArrayList<String> getCompulsorySubjects() {
		return compulsorySubjects;
	}

	public void setCompulsorySubjects(ArrayList<String> compulsorySubjects) {
		this.compulsorySubjects = compulsorySubjects;
	}

	public ArrayList<branch> getBranchCriterion() {
		return branchCriterion;
	}

	public void setBranchCriterion(ArrayList<branch> branchCriterion) {
		this.branchCriterion = branchCriterion;
	}

	public int getStationVacancy() {
		return vacancy;
	}

	public void setStationVacancy(int vacancy) {
		this.vacancy = vacancy;
	}

	public float getMinCGPA() {
		return minCGPA;
	}

	public void setMinCGPA(float minCGPA) {
		this.minCGPA = minCGPA;
	}

	public boolean equals(PSStation other)
	{
		if((this.name == other.getName())&&(this.location==other.getLocation()))
		{
			return true;
		}
		
		return false;
	}
	
	public String toString() {
		   return "Name: "+this.name+"\nLocation: "+this.location+"\nMin CG cutoff: "+this.minCGPA+"\nCompulsory courses"
		         +this.compulsorySubjects+"\nAllowed branches"+this.branchCriterion;
		}

}