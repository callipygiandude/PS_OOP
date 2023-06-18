package psallotment;

import java.util.*;

public class Student extends User implements Runnable{
	private static ArrayList<Student> listOfStudents = new ArrayList<Student>();
	private float cgpa;
	private String id;
	private branch br;
	private ArrayList<String> subjects;
	private ArrayList<String> preferences;
	private boolean frozen;
	private PSStation currentAllotment;
	
	Student(String name, String email, String password, float cgpa, String id, branch br, ArrayList<String> subjects) {
		super(name, email, password);
		this.cgpa = cgpa;
		this.id = id;
		this.br = br;
		this.subjects = subjects;
		this.preferences = new ArrayList<>();
		this.frozen = false;
		this.currentAllotment = null;
		
		listOfStudents.add(this);		
	}
	
	public float getCgpa() {
		float temp = cgpa;
		return temp;
	}
	
	public void setCgpa(float cg) {
		cgpa = cg;
	}
	
	public String getId() {
		String temp = new String(id);
		return temp;
	}
	
	public void setId(String i) {
		id = i;
	}
	
	public branch getBranch() {
		branch temp = br;
		return temp;
	}
	
	public void setBranch(branch b) {
		br = b;
	}
	
	public ArrayList<String> getSubjects() {
		ArrayList<String> temp = new ArrayList<String>(subjects);
		return temp;
	}
	
	public void addSubject(String sub) {
		subjects.add(sub);
	}
	
	public ArrayList<String> getPreferences() {
		ArrayList<String> temp = new ArrayList<String>(preferences);
		return temp;
	}
	
	public void addPreference(String ps, int prefNo) {
		preferences.add(prefNo, ps);
	}
	/*public String getLocation(String s) {
		
		return StationList.stationLocations.get(s);
	}*///idk if req
	
	public void updatePreferences(int oldPrefNo, int newPrefNo) {
		String psStation = preferences.get(oldPrefNo);
		preferences.remove(oldPrefNo);
		preferences.add(newPrefNo, psStation);
	}
	
	public void removePreference(int prefNo) {
		preferences.remove(prefNo);
	}
	
	public PSStation getCurrentAllotment() {
		return currentAllotment;
	}

	public void setCurrentAllotment(PSStation currentAllotment) {
		this.currentAllotment = currentAllotment;
	}

	public boolean getFrozen() {
		return frozen;
	}

	public void setFrozen(boolean frozen) {
		this.frozen = frozen;
	}
	
	public static List<Student> getStudentList(){
		return listOfStudents;
	}
	
	public void printDetails()
	{
		System.out.println("Here are the required details.");
		System.out.println("Name: " + this.getName());
		System.out.println("ID: " + this.getId());
		System.out.println("CGPA: " + this.getCgpa());
		System.out.println("Branch: " + this.getBranch());
		if (this.getCurrentAllotment() == null)
			System.out.println("Current PS Station: Not applicable");
		else
			System.out.println("Current PS Station: " + this.getCurrentAllotment());
		System.out.println("Preference finalized: " + (this.getFrozen() ? "Yes" : "No"));
	}
	

	@Override
	public void run() {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		while(true){ // main menu
			System.out.println("Choose an option");
			System.out.println("1. View List of Stations");
			System.out.println("2. Provide Preference List");
			System.out.println("3. View allotted station");
			System.out.println("4. Logout");

			int op = sc.nextInt();
			sc.nextLine();
			switch(op){
				case 1:
					System.out.println("List of Stations");
					for(PSStation station : PSList.getInstance().getStationList()){
						System.out.println(station.toString() + "\n");
					}
					break;

				case 2:
					System.out.println("The current student preferences are:-");
					System.out.println(getPreferences());
					System.out.println("1. Add preference \n2.Remove preference");
					op = sc.nextInt();
					sc.nextLine();
					switch(op)
					{
					case 1:
						System.out.println("Enter station name to add");
						String station ="";
						while(station == "")
						{
							station = sc.nextLine();
							if(PSList.getInstance().searchByName(station)!=null)
								break;
							else
							{
								System.out.println("This station does not exist! Do you want to enter another station?");
								String choice = sc.nextLine();
								if (!choice.equalsIgnoreCase("Yes"))
									break;
								station = "";
							}
						}
						System.out.println("Enter preference position");
						synchronized(this) {
							int idx = sc.nextInt();
							sc.nextLine();
							addPreference(station, idx - 1);}
						break;
					case 2:
						System.out.println("Enter preference number to remove");
						synchronized(this) {
							int idx = sc.nextInt();
							sc.nextLine();
							removePreference(idx - 1);}
						break;
					}
					break;

				case 3:
					if(this.getFrozen()){
						System.out.println("Your allotted station is: ");
						System.out.println(this.getCurrentAllotment());
						System.out.println("You have frozen this allotment.");
					}else{
						if (currentAllotment == null) {
							System.out.println("No station alloted yet");
						}
						else {
						System.out.println("You have been alloted the station " + currentAllotment + ".");
						System.out.println("Do you want to accept the current allotment? (Yes/No): ");
						String choice = sc.nextLine();
						if (choice.equalsIgnoreCase("Yes")) {
							setFrozen(true);
						} else if (choice.equalsIgnoreCase("No")) {
							getCurrentAllotment().setStationVacancy(getCurrentAllotment().getStationVacancy()+1);
							setFrozen(false);
							for (int i = 0; i < preferences.size(); i++) {
								if (preferences.get(i).equals(currentAllotment.getName())) {
									removePreference(i);
									break;
								}
							}
							currentAllotment = null;
						}
						}
					}
					break;
				case 4: op = 4;
					break;
			}
			if (op == 4)
				break;
		}
	}
	
}