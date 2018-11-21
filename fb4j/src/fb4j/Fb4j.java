/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fb4j;


import com.restfb.Connection;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
//import com.restfb.FacebookClient.AccessToken;
import com.restfb.Parameter;
import com.restfb.types.FacebookType;
import com.restfb.types.Group;
import com.restfb.types.Post;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import com.restfb.types.User;
import java.util.List;
import java.util.Scanner;

/**
 *
 * @author Carlos
 */
public class Fb4j {

   public static String accessToken = "EAACzEciXs8ABAL1XwFiJABPEvqkxqnvZAwPFU5YZCzduH79Q9nGb7VNsI0hqzIcIg2cwEZChphf0DftIxkgEEGHH6hfAYgkxF91FQrLmdZCLnD83Pt75VIjK2VgiDiW6DdU0UkNeyJJ1dG6TumH8v0W0yLTPSWpb8G87762nvNhdDGp76ceGvLPO9jmutY2i8SmpNwQ3ZCwZDZD";
   public static String Wall="";     
   public static FacebookClient fbClient = new DefaultFacebookClient(accessToken);
   
   
   public static void main(String[] args) {
        // TODO code application logic here
     
        Scanner sc = new Scanner(System.in);
        int seleccion;
        // Inicio Menu
	System.out.println("Simple Facebook Client");
        System.out.println("Por favor ingrese una opcion: ");
        System.out.println("Opciones: ");
	System.out.println("(0) Obtener Wall");
	System.out.println("(1) Postear en Feed");
	System.out.println("(2) Postear en Grupo");
	System.out.println("(3) Postear link");
	System.out.println("(4) Salir");

	// Fin de Menu
        seleccion = sc.nextInt();
        
        switch(seleccion){
	case 0: obtenerWall();
            break;
        case 1:postEnFeed();
            break;
        case 2: postEnGrupo();
            break;
        case 3: postLink();
            break;
        case 4: System.exit(0);
            break;
        default: System.out.println("Opcion no valida, por favor intente de nuevo");
            break;
        }
       
        /*
        AccessToken exAccessToken = fbClient.obtainExtendedAccessToken("328057404636798", "a81b4d8dcd6b890337bce09fd4f43d31");
 
        System.out.println(exAccessToken.getAccessToken());
        System.out.println(exAccessToken.getExpires());
        User me = fbClient.fetchObject("me" , User.class);
        
        System.out.println(me.getName());
        System.out.println(me.getBio());*/
    }

    public static void obtenerWall() {
        Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
        //OBTENER WALL
        int counter = 0;
        
        for(List<Post> page : result){
            for(Post aPost : page){
                System.out.println(aPost.getMessage());
                Wall= Wall+aPost.getMessage();
                counter++;
            }
        }
        System.out.println("Numero de posts: "+counter);
        exportToFile();
    }
    
    public static void exportToFile(){
        
        try{
           File archivo = new File("Wall.txt");
           FileWriter escribir = new FileWriter(archivo,true);
           escribir.write(Wall);
           escribir.close();
           System.out.println("Archivo .txt creado con exito");
        }catch(IOException e){
            System.out.println("Error al escribir");
        }
         
    }
    
    public static void postEnFeed() {
        
        System.out.println("ESTA OPCION NO SE PUEDE REALIZAR, PERO EL CODIGO DEBERIA QUEDAR COMO EN EL ARCHIVO");
        
        /*Connection<Post> result = fbClient.fetchConnection("me/feed", Post.class);
        
        FacebookType response = fbClient.publish("me/feed", FacebookType.class,Parameter.with("message", "Test de posteo"));
        
        System.out.println("fb.com/"+response.getId());*/
        
    }

    public static void postEnGrupo() {
        Connection<Group> resultGroup = fbClient.fetchConnection("me/groups",Group.class);
        
        for(List<Group> GroupPage : resultGroup){
            for(Group aGroup : GroupPage){
                Scanner sc2 = new Scanner(System.in);
                System.out.println("¿Quieres publicar en el grupo: "+aGroup.getName()+"? (Si/No/Salir)");
                String ans = sc2.nextLine();
                if(ans.equalsIgnoreCase("Si")){
                    System.out.println("¿Que quieres publicar?");
                    String msg = sc2.nextLine();
                    FacebookType response = fbClient.publish(aGroup.getId()+"/feed",FacebookType.class,Parameter.with("message", msg));
                    System.out.println("fb.com/"+response.getId());
                    System.exit(0);
                }else if(ans.equalsIgnoreCase("Salir")){
                    System.exit(0);
                }
            }
        }
    }

    public static void postLink() {
Connection<Group> resultGroup2 = fbClient.fetchConnection("me/groups",Group.class);
        
        for(List<Group> GroupPage2 : resultGroup2){
            for(Group aGroup2 : GroupPage2){
                Scanner sc3 = new Scanner(System.in);
                System.out.println("¿Quieres publicar en el grupo: "+aGroup2.getName()+"? (Si/No/Salir)");
                String ans2 = sc3.nextLine();
                if(ans2.equalsIgnoreCase("Si")){
                    System.out.println("¿Que quieres publicar?");
                    String msg2 = sc3.nextLine();
                    FacebookType response2 = fbClient.publish(aGroup2.getId()+"/feed",FacebookType.class,Parameter.with("message", msg2),Parameter.with("link", "http://www.google.com"));
                    System.out.println("fb.com/"+response2.getId());
                    System.exit(0);
                }else if(ans2.equalsIgnoreCase("Salir")){
                    System.exit(0);
                }
            }
        }
    }
}
