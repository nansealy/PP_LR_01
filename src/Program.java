package src;

import java.util.Scanner;

public class Program {
    private static final String DTD_PATH = "group.dtd";

    public static void main(String[] args) {
        try {
            System.out.println("Введите входной файл (название_файла.xml)");
            Scanner scanner = new Scanner(System.in);
            String fInName = scanner.nextLine();
            System.out.println("Введите входной файл (название_файла.xml)");
            String fOutName = scanner.nextLine();

            Group group = (new DomReader()).read(fInName);
            countAverage(group);
            (new DomWriter()).write(group, fOutName, DTD_PATH);

            scanner.close();
            System.out.println("Успешно!");
        } catch(Exception e) {
            System.out.println(e.getMessage());
        }
    }

    private static void countAverage(Group group) {
        int sLen = group.students.size();
        
        for(int i = 0; i < sLen; i++) {
            Student student = group.students.get(i);
            int mLen = student.subjects.size(), sum = 0;

            for(int j = 0; j < mLen; j++) {
                sum += student.subjects.get(i).mark;
            }

            student.avg = (double)(sum / mLen);
        }
    }
}