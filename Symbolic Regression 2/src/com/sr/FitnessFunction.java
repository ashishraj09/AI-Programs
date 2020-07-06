package com.sr;

import org.apache.log4j.Logger;
import org.jgap.gp.GPFitnessFunction;
import org.jgap.gp.IGPProgram;
import org.jgap.gp.terminal.Variable;

public class FitnessFunction extends GPFitnessFunction {

	static final Logger logger = Logger.getLogger(FitnessFunction.class);
	private static final long serialVersionUID = 7843931891125436125L;
	Variable[] vx;
	int[][] x;
	int[] y;

	public FitnessFunction(Variable[] vx, int[][] x, int[] y) {
		super();
		this.vx = vx;
		this.x = x;
		this.y = y;
	}

	@Override
	protected double evaluate(final IGPProgram arg0) {
		int error = 0;
		try {

			Object[] noArgs = new Object[0];
			for (int i = 0; i < x.length; i++) {
				for (int j = 0; j < x[0].length; j++) {
					vx[j].set(x[i][j]);
				}

				int result = arg0.execute_int(0, noArgs);
				error += Math.pow(result - y[i], 2);

			}
		} catch (Exception e) {
			logger.error("FitnessFunction() ", e);
		}

		return error;
	}
}
