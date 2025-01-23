package dici.stats.sharing;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import dici.stats.TestDatasets;

public class TestSharing {

	public static void main(String[] args) {
		new TestSharing();
	}

	public TestSharing() {
		new Controller();
	}

	protected class View extends JFrame implements ActionListener{
		private Controller controller;

		private JPanel panelGraph;
		private JTextField txtName;
		private JLabel lblCorrelation;
		private JButton btnUpdate;

		public View( Controller controller ) {
			super();
			this.controller = controller;
			this.setSize( 1100, 850 );
			this.setDefaultCloseOperation( JFrame.EXIT_ON_CLOSE );
			this.setLayout( new BorderLayout() );
			this.initComponents();
			this.setVisible( true );
		}

		private void initComponents() {
			this.panelGraph = new JPanel();
			this.panelGraph.setBackground( Color.GRAY );
			this.add( this.panelGraph, BorderLayout.CENTER );

			JPanel panelSouth = new JPanel();
			this.txtName = new JTextField( 20 );
			panelSouth.add( this.txtName );

			this.lblCorrelation = new JLabel( "..." );
			panelSouth.add( this.lblCorrelation );

			this.btnUpdate = new JButton( "Update" );
			this.btnUpdate.addActionListener( this );
			panelSouth.add( this.btnUpdate );

			this.add( panelSouth, BorderLayout.SOUTH );
		}

		@Override
		public void actionPerformed( ActionEvent e ) {
			if ( e.getSource() == this.btnUpdate ) {
				String request = this.txtName.getText();
				AnalysisResponse analysisResponse = this.controller.getStats( request);
				
				this.panelGraph.removeAll();
				this.panelGraph.add( analysisResponse.getPanelGraph() );
				this.lblCorrelation.setText( analysisResponse.getCorrelationStrength() );
			}
		}
	}

	protected class Controller {
		private Model model;
		private View view;

		public Controller() {
			this.model = new Model();
			this.view = new View( this );
		}

		public AnalysisResponse getStats( String request ) {
			double[][] data   = this.model.getDatas( request );
			String[] colNames = this.model.getLabels( request );
			AnalysisRequest analysisRequest = new AnalysisRequest( data, colNames );
			return analysisRequest.sendRequest();
		}
	}

	protected class Model { //Simule l'API
		public double[][] getDatas( String request ){ 
			switch (request) {
				case "glicemie":
					return TestDatasets.DATA_GLICEMIE;
				case "poids":
					return TestDatasets.DATA_TAILLE_POIDS;
				case "saut":
					return TestDatasets.DATA_SAUT;
				default:
					return new double[0][0];
			}
		}

		public String[] getLabels( String request ){ 
			switch (request) {
				case "glicemie":
					return TestDatasets.COL_NAMES_GLICEMIE;
				case "poids":
					return TestDatasets.COL_NAMES_TAILLE_POIDS;
				case "saut":
					return TestDatasets.COL_NAMES_SAUT;
				default:
					return new String[0];
			}
		}
	}
}
