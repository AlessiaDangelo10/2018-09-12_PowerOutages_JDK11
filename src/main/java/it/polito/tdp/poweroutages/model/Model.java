package it.polito.tdp.poweroutages.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

import it.polito.tdp.poweroutages.db.PowerOutagesDAO;

public class Model {
	
	private Map<Integer, Nerc> idMap;
	private PowerOutagesDAO dao;
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	private List<Adiacenza> adiacenze;
	
	public Model() {
		this.idMap = new HashMap<>();
		this.dao = new PowerOutagesDAO();
		
	}
	
	public String creaGrafo() {
		this.dao.loadAllNercs(idMap);
		this.grafo = new SimpleWeightedGraph<>(DefaultWeightedEdge.class);
		Graphs.addAllVertices(grafo, idMap.values());
		this.adiacenze = this.dao.getAdiacenze(idMap);
		for(Adiacenza a : adiacenze) {
			Graphs.addEdgeWithVertices(this.grafo, a.getN1(), a.getN2(), a.getPeso());
		}
		return String.format("Grafo creato!\nVertici: %d\nArchi: %d", this.grafo.vertexSet().size(),
				this.grafo.edgeSet().size());
	}
	
	public int getVertexNumber() {
		return this.grafo.vertexSet().size();
	}
	
	public int getEdgesNumber() {
		return this.grafo.edgeSet().size();
	}
	
	public Set<Nerc> getNerc(){
		return this.grafo.vertexSet();
	}

	public String getVicini(Nerc n) {
		
		String s = "\nLista vicini di "+n+":\n";
		List<Adiacenza> lista = new ArrayList<>();
		for(Nerc x : Graphs.neighborListOf(grafo, n)) {
			DefaultWeightedEdge d = this.grafo.getEdge(n, x);
			Double peso = this.grafo.getEdgeWeight(d);
			//s += x + "  peso= "+ peso +"\n";
			lista.add(new Adiacenza(n, x, peso));
			
		}
		Collections.sort(lista);
		for(Adiacenza y : lista) {
			s += y.getN2() + "  peso= " + y.getPeso() + "\n";
		}
		return s;
	}

	public String simula(Integer k) {
		String output = "\nRisultati simulazioni:\n";
		Simulator sim = new Simulator();
		List<Evento> eventi = this.dao.getEvents(idMap);
		sim.init(grafo, k, eventi);
		sim.run();
		List<Nerc> lista = sim.getBonusNerc();
		Collections.sort(lista);
		for(Nerc w : lista) {
			output += w + " --> " + w.getBonus() + "\n";
		}
		output += "Numero di catastrofi: " + sim.getCatastrofi();
		
		return output;
	}

}
