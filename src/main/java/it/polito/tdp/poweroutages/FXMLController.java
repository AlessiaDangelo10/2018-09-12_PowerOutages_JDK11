package it.polito.tdp.poweroutages;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.poweroutages.model.Model;
import it.polito.tdp.poweroutages.model.Nerc;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextArea txtResult;

    @FXML
    private Button btnCreaGrafo;

    @FXML
    private ComboBox<Nerc> cmbBoxNerc;

    @FXML
    private Button btnVisualizzaVicini;

    @FXML
    private TextField txtK;

    @FXML
    private Button btnSimula;

    @FXML
    void doCreaGrafo(ActionEvent event) {

    	String x = model.creaGrafo();
    	
    	this.cmbBoxNerc.getItems().addAll(model.getNerc());
    	
    	this.btnVisualizzaVicini.setDisable(false);
		this.btnSimula.setDisable(false);
    	this.txtResult.appendText(x);
    }

    @FXML
    void doSimula(ActionEvent event) {
    	this.txtResult.clear();
    	Integer K;
    	try {
    		K = Integer.parseInt(this.txtK.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.appendText("Inserire un numero");
    		return;
    	}
    	String output = model.simula(K);
    	this.txtResult.appendText(output);
    }

    @FXML
    void doVisualizzaVicini(ActionEvent event) {

    	Nerc n = this.cmbBoxNerc.getValue();
    	if(n==null) {
    		this.txtResult.appendText("\nInserire un nerc!!!");
    		return;
    	}
    	String output = model.getVicini(n);
    	
    	this.txtResult.appendText(output);
    	
    }

    @FXML
    void initialize() {
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnCreaGrafo != null : "fx:id=\"btnCreaGrafo\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert cmbBoxNerc != null : "fx:id=\"cmbBoxNerc\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnVisualizzaVicini != null : "fx:id=\"btnVisualizzaVicini\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'PowerOutages.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'PowerOutages.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.btnVisualizzaVicini.setDisable(true);
		this.btnSimula.setDisable(true);
	}
}
