import java.util.ArrayList;

public class Group {

    private int title;
    private ArrayList<Student> GroupList = new ArrayList<Student>();
    private Student head;

    public Group(int title) {                               // создание группы с указанием названия
        this.title = title;
    }

    public void addStudent(Student student) {              // добавление студента
        GroupList.add(student);
        student.setGroup(this);
    }

    public int getTitle() {
        return title;
    }
    public ArrayList<Student> getGroupList() {
        return GroupList;
    }


    public void setHead() {                                 // избрание старосты
        this.head = GroupList.get((int)Math.random()*(GroupList.size()));
    }

    public Student searchStudent(int id) {                  // поиск студента по ФИО или ИД
        for (Student student : GroupList)
            if (id == student.getId())
                return student;
        return null;
    }

    public Student searchStudent(String fio) {              // поиск студента по ФИО или ИД
        for (Student student : GroupList)
            if (fio.equals (student.getFio()))
                return student;
        return null;
    }

    public double averageMark() {                          // вычисление среднего балла в группе
        double averageGroup = 0;
        for (Student student : GroupList) {
            averageGroup = (averageGroup + student.average());
        }
        return (averageGroup/GroupList.size());
    }

    public void deletedStudent (String fio) {             // исключение студента из группы
        GroupList.remove(searchStudent(fio));
    }
    public void deletedStudent (Student student) {             // исключение студента из группы
        GroupList.remove(student);
    }

    public void statisticGroup() {
        for (Student student : GroupList) {
            System.out.println("FIO student: " + student.getFio() + "\t\t" + "Average mark: " + String.format ("%.1f", student.average())+".");
        }
    }
}
