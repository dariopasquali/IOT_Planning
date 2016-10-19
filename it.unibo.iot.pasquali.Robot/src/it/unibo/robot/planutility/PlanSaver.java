package it.unibo.robot.planutility;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PlanSaver {
	
	private String filename;
	private PlanExtension fileExtension;

	ArrayList<Plan> plans;
	
	public PlanSaver(String filename, PlanExtension fileExtension)
	{
		this.filename = filename;
		this.fileExtension = fileExtension;
		this.plans = new ArrayList<Plan>(); 
	}	
	
	
	
	public void storePlan()
	{
		String toSave = "";
		for(Plan p : plans)
			toSave += p.getPlan();
		
		File f = new File(filename+"."+fileExtension.getExtension());
		try
		{
			FileOutputStream fout = new FileOutputStream(f);
			fout.write(toSave.getBytes());
			fout.close();
		}
		catch (FileNotFoundException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	public String getPlans()
	{
		String toSave = "";
		for(Plan p : plans)
			toSave += p.getPlan();
		
		return toSave;
	}



	public void addPlan(Plan newPlan) {
		plans.add(newPlan);		
	}



	public String getFileName() {
		return filename;
	}

}
