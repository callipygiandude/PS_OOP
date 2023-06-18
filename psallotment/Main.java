package psallotment;

import java.util.*;

public class Main {
	public static void main(String args[]) {
		ArrayList<branch> b = new ArrayList<>();
		b.add(branch.CSE);
		ArrayList<String> sub = new ArrayList<>(Arrays.asList("CP", "M1"));
		PSStation st1 = new PSStation("Google", "Mumbai", 2, 9, b, sub);//CSE.
		b.add(branch.EEE);
		b.add(branch.ENI);
		sub = new ArrayList<>(Arrays.asList("ES", "DD"));
		PSStation st2 = new PSStation("Texas Instr", "Bangalore", 1, 8, b, sub);//CSE,EEE,ENI.
		sub.clear();
		b.add(branch.CHEM);
		b.add(branch.CIV);
		b.add(branch.MANU);
		b.add(branch.MECH);
		PSStation st3 = new PSStation("TCS", "Delhi", 2, 7, b, sub);//all branches except ECE
		b.clear();
		b.add(branch.CSE);
		b.add(branch.ECE);
		PSStation st4 = new PSStation("Microsoft", "Mumbai", 3, 7.5f, b, sub);//CSE,ECE
		b.clear();
		b.add(branch.CHEM);
		b.add(branch.CIV);
		b.add(branch.MANU);
		b.add(branch.MECH);
		b.add(branch.CSE);
		PSStation st5 = new PSStation("HP", "Kolkata", 6, 5, b, sub);//CHEM,CIV,MANU,MECH
		PSList.getInstance().addStation(st1);
		PSList.getInstance().addStation(st2);		
		PSList.getInstance().addStation(st3);
		PSList.getInstance().addStation(st4);
		PSList.getInstance().addStation(st5);


		//		ArrayList<String> sub = new ArrayList<>();
		sub = new ArrayList<>(Arrays.asList("CP", "M2", "M1"));
		String bitsid = "f20202913";
		String email = bitsid + "@gmail.com";
		ArrayList<String> pref = new ArrayList<>(Arrays.asList("Google", "HP","TCS"));
		Student s1 = new Student("Manoj", email, "manoj123", (float)8.7, bitsid, branch.CSE, sub);
		int i=0;
		for(String itrString : pref)
		{
			s1.addPreference(itrString, i);
			i++;
		}
		sub.clear();
		sub = new ArrayList<>(Arrays.asList("OOP", "M3", "DD"));
		bitsid = "f20190543";
		email = bitsid + "@gmail.com";
		Student s2 = new Student("Sunil", email, "sunil9876", (float)9.63, bitsid, branch.MANU, sub);
		i =0;
		for(String itrString : new ArrayList<String>(Arrays.asList("TCS","HP","Microsoft")))
		{
			s2.addPreference(itrString, i);
			i++;
		}
		sub.clear();
		sub = new ArrayList<>(Arrays.asList("ES", "DD"));
		bitsid = "f20190923";
		email = bitsid + "@gmail.com";
		Student s3 = new Student("Neeraj", email, "lucifer689", (float)9.63, bitsid, branch.EEE, sub);
		i =0;
		for(String itrString : new ArrayList<String>(Arrays.asList("TCS","HP","Microsoft")))
		{
			s3.addPreference(itrString, i);
			i++;
		}
		sub.clear();
		sub = new ArrayList<>(Arrays.asList("ES", "DD"));
		bitsid = "f20210543";
		email = bitsid + "@gmail.com";
		Student s4 = new Student("Ashish", email, "ashish9876", (float)9.63, bitsid, branch.CHEM, sub);
		i=0;
		for(String itrString : new ArrayList<String>(Arrays.asList("Texas Instr","HP","Microsoft")))
		{
			s4.addPreference(itrString, i);
			i++;
		}

		Admin admin = new Admin("admin", "admin@gmail.com", "root123");
		Scanner sc = new Scanner(System.in);
		while (true) {
			System.out.println("Hello, what do you want to do?\n1. Login as student\n2. Login as admin\n3. Register as student\n4. Exit portal");
			int op = sc.nextInt();
			sc.nextLine();
			switch(op) {
			case 1: System.out.print("Enter id: ");// login as student
			String id = sc.nextLine();
			System.out.print("Enter password: ");
			String pwd = sc.nextLine();
			boolean flag = false;
			for (Student s: Student.getStudentList()) {
				if (id.equals(s.getId()) && pwd.equals(s.getPassword())) {
					// student accepted
					Thread studentThread = new Thread(s);
					studentThread.start();
					try {
						studentThread.join();
					} catch (Exception e) {
						System.out.println(e.toString());
					}

					flag = true;
					break;
				}
			}
			if(!flag)
				System.out.println("Wrong ID or password!");
			else {
				flag = false;
			}
			break;

			case 2: System.out.print("Enter username: ");// login as admin
			String username = sc.nextLine();
			System.out.print("Enter password: ");
			String pass= sc.nextLine();
			if (username.equals(admin.getName()) && pass.equals(admin.getPassword())) {
				// admin accepted
				Thread adminThread = new Thread(admin);
				adminThread.start();
				try {
					adminThread.join();
				} catch (Exception e) {
					System.out.println(e.toString());
				}
			}
			else {
				System.out.println("Wrong username or password, please try again");
			}
			break;
			case 3: // register new
				System.out.println("Enter name");
				String name = sc.nextLine();
				
				System.out.println("Enter BITS ID number");
				String idno = sc.nextLine();
				
				System.out.println("Enter password");
				String password = sc.nextLine().trim();
				System.out.println("Enter CGPA");
				float cgpa;
				cgpa = 0;
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
				
				System.out.println("Enter branch");
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
				System.out.print("Enter subjects seperated by commas");
				ArrayList<String> subjs = new ArrayList<String>(Arrays.asList(sc.nextLine().trim().split(",")));
				new Student(name,idno+"@gmail.com",password,cgpa,idno,br,subjs);
				break;
				

			case 4: // exit code
				System.out.println("Exiting application");
				break;

			default: System.out.println("Wrong choice, please try again.");
			}
			if (op == 4)
				break;
		}


	}

}
