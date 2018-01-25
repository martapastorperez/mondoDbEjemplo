
package mongoej;

import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.gt;
import static com.mongodb.client.model.Filters.lt;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.bson.types.ObjectId;

public class MongoEj {

    
    public static void main(String[] args) {
       MongoClient client=new MongoClient("localhost",27017);//conectarse
       MongoDatabase database= client.getDatabase("training");//conectarte a la base
       MongoCollection <Document> colecion= database.getCollection("scores");//conectarse a la colleccion
       BasicDBObject condicion=new BasicDBObject("kind","essay");//condicion
       FindIterable <Document> cursor1=colecion.find(condicion);//para crear la "consulta"
       MongoCursor <Document> iterar= cursor1.iterator(); //Llama a la "consulta"
            while(iterar.hasNext()){
                Document docu= iterar.next();
                String kind= docu.getString("kind");
                Double score= docu.getDouble("score");
                Double estudiante=docu.getDouble("student");
                System.out.println(kind+","+score+","+estudiante);
            }
            
            Document engadir=new Document("kind","taller");
            engadir.append("score",111111);
            engadir.append("enderezo",new Document()// aqui empieza un subdocumento dentro de enderezo esta rua y numero
                   .append("rua","urzaiz")
                    .append("numero",27)
            );
            
            
            colecion.insertOne(engadir);
            
           
            
            
            
          
            //Actualizar 
              BasicDBObject condicion2 = new BasicDBObject("_id",1000);
              BasicDBObject oper = new BasicDBObject().append("$set", new BasicDBObject("numero",20));   
            colecion.updateMany(condicion2, oper);
            
            //OTRA FORMA DE ACTUALIZAR
            //Document condicion=new Document("kin","proba);
            //Document oper=new Document("$set", new Document("numero",20));
            //colecion.UpdateMany(condicion,oper);
            
            
            //incrementar el valor del numero en 4
             BasicDBObject condicion3 = new BasicDBObject("_id",1000);
             BasicDBObject oper2 = new BasicDBObject().append("$inc", new BasicDBObject("numero",4));   
            colecion.updateMany(condicion3, oper2);
            
            //Buscar
            BasicDBObject condicion4=new BasicDBObject("_id",1000);
            FindIterable <Document> cursornovo=colecion.find(condicion4);
            Document doc=cursornovo.first();
            String kind=doc.getString("kind");
            Integer numero=doc.getInteger("numero");
            System.out.println(kind+","+numero);
            
            
            //buscar los registros de tipo proba y que sea mayor de 20
               //Vale para lo mismo que para la condicion 6
            //BasicDBObject condicion5=new BasicDBObject("kind","essay")
                                                     //    .append("student",new BasicDBObject ("$gt",0).append("$lt", 3));
               
               Bson condicion6= and(new Document("kind","essay"),gt("student",0), lt("student",3));
             
               BasicDBObject campos=new BasicDBObject();
                       campos.put("_id",0);
                       campos.put("kind",1);
                       campos.put("student",1);
            
                       
            FindIterable <Document> cursornovo2=colecion.find(condicion6).projection(campos);
            MongoCursor <Document> iterar2= cursornovo2.iterator(); //Llama a la "consulta"
            while(iterar2.hasNext()){
                Document docu2= iterar2.next();
                String kind2= docu2.getString("kind");
                Double score2= docu2.getDouble("score");
                Double estudiante2=docu2.getDouble("student");
                System.out.println(kind2+","+score2+","+estudiante2);
            }
            
     
            
             iterar.close();
             iterar2.close();
                  
            client.close();
    }
    
}
