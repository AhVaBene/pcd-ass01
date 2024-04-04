package pcd.ass01.app;

import pcd.ass01.simtraffic.concurrent.base.*;
import pcd.ass01.simtraffic.concurrent.engine.*;
import pcd.ass01.simtraffic.concurrent.utils.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.concurrent.CyclicBarrier;

public class RoadSimView extends JFrame implements SimulationListener {

	private RoadSimViewPanel panel;
	private static final int CAR_DRAW_SIZE = 10;
	
	public RoadSimView(CyclicBarrier barrier, Counter counter) {
		super("RoadSim View");
		setSize(1500,600);
			
		panel = new RoadSimViewPanel(1500,600); 
		panel.setSize(1500, 600);

		JTextField text = new JTextField(10);

		JButton button1 = new JButton("START");
		StartAndStopListener listener = new StartAndStopListener(barrier, counter, text);
		button1.addActionListener(listener);

		JButton button2 = new JButton("STOP");
		button2.addActionListener(listener);


		JPanel cp = new JPanel();
		JPanel cp2 = new JPanel();
		LayoutManager layout = new BorderLayout();
		cp.setLayout(layout);
		cp.add(BorderLayout.CENTER,panel);
		cp2.add(BorderLayout.WEST,button1);
		cp2.add(BorderLayout.WEST,button2);
		cp2.add(BorderLayout.EAST, text);
		cp.add(BorderLayout.SOUTH, cp2);
		setContentPane(cp);		
		
		setDefaultCloseOperation(EXIT_ON_CLOSE);
			
	}
	
	public void display() {
		SwingUtilities.invokeLater(() -> {
			this.setVisible(true);
		});
	}

	@Override
	public void notifyInit(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyStepDone(int t, List<AbstractAgent> agents, AbstractEnvironment env) {
		var e = ((RoadsEnv) env);
		panel.update(e.getRoads(), e.getAgentInfo(), e.getTrafficLights());
	}
	
	
	class RoadSimViewPanel extends JPanel {
		
		List<CarAgentInfo> cars;
		List<Road> roads;
		List<TrafficLight> sems;
		
		public RoadSimViewPanel(int w, int h){
		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);   
	        Graphics2D g2 = (Graphics2D)g;
			g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
			g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
			g2.clearRect(0,0,this.getWidth(),this.getHeight());
			
			if (roads != null) {
				for (var r: roads) {
					g2.drawLine((int)r.getFrom().x(), (int)r.getFrom().y(), (int)r.getTo().x(), (int)r.getTo().y());
				}
			}
			
			if (sems != null) {
				for (var s: sems) {
					if (s.isGreen()) {
						g.setColor(new Color(0, 255, 0, 255));
					} else if (s.isRed()) {
						g.setColor(new Color(255, 0, 0, 255));
					} else {
						g.setColor(new Color(255, 255, 0, 255));
					}
					g2.fillRect((int)(s.getPos().x()-5), (int)(s.getPos().y()-5), 10, 10);
				}
			}
			
			g.setColor(new Color(0, 0, 0, 255));

			if (cars != null) {
				for (var c: cars) {
					double pos = c.getPos();
					Road r = c.getRoad();
					V2d dir = V2d.makeV2d(r.getFrom(), r.getTo()).getNormalized().mul(pos);
					g2.drawOval((int)(r.getFrom().x() + dir.x() - CAR_DRAW_SIZE/2), (int)(r.getFrom().y() + dir.y() - CAR_DRAW_SIZE/2), CAR_DRAW_SIZE , CAR_DRAW_SIZE);
				}
			}
  	   }
	
	   public void update(List<Road> roads, 
			   			  List<CarAgentInfo> cars,
			   			List<TrafficLight> sems) {
		   this.roads = roads;
		   this.cars = cars;
		   this.sems = sems;
		   repaint();
	   }
	}
}
