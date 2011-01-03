/*******************************************************************************
 * Copyright (c) 2009 LegSem.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the GNU Lesser Public License v2.1
 * which accompanies this distribution, and is available at
 * http://www.gnu.org/licenses/old-licenses/gpl-2.0.html
 * 
 * Contributors:
 *     LegSem - initial API and implementation
 ******************************************************************************/
package org.mule.transport.legstar.model;

import java.io.File;
import org.apache.tools.ant.Project;
import org.apache.tools.ant.ProjectHelper;
import org.mule.transport.legstar.gen.AbstractTestTemplate;

import com.legstar.cixs.gen.ant.AbstractCixsGenerator;
import com.legstar.codegen.CodeGenUtil;

/**
 * Adapter Generator ant tests.
 * <p/>
 * Tests the velocity templates used to produce ant scripts which in
 * turn generate artifacts. These templates are usually used by IDEs,
 * they are not needed for a batch usage of the generators.
 *
 */
public abstract class AbstractAntBuildCixsMuleModelTest extends AbstractTestTemplate {

    /** Model expected by template. */
	private AbstractAntBuildCixsMuleModel _antModel;
	
	/** The target directory.*/
	private File _targetDir;
	
	/** Veocity template for ant file generation.*/
	private String _vlcTemplate;

    /** True when references should be created. */
    private static final boolean CREATE_REFERENCES = false;

	public AbstractAntBuildCixsMuleModelTest(
			final AbstractAntBuildCixsMuleModel antModel,
			final String vlcTemplate) {
    	_antModel = antModel;
    	_vlcTemplate = vlcTemplate;
    }
	
	/** {@inheritDoc}*/
    public void setUp() {
        super.setUp();
        _antModel.setMuleHome("${env.MULE3_HOME}");
        setCreateReferences(CREATE_REFERENCES);
    }

    /**
     * Generate the ant script, check it and run it.
     * @param antName the generated and script name
     * @throws Exception if something goes wrong
     */
    public void processAnt(final String antName) throws Exception {
        AbstractCixsGenerator.generateFile("Test generator",
        		_vlcTemplate,
                "antModel",
                _antModel,
                getParameters(),
                _antModel.getTargetAntDir(),
                antName);

        compare(_antModel.getTargetAntDir(), antName,
                _antModel.getCixsMuleComponent().getName());
        runAnt(_antModel.getTargetAntDir(), antName);
    }
    
    /**
     * Submit execution of an ant script.
     * @param antFilesDir where the ant script is
     * @param scriptName the ant script name
     */
    public void runAnt(final File antFilesDir, final String scriptName) {
        Project project = new Project();
        project.init();
        ProjectHelper.getProjectHelper().parse(project, new File(antFilesDir, scriptName));
        project.executeTarget(project.getDefaultTarget());
    }

    /**
     * Common initialization. Segregate output so that various tests
     * do not overwrite each other.
     * @param muleComponent the service
     */
    public void initCixsMuleComponent(final CixsMuleComponent muleComponent) {
        _antModel.setProductLocation("..");
        _antModel.setHostCharset("IBM01147");
        _antModel.setCixsMuleComponent(muleComponent);
        _targetDir = new File("../legstar-mulegen-" + muleComponent.getName());
        _antModel.setJaxbBinDir(new File(_targetDir, "target/classes"));
        CodeGenUtil.checkDirectory(_antModel.getJaxbBinDir(), true);
        _antModel.setTargetSrcDir(new File(_targetDir, "src/main/java"));
        _antModel.setTargetBinDir(new File(_targetDir, "target/classes"));
        _antModel.setTargetDistDir(new File(_targetDir, "target/dist"));
        _antModel.setTargetMuleConfigDir(new File(_targetDir, "src/main/resources"));
        _antModel.setTargetJarDir(new File("${mule.home}/lib/user"));
        _antModel.setTargetAntDir(new File(_targetDir, "ant"));
        CodeGenUtil.checkDirectory(_antModel.getTargetAntDir(), true);
        
        /* This is a dirty trick. This component does not need custom libraries so we
         * use the placeholder to get this generator classes to supersede the ones
         * that come from the LEGSTAR_HOME location. */
        _antModel.setCustBinDir(new File("../legstar-mule-generator/target/classes"));
        
    }

    /**
     * @return the ant generation model
     */
    public AbstractAntBuildCixsMuleModel getAntModel() {
		return _antModel;
	}

	/**
	 * @return the target directory
	 */
	public File getTargetDir() {
		return _targetDir;
	}

}
