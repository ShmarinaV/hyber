/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


///подключаем библиотеки и классы других папок (edb,lab1_Db)
import edb.Gruppyi;
import edb.Studentyi;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import lab1_db.NewHibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 *
 * @author 17693
 */

//главный класс, который наследован от HttpServlet
public class MainServlet extends HttpServlet {

   
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
   //метод, который обрабатывает и геты doGet и посты toPost, которые приходят к сервлету.
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) //request - запрос, response - результат 
          //параметры запроса вытаскиваем из request
            //результат записывает в response, он же выходит в браузер
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();//PrintWriter - класс, который даёт возможность писать в writer... 
        //...объекты, и конвектирует их в String(текст)
          // присвоим out(переменной) значение ответа от сервлета, который будет его выводить (в алерт)
        
      
        String enteredValue;//переменная,в которой хранится текст, который мы вводим в поле в браузере
        String[] selectedOptions = request.getParameterValues("options");//массив текстовых переменных, 
        enteredValue = request.getParameter("enteredValue");//присваивает переменной enteredValue извлечённый параметр...
        //...(данные)  запроса
        
        PrintWriter printWriter;//создадим ещё один экхемпляр класса PrintWriter, но пока не будем ему присваивать значение 
        
        SessionFactory sf = NewHibernateUtil.getSessionFactory();//используется для получения объектов session, которые...
        //...используются для операций с базами данных.
        
        Session s = sf.openSession();//короткоживущий объект, который связывает объекты приложения...
        //и базу данных
        
        
        // HQL - это объектно-ориентированный язык запросов
        
        Query q = s.createQuery("from Gruppyi g");//создаём запрос 
        List<Gruppyi> gruppyi = q.list();//создаём коллекцию из элемнтов, которые находятся в таблице Gruppyi (Группы)
           
        
        try {
            /* TODO output your page here. You may use following sample code. */
            
            //out.println - отвечают за html на странице в браузере(задаёт параметры html)
            out.println("<!DOCTYPE html> <html> <head>");
            out.println("<title>Hello</title>");            
            out.println("</head>");
            out.println("<body>");
            
           //printWriter который ниже - для того, чтобы записывать файлы в наш созданный html и выводить их на страницу
            printWriter = response.getWriter();
            printWriter.print("<p> You input: ");
            printWriter.print(enteredValue);//вводит наше значение, которое мы ввели
            printWriter.print("</p>");
            printWriter.print("<p> Students: ");
            printWriter.println("<br/>");
            
            if(gruppyi != null){ //запускаем цикл, если таблица с группами не пустая 
                //цикл->
                for (Gruppyi group : gruppyi)//запускаем цикл (Gruppyi group : gruppyi - означает, что мы будем...
                    //...перебирать все группы)
                {
                    
                    List<Studentyi> students = group.getStudentyis();//создаём коллекцию из данных таблицы Studentyi...
                    
                    
                    //цикл->
                    for(Studentyi student : students){//для каждого студента
                    printWriter.print(student.getFamiliya()+" "+student.getImya()+" "+student.getOtchestvo() + " " + student.getGruppyi().getNazvanie());//...
                    //вытаскиваем фамилию студента, имя, отчество и группу в которой он учится в браузер
                                    
                    printWriter.println("<br/>");//перевод на новую строку
                    }
                }
                
                //выводим в браузер Info about groups (name and count of students
                printWriter.print("<p> Info about groups (name and count of students) ");
                printWriter.println("<br/>");//перевод на новую строку в браузере
                //цикл->
                for (Gruppyi group: gruppyi){//запускаем цикл (Gruppyi group : gruppyi - означает, что мы будем...
                    //...перебирать все группы)
                    printWriter.print(group.getNazvanie()+" "+ group.getStudentyis().size());//выводим в браузер Названия группы...
                    //...и количество студентов них
                    printWriter.println("<br/>");//перевод на новую строку
                }
                printWriter.println("</p>");// добавляем пустую строку в браузер
                
                printWriter.print("<p> Info about A");//выводим в браузер Info about A
                //цикл->
                for (Gruppyi group: gruppyi){//снова перебираем каждую группу из коллекции групп Gruppyi
                    if (group.getNazvanie().startsWith("A")){//условие, если у группы название начинается с буквы "А"
                        printWriter.println("<br/>");//вставляем пустую строку в браузер
                        printWriter.print(group.getNazvanie());//вывести в браузер название группы, если , конечно,
                        //...такая группа подходит под условие
                    }
                   // если не подходит группа под условие, т.е. не нвчинается с "А" - ничего не происходит
                }
                if(enteredValue!=null){//условие, если введённое число не пустое(т.е. мы точно ,знаем,что мыы его ввели
                   //цикл->
                    for (Gruppyi group: gruppyi){// перебираем все группы из коллекции Группы
                    if (group.getNazvanie().equals(enteredValue)){//условие - если название группы совпадёт с ввезённым значением, то
                        gruppyi.add(gruppyi.size()-1, group);// добавить в конец списка всего(коллекции) эту группу с таким же
                        //...названием и характеристиками
                        printWriter.println("<br/>");//перевод на новую строку в браузере
                        printWriter.print("Group " + group.getNazvanie() + " removed");//вывести в браузер, что , мол, Группа "название группы" удалена
                        gruppyi.remove(group);//удаляем группу(с тем названием, что мы ввели, т.е enteredValue
                        printWriter.println("<br/>");//перевод на новую строку в браузере
                    }
                    }
                }
                for (Gruppyi group : gruppyi)//для каждой группы из коллекции группы
                {
                    List<Studentyi> students = group.getStudentyis();////создаём коллекцию из данных таблицы Studentyi...
                    ///...короче заполняем список студентов в так называемую коллекцию(грубо говоря массив) но для...
                    //...Казакова - это коллекция
                    
                    //цикл->
                    for(Studentyi student : students){//для каждого студента в каждой группе
                    printWriter.print(student.getFamiliya()+" "+student.getImya()+" "+student.getOtchestvo() + " " + student.getGruppyi().getNazvanie());//...
                    //... выводим на экран его Фамилию, имя , отчество и группу, в которой он учится
                    printWriter.println("<br/>");//перевод на новую строку в браузере
                    }
                }
                 
                printWriter.print("<p> Info about groups (name and count of students) ");//выводим в браузер текст 
                printWriter.println("<br/>");//перевод на новую строку в браузере
                
                //цикл->
                for (Gruppyi group: gruppyi){//для каждой группы из коллекции Групп
                    printWriter.print(group.getNazvanie()+" "+ group.getStudentyis().size());//вывести название...
                    //...группы и количество студентов в ней
                    
                    printWriter.println("<br/>");//перевод на новую строку в браузере
                }
                
                printWriter.println("</p>");
                
                
                printWriter.print("<p> Info about A");//выводим в браузер текст 
                
                //цикл->
                for (Gruppyi group: gruppyi){//для каждой группы из коллекции Групп
                    
                    //если для группы выполняется условие
                    if (group.getNazvanie().startsWith("A")){//"если название группы начинается с "А"
                        printWriter.println("<br/>");//перевод на новую строку в браузере
                        printWriter.print(group.getNazvanie());//вывести название группы в браузер
                    }
                }
                
                printWriter.println("</p>");//закрываем абзац
                
            }
            //тут html ниже
            printWriter.println("</p>");
            out.println("</body>");
            out.println("</html>");
        } 
        finally{
            out.close();//закрывает наш PrintWriter, теперь выводить мы не можем в браузер ничего без него
            s.close();//закрываем session
            sf.close();//закрываем SessionFactory
           
        }
    }

}