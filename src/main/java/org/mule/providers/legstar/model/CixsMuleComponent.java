package org.mule.providers.legstar.model;

import java.util.ArrayList;
import java.util.List;

import org.mule.providers.legstar.gen.util.CixsMuleGenUtil;

/**
 * Describes a Mule-LegStar Component with CICS access capabilities.
 */
public class CixsMuleComponent {
	
	/** Default suffix for class implementation name. */
    private static final String DEFAULT_IMPL_SUFFIX = "Impl";
	
	/** Mule component name. */
	private String mName;

	/** Mule component package name. */
	private String mPackageName;

	/** Mule component interface class name. */
	private String mInterfaceClassName;
	
	/** Mule component implementation class name. */
	private String mImplementationClassName;

	/** The Mule component list of operations. */
	private List < CixsOperation > mCixsOperations =
		new ArrayList < CixsOperation >();

	/**
	 * @return the Mule component name
	 */
	public final String getName() {
		return mName;
	}

	/**
	 * @param name the Mule component name to set
	 */
	public final void setName(final String name) {
		mName = name;
	}

	/**
	 * @return the Mule component package name
	 */
	public final String getPackageName() {
		return mPackageName;
	}

	/**
	 * @param packageName the Mule component package name to set
	 */
	public final void setPackageName(final String packageName) {
		mPackageName = packageName;
	}

	/**
	 * @return the Mule component interface class name
	 */
	public final String getInterfaceClassName() {
		if (mInterfaceClassName == null || mInterfaceClassName.length() == 0) {
			return CixsMuleGenUtil.classNormalize(mName);
		}
		return mInterfaceClassName;
	}

	/**
	 * @param interfaceClassName the Mule component interface class name to set
	 */
	public final void setInterfaceClassName(final String interfaceClassName) {
		mInterfaceClassName = interfaceClassName;
	}

	/**
	 * @return the Mule component implementation class name
	 */
	public final String getImplementationClassName() {
		if (mImplementationClassName == null || mImplementationClassName.length() == 0) {
			if (mName == null) {
				return DEFAULT_IMPL_SUFFIX;
			}
			return CixsMuleGenUtil.classNormalize(mName + DEFAULT_IMPL_SUFFIX);
		}
		return mImplementationClassName;
	}

	/**
	 * @param implementationClassName the Mule component implementation class name to set
	 */
	public final void setImplementationClassName(final String implementationClassName) {
		mImplementationClassName = implementationClassName;
	}

	/**
	 * @return the Mule component list of operations
	 */
	public final List<CixsOperation> getCixsOperations() {
		return mCixsOperations;
	}

	/**
	 * @param cixsOperations the Mule component list of operations to set
	 */
	public final void setCixsOperations(final List<CixsOperation> cixsOperations) {
		mCixsOperations = cixsOperations;
	}

    /**
     * Operations are actually a set of uniquely named operations.
     * @param operation the operation to add
     * @throws CixsModelException if operation is a duplicate
     */
    public final void addCixsOperation(
            final CixsOperation operation) throws CixsModelException {
        /* Check that this operation is not already part of the set */
        if (mCixsOperations.contains(operation)) {
            throw new CixsModelException(
                    "This component already contains this operation");
        }
        
        mCixsOperations.add(operation);
    }
    
}
