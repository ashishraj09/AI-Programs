package com.sr;

import org.apache.log4j.Logger;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class GenoType extends GPProblem {

	static final Logger logger = Logger.getLogger(GenoType.class);
	public Variable[] vx;

	public GenoType(GPConfiguration config, Variable[] vx) throws InvalidConfigurationException {
		super(config);
		this.vx = vx;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public GPGenotype create() throws InvalidConfigurationException {

		try {
			GPConfiguration config = getGPConfiguration();
			Class[] types = { CommandGene.IntegerClass };
			Class[][] argTypes = { {}, };
			CommandGene[][] nodeSets = { { vx[0], vx[1], vx[2], new Add(config, CommandGene.IntegerClass),
					new Subtract(config, CommandGene.IntegerClass), new Multiply(config, CommandGene.IntegerClass),
					new Terminal(config, CommandGene.IntegerClass, -1, 1, true), } };

			return GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 10, true);
		} catch (InvalidConfigurationException e) {
			logger.error(e.getLocalizedMessage());
		}
		return null;

	}

}