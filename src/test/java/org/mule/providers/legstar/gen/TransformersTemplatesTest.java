package org.mule.providers.legstar.gen;


import org.mule.providers.legstar.model.CixsMuleComponent;

import com.legstar.cixs.gen.model.CixsOperation;
import com.legstar.codegen.CodeGenUtil;


/**
 * Test cases for transformers velocity templates.
 */
public class TransformersTemplatesTest extends AbstractTestTemplate {

    public void testLsfileaeObjectToHostByteArray() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        String operationPackageName = getCixsHelper().getOperationPackageName(
                operation, muleComponent.getPackageName());
        getParameters().put("operationPackageName", operationPackageName);
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        CixsMuleGenerator.generateObjectToHbaTransformer(
                operation, getParameters(), componentClassFilesLocation,
                "DfhcommareaType", "Request");
        String resStr = getSource(
                componentClassFilesLocation,
                operation.getRequestHolderType() + "ToHostByteArray.java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileae;"));
        assertTrue(resStr.contains("public class DfhcommareaTypeToHostByteArray extends AbstractLegStarTransformer {"));
        assertTrue(resStr.contains("public DfhcommareaTypeToHostByteArray() {"));
        assertTrue(resStr.contains("registerSourceType(DfhcommareaType.class);"));
        assertTrue(resStr.contains("DfhcommareaType srcObject = (DfhcommareaType) src;"));
        assertTrue(resStr.contains("legstarMessage.addMessagePart("));
        assertTrue(resStr.contains("new DfhcommareaTypeBinding(srcObject),"));
        assertTrue(resStr.contains("79, getHostCharset(), null);"));
    }

    public void testLsfileacObjectToHostByteArray() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        String operationPackageName = getCixsHelper().getOperationPackageName(
                operation, muleComponent.getPackageName());
        getParameters().put("operationPackageName", operationPackageName);
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        CixsMuleGenerator.generateObjectToHbaTransformer(
                operation, getParameters(), componentClassFilesLocation,
                "LsfileacRequestHolder", "Request");
        String resStr = getSource(
                componentClassFilesLocation,
                operation.getRequestHolderType() + "ToHostByteArray.java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(resStr.contains("public class LsfileacRequestHolderToHostByteArray extends AbstractLegStarTransformer {"));
        assertTrue(resStr.contains("LsfileacRequestHolder srcObject = (LsfileacRequestHolder) src;"));
        assertTrue(resStr.contains("new QueryDataTypeBinding(srcObject.getQueryData()),"));
        assertTrue(resStr.contains("48, getHostCharset(), \"QueryData\");"));
        assertTrue(resStr.contains("new QueryLimitTypeBinding(srcObject.getQueryLimit()),"));
        assertTrue(resStr.contains("10, getHostCharset(), \"QueryLimit\");"));
    }
    
    public void testLsfileaeHostByteArrayToObject() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        String operationPackageName = getCixsHelper().getOperationPackageName(
                operation, muleComponent.getPackageName());
        getParameters().put("operationPackageName", operationPackageName);
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        CixsMuleGenerator.generateHbaToObjectTransformer(
                operation, getParameters(), componentClassFilesLocation,
                "DfhcommareaType", "Request");
        String resStr = getSource(
                componentClassFilesLocation,
                "HostByteArrayTo" + operation.getRequestHolderType() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileae;"));
        assertTrue(resStr.contains("public class HostByteArrayToDfhcommareaType extends AbstractLegStarTransformer {"));
        assertTrue(resStr.contains("public HostByteArrayToDfhcommareaType() {"));
        assertTrue(resStr.contains("setReturnClass(DfhcommareaType.class);"));
        assertTrue(resStr.contains("DfhcommareaTypeBinding dfhcommareaBinding ="));
        assertTrue(resStr.contains("new DfhcommareaTypeBinding();"));
        assertTrue(resStr.contains("dfhcommareaBinding, getHostCharset(), null);"));
        assertTrue(resStr.contains("return dfhcommareaBinding.getDfhcommareaType();"));
    }

    public void testLsfileacHostByteArrayToObject() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        String operationPackageName = getCixsHelper().getOperationPackageName(
                operation, muleComponent.getPackageName());
        getParameters().put("operationPackageName", operationPackageName);
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        CixsMuleGenerator.generateHbaToObjectTransformer(
                operation, getParameters(), componentClassFilesLocation,
                "LsfileacRequestHolder", "Request");
        String resStr = getSource(
                componentClassFilesLocation,
                "HostByteArrayTo" + operation.getRequestHolderType() + ".java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(resStr.contains("public class HostByteArrayToLsfileacRequestHolder extends AbstractLegStarTransformer"));
        assertTrue(resStr.contains("public HostByteArrayToLsfileacRequestHolder() {"));
        assertTrue(resStr.contains("LsfileacRequestHolder holder = new LsfileacRequestHolder();"));
        assertTrue(resStr.contains("QueryDataTypeBinding queryDataBinding ="));
        assertTrue(resStr.contains("new QueryDataTypeBinding();"));
        assertTrue(resStr.contains("queryDataBinding, getHostCharset(), \"QueryData\");"));
        assertTrue(resStr.contains("holder.setQueryData(queryDataBinding.getQueryDataType());"));
        assertTrue(resStr.contains("QueryLimitTypeBinding queryLimitBinding ="));
        assertTrue(resStr.contains("new QueryLimitTypeBinding();"));
        assertTrue(resStr.contains("queryLimitBinding, getHostCharset(), \"QueryLimit\");"));
        assertTrue(resStr.contains("holder.setQueryLimit(queryLimitBinding.getQueryLimitType());"));
    }
 
    public void testLsfileaeObjectToHttpResponse() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileaeMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        String operationPackageName = getCixsHelper().getOperationPackageName(
                operation, muleComponent.getPackageName());
        getParameters().put("operationPackageName", operationPackageName);
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        CixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), componentClassFilesLocation,
                "DfhcommareaType", "Request");
        String resStr = getSource(
                componentClassFilesLocation,
                operation.getRequestHolderType() + "ToHttpResponse.java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileae;"));
        assertTrue(resStr.contains("public class DfhcommareaTypeToHttpResponse"));
        assertTrue(resStr.contains("extends AbstractObjectToHttpResponseTransformer {"));
        assertTrue(resStr.contains("public DfhcommareaTypeToHttpResponse() {"));
        assertTrue(resStr.contains("registerSourceType(DfhcommareaType.class);"));
        assertTrue(resStr.contains("DfhcommareaTypeToHostByteArray xformer ="));
        assertTrue(resStr.contains("new DfhcommareaTypeToHostByteArray();"));
    }

    public void testLsfileacObjectToHttpResponse() throws Exception {
        
        CixsMuleComponent muleComponent = TestCases.getLsfileacMuleComponent();
        CixsOperation operation = muleComponent.getCixsOperations().get(0);
        String operationPackageName = getCixsHelper().getOperationPackageName(
                operation, muleComponent.getPackageName());
        getParameters().put("operationPackageName", operationPackageName);
        
        String componentClassFilesLocation = CodeGenUtil.classFilesLocation(
                GEN_SRC_DIR, muleComponent.getPackageName());
        CixsMuleGenerator.generateObjectToHttpResponseTransformer(
                operation, getParameters(), componentClassFilesLocation,
                "LsfileacRequestHolder", "Request");
        String resStr = getSource(
                componentClassFilesLocation,
                operation.getRequestHolderType() + "ToHttpResponse.java");

        assertTrue(resStr.contains("package "
                + TestCases.LEGS4MULE_PKG_PREFIX + ".lsfileac;"));
        assertTrue(resStr.contains("public class LsfileacRequestHolderToHttpResponse"));
        assertTrue(resStr.contains("extends AbstractObjectToHttpResponseTransformer {"));
        assertTrue(resStr.contains("public LsfileacRequestHolderToHttpResponse() {"));
        assertTrue(resStr.contains("registerSourceType(LsfileacRequestHolder.class);"));
        assertTrue(resStr.contains("LsfileacRequestHolderToHostByteArray xformer ="));
        assertTrue(resStr.contains("new LsfileacRequestHolderToHostByteArray();"));
    }
    
}
