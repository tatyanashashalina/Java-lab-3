import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Deanery {

    private HashMap<Integer, Group> listGroups = new HashMap<Integer, Group>();
    private Object Group;
    private ArrayList<Student> losers = new ArrayList<Student>();


    public void addStudenttoGroup() {                          // создание студентов на основе данных из файла
        JSONParser parserStudent = new JSONParser();

        try {
            FileReader reader = new FileReader("src/Students.json");
            Object obj = parserStudent.parse(reader);
            JSONObject jsonList = (JSONObject) obj;
            JSONArray studentsList = (JSONArray) jsonList.get("students");

            for (int numStudent = 0; numStudent < studentsList.size(); numStudent++) {
                JSONObject studentJson = (JSONObject) studentsList.get(numStudent);
                String fio = (String) studentJson.get("fio");
                int numGroup = Integer.parseInt(studentJson.get("group").toString());
                if (!listGroups.containsKey(numGroup))
                   listGroups.put (numGroup, new Group(numGroup));
                // Обращение к статическим переменным и методам осуществляется не через объект (хотя так наверное тоже можно), а через сам класс,
                // т.к. переменная/функция принадлежит именно классу, а не конкретному объекту.
                // т.е. мы написали функцию, к которой будем обращаться при создании ОБЪЕКТОВ, она будем давать нам новый айди и сама изменять внутреннюю переменную, из которой она и ьерет новые айди
                Student newStudent = new Student(fio, listGroups.get(numGroup), Student.calculateNewId());
                listGroups.get(numGroup).addStudent(newStudent);
            }
        }

        catch(Exception e) {
            System.out.println("Error 1!");
        }
    }

    public void statistic() {                                                               //накопление статистики по успеваемости студентов и групп
        for (HashMap.Entry<Integer, Group> item: listGroups.entrySet()) {
            System.out.println("\n\nNumber group: " + item.getKey() + "\t\t\t" + "Average group's mark: " + String.format ("%.1f",item.getValue().averageMark())+".\n");
            item.getValue().statisticGroup();
            getLosers();
        }
    }

    public void movingStudent(int numGroupOne, int numGroupTwo, String fioStudent) {        //перевод студентов из группы в группу

        Student movStudent = listGroups.get(numGroupOne).searchStudent(fioStudent); // возможно мы здесь получаем null вместо студента. поэтому ошибка. Надо добавить проверку вернувшегося из searchStudent объекта - нулл он или нет
        if (movStudent == null)
            return;
        listGroups.get(numGroupOne).deletedStudent(fioStudent);
        listGroups.get(numGroupTwo).addStudent(movStudent);
        movStudent.setGroup(listGroups.get(numGroupTwo));
    }

    private void getLosers() {
        System.out.println("\n\nLoser list:");
        Student student = null;
        for (HashMap.Entry<Integer, Group> item: listGroups.entrySet()) {
            Group checkGroup = item.getValue();

            for (Student checkStudent : checkGroup.getGroupList()) {
                if (checkStudent.average() < 2.5) {
                    student = checkStudent;
                    System.out.println(student.getFio() + "\t\tAverage mark:" + String.format("%.1f", student.average())+".");
                }
            }
        }
    }

    public void deletedStudent(String fio) {                                                //отчисление студентов за неуспеваемость

        for (HashMap.Entry<Integer, Group> item : listGroups.entrySet()){
            Group checkGroup = item.getValue();
            checkGroup.deletedStudent(fio);
        }
            }

    public void updated () {                                                                //сохранение обновленных данных в файлах
        try {
            JSONObject objList = new JSONObject();
            JSONArray updatedlist = new JSONArray();
            
            for (HashMap.Entry<Integer, Group> item : listGroups.entrySet()) {
                Group Dategroup = item.getValue();
                for (Student student : Dategroup.getGroupList()) {
                    String fio = student.getFio();
                    int group = student.getId();

                    JSONObject objStudent = new JSONObject();
                    objStudent.put("fio", fio);
                    objStudent.put("group", group);
                    updatedlist.add(objStudent);
                }
                // оставить один трай и к нему кэтч
                // ты используешь трай с ресусрами, которыйне поддерживается стандартом
                // к тому же ты созлаешь объект в скобках и потом пытаешься его использовать. Думаю вне скобок этот объект уже не существует (объект file)
            }
            objList.put("students", updatedlist);
            FileWriter file = new FileWriter("UPD.txt");
            file.write(objList.toJSONString());
            file.flush();

        }
        catch (Exception e) {
            System.out.println("Error 2!");
        }
    }

    public void choosingHead() {                                                            //инициация выборов старост в группах

        for (HashMap.Entry<Integer, Group> item: listGroups.entrySet()) {
           item.getValue().setHead();
        }
    }
}
