public class Main {

    public static void main(String[]args) {

        Deanery check = new Deanery();

        check.addStudenttoGroup();
        check.movingStudent(101,102, "Янченко  Р.С.");
        check.deletedStudent("Аносов Ю.Я.");
        check.statistic();
        check.updated();
        check.choosingHead();

    }
}
