package it.project.SpringApplication.model;

public class Agenda {
	private String time_period;
	private String ref_area;
	private String indicator;
	private String break_down;
	private String unit_measure;
	private double value;
	
	public Agenda(String time_period, String ref_area, String indicator, String break_down, String unit_measure, double value ) {
		
		this.time_period = time_period;
		this.ref_area = ref_area;
		this.indicator = indicator;
		this.break_down = break_down;
		this.unit_measure = unit_measure;
		this.value = value;
		
	}
	
	public String getTime_period() {
		return time_period;
	}
	public void setTime_period(String time_period) {
		this.time_period = time_period;
	}
	public String getRef_area() {
		return ref_area;
	}
	public void setRef_area(String ref_area) {
		this.ref_area = ref_area;
	}
	public String getIndicator() {
		return indicator;
	}
	public void setIndicator(String indicator) {
		this.indicator = indicator;
	}
	public String getBreak_down() {
		return break_down;
	}
	public void setBreak_down(String break_down) {
		this.break_down = break_down;
	}
	public String getUnit_measure() {
		return unit_measure;
	}
	public void setUnit_measure(String unit_measure) {
		this.unit_measure = unit_measure;
	}
	public double getValue() {
		return value;
	}
	public void setValue(double value) {
		this.value = value;
	}
	

}
