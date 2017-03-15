import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.Plot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.border.LineBorder;
import java.awt.Color;

/**
 * Datagraph for stats for Ai
 */
public class Datagraph extends JFrame implements kidataupdater{

	private JPanel contentPane;
	public ChartPanel Utilityscore;
	public XYSeries utility;
	public XYSeries score;
	public XYSeries ppillscore;
	public XYSeries ppills_remain;
	public XYSeries ppilldistance;
	public XYSeries pilldistances;
	public XYSeries pillscore;
	public XYSeries ghost_k;
	public int dataset=1;
	public ChartPanel PPScore;
	public ChartPanel Pilldistance;
	public ChartPanel ghost;
	boolean error=false;
	
	
/**
 * Creats Datagraph
 */
	public Datagraph() {
		KIData.addupdater(this);
		setBounds(100, 100, 729, 551);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		utility=new XYSeries("Utility");
		score=new XYSeries("Gamescore");
		ppillscore=new XYSeries("Power Pill Score");
		ppills_remain=new XYSeries("Power Pill Remain Score");
		ppilldistance=new XYSeries("Power Pill Distance Score");
		pilldistances=new XYSeries("Pill Distance Score");
		pillscore=new XYSeries("Pill Score");
		ghost_k=new XYSeries("Ghost Near Score");
		XYSeriesCollection utilscore=new XYSeriesCollection();
		
		utilscore.addSeries(utility);
		XYSeriesCollection pscore=new XYSeriesCollection();
		pscore.addSeries(pillscore);
		pscore.addSeries(ppillscore);
		XYSeriesCollection pilldistance=new XYSeriesCollection();
		pilldistance.addSeries(ppilldistance);
		pilldistance.addSeries(pilldistances);
		JFreeChart utilscorechart=ChartFactory.createXYLineChart("UTILITY","SIM STEPS", "SCORE",utilscore,PlotOrientation.VERTICAL,true,true,false);
		utilscorechart=makedynamic(utilscorechart);
		
		Utilityscore = new ChartPanel(utilscorechart);
		Utilityscore.setBounds(0, 0, 342, 257);
		contentPane.add(Utilityscore);
		JFreeChart ppscorechart=ChartFactory.createXYLineChart("Pill Score", "SIM STEP", "SCORE", pscore);
		ppscorechart=makedynamic(ppscorechart);
		PPScore = new ChartPanel(ppscorechart);
		PPScore.setBounds(0, 256, 342, 256);
		contentPane.add(PPScore);
		JFreeChart pilldistancechart=ChartFactory.createXYLineChart("Pilldistance", "SIM STEP", "SCORE", pilldistance);
		pilldistancechart=makedynamic(pilldistancechart);
		Pilldistance = new ChartPanel(pilldistancechart);
		Pilldistance.setMouseZoomable(true);
		Pilldistance.setBounds(352, 256, 342, 256);
		contentPane.add(Pilldistance);
		XYSeriesCollection ghnearscore=new XYSeriesCollection(ghost_k);
		JFreeChart ghsocast=ChartFactory.createXYLineChart("Ghost Distance", "SIM STEP", "SCORE", ghnearscore);
		ghsocast=makedynamic(ghsocast);
		ghost = new ChartPanel(ghsocast);
		ghost.setBounds(352, 1, 342, 256);
		contentPane.add(ghost);
		
	}

/*
 * Updates data on screen
 * @see kidataupdater#update()
 */
	@Override
	public void update() {
		// TODO Auto-generated method stub
		try {
			
			utility.add(dataset, KIData.utillity);
			ppillscore.add(dataset,KIData.Ppillscore);
			ppills_remain.add(dataset, KIData.ppill_remain);
			ppilldistance.add(dataset,KIData.ppilldistance);
			pilldistances.add(dataset,KIData.pilldistance);
			pillscore.add(dataset,KIData.pillscore);
			
						
		
			ghost_k.add(dataset,KIData.ghost_k);
	} catch (Exception e) {
			// TODO: handle exception
			
			e.printStackTrace();
			error=true;
			System.out.println("PLOT EXEPTION WITH DATA");
			System.out.println("UTILITY "+KIData.utillity);
			System.out.println("PPSCORE "+KIData.Ppillscore);
			System.out.println("PP REMAIN "+KIData.ppill_remain);
			System.out.println("Pp distance "+KIData.ppilldistance);
			System.out.println("Pill distance "+KIData.pilldistance);
			System.out.println("Pillscore "+KIData.pillscore);
			System.out.println("Ghostscore "+KIData.ghost_k);
			utility.remove(utility.getItemCount()-1);
			
			ppillscore=new XYSeries("Power Pill Score");
			ppills_remain.remove(ppills_remain.getItemCount()-1);
			ppilldistance.remove(ppilldistance.getItemCount()-1);
			pilldistances.remove(utility.getItemCount()-1);
			pillscore.remove(pillscore.getItemCount()-1);
			ghost_k=new XYSeries("Ghost Near Score");
			
		}
	
		
		
		
		
		dataset++;
	}

	private JFreeChart makedynamic(JFreeChart mod){
		ValueAxis vx=mod.getXYPlot().getDomainAxis();
		vx.setAutoRange(true);
		vx.setFixedAutoRange(1000);
		mod.getXYPlot().getRangeAxis().setAutoRange(true);
		
		return mod;
	}
}
