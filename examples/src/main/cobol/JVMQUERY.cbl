       PROCESS XOPTS(APOST)
       PROCESS NOSEQ LIB OPTIMIZE(FULL)
       IDENTIFICATION DIVISION.
       PROGRAM-ID. JVMQUERY.
      *****************************************************************
      * OVERVIEW                                                      *
      * --------                                                      *
      * Sample transaction calling a remote service using LegStar     *
      * c2wsrt C API.                                                 *
      * This program assumes the JVMQuery UMO has been deployed to a  *
      * Mule server along with LegStar dependencies.                  *
      *****************************************************************

       ENVIRONMENT DIVISION.
       CONFIGURATION SECTION.
       DATA DIVISION.
      *****************************************************************
      *        W O R K I N G    S T O R A G E    S E C T I O N        *
      *****************************************************************
       WORKING-STORAGE SECTION.
       
      *---------------------------------------------------------------*
      *  C2WS API parameters                                          *
      *---------------------------------------------------------------*
      * Address of c2ws service provider.
      *    
       77  C2WS-SERVICE-URI            PIC X(22) VALUE
           'http://muleserver:8083'.
      *    
      * C2ws service credentials.
      *    
       77  C2WS-USERID                 PIC X(8) VALUE
           '        '.
       77  C2WS-PASSWORD               PIC X(8) VALUE
           '        '.
      *    
      * Service requested.
      *    
       77  C2WS-SERVICE-NAME           PIC X(12) VALUE
           'MuleJvmquery'.
           
      *---------------------------------------------------------------*
      *  Constants                                                    *
      *---------------------------------------------------------------*
       77  OK-CODE                     PIC S9(8) BINARY VALUE 0.
       77  ERROR-CODE                  PIC S9(8) BINARY VALUE -1.
       77  THIS-TRACE-ID               PIC X(13) VALUE 'JVMQUERY'.
 
      *---------------------------------------------------------------*
      * Structure shared with c2ws C API.                             *
      * C Structures are aligned on natural storage boundaries so we  *
      * need to specify SYNCHRONIZED.                                 *
      * The last character of each string is reserved to hold a       *
      * C string delimiter.                                           *
      *---------------------------------------------------------------*
       01  TRACE-PARMS SYNCHRONIZED.
           05 TRACE-ID                 PIC X(17) VALUE SPACES.
           05 TRACE-MODE               PIC S9(8) BINARY VALUE 1.
              88 TRACES-OFF       VALUE 0.
              88 TRACES-ON        VALUE 1.
           05 ERROR-MESSAGE            PIC X(266) VALUE SPACES.

       01  WS-INVOKE-PARMS SYNCHRONIZED.
           05  WS-URI                  PIC X(513) VALUE SPACES.
           05  WS-SERVICE-NAME         PIC X(33) VALUE SPACES.
           05  WS-REQUEST-DATA         POINTER VALUE NULL.
           05  WS-REQUEST-DATA-LEN     PIC S9(8) BINARY VALUE ZERO.
           05  WS-REPLY-DATA           POINTER VALUE NULL.
           05  WS-REPLY-DATA-LEN       PIC S9(8) BINARY VALUE ZERO.
           05  WS-OPTIONS.
               10  WS-CONNECT-TIMEOUT  PIC 9(9) BINARY VALUE 3.
               10  WS-RECV-TIMEOUT     PIC 9(9) BINARY VALUE 10.
               10  WS-PROXY-URI        PIC X(513) VALUE SPACES.
               10  WS-USERID           PIC X(33) VALUE SPACES.
               10  WS-PASSWORD         PIC X(33) VALUE SPACES.
           
      *---------------------------------------------------------------*
      *  Work variables                                               *
      *---------------------------------------------------------------*
       01  WS-RESP                     PIC S9(8) COMP VALUE ZERO.
       01  WS-RESP2                    PIC S9(8) COMP VALUE ZERO.
       01  WS-RDISP                    PIC +9(8) VALUE ZERO.

      *---------------------------------------------------------------*
      *  Request parameters expected by target web service            *
      *---------------------------------------------------------------*
       01 COM-REQUEST.
           05 JvmQueryRequestType.
               10 envVarNames--C PIC 9(9) BINARY.
               10 envVarNames PIC X(32) OCCURS 0 TO 10 DEPENDING ON
                   envVarNames--C.

       
      *****************************************************************
      *            L I N K A G E       S E C T I O N                  *
      *****************************************************************
       LINKAGE SECTION.

      *---------------------------------------------------------------*
      *  Reply parameters as returned by target web service           *
      *---------------------------------------------------------------*
       01 COM-REPLY.
           05 JvmQueryReplyType.
               10 envVarValues--C PIC 9(9) BINARY.
               10 country PIC X(32).
               10 currencySymbol PIC X(32).
               10 envVarValues PIC X(32) OCCURS 0 TO 10 DEPENDING ON
                   envVarValues--C.
               10 formattedDate PIC X(32).
               10 language PIC X(32).

               
      *****************************************************************
      *    P R O C E D U R E  D I V I S I O N   S E C T I O N         *
      *****************************************************************
       PROCEDURE DIVISION.

           IF TRACES-ON
               DISPLAY
                   'JVMQUERY STARTING ===============================' 
           END-IF.
           
           PERFORM INITIALIZE-C2WS-API THRU
               END-INITIALIZE-C2WS-API.  

           PERFORM SET-REQUEST THRU
               END-SET-REQUEST.

           PERFORM INVOKE-SERVICE THRU
               END-INVOKE-SERVICE.
               
           IF TRACES-ON
               PERFORM PRINT-RESULTS THRU
                   END-PRINT-RESULTS 
           END-IF.
               
           IF TRACES-ON
               DISPLAY
                   'JVMQUERY STOPPING ===============================' 
           END-IF.
           EXEC CICS SEND CONTROL FREEKB END-EXEC. 
           EXEC CICS RETURN END-EXEC.

           GOBACK.
       
      *---------------------------------------------------------------*
      *  Initialize the c2ws API. You can turn traces on and specify  *
      *  a trace identifier.                                          *
      *---------------------------------------------------------------*
       INITIALIZE-C2WS-API.
       
           MOVE THIS-TRACE-ID TO TRACE-ID.
           
           CALL 'init' USING dfheiblk TRACE-PARMS
                       RETURNING WS-RESP.
           IF (WS-RESP NOT = OK-CODE)
               MOVE 'INITIALIZE-C2WS-API failed' TO ERROR-MESSAGE
               DISPLAY ERROR-MESSAGE
               EXEC CICS SEND TEXT FROM(ERROR-MESSAGE) FREEKB END-EXEC 
               EXEC CICS RETURN END-EXEC
           END-IF.
           
       END-INITIALIZE-C2WS-API.   EXIT.
      
      *---------------------------------------------------------------*
      *  Populate the request parameters                              *
      *---------------------------------------------------------------*
       SET-REQUEST.
       
           IF TRACES-ON
               DISPLAY 'START SET-REQUEST' 
           END-IF.
           
      *  Set input values in COM-REQUEST                         *
           MOVE 2 TO envVarNames--C OF COM-REQUEST.
           MOVE 'MULE_HOME' TO envVarNames OF COM-REQUEST(1).
           MOVE 'JAVA_HOME' TO envVarNames OF COM-REQUEST(2).
           
           IF TRACES-ON
               DISPLAY 'SET-REQUEST ENDED' 
           END-IF.
           
       END-SET-REQUEST.   EXIT.
       
      *---------------------------------------------------------------*
      *  Invoke target web service                                    *
      *---------------------------------------------------------------*
       INVOKE-SERVICE.
           IF TRACES-ON
               DISPLAY 'ABOUT TO RUN INVOKE-SERVICE' 
           END-IF.
      *
      * Prepare invoke parameter set
      *    
           MOVE C2WS-SERVICE-URI   TO WS-URI.
           MOVE C2WS-SERVICE-NAME  TO WS-SERVICE-NAME.
           SET WS-REQUEST-DATA     TO ADDRESS OF COM-REQUEST.
           MOVE LENGTH OF COM-REQUEST TO WS-REQUEST-DATA-LEN.
           MOVE C2WS-USERID        TO WS-USERID.
           MOVE C2WS-PASSWORD      TO WS-PASSWORD.
      *
      * Invoke target web service
      *    
           CALL 'invoke' USING WS-INVOKE-PARMS
                         RETURNING WS-RESP.
           IF (WS-RESP NOT = OK-CODE)
               COMPUTE WS-RDISP = WS-RESP
               DISPLAY 'INVOKE-SERVICE failed. Return code=' WS-RDISP
               DISPLAY ERROR-MESSAGE
               EXEC CICS SEND TEXT FROM(ERROR-MESSAGE) FREEKB END-EXEC 
               EXEC CICS RETURN END-EXEC
           END-IF.
           
           SET ADDRESS OF COM-REPLY TO WS-REPLY-DATA.

           IF TRACES-ON
               DISPLAY 'INVOKE-SERVICE SUCCESS' 
           END-IF.
           
       END-INVOKE-SERVICE.   EXIT.
      
      *---------------------------------------------------------------*
      *  Display results returned from target web service             *
      *---------------------------------------------------------------*
       PRINT-RESULTS.
       
      *  Display data returned in  COM-REPLY    *
           STRING 'INVOKE-SERVICE success. Server language is '
                  DELIMITED BY SIZE
                  language OF COM-REPLY
                  DELIMITED BY SPACE
                  INTO ERROR-MESSAGE.
           EXEC CICS SEND TEXT FROM(ERROR-MESSAGE) FREEKB END-EXEC.
           
           DISPLAY 'country=' country OF COM-REPLY.
           DISPLAY 'currencySymbol=' currencySymbol OF COM-REPLY.
           DISPLAY 'formattedDate=' formattedDate OF COM-REPLY.
           DISPLAY 'language=' language OF COM-REPLY.
           DISPLAY 'envVarValues--C=' envVarValues--C OF COM-REPLY.
           DISPLAY 'envVarValues(1)=' envVarValues OF COM-REPLY (1).
           DISPLAY 'envVarValues(2)=' envVarValues OF COM-REPLY (2).
           
       END-PRINT-RESULTS.   EXIT.
       
       END PROGRAM JVMQUERY.
