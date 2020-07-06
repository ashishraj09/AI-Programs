package com.sr;

import org.apache.log4j.Logger;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FitnessFunction extends GPFitnessFunction {

	static final Logger logger = Logger.getLogger(FitnessFunction.class);
	private static final long serialVersionUID = 7843931891125436125L;
	Variable vx;
	Float[] x;
	Float[] y;

	public FitnessFunction(Variable vx, Float[] x, Float[] y) {
		super();
		this.vx = vx;
		this.x = x;
		this.y = y;
	}

	@Override
	protected double evaluate(final IGPProgram arg0) {
		double error = 0.0;
		try {

			Object[] noArgs = new Object[0];
			for (int i = 0; i < x.length; i++) {
				vx.set(x[i]);
				double result = arg0.execute_float(0, noArgs);
				error += Math.pow(Math.abs(result - y[i]), 2);
				if (Double.isInfinite(error)) {
					return Double.MAX_VALUE;
				}
			}
		} catch (Exception e) {
			logger.error("FitnessFunction() ", e);
		}

		if (error < 0.001) {
			error = 0.0d;
		}
		return error;
	}
}
