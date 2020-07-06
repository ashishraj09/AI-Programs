package com.classifier;

import org.apache.log4j.Logger;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Add3;
import org.jgap.gp.function.Add4;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Multiply3;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class GenoType extends GPProblem {

	static final Logger logger = Logger.getLogger(GenoType.class);

	public Variable[] vxs;
	public GPConfiguration config;

	public GenoType(GPConfiguration config, Variable[] vxs) throws InvalidConfigurationException {
		super();
		this.vxs = vxs;
		this.config = config;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public GPGenotype create() throws InvalidConfigurationException {
		try {
			Class[] types = { CommandGene.FloatClass };
			Class[][] argTypes = { {} };

			CommandGene[][] nodeSets = { { vxs[0], vxs[1], vxs[2], vxs[3], vxs[4], vxs[5], vxs[6], vxs[7], vxs[8],
					vxs[9], vxs[10], vxs[11], vxs[12], vxs[13], vxs[14], vxs[15], vxs[16], vxs[17], vxs[18], vxs[19],
					vxs[20], vxs[21], vxs[22], vxs[23], vxs[24], vxs[25], vxs[26], vxs[27], vxs[28], vxs[29], vxs[30],
					vxs[31], vxs[32], vxs[33], vxs[34], vxs[35], new Add(config, CommandGene.FloatClass),
					new Add3(config, CommandGene.FloatClass), new Add4(config, CommandGene.FloatClass),
					new Multiply(config, CommandGene.FloatClass), new Multiply3(config, CommandGene.FloatClass),
					new Divide(config, CommandGene.FloatClass), new Subtract(config, CommandGene.FloatClass),
					new Terminal(config, CommandGene.FloatClass, -180, 180, true), } };
			return GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 30, true);
		} catch (InvalidConfigurationException e) {
			logger.error("create() :: InvalidConfigurationException: ", e);
		} catch (Exception e) {
			logger.error("create() :: Exception: ", e);
		}

		return null;
	}

}
