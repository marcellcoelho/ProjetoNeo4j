package br.com.marcell.projeto.neo4j;

import java.io.File;

import org.neo4j.graphalgo.GraphAlgoFactory;
import org.neo4j.graphalgo.PathFinder;
import org.neo4j.graphalgo.WeightedPath;
import org.neo4j.graphdb.Direction;
import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.graphdb.Node;
import org.neo4j.graphdb.Path;
import org.neo4j.graphdb.PathExpanders;
import org.neo4j.graphdb.Relationship;
import org.neo4j.graphdb.Transaction;
import org.neo4j.graphdb.factory.GraphDatabaseFactory;

public class Main {

	private static final String COST = "distancia";

	public static void main(String[] args) {

		GraphDatabaseFactory dbFactory = new GraphDatabaseFactory();
		GraphDatabaseService db = dbFactory
				.newEmbeddedDatabase(new File("C:/Users/Administrador/Documents/Neo4j/default.graphdb"));
		Transaction tx = db.beginTx();

		Node nodeA = db.createNode(LabelEnum.ENDERECO);
		nodeA.setProperty("CEP", 58071710);
		nodeA.setProperty("Logradouro", "R. Petrarca Grisi");
		nodeA.setProperty("Bairro", "Cristo Redentor");
		nodeA.setProperty("Cidade", "João Pessoa");
		nodeA.setProperty("Estado", "PB");
		
		Node nodeB = db.createNode(LabelEnum.ENDERECO);
		Node nodeC = db.createNode(LabelEnum.ENDERECO);
		Node nodeD = db.createNode(LabelEnum.ENDERECO);
		Node nodeE = db.createNode(LabelEnum.ENDERECO);
		Node nodeF = db.createNode(LabelEnum.ENDERECO);
		Node nodeG = db.createNode(LabelEnum.ENDERECO);
		Node nodeH = db.createNode(LabelEnum.ENDERECO);
		Node nodeI = db.createNode(LabelEnum.ENDERECO);
		Node nodeJ = db.createNode(LabelEnum.ENDERECO);
		
		Relationship relationAB = nodeA.createRelationshipTo(nodeB, RelationEnum.DISTANCIA);
		relationAB.setProperty("distancia", 10);

		Relationship relationBC = nodeB.createRelationshipTo(nodeC, RelationEnum.DISTANCIA);
		relationBC.setProperty("distancia", 9);
		
		Relationship relationBG = nodeB.createRelationshipTo(nodeG, RelationEnum.DISTANCIA);
		relationBG.setProperty("distancia", 15);
		
		Relationship relationCG = nodeC.createRelationshipTo(nodeG, RelationEnum.DISTANCIA);
		relationCG.setProperty("distancia", 20);
		
		Relationship relationGD = nodeG.createRelationshipTo(nodeD, RelationEnum.DISTANCIA);
		relationGD.setProperty("distancia", 20);

		Relationship relationGH = nodeG.createRelationshipTo(nodeH, RelationEnum.DISTANCIA);
		relationGH.setProperty("distancia", 7);
		
		Relationship relationHI = nodeH.createRelationshipTo(nodeI, RelationEnum.DISTANCIA);
		relationHI.setProperty("distancia", 3);
		
		Relationship relationID = nodeI.createRelationshipTo(nodeD, RelationEnum.DISTANCIA);
		relationID.setProperty("distancia", 12);
		
		Relationship relationDE = nodeD.createRelationshipTo(nodeE, RelationEnum.DISTANCIA);
		relationDE.setProperty("distancia", 7);
		
		Relationship relationDF = nodeD.createRelationshipTo(nodeF, RelationEnum.DISTANCIA);
		relationDF.setProperty("distancia", 8);
		
		Relationship relationFJ = nodeF.createRelationshipTo(nodeJ, RelationEnum.DISTANCIA);
		relationFJ.setProperty("distancia", 22);
		
		Relationship relationEJ = nodeE.createRelationshipTo(nodeJ, RelationEnum.DISTANCIA);
		relationEJ.setProperty("distancia", 11);
		

		Path path = runDijkstraPathFinder(nodeA, nodeE);
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

	private static Path runDijkstraPathFinder(Node startNode, Node endNode) {
		PathFinder<WeightedPath> finder = GraphAlgoFactory
				.dijkstra(PathExpanders.forTypeAndDirection(RelationEnum.DISTANCIA, Direction.OUTGOING), COST);
		return finder.findSinglePath(startNode, endNode);
	}

}
