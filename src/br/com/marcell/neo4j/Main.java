package br.com.marcell.neo4j;

import java.io.File;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.RelationshipType;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {


	public static void main(String[] args) {
		
		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		GraphDatabaseService db= dbFactory.newEmbeddedDatabase(new File("C:/Users/Administrador/Documents/Neo4j/novo"));
		try (Transaction tx = db.beginTx()) {

//			Node javaNode = db.createNode(Tutorials.JAVA);
//			javaNode.setProperty("TutorialID", "JAVA001");
//			javaNode.setProperty("Title", "Learn Java");
//			javaNode.setProperty("NoOfChapters", "25");
//			javaNode.setProperty("Status", "Completed");				
//			
//			Node scalaNode = db.createNode(Tutorials.SCALA);
//			scalaNode.setProperty("TutorialID", "SCALA001");
//			scalaNode.setProperty("Title", "Learn Scala");
//			scalaNode.setProperty("NoOfChapters", "20");
//			scalaNode.setProperty("Status", "Completed");
//			
//			Relationship relationship = javaNode.createRelationshipTo
//			(scalaNode,TutorialRelationships.JVM_LANGIAGES);
//			relationship.setProperty("Id","1234");
//			relationship.setProperty("OOPS","YES");
//			relationship.setProperty("FP","YES");
			
			 // START SNIPPET: shortestPathUsage
	        Node startNode = db.getNodeById(2);
	        Node middleNode1 = db.getNodeById(3);
	        Node middleNode2 = db.getNodeById(4);
	        Node middleNode3 = db.getNodeById(5);
	        Node endNode = db.getNodeById(6);
	        createRelationshipsBetween( startNode, middleNode1, endNode );
	        createRelationshipsBetween( startNode, middleNode2, middleNode3, endNode );

	        PathFinder<Path> finder = GraphAlgoFactory.shortestPath(PathExpanders.forTypeAndDirection( ExampleTypes.MY_TYPE, Direction.OUTGOING ), 15 );
	        Path path = finder.findAllPaths( startNode, endNode ).iterator().next();
	        System.out.println(path.length());
	        System.out.println(path.startNode());
	        System.out.println(path.endNode());
	        
	        for (Node node : path.nodes()) {
				System.out.println(node.getId());
			}

	        tx.success();
			tx.close();
			tx.terminate();
		}
	}
	
	 private static enum ExampleTypes implements RelationshipType
	    {
	        MY_TYPE
	    }
	
	public static void createRelationshipsBetween( final Node... nodes )
    {
        for ( int i = 0; i < nodes.length - 1; i++ )
        {
            nodes[i].createRelationshipTo( nodes[i+1], ExampleTypes.MY_TYPE );
        }
    }
	
}
