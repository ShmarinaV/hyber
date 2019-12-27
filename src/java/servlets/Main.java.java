/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


///���������� ���������� � ������ ������ ����� (edb,lab1_Db)
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

//������� �����, ������� ���������� �� HttpServlet
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
    
   //�����, ������� ������������ � ���� doGet � ����� toPost, ������� �������� � ��������.
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) //request - ������, response - ��������� 
          //��������� ������� ����������� �� request
            //��������� ���������� � response, �� �� ������� � �������
            throws ServletException, IOException {
        
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();//PrintWriter - �����, ������� ��� ����������� ������ � writer... 
        //...�������, � ������������ �� � String(�����)
          // �������� out(����������) �������� ������ �� ��������, ������� ����� ��� �������� (� �����)
        
      
        String enteredValue;//����������,� ������� �������� �����, ������� �� ������ � ���� � ��������
        String[] selectedOptions = request.getParameterValues("options");//������ ��������� ����������, 
        enteredValue = request.getParameter("enteredValue");//����������� ���������� enteredValue ����������� ��������...
        //...(������)  �������
        
        PrintWriter printWriter;//�������� ��� ���� ��������� ������ PrintWriter, �� ���� �� ����� ��� ����������� �������� 
        
        SessionFactory sf = NewHibernateUtil.getSessionFactory();//������������ ��� ��������� �������� session, �������...
        //...������������ ��� �������� � ������ ������.
        
        Session s = sf.openSession();//�������������� ������, ������� ��������� ������� ����������...
        //� ���� ������
        
        
        // HQL - ��� ��������-��������������� ���� ��������
        
        Query q = s.createQuery("from Gruppyi g");//������ ������ 
        List<Gruppyi> gruppyi = q.list();//������ ��������� �� ��������, ������� ��������� � ������� Gruppyi (������)
           
        
        try {
            /* TODO output your page here. You may use following sample code. */
            
            //out.println - �������� �� html �� �������� � ��������(����� ��������� html)
            out.println("<!DOCTYPE html> <html> <head>");
            out.println("<title>Hello</title>");            
            out.println("</head>");
            out.println("<body>");
            
           //printWriter ������� ���� - ��� ����, ����� ���������� ����� � ��� ��������� html � �������� �� �� ��������
            printWriter = response.getWriter();
            printWriter.print("<p> You input: ");
            printWriter.print(enteredValue);//������ ���� ��������, ������� �� �����
            printWriter.print("</p>");
            printWriter.print("<p> Students: ");
            printWriter.println("<br/>");
            
            if(gruppyi != null){ //��������� ����, ���� ������� � �������� �� ������ 
                //����->
                for (Gruppyi group : gruppyi)//��������� ���� (Gruppyi group : gruppyi - ��������, ��� �� �����...
                    //...���������� ��� ������)
                {
                    
                    List<Studentyi> students = group.getStudentyis();//������ ��������� �� ������ ������� Studentyi...
                    
                    
                    //����->
                    for(Studentyi student : students){//��� ������� ��������
                    printWriter.print(student.getFamiliya()+" "+student.getImya()+" "+student.getOtchestvo() + " " + student.getGruppyi().getNazvanie());//...
                    //����������� ������� ��������, ���, �������� � ������ � ������� �� ������ � �������
                                    
                    printWriter.println("<br/>");//������� �� ����� ������
                    }
                }
                
                //������� � ������� Info about groups (name and count of students
                printWriter.print("<p> Info about groups (name and count of students) ");
                printWriter.println("<br/>");//������� �� ����� ������ � ��������
                //����->
                for (Gruppyi group: gruppyi){//��������� ���� (Gruppyi group : gruppyi - ��������, ��� �� �����...
                    //...���������� ��� ������)
                    printWriter.print(group.getNazvanie()+" "+ group.getStudentyis().size());//������� � ������� �������� ������...
                    //...� ���������� ��������� ���
                    printWriter.println("<br/>");//������� �� ����� ������
                }
                printWriter.println("</p>");// ��������� ������ ������ � �������
                
                printWriter.print("<p> Info about A");//������� � ������� Info about A
                //����->
                for (Gruppyi group: gruppyi){//����� ���������� ������ ������ �� ��������� ����� Gruppyi
                    if (group.getNazvanie().startsWith("A")){//�������, ���� � ������ �������� ���������� � ����� "�"
                        printWriter.println("<br/>");//��������� ������ ������ � �������
                        printWriter.print(group.getNazvanie());//������� � ������� �������� ������, ���� , �������,
                        //...����� ������ �������� ��� �������
                    }
                   // ���� �� �������� ������ ��� �������, �.�. �� ���������� � "�" - ������ �� ����������
                }
                if(enteredValue!=null){//�������, ���� �������� ����� �� ������(�.�. �� ����� ,�����,��� ��� ��� �����
                   //����->
                    for (Gruppyi group: gruppyi){// ���������� ��� ������ �� ��������� ������
                    if (group.getNazvanie().equals(enteredValue)){//������� - ���� �������� ������ ������� � �������� ���������, ��
                        gruppyi.add(gruppyi.size()-1, group);// �������� � ����� ������ �����(���������) ��� ������ � ����� ��
                        //...��������� � ����������������
                        printWriter.println("<br/>");//������� �� ����� ������ � ��������
                        printWriter.print("Group " + group.getNazvanie() + " removed");//������� � �������, ��� , ���, ������ "�������� ������" �������
                        gruppyi.remove(group);//������� ������(� ��� ���������, ��� �� �����, �.� enteredValue
                        printWriter.println("<br/>");//������� �� ����� ������ � ��������
                    }
                    }
                }
                for (Gruppyi group : gruppyi)//��� ������ ������ �� ��������� ������
                {
                    List<Studentyi> students = group.getStudentyis();////������ ��������� �� ������ ������� Studentyi...
                    ///...������ ��������� ������ ��������� � ��� ���������� ���������(����� ������ ������) �� ���...
                    //...�������� - ��� ���������
                    
                    //����->
                    for(Studentyi student : students){//��� ������� �������� � ������ ������
                    printWriter.print(student.getFamiliya()+" "+student.getImya()+" "+student.getOtchestvo() + " " + student.getGruppyi().getNazvanie());//...
                    //... ������� �� ����� ��� �������, ��� , �������� � ������, � ������� �� ������
                    printWriter.println("<br/>");//������� �� ����� ������ � ��������
                    }
                }
                 
                printWriter.print("<p> Info about groups (name and count of students) ");//������� � ������� ����� 
                printWriter.println("<br/>");//������� �� ����� ������ � ��������
                
                //����->
                for (Gruppyi group: gruppyi){//��� ������ ������ �� ��������� �����
                    printWriter.print(group.getNazvanie()+" "+ group.getStudentyis().size());//������� ��������...
                    //...������ � ���������� ��������� � ���
                    
                    printWriter.println("<br/>");//������� �� ����� ������ � ��������
                }
                
                printWriter.println("</p>");
                
                
                printWriter.print("<p> Info about A");//������� � ������� ����� 
                
                //����->
                for (Gruppyi group: gruppyi){//��� ������ ������ �� ��������� �����
                    
                    //���� ��� ������ ����������� �������
                    if (group.getNazvanie().startsWith("A")){//"���� �������� ������ ���������� � "�"
                        printWriter.println("<br/>");//������� �� ����� ������ � ��������
                        printWriter.print(group.getNazvanie());//������� �������� ������ � �������
                    }
                }
                
                printWriter.println("</p>");//��������� �����
                
            }
            //��� html ����
            printWriter.println("</p>");
            out.println("</body>");
            out.println("</html>");
        } 
        finally{
            out.close();//��������� ��� PrintWriter, ������ �������� �� �� ����� � ������� ������ ��� ����
            s.close();//��������� session
            sf.close();//��������� SessionFactory
           
        }
    }

}