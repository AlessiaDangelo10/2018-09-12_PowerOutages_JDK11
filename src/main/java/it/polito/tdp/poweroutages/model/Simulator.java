package it.polito.tdp.poweroutages.model;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.SimpleWeightedGraph;

public class Simulator {
	
	//PARAMETRI SIMULAZIONE
	private long K;
	private Graph<Nerc, DefaultWeightedEdge> grafo;
	
	//OUTPUT DA CALCOLARE
	private int catastrofi;
	
	//STATO DEL MONDO
	private List<Donazione> donazioni;
	
	//CODA DEGLI EVENTI
	private PriorityQueue<Evento> queue;
	
	//INIZIALIZZAZIONE
	public void init(Graph<Nerc, DefaultWeightedEdge> grafo, int k, List<Evento> eventi) {
		
		this.grafo = grafo;
		this.K = (long) k;
		this.catastrofi = 0;
		this.donazioni = new ArrayList<>();
		
		for(Nerc n : this.grafo.vertexSet())
			n.resetBonus();
		this.queue = new PriorityQueue<>();
		for(Evento x : eventi) {
			this.queue.add(x);
		}
		
	}
	
	public void run() {
		
		while(!this.queue.isEmpty()) {
			Evento e = this.queue.poll();
			System.out.println(e);
			processEvent(e);
		}
		
	}
	
	public void processEvent(Evento e) {
		
		Nerc n = e.getNerc();
		
		switch(e.getType()) {
		
		case INIZIO:
			List<Nerc> possibili = new ArrayList<>();
			n.setLibero(false);
			Nerc scelto = null;
			if(donazioni.size()!=0) {
				for(Donazione d : donazioni) {
					//System.out.println(d);
					if(d.getDonatore().equals(n) && this.donatoreKMesi(d.getInizio(), e.getTime()) && d.getRicevente().isLibero()) {
						possibili.add(d.getRicevente());
					}
				}
			}
			
			if(possibili.size()==1) {
				scelto = possibili.get(0);
				donazioni.add(new Donazione(scelto, n, e.getTime(),e.getTime().plus(e.getNumGiorni(), ChronoUnit.DAYS)));
				scelto.setLibero(false);
				scelto.increaseBonus(e.getNumGiorni());
			}
			//fare casi con possibili = 0 e possibili > 1
			else if(possibili.size()==0) {
				scelto = cercaMinore(n, Graphs.neighborListOf(grafo, n));
				donazioni.add(new Donazione(scelto, n, e.getTime(), e.getTime().plus(e.getNumGiorni(), ChronoUnit.DAYS)));
				scelto.setLibero(false);
				scelto.increaseBonus(e.getNumGiorni());
			}
			else if(possibili.size()>1) {
				scelto = cercaMinore(n, possibili);
				donazioni.add(new Donazione(scelto, n, e.getTime(), e.getTime().plus(e.getNumGiorni(), ChronoUnit.DAYS)));
				scelto.setLibero(false);
				scelto.increaseBonus(e.getNumGiorni());
			}
			
			if(scelto == null) {
				this.catastrofi++;
			}
			
			break;
			
		case FINE:
			
			n.setLibero(true);
			for(Donazione d : donazioni) {
				if(d.getRicevente().equals(n) && d.getFine().equals(e.getTime()))
					d.getDonatore().setLibero(true);
			}
			
			break;
		
		}
		
	}

	private boolean donatoreKMesi(LocalDateTime inizio, LocalDateTime time) {
		//System.out.println("******"+inizio+ "         "+time);
		if(time.getYear()==inizio.getYear()) {
			if(time.getMonthValue()-inizio.getMonthValue()<K)
				return true;
		}else {
			if(time.getMonthValue()-inizio.getMonthValue()+12<K)
				return true;
		}
		return false;
	}

	private Nerc cercaMinore(Nerc z, List<Nerc> vicini) {
		Nerc scelto = vicini.get(0);
		Double minore = this.grafo.getEdgeWeight(this.grafo.getEdge(z, scelto));
		for(Nerc n : vicini) {
			if(n.isLibero()) {
				DefaultWeightedEdge d = this.grafo.getEdge(z, n);
				Double peso = this.grafo.getEdgeWeight(d);
				if(peso<minore) {
					minore = peso;
					scelto = n;
				}
			}
		}
		return scelto;
	}
	
	public List<Nerc> getBonusNerc(){
		List<Nerc> f = new ArrayList<>(this.grafo.vertexSet());
		return f;
	}
	
	public int getCatastrofi() {
		return this.catastrofi;
	}

}
