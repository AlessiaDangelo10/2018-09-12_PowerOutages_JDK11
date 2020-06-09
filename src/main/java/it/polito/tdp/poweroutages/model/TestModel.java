package it.polito.tdp.poweroutages.model;

public class TestModel {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		Model model = new Model();
		model.creaGrafo();
		System.out.format("Grafo creato!\nVertici: %d\tArchi: %d", model.getVertexNumber(), model.getEdgesNumber());
	}

}
