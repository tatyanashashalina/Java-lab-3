import java.util.ArrayList;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Iterator;

public class Student {
// приватная статическая переменная. Доступ к ней извне мы не имеем
    static private int id = 10;                                      // идентификационный номер
    private String fio;                                             // фамилия и инициалы
    private Group group;                                            // ссылка на группу (объект Group)
    private ArrayList<Double> marks = new ArrayList<Double>();          // контейнер оценок (ArrayList)

    public Student(String fio, Group group, int id) {                // создание студента с указанием ИД и ФИО
        addMarks();
        this.fio = fio;
        this.group = group;
        this.id = id;
    } // присвоить переменные в поля зыбли

    public void setGroup(Group group) {                              // зачисление в группу
        this.group = group;
    }

    public void addMarks () {                                        // добавление оценки
          for (int i= 0; i<15; i++) {
            marks.add ((double)(Math.random()*6)); // должен быть остаток от деления а не умножение. а вообще должно работать. надо проваерить.запустить то есть
        }
     }

    public double average() {                                        // вычисление средней оценки
        double total = 0;
        for (double mark : marks) {
            total = total + mark;
        }
        double averMark = total/ marks.size();
        return averMark;
    }

    public Group getGroup() {
        return group;
    }

    public int getId() {
        return id;
    }

    public String getFio() {
        return fio;
    }

    public static int calculateNewId() {
        // статический метод, который ведет подсчет айди. Изменяет статическую переменную чтобы значения не повторялись и возвращает ее значение.
        id++;
        return id;
    }
}
