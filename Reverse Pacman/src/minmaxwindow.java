import java.awt.BorderLayout;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.nio.charset.spi.CharsetProvider;
import java.security.AlgorithmParameterGenerator;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.awt.event.ActionEvent;

import org.graphstream.algorithm.AStar;
import org.graphstream.algorithm.Kruskal;
import org.graphstream.algorithm.measure.ChartMeasure.PlotException;
import org.graphstream.algorithm.measure.ChartSeries1DMeasure;
import org.graphstream.graph.*;
import org.graphstream.graph.implementations.*;
import org.graphstream.ui.layout.HierarchicalLayout;
import org.graphstream.ui.view.View;
import org.graphstream.ui.view.Viewer;
import org.graphstream.ui.view.Viewer.CloseFramePolicy;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import ch.aplu.jgamegrid.Location;
import ch.aplu.util.JRunner;
import tree.Gaertner;
import tree.Tree_Node;
import tree.gamestate;

import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.SwingConstants;
import javax.swing.ImageIcon;

/**
 * Frame that enables advanced options for the Minimax Agent
 */
public class minmaxwindow extends JFrame {

	private JPanel contentPane;
	public JButton btnShowTree;
	public Graph gp;
	public Tree_Node extrem;
	public Viewer vw;
	public boolean firstrun = false;
	public double max = -10000000000000.0;
	public String css = "node.pac {fill-color: #e2ee1d;}" + "node.ghost { fill-color: #ee5c1d; }"
			+ "node.gray {fill-color: #908a88;}" + "edge.gray {fill-color: #908a88;}"
			+ "edge.select{shape: blob; fill-color: #0000a0;}" + "node.select {fill-color: #2f0;}"
			+ "node.endpoint { size: 20px; fill-color: #fff400;}";
	public JButton btnNewButton;
	public JSpinner spinner;
	public JButton btnApply;
	public JLabel lblTreeDebth;
	public JButton btnNewButton_1;
	public JSeparator separator;
	public JLabel lblImmage;
	public JLabel lblcutMinmaxAgent;
	



	/**
	 * Create the frame.
	 */
	public minmaxwindow() {
		setTitle("MINIMAX Agent");
		setResizable(false);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 564, 322);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		System.setProperty("org.graphstream.ui.renderer", "org.graphstream.ui.j2dviewer.J2DGraphRenderer");
		btnShowTree = new JButton("Show tree");
		btnShowTree.setToolTipText("Shows the minimax search tree");
		btnShowTree.setForeground(Color.GRAY);
		btnShowTree.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		btnShowTree.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				ArrayList<Location> ghlist = new ArrayList<Location>();

				for (Ghostplayer gh : PacMan.playerlist) {
					ghlist.add(gh.getLocation());
				}
				gamestate gs = new gamestate(PacGrid.clonemaze(), PacMan.pacActor.getLocation(), ghlist, PacMan.pacActor.getpills(),
						KIActor.getpower, PacMan.pacActor.getwalked(), PacMan.pacActor.getPpills());
				Gaertner gr=new Gaertner(gs,KIData.zeroghost);
				gr.maketree();
				Tree_Node root=gr.minmax();
			
				gp = new SingleGraph("Tree");
				gp.addAttribute("ui.quality");
				gp.addAttribute("ui.antialias");
				gp.addAttribute("ui.stylesheet", css);
				vw = gp.display();
				firstrun = true;
				btnNewButton.setEnabled(true);
				vw.enableAutoLayout();
				vw.setCloseFramePolicy(CloseFramePolicy.CLOSE_VIEWER);
				
				new Thread(new Runnable() {

					@Override
					public void run() {
						// TODO Auto-generated method stub
						try {
							Thread.sleep(1000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						drawtree(gr.root);
						try {
							Thread.sleep(3000);
						} catch (InterruptedException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					}
				}).start();
				;

			}
		});
		btnShowTree.setBounds(365, 178, 183, 23);
		contentPane.add(btnShowTree);

