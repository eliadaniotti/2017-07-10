package it.polito.tdp.artsmia.model;

import java.util.*;

import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;
import org.jgrapht.traverse.DepthFirstIterator;
import org.jgrapht.traverse.GraphIterator;

import it.polito.tdp.artsmia.db.ArtsmiaDAO;

public class Model {
	ArtsmiaDAO dao = new ArtsmiaDAO();
	SimpleWeightedGraph<ArtObject, DefaultWeightedEdge> grafo = new SimpleWeightedGraph<ArtObject, DefaultWeightedEdge>(DefaultWeightedEdge.class);
	List<ArtObject> oggetti ;
	
	public Model() {
		oggetti = new LinkedList<ArtObject>(dao.listObjects());
	}
	
	public List<ArtObject> getObjects(){
		return dao.listObjects();
	}

	public List<Exhibition> getExhibitions(){
		return dao.listExhibitions();
	}
	
	public void creaGrafo() {
		Graphs.addAllVertices(grafo, oggetti);
		
		for(ArtObject a : grafo.vertexSet())
			for(ArtObject aa : grafo.vertexSet())
				if(!a.equals(aa) && !grafo.containsEdge(a, aa)) {
					int peso = dao.getPeso(a, aa);
					if(peso > 0)
						Graphs.addEdge(grafo, a, aa, peso);
				}
	}
	
	public List<ArtObject> getConnessi(int id){
		ArtObject a = null;
		
		for(ArtObject aa : grafo.vertexSet())
			if(aa.getId()==id) {
				a = aa;
				break;
			}
				
			
			List<ArtObject> ragg = new LinkedList<ArtObject>();
			GraphIterator<ArtObject,DefaultWeightedEdge> dfi = new DepthFirstIterator<ArtObject,DefaultWeightedEdge>(grafo, a);
			
			while (dfi.hasNext()) {
				ragg.add(dfi.next());
			}
			
			return ragg;
	}


}
