package dici.stats.objects;

public class XYCollection {
	private ValuesCollection valuesX;
	private ValuesCollection valuesY;

	public XYCollection(ValuesCollection valuesX, ValuesCollection valuesY){
		this.valuesX = valuesX;
		this.valuesY = valuesY;
	}

	public ValuesCollection getValuesX(){
		return this.valuesX;
	}

	public ValuesCollection getValuesY(){
		return this.valuesY;
	}

	public int size(){
		return this.valuesX.size();
	}

	public double covariance(){
		double sum = 0;
		double avgX = this.valuesX.avg();
		double avgY = this.valuesX.avg();

		for(int i = 0; i < this.size(); i++)
			sum += (this.valuesX.getValue(i) - avgX) * (this.valuesY.getValue(i) - avgY);

		return sum / this.size();
	}
}
