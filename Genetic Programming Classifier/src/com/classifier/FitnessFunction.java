package com.classifier;

import org.apache.log4j.Logger;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FitnessFunction extends GPFitnessFunction {

	private static final long serialVersionUID = -8141186040852800795L;
	static final Logger logger = Logger.getLogger(FitnessFunction.class);
	public Variable[] vxs;

	public float[][] xs;
	public float[] y;

	public FitnessFunction(Variable[] vxs, float[][] xs, float[] y) {
		super();
		this.vxs = vxs;
		this.xs = xs;
		this.y = y;
	}

	@Override
	protected double evaluate(IGPProgram arg0) {
		double matched = 0;
		Object[] noargs = new Object[0];
		for (int i = 0; i < xs.length; i++) {
			for (int j = 0; j < vxs.length; j++) {
				vxs[j].set(xs[i][j]);
			}
			try {
				double result = arg0.execute_float(0, noargs);
				if (Double.isNaN(result))
					return Double.MAX_VALUE;
				if (result >= 0) {
					result = 1;
				} else {
					result = 2;
				}

				if (result == y[i]) {
					matched++;
				}
			} catch (ArithmeticException ex) {
				logger.error("evaluate():: ArithmeticException ", ex);
				throw ex;
			}
		}
		matched = ((matched / (double) xs.length) * 100);

		return matched;
	}

}
