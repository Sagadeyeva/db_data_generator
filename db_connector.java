package data_generator;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.lang.*;
import java.util.logging.*;

import org.json.JSONObject;

import java.io.*;



enum style_type{
	Ballade, Canzona, Chanson, Fantasia, Galliard, 
	Intermedio, Laude, Litany, Mass, Opera,
	Sequence};
	
enum instrument_type {Accordion, Bagpipes, Banjo, Bassoon, Cello, Clarinet, 
	Cymbal, Drum, Timpani, Dulcimer, Flute, Gong, Guitar, Lute, Lyre, Marimba, Oboe, Organ, 	
	Piano, Trombone, Trumpet, Tuba, Viola, Violin, Fiddle, 
	Xylophone, Zither};
	

public class db_connector {
	
	private static style_type randomStyle() {
	    int pick = new Random().nextInt(style_type.values().length);
	    return style_type.values()[pick];
	}
	
	private static instrument_type randomInstrument() {
	    int pick = new Random().nextInt(instrument_type.values().length);
	    return instrument_type.values()[pick];
	}
	

	
    public static void main(String[] args) {

        Connection connection = null;
        String url = "jdbc:postgresql://127.0.0.1:5432/postgres";
        //Имя пользователя БД
        String name = "postgres";
        //Пароль
        String password = "1234";
        Statement statement=null;
      
        try {
            //Загружаем драйвер
            Class.forName("org.postgresql.Driver");
            System.out.println("Драйвер подключен");
            //Создаём соединение
            connection = DriverManager.getConnection(url, name, password);
            System.out.println("Соединение установлено");
            
            Scanner in=new Scanner(System.in);
            System.out.println("Укажите количество записей, которое хотите добавить --->");
            int amount=in.nextInt();
            
            statement = connection.createStatement();
            
            //Заполнение тблицы "Styles"
        	for (int i=1; i<=amount; i++)	{
          		style_type st=randomStyle();
        		statement.executeUpdate ("INSERT INTO styles (style_id, style) VALUES ('"+i+"','"+st+"');");
        	
        	}
     
            //Заполнение таблицы "Instruments"
         	for (int i=1; i<=amount; i++)	{
         		instrument_type instr=randomInstrument();
         		statement.executeUpdate ("INSERT INTO instruments (instrument_id, instrument) VALUES ('"+i+"','"+instr+"');");
         	
         	} 
     
         	//Заполнение таблицы "Comps"
         	ArrayList<String> comps = new ArrayList<String>();
            Scanner sc10=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/comps.txt"));
         	
            while (sc10.hasNext()) {
                comps.add(sc10.nextLine());
            }
            
            String line40=null;
            for (int i=1; i<=amount; i++)	{
         		line40=comps.get((new Random()).nextInt(comps.size()));
         		statement.executeUpdate ("INSERT INTO comps (id, comp) VALUES ('"+i+"','"+line40+"');");
            }
         	
         	         	
   	//Заполнение таблицы "Composers"
         
         	 ArrayList<String> names = new ArrayList<String>();
             ArrayList<String> years = new ArrayList<String>();
             ArrayList<String> towns = new ArrayList<String>();
             Scanner sc3=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/composers.txt"));
             Scanner sc4=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/years.txt"));
             Scanner sc5=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/cities.txt"));
             
             while (sc3.hasNext()) {
                 names.add(sc3.nextLine());
             }
         
             while (sc4.hasNext()) {
                 years.add(sc4.nextLine());
             }
        
             while (sc5.hasNext()) {
                 towns.add(sc5.nextLine());
             }
                    
         	String line3 = null;
         	String line4 = null;
         	String line5 = null;
         	String line6 = null;
         	
         	for (int i=1; i<=amount; i++)	{
         		line3=names.get((new Random()).nextInt(names.size()));
         		line4=years.get((new Random()).nextInt(years.size()));
         		line5=years.get((new Random()).nextInt(years.size()));
         		line6=towns.get((new Random()).nextInt(towns.size()));
         		int birth=0;
         		int death=0;
         		birth=Integer.parseInt(line4);
         		death=Integer.parseInt(line5);
         		statement.executeUpdate ("INSERT INTO composers (name, birth, death, hometown, id) VALUES ('"+line3+"', '"+birth+"', '"+death+"', '"+line6+"', '"+i+"');");
         	        	
         	}
         	
        //Заполнение таблицы teachers
         	ArrayList<String> teachers_ids = new ArrayList<String>();
         	String line7=null;
         	for (int i=1; i<=amount; i++) {
         		teachers_ids.add(Integer.toString(i));
         	}
         	for (int i=1; i<=amount; i++) {
         		line7=teachers_ids.get((new Random()).nextInt(teachers_ids.size()));
         		int teacher_id=0;
         		teacher_id=Integer.parseInt(line7);
         		statement.executeUpdate ("INSERT INTO teachers (composer_id, teacher_id) VALUES ('"+i+"', '"+teacher_id+"');");
         	}
         	
        //Заполнение таблицы compositions
         	
         	ArrayList<String> compositions = new ArrayList<String>();
          	ArrayList<String> tonalities = new ArrayList<String>();
          	ArrayList<Integer> composersIds = new ArrayList<Integer>();
        	ArrayList<Integer> stylesIds = new ArrayList<Integer>();
        	ArrayList<Integer> instrumentsIds = new ArrayList<Integer>();
         	for (int i=1; i<=amount; i++) {
         		composersIds.add(i);
         		stylesIds.add(i);
         		instrumentsIds.add(i);
         	}
            Scanner sc6=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/compositions.txt"));
            Scanner sc7=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/tonalities.txt"));
            
            while (sc6.hasNext()) {
                compositions.add(sc6.nextLine());
            }
        
            while (sc7.hasNext()) {
                tonalities.add(sc7.nextLine());
            }
                   
        	String line10 = null;
        	String line11 = null;
        	String line12 = null;
        	String line20=null;
        	String line21=null;
        	String line23=null;
        	int instr_id=0;
        	int style_id=0;
        	int composerID=0;
        	for (int i=1; i<=amount; i++)	{
        		line10=compositions.get((new Random()).nextInt(compositions.size()));
        		line11=tonalities.get((new Random()).nextInt(tonalities.size()));
          		composerID=composersIds.get((new Random()).nextInt(composersIds.size()));
        		instr_id=instrumentsIds.get((new Random()).nextInt(instrumentsIds.size()));
        		style_id=stylesIds.get((new Random()).nextInt(stylesIds.size()));
        		line20=Integer.toString(style_id);
        		line21=Integer.toString(instr_id);
        		line23=Integer.toString(composerID);		
        		statement.executeUpdate ("INSERT INTO compositions (compositionid, name, composerid, tonality, style_id, instrument_id) VALUES ('"+i+"', '"+line10+"', '"+line23+"', '"+line11+"', '"+line21+"', '"+line20+"');");
           }
        	    	
        
        //Заполнение таблицы sheet
         	ArrayList<String> sheet = new ArrayList<String>();
         	Scanner sc1=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/json_data.txt"));
         	ArrayList<Integer> compositionsIds = new ArrayList<Integer>();
         	for (int i=1; i<=amount; i++) {
         		compositionsIds.add(i);
         	}
         	while (sc1.hasNext()) {
                sheet.add(sc1.nextLine());
            }
         	int compositionID=0;
         	String line8=null;
         	String line18=null;
         	for (int i=1; i<=amount; i++) {
         		line8=sheet.get((new Random()).nextInt(sheet.size()));
         		JSONObject json_sheet=new JSONObject(line8);
               		System.out.println(line8);
         		compositionID= compositionsIds.get((new Random()).nextInt(compositionsIds.size()));
        		line18=Integer.toString(compositionID);
         		statement.executeUpdate ("INSERT INTO sheet (id, sheet, compositionid) VALUES ('"+i+"', '"+line8+"', '"+line18+"');");
         	}
         	
        //Заполнение таблицы musicians
         	
         	ArrayList<String> musicians = new ArrayList<String>();     
           
            Scanner sc2=new Scanner(new File("/home/lana/eclipse-workspace/data_generator/src/data_generator/musicians.txt"));
                               
            while (sc2.hasNext()) {
                musicians.add(sc2.nextLine());
            }      
                   
        	String line9 = null;        	
        	
        	for (int i=1; i<=amount; i++)	{
        		line9=musicians.get((new Random()).nextInt(musicians.size()));
        		statement.executeUpdate ("INSERT INTO musicians (id, musician) VALUES ('"+i+"', '"+line9+"');");
        	        	
        	}      	
        	
        
        //Заполнение таблицы competitions        	
         	
         	ArrayList<Integer> awards = new ArrayList<Integer>();
         	for (int i=1; i<=3; i++) {
         		awards.add(i);
         	}
             
         	
         	ArrayList<Integer> compsIds = new ArrayList<Integer>();
         	for (int i=1; i<=amount; i++) {
         		compsIds.add(i);
         	}
         	
    		line20=Integer.toString(style_id);
    		line21=Integer.toString(instr_id);
         	
        	String line13 = null;
        	String line14=null;
        	String line15=null;
        	String line16=null;
        	String line17=null;
        	
        	String line31=null;
        	String line32=null;
        	 	
        	int aw=0;
        	int compositionId;
        	int comp_id;
        	for (int i=1; i<=amount; i++)	{
        		instr_id=instrumentsIds.get((new Random()).nextInt(instrumentsIds.size()));
        		comp_id=compsIds.get((new Random()).nextInt(compsIds.size()));
        		line13=musicians.get((new Random()).nextInt(musicians.size()));
        		aw=awards.get((new Random()).nextInt(awards.size()));
          		line14=Integer.toString(aw);
        		line15=years.get((new Random()).nextInt(years.size()));
        		compositionId= compositionsIds.get((new Random()).nextInt(compositionsIds.size()));
        		line17=Integer.toString(compositionId);
        
        		line31=Integer.toString(instr_id);
        		line32=Integer.toString(comp_id);
        		statement.executeUpdate ("INSERT INTO competitions (id, musician, year, award, composition, instrument, competition) VALUES ('"+i+"', '"+line13+"', '"+line15+"', '"+line14+"', '"+line17+"', '"+line31+"', '"+line32+"');");
        	        	
        	}
                 	
         	sc1.close();
        	sc2.close();
         	sc3.close();
         	sc4.close();
         	sc5.close();
         	sc6.close();
         	sc7.close();
         	sc10.close();
        }
           
        catch (Exception ex) {
            //выводим наиболее значимые сообщения
            Logger.getLogger(db_connector.class.getName()).log(Level.SEVERE, null, ex);
        } 
      
        
        finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException ex) {
                    Logger.getLogger(db_connector.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        System.out.println("Конец\n");
        }
    
      
}
