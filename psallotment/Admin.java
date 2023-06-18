package psallotment;

import java.util.*;
import java.lang.Thread;

public class Admin extends User implements Runnable{

	public Admin(String name, String email, String password){
		super(name, email, password);
	}

	public void updateLocation(PSStation station, String location){
		station.setLocation(location);
	}

	public void updateVacancy(PSStation station, int vacancy){
		station.setStationVacancy(vacancy);
	}

	public void updateMinCgpa(PSStation station, float minCGPA){
		station.setMinCGPA(minCGPA);
	}

	public void updateCompulsorySubjects(PSStation station, ArrayList<String> compulsorySubjects){
		station.setCompulsorySubjects(compulsorySubjects);
	}

	public void updateBranchCriterion(PSStation station, ArrayList<branch> branchCriterion){
		station.setBranchCriterion(branchCriterion);
	}

	public void addPSStation(PSStation station){
		PSList plist = PSList.getInstance(); //
		plist.addStation(station);
	}

	public void doAllotment(){
		PSList plist = PSList.getInstance(); //
		List<Student> studentList = Student.getStudentList();
		Collections.sort(studentList, (s1, s2) -> {
			if (s2.getCgpa() - s1.getCgpa() > 0)
				return 1;
			else if (s2.getCgpa() - s1.getCgpa() == 0)
				return 0;
			else
				return -1;
		});
		
		for(Student student : studentList){
			if(student.getFrozen() == true){
				continue;
			}
			for(String pref : student.getPreferences()){
				PSStation station = plist.searchByName(pref); //changes
				if(station.getStationVacancy() <= 0){
					continue;
				}
				if(student.getCgpa() < station.getMinCGPA()){
					continue;
				}
				if(!station.getBranchCriterion().contains(student.getBranch())){
					continue;
				}
				if(!student.getSubjects().containsAll(station.getCompulsorySubjects())){
					continue;
				}
				//
				if(student.getCurrentAllotment()!=null)
					if(!station.getName().equals(student.getCurrentAllotment()))
						student.getCurrentAllotment().setStationVacancy(student.getCurrentAllotment().getStationVacancy()+1);
					
				student.setCurrentAllotment(station);
				station.setStationVacancy(station.getStationVacancy()-1);
				break;
			}
		}
	}


