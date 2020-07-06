package com.sr;

import org.apache.log4j.Logger;
import org.jgap.InvalidConfigurationException;
import org.jgap.gp.CommandGene;
import org.jgap.gp.GPProblem;
import org.jgap.gp.function.Add;
import org.jgap.gp.function.Divide;
import org.jgap.gp.function.Multiply;
import org.jgap.gp.function.Subtract;
import org.jgap.gp.impl.GPConfiguration;
import org.jgap.gp.impl.GPGenotype;
import org.jgap.gp.terminal.Terminal;
import org.jgap.gp.terminal.Variable;

public class GenoType extends GPProblem{
	
	static final Logger logger = Logger.getLogger(GenoType.class);
	public  Variable vx;

	public GenoType(GPConfiguration config,Variable vx) throws InvalidConfigurationException {
		super(config);
		this.vx = vx;
	}

	@Override
	@SuppressWarnings("rawtypes")
	public GPGenotype create() throws InvalidConfigurationException {
	
			try {
			GPConfiguration config = getGPConfiguration();			
			Class[] types = {CommandGene.FloatClass};
			Class[][] argTypes = {{},};
			CommandGene[][] nodeSets = { 
					{
								vx ,
								new Add(config, CommandGene.FloatClass),
								new Subtract(config, CommandGene.FloatClass),
								new Multiply(config, CommandGene.FloatClass),
								new Divide(config, CommandGene.FloatClass),
								new Terminal(config, CommandGene.FloatClass, -5, 5, true),
					}
			};
			
			
				return GPGenotype.randomInitialGenotype(config, types, argTypes, nodeSets, 20, true);
			} catch (InvalidConfigurationException e) {
				logger.error(e.getLocalizedMessage());
			}
			return null;

	}
	

}