		btnNewButton = new JButton("Show decision");
		btnNewButton.setToolTipText("shows selected path");
		btnNewButton.setForeground(Color.GRAY);
		btnNewButton.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		btnNewButton.setEnabled(false);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (firstrun) {
					if (!(vw == null)) {
						AStar astar = new AStar(gp);
						astar.compute(KIData.root.getState().hashCode() + "", extrem.getState().hashCode() + "");
						Path tes = astar.getShortestPath();
						String gss = css;
						gp.changeAttribute("ui.stylesheet", gss);
						new Thread(new Runnable() {

							@Override
							public void run() {
								// TODO Auto-generated method stub
								try {
									draw_route(tes);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							}
						}).start();

					}
				} else {
					btnNewButton.setEnabled(false);
				}
			}
		});
		btnNewButton.setBounds(365, 212, 183, 23);
		contentPane.add(btnNewButton);

		spinner = new JSpinner();
		spinner.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		spinner.setModel(new SpinnerNumberModel(2, 1, 10, 1));
		spinner.setBounds(410, 120, 39, 23);
		contentPane.add(spinner);

		btnApply = new JButton("APPLY");
		btnApply.setForeground(Color.GRAY);
		btnApply.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		btnApply.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Gaertner.tree_debth = (int) spinner.getValue();
			}
		});
		btnApply.setBounds(459, 120, 89, 23);
		contentPane.add(btnApply);

		lblTreeDebth = new JLabel("TREE DEBTH");
		lblTreeDebth.setForeground(new Color(255, 153, 0));
		lblTreeDebth.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 18));
		lblTreeDebth.setBounds(297, 120, 113, 20);
		contentPane.add(lblTreeDebth);

		btnNewButton_1 = new JButton("Show graph");
		btnNewButton_1.setToolTipText("opens utillity graph");
		btnNewButton_1.setForeground(Color.GRAY);
		btnNewButton_1.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 15));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Datagraph dg=new Datagraph();
				dg.setVisible(true);
			}
		});
		btnNewButton_1.setBounds(365, 246, 183, 23);
		contentPane.add(btnNewButton_1);
		
		separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setForeground(new Color(255, 153, 0));
		separator.setBackground(new Color(255, 153, 0));
		separator.setBounds(10, 0, 17, 302);
		contentPane.add(separator);
		
		lblImmage = new JLabel("immage");
		lblImmage.setIcon(new ImageIcon(minmaxwindow.class.getResource("/res/minmax.png")));
		lblImmage.setBounds(10, 10, 200, 200);
		contentPane.add(lblImmage);
		
		lblcutMinmaxAgent = new JLabel("\"MINIMAX\" AGENT");
		lblcutMinmaxAgent.setForeground(Color.LIGHT_GRAY);
		lblcutMinmaxAgent.setFont(new Font("Microsoft JhengHei Light", Font.PLAIN, 28));
		lblcutMinmaxAgent.setBounds(240, 26, 308, 51);
		contentPane.add(lblcutMinmaxAgent);
	}

	public void drawtree(Tree_Node tn) {
		int temptest = tn.getState().hashCode();
		gp.addNode(tn.getState().hashCode() + "");
		for (Tree_Node tnn : tn.next) {
			try {
				draw_further(tnn, 99);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public void draw_further(Tree_Node tn, int weth) throws InterruptedException {
		if (!((int) spinner.getValue() > 2)) {
			Thread.sleep(10);
		}

		Node tempn = gp.addNode(tn.state.hashCode() + "");
		//tn.getState().setUtillity(Gaertner.evalution_function(tn.getState()));
		tempn.addAttribute("ui.label", Math.round(tn.getState().getUtillity()));

		if (!tn.isPacmove()) {
			tempn.addAttribute("ui.class", "ghost");
		} else {
			tempn.addAttribute("ui.class", "pac");
		}

		gp.addEdge(tn.prev.getState().hashCode() + "->" + tn.getState().hashCode(), tn.prev.getState().hashCode() + "",
				tn.getState().hashCode() + "").addAttribute("weight", weth);

		if (!(tn.next == null)) {
			for (Tree_Node tnn : tn.next) {
				draw_further(tnn, weth - 1);

			}
		} else {
			if (tn.getState().getUtillity() > max) {
				extrem = tn;

				max = extrem.getState().getUtillity();
			}
		}

	}

	public void draw_route(Path pt) throws InterruptedException {
		for (Node nd : gp.getEachNode()) {
			nd.clearAttributes();
			nd.addAttribute("ui.class", "gray");
		}
		for (Edge eg : gp.getEachEdge()) {
			eg.clearAttributes();
			eg.addAttribute("ui.class", "gray");
		}
		pt.getRoot().clearAttributes();
		pt.getRoot().addAttribute("ui.class", "endpoint");
		List<Node> nl = pt.getNodePath();
		nl.remove(0);
		Node tp = pt.getNodePath().get(pt.getNodePath().size() - 1);
		tp.clearAttributes();
		tp.addAttribute("ui.class", "endpoint");
		nl.remove(tp);
		Thread.sleep(1000);
		for (Node node : nl) {
			Thread.sleep(250);
			node.clearAttributes();
			node.addAttribute("ui.class", "select");
		}
		for (Edge eg : pt.getEdgePath()) {
			Thread.sleep(125);
			eg.clearAttributes();
			eg.addAttribute("ui.class", "select");
		}

	}
}
