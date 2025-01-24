package dici.models.stats.methods;

import dici.models.stats.objects.XYCollection;

public interface IMethod {
	public XYCollection getXYCollection();
	public double compute();
}