	@Override
	public void run() {
		Scanner sc = new Scanner(System.in);

		while(true) {//main menu
			System.out.println("Choose an option");
			System.out.println("1. Manage Students");
			System.out.println("2. Manage PS Stations");
			System.out.println("3. Start Station allocation");
			System.out.println("4. Logout");
			int op = sc.nextInt();
			sc.nextLine();
			switch(op)
			{
			case 1://case manage student
				while(true) {
					System.out.println("Enter Student name or ID to edit");
					String ident = sc.nextLine();
					List<Student> studentList = Student.getStudentList();
					Student student = null;
					for(Student s : studentList)
					{
						if(s.getName().equalsIgnoreCase(ident)||s.getId().equalsIgnoreCase(ident))
						{
							student = s;
						}	
					}
					if(student == null)
					{
						System.out.print("No student with that name or ID found \nEnter 1 to search  again, 2 to go back");
						int opt = sc.nextInt();
						sc.nextLine();

						if(opt ==1)
							continue;
						else
							break;
					}
					student.printDetails();
					while(true) {
						System.out.println("Choose an option");
						System.out.println("1. Change Branch");
						System.out.println("2. Change CGPA");
						System.out.println("3. Change pref list");
						System.out.println("4. Back");
						int opt = sc.nextInt();
						sc.nextLine();

						switch(opt)
						{
						case 1:
							System.out.println("Enter new branch");
							branch br = null;
							while(br == null)
							{
								String brch = sc.nextLine();
								try
								{
									br = branch.valueOf(brch);
								}
								catch(Exception e)
								{
									System.out.println("Enter a valid branch!");
								}
							}
							synchronized(student)
							{
								student.setBranch(br);
							}
							break;

						case 2:
							System.out.println("Enter new CGPA");
							{
								float cgpa = 0;
								while(cgpa == 0)
								{
									try 
									{
										cgpa = sc.nextFloat();
										sc.nextLine();
									}
									catch(Exception e)
									{
										cgpa = 0;
										System.out.println("Enter valid CGPA");
										continue;
									}
									if(cgpa<0||cgpa>10)
									{
										System.out.println("Enter valid CGPA!");
										cgpa = 0;
									}
								}
								synchronized(student) {
									student.setCgpa(cgpa);
								}
							}
							break;

						case 3:
							System.out.println("The current student preferences are:-");
							System.out.println(student.getPreferences());
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
										System.out.println("This station does not exist! Enter valid station");
										station = "";
									}
								}
								System.out.println("Enter preference position");
								synchronized(student) {
									int idx = sc.nextInt();
									sc.nextLine();
									student.addPreference(station, idx - 1);}
								break;
							case 2:
								System.out.println("Enter preference number to remove");
								synchronized(student) {
									int idx = sc.nextInt();
									sc.nextLine();
									student.removePreference(idx-1);}
								break;
							}
							break;

						case 4:
						}
						if(opt == 4)
							break;
					}
					break;
				}
				break;
			case 2://manage PSStation
				System.out.println("Choose an option");
				System.out.println("1. Add new PS Station");
				System.out.println("2. Edit existing station");
				System.out.println("3. Back");
				op = sc.nextInt();
				sc.nextLine();
				switch(op)
				{
				case 1:
					System.out.println("Enter PSStation name");
					String name = sc.nextLine();
					System.out.println("Enter PSStation location");
					String loc = sc.nextLine();
					System.out.println("Enter number of vacancies in station");
					int vac = sc.nextInt();
					sc.nextLine();
					System.out.println("Enter minimum CGPA criteria, or leave blank for no criteria");
					float minCGPA = -1;
					while(minCGPA ==-1)
					{
						String s = sc.nextLine();
						if(s.equals("")) {
							minCGPA = 0;
							break;
						}

						try
						{
							minCGPA = Float.parseFloat(s);
						}
						catch(Exception e)
						{
							System.out.println("Invalid CGPA!");
							continue;
						}
						if(minCGPA<0||minCGPA>10)
						{
							System.out.println("Invalid CGPA!");
							minCGPA = -1;
						}
					}
					System.out.println("Enter accepted branches seperated by commas, or leave blank for all");
					ArrayList<branch> branchEnum;
					while(true) {
						String st = sc.nextLine().trim();
						String[] branches;
						branchEnum = new ArrayList<branch>();
						if(st.length() !=0){
							branches = st.trim().split(",");
							try {
								for(String s : branches) {
									branchEnum.add(branch.valueOf(s));
								}
								break;
							}
							catch(Exception e) {
								System.out.println("Enter valid branch list!");
								continue;
							}
						}
						else {
							branchEnum.addAll(Arrays.asList(branch.values()));
							break;
						}
					}
					System.out.println("Enter compulsory subjects seperated by commas, or leave blank for none");
					ArrayList<String> compulsorySubjects = new ArrayList<String>();
					while(true) {
						String[] subjects = sc.nextLine().split(",");
						compulsorySubjects = new ArrayList<String>();
						if(subjects.length !=0){
							try {
								for(String s : subjects) {
									compulsorySubjects.add(s);
								}
								break;
							}
							catch(Exception e) {
								System.out.println("Enter valid branch list!");
								continue;
							}
						}
						else
							break;
					}

					System.out.println("New PS Station created succesfully\n"+(new PSStation(name, loc,vac,minCGPA,branchEnum,compulsorySubjects)));
					break;

				case 2:
					System.out.println("Enter name of station");
					PSStation stat;
					while(true) {
						String stname = sc.nextLine();
						if(PSList.getInstance().searchByName(stname)!=null)
						{
							stat = PSList.getInstance().searchByName(stname);
							break;
						}
						else {
							System.out.println("Enter valid station name");
							continue;
						}
					}

					while(true) {
						System.out.println(stat);
						System.out.println("Choose an option");
						System.out.println("1. Change station location");
						System.out.println("2. Change CGPA requirements");
						System.out.println("3. Change subject requirements");
						System.out.println("4. Change branch requirements");
						System.out.println("5. Change vacancies");
						System.out.println("6. Back");
						op = sc.nextInt();
						sc.nextLine();

						switch(op) {
						case 1:
							System.out.println("Enter new location");
							stat.setLocation(sc.nextLine());
							break;

						case 2:
							System.out.println("Enter new CGPA");
							{
								float cgpa = 0;
								while(cgpa == 0)
								{
									try 
									{
										cgpa = sc.nextFloat();
										sc.nextLine();
									}
									catch(Exception e)
									{
										cgpa = 0;
										System.out.println("Enter valid CGPA");
										continue;
									}
									if(cgpa<0||cgpa>10)
									{
										System.out.println("Enter valid CGPA!");
										cgpa = 0;
									}
								}
								stat.setMinCGPA(cgpa);							
							}
							break;

						case 3:
							while(true) {
								System.out.println("Choose an option");
								System.out.println("1. Add subject");
								System.out.println("2. Remove subject");
								System.out.println("3. Back");

								ArrayList<String> newSubjectList = stat.getCompulsorySubjects();
								System.out.println(newSubjectList);
								int opt = sc.nextInt();
								sc.nextLine();
								switch(opt) {
								case 1:
									System.out.println("Enter new subject requirement");
									newSubjectList.add(sc.nextLine());
									stat.setCompulsorySubjects(newSubjectList);
									break;

								case 2:
									System.out.println("Enter subject name to remove");
									while(true) {
										String s = sc.nextLine();
										if(newSubjectList.contains(s)) {
											newSubjectList.add(s);
											break;
										}
										else
											System.out.println("Enter valid subject");
									}
									break;

								case 3:
									op =3;
								}

								if(op ==3)
									break;
							}
							break;

						case 4:
							while(true) {
								System.out.println("Choose an option");
								System.out.println("1. Add branch");
								System.out.println("2. Remove branch");
								System.out.println("3. Back");

								ArrayList<branch> newBranchList = stat.getBranchCriterion();
								System.out.println(newBranchList);
								int opt = sc.nextInt();
								sc.nextLine();
								switch(opt) {
								case 1:
									System.out.println("Enter new subject requirement");
									while(true) {
										try {
											newBranchList.add(branch.valueOf(sc.nextLine()));
											break;
										}
										catch(Exception e)
										{
											System.out.println("Invalid branch!");
											continue;
										}
									}

									stat.setBranchCriterion(newBranchList);
									break;

								case 2:
									System.out.println("Enter subject name to remove");
									while(true) {
										branch b = branch.valueOf(sc.nextLine());
										if(newBranchList.contains(b)) {
											newBranchList.add(b);
											break;
										}
										else
											System.out.println("Enter valid subject");
									}
									break;

								case 3:
									opt =3;
								}

								if(opt ==3)
									break;
							}
							break;

						case 5:
							System.out.println("Enter new number of vacancies");
							int vacancies = sc.nextInt();
							sc.nextLine();
							stat.setStationVacancy(vacancies);
							break;

						case 6:
							op =6;
						}
						if(op==6)
							break;
					}
				}
				break;
			case 3:
				this.doAllotment();
				System.out.println("Allotment succesful!");
				break;
				
			case 4:
				//op = 4;
			}
			if(op == 4)
				break;
		}
	}
}
