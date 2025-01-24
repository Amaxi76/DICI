package dici.models.stats.objects;

public class ValuesCollection {
	private String name;
	private double[] ensValue;
	
	public ValuesCollection( String name, double[] ensValue){
		this.name = name;
		this.ensValue = ensValue;
	}

	public String getName(){
		return this.name;
	}

	public int size(){
		return this.ensValue.length;
	}

	public double getValue(int i){
		return this.ensValue[i];
	}

	public double avg(){
		double sum = 0;
		for (int i = 0; i < this.ensValue.length; i++){
			sum += this.ensValue[i];
		}
		return sum / this.ensValue.length;
	}

	public double min(){
		double min = this.ensValue[0];
		for (int i = 1; i < this.ensValue.length; i++){
			if (this.ensValue[i] < min){
				min = this.ensValue[i];
			}
		}
		return min;
	}

	public double max(){
		double max = this.ensValue[0];
		for (int i = 1; i < this.ensValue.length; i++){
			if (this.ensValue[i] > max){
				max = this.ensValue[i];
			}
		}
		return max;
	}
}
