package dici.stats.methods;

import dici.stats.objects.XYCollection;

public interface IMethod {
	public XYCollection getXYCollection();
	public double compute();
}
