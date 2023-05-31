package org.example;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;


public class StudentManager {

    public ArrayList<Students> students = new ArrayList<>();
    public ArrayList<Students> tempStudents = new ArrayList<>();
    private static final Logger LOGGER
            = LogManager.getLogger(StudentManager.class);

    public void addStudent() throws JAXBException, FileNotFoundException {
        LOGGER.info("Execute: addStudent():");
        try {
            Students newStud = new Students();
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter student Name");
            newStud.setName(scanner.nextLine());
            System.out.println("Enter student ID");
            newStud.setStudentID(scanner.nextLine());
            System.out.println("Enter student E-mail");
            newStud.setEmail(scanner.nextLine());

            System.out.println("Now it's time to enter students birthday date" + "\n");

            System.out.println("Enter the Day of students birthday (Two digits)");

            String tempDay = scanner.nextLine();
            System.out.println("Enter the Month of students birthday (Two digits)");
            String tempMonth = scanner.nextLine();

            System.out.println("Enter the Year of students birthday (At least four digits)");
            String tempYear = scanner.nextLine();

            String dobTempS = tempYear + "-" + tempMonth + "-" + tempDay;

            LocalDate dobTemp = LocalDate.parse(dobTempS);
            DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd/MM/yyyy");

            String formattedDate = dobTemp.format(myFormatObj);
            newStud.setDateOfBirth(formattedDate);

            students.add(newStud);
            LOGGER.debug("Added a new student:" + newStud);
            saveToXML();
        } catch (FileNotFoundException e){
            addStudent();
        } catch (DateTimeParseException e) {
            System.out.println("You did not provide the correct birthday date");
            LOGGER.error("Incorrect date input");
        }
    }

    public void printStudents() {
        LOGGER.info("Execute: printStudents():");
        if (students != null) {

            int i = 1;
            for (Students x : students) {
                System.out.print("#" + i + "    ");
                System.out.println(x);
                i++;
            }
        } else {
            LOGGER.debug("There are no records, try adding a new student instead");
        }
    }

    public void printStudents(List<Students> a) {
        LOGGER.info("Execute: printStudents(filtStud):");
        if (a != null) {
            System.out.println("Student records that fulfil the search criteria:" + "\n");
            for (Students x : a) {
                System.out.println(x);
            }
        } else {
            System.out.println("There are no records, try adding a new student instead");
        }
    }

    public void findStud() {
        LOGGER.info("Execute: findStud():");
        Scanner scan = new Scanner(System.in);
        Scanner pauser = new Scanner(System.in);
        List<Students> filtStud;

        System.out.println("By what criteria would you like the search by?");
        System.out.println("1 - Name");
        System.out.println("2 - Student ID");
        System.out.println("3 - E-Mail");
        String usrSrchInp = scan.nextLine();

        switch (usrSrchInp) {
            case "1":
                LOGGER.debug("Searching through student records by Name");
                System.out.println("Enter the name of the student that you with to find");
                String usrSrchFilt = scan.nextLine();
                filtStud = students.stream().filter(q -> q.getName()
                        .startsWith(usrSrchFilt)).collect(Collectors.toList());
                printStudents(filtStud);
                System.out.println("\n" +"Press any key to continue...");
                pauser.nextLine();
                break;
            case "2":
                LOGGER.debug("Searching through student records by Student ID");
                System.out.println("Enter the Student ID of the student that you with to find");
                usrSrchFilt = scan.nextLine();
                filtStud = students.stream().filter(q -> q.getStudentID()
                        .startsWith(usrSrchFilt)).collect(Collectors.toList());
                printStudents(filtStud);
                System.out.println("\n" +"Press any key to continue...");
                pauser.nextLine();
                break;
            case "3":
                LOGGER.debug("Searching through student records by Date of Birth");
                System.out.println("Enter the E-Mail of the student that you with to find");
                usrSrchFilt = scan.nextLine();
                filtStud = students.stream().filter(q -> q.getEmail()
                        .startsWith(usrSrchFilt)).collect(Collectors.toList());
                printStudents(filtStud);
                System.out.println("\n" +"Press any key to continue...");
                pauser.nextLine();
                break;
            default:
                System.out.println("There was no such option");
                break;
        }
    }

    public void removeStud() throws JAXBException, FileNotFoundException {
        LOGGER.info("Execute: removeStud():");
        try {
            if (students != null) {
                printStudents();
                Scanner scanner = new Scanner(System.in);
                System.out.println("Which student # would you like to see deleted?");
                int i = scanner.nextInt();
                i--;
                LOGGER.debug("Removing student: " + i);
                students.remove(i);
                saveToXML();
            } else {
                System.out.println("There are no records, try adding a new student instead");
            }
        } catch (IndexOutOfBoundsException | InputMismatchException e) {
            System.out.println("A student with provided # does not exist.");
            LOGGER.error("Incorrect input");
        }
    }

    public void saveToXML() throws JAXBException, FileNotFoundException {
        LOGGER.info("Execute: saveToXML():");
        try {
            JAXBContext context = JAXBContext.newInstance(Students.class, StudentList.class);

            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);

            StudentList objectListWrapper = new StudentList();
            objectListWrapper.setStudentList(students);

            LOGGER.debug("Saving to five Students.xml");
            marshaller.marshal(objectListWrapper, new FileOutputStream("Students.xml"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void readFromXML() throws JAXBException, FileNotFoundException {
        LOGGER.info("Execute: readFromXML():");
        try {
            JAXBContext context = JAXBContext.newInstance(Students.class, StudentList.class);

            Unmarshaller unmarshaller = context.createUnmarshaller();

            StudentList objectListWrapper = (StudentList) unmarshaller
                    .unmarshal(new FileInputStream("Students.xml"));

            tempStudents = objectListWrapper.getStudentList();
            if (tempStudents == null) {
                LOGGER.debug("The file was corrupted. Deleting file.");
                File file = new File("Students.xml");
                file.delete();
            } else {
                LOGGER.debug("File was successfully read.");
                students = tempStudents;
            }
        } catch (Exception e) {
            LOGGER.error("File missing");
            System.out.println("Students.xml file was not found. A new file will be created");
        }
    }
}
