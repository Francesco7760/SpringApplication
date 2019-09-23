package it.project.SpringApplication.model;

public class Stat {
	private String field;
	private double avg; ;
	private double min;
	private double max;
	private double dev_std;
	private double sum;
	private int count;
	
	public Stat (String field, double avg, double min, double max, double dev_std, double sum, int count) {
		this.field = field;
		this.avg = avg;
		this.min = min;
		this.max = max;
		this.dev_std = dev_std;
		this.sum = sum;
		this.count = count;
	}

	public String getField() {
		return field;
	}

	public void setField(String field) {
		this.field = field;
	}

	public double getAvg() {
		return avg;
	}

	public void setAvg(double avg) {
		this.avg = avg;
	}

	public double getMin() {
		return min;
	}

	public void setMin(double min) {
		this.min = min;
	}

	public double getMax() {
		return max;
	}

	public void setMax(double max) {
		this.max = max;
	}

	public double getDev_std() {
		return dev_std;
	}

	public void setDev_std(double dev_std) {
		this.dev_std = dev_std;
	}

	public double getSum() {
		return sum;
	}

	public void setSum(double sum) {
		this.sum = sum;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

}
