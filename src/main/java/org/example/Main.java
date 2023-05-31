package org.example;

import javax.xml.bind.JAXBException;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {
    public static void mainMenuText(){
        System.out.println();
        System.out.println("Choose one of the available options: ");
        System.out.println("1 - Show saved Students");
        System.out.println("2 - Add a new Student");
        System.out.println("3 - Remove Student");
        System.out.println("4 - Search for Student");
        System.out.println("0 - Exit");
    }

    public static void main(String[] args) throws JAXBException, FileNotFoundException {
        StudentManager studMan = new StudentManager();
        Scanner scan = new Scanner(System.in);
        studMan.readFromXML();

        String usrInp;
        boolean finishThis = false;

        while (!finishThis) {
            mainMenuText();
            usrInp = scan.nextLine();

            switch (usrInp) {
                case "1":
                    System.out.println();
                    studMan.printStudents();
                    break;
                case "2":
                    System.out.println();
                    studMan.addStudent();
                    break;
                case "3":
                    System.out.println();
                    studMan.removeStud();
                    break;
                case "4":
                    System.out.println();
                    studMan.findStud();
                    break;
                case "0":
                    finishThis = true;
                    System.out.println("Exiting...");
                    break;
                default:
                    System.out.println("There is no such option" + "\n");
            }
        }
    }
}