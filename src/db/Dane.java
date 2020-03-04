package db;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Dane {
    private Connection con;
    private Statement stmt;
   private ResultSet rs;
   private PreparedStatement prpstmt;

   public Dane() {
            try
    {
        Class.forName("com.mysql.jdbc.Driver");
         con = DriverManager.getConnection("jdbc:mysql://localhost:3306/testQuiz", "root", "12qw34er");
         stmt = con.createStatement();

         rs = stmt.executeQuery("select * from Accounts");
        while (rs.next())
            System.out.println(rs.getInt(1) + "  " + rs.getString(2) + "  " + rs.getString(3));
        //con.close();
    }catch(Exception e)

    {
        System.out.println(e);
    }

}

    public ArrayList<String> getResult(int quizID){
        PreparedStatement ps = null;
        ArrayList<String> results = new ArrayList<>();
        ResultSet rs1;
        try {
            ps = con.prepareStatement("SELECT * FROM testQuiz.Results where `idQuiz`="+quizID+";");
            rs1=ps.executeQuery();
            while (rs1.next()) results.add(this.getAccountEmail(rs1.getInt(1))+" "+rs1.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return results;
    }


public void setResult(int accountID, int quizID,double result){
    PreparedStatement ps = null;
    try {
        stmt.executeUpdate("DELETE FROM `testQuiz`.`Results` WHERE `idAccount`="+accountID+" AND `idQuiz` ="+quizID+";");


        ps = con.prepareStatement(" INSERT INTO `testQuiz`.`Results` (`idAccount`, `idQuiz`, `result`) VALUES (?, ?, ?);");
        ps.setString(1, String.valueOf(accountID));
        ps.setString(2, String.valueOf(quizID));
        ps.setString(3, String.valueOf(result));
        ps.execute();



    } catch (SQLException e) {
        e.printStackTrace();
    }
}

public int getCorrectAnswerID(int questionID){
       int ID=-1;
    System.out.println(questionID);
    try {
    PreparedStatement ps = con.prepareStatement("select * from Answers where idQuestions = ? and validation = 1;");
    ps.setString(1, String.valueOf(questionID));
    rs = ps.executeQuery();
    rs.next();
    ID=rs.getInt(1);
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return ID-1;
}

public ArrayList<String> getQuizes(){
    ArrayList<String> quiz = new ArrayList<>();
    try {
        rs = stmt.executeQuery("select * from Quiz");
        while (rs.next()) quiz.add(rs.getString(2));
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return quiz;
}
    public ArrayList<String> getQuestions(int quizID){
        ArrayList<String> questions = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from Questions where idQuiz = ?;");
            ps.setString(1, String.valueOf(quizID));
            rs = ps.executeQuery();
            while (rs.next()) questions.add(rs.getString(3));
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return questions;
    }
    public ArrayList<String> getAnswers(int questionID){
        System.out.println("Hello im getting answers");
        ArrayList<String> answers = new ArrayList<>();
        try {
            PreparedStatement ps = con.prepareStatement("select * from testQuiz.Answers where idQuestions = ?;");
            ps.setString(1, String.valueOf(questionID));
            rs = ps.executeQuery();
            System.out.println("Hello im getting answers");
            while (rs.next()) {answers.add(rs.getString(3));
                System.out.println(rs.getString(3));}
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return answers;
    }

public int getQuizID(String title) throws SQLException {
    PreparedStatement ps = con.prepareStatement("select * from Quiz where title = ?;");
    ps.setString(1,title);
    rs = ps.executeQuery();
    rs.next();

return rs.getInt(1);

}
    public String getAccountEmail(int idAccount) throws SQLException {
        PreparedStatement ps = con.prepareStatement("select * from Accounts where idAccount = ?;");
        ps.setString(1, String.valueOf(idAccount));
        rs = ps.executeQuery();
        rs.next();

        return rs.getString(2);

    }
    public int getQuestionID(String question) throws SQLException {

        PreparedStatement ps = con.prepareStatement("select * from Questions where text = ?;");
        ps.setString(1,question);
        rs = ps.executeQuery();
        rs.next();
        System.out.println(question+"ID"+rs.getInt(1));
        return rs.getInt(1);
    }
    private int setUniqueID(String table) throws SQLException {

            PreparedStatement ps = con.prepareStatement("select * from testQuiz."+table+";");
           // ps.setString(1,table);
            rs= ps.executeQuery();
        while(rs.next());

        if (!rs.previous())
            return 1;

        return rs.getInt(1)+1;
    }

    public int porownaj(String email, String password, int accRole){
       int idAccount=-1;
        try {
            rs = stmt.executeQuery("select * from Accounts");
            while (rs.next()){
                System.out.println(rs.getString(2));
                if (email.trim().equals(rs.getString(2))&&password.trim().equals(rs.getString(3))&&rs.getInt(4)==accRole){
                    idAccount=rs.getInt(1);
                    return idAccount;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    return idAccount;

    }
    public void addStudent(String email, String password){
        String query ="INSERT INTO `testQuiz`.`Accounts` (`idAccount`, `email`, `password`, `accountRole`) VALUES (?, ?, ?, 2);";
        try {

//            System.out.println(rs.getString(2));
            prpstmt=con.prepareStatement(query);
            prpstmt.setString(1, String.valueOf(setUniqueID("Accounts")));
            prpstmt.setString(2, email);
            prpstmt.setString(3,password);
            prpstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    public void addQuizTitle(String title){
       String query ="INSERT INTO `testQuiz`.`Quiz` (`idnew_table`, `title`) VALUES (?, ?);";
        try {
            prpstmt=con.prepareStatement(query);
            prpstmt.setString(1, String.valueOf(setUniqueID("Quiz")));
            prpstmt.setString(2,title);
            prpstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addQuestion(String question, String tittleQuiz){
        String query ="INSERT INTO `testQuiz`.`Questions` (`idQuestions`, `idQuiz`, `text`) VALUES (?, ?, ?);";
        try {

//            System.out.println(rs.getString(2));
                prpstmt=con.prepareStatement(query);
                prpstmt.setString(1, String.valueOf(setUniqueID("Questions")));
                prpstmt.setString(2, String.valueOf(getQuizID(tittleQuiz)));
                prpstmt.setString(3,question);
                prpstmt.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public void addAnswers(String[]answers,String question,int correct){
       String query ="INSERT INTO `testQuiz`.`Answers` (`idAnswers`, `idQuestions`, `text`, `validation`) VALUES (?, ?, ?, ?);";
        try {



        for (int i = 0;i<answers.length;i++){

            PreparedStatement  ps1=con.prepareStatement(query);
            ps1.setString(1, String.valueOf(i+1));
            ps1.setString(2, String.valueOf(getQuestionID(question)));
            ps1.setString(3,answers[i]);
            if (i==correct) {
                ps1.setString(4, String.valueOf(1));
            }else
                ps1.setString(4, String.valueOf(0));
            ps1.execute();

        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